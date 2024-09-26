package one.tranic.scorpio.api

import one.tranic.scorpio.api.impl.Message
import one.tranic.scorpio.api.impl.PlatformMessage
import org.slf4j.Logger

/**
 * Manages the lifecycle and communication of adapter extensions in the Scorpio API.
 * This object provides methods to register adapters, send messages, and manage filters.
 */
object AdapterManager {
    private val registeredAdapters = mutableListOf<AdapterExtension>()
    private lateinit var receivePlatformMessage: (PlatformMessage) -> Unit
    private lateinit var filter: (message: Message) -> Message?
    private lateinit var logger: Logger

    /**
     * Sets the logger for the AdapterManager.
     * Adapters should not call this method.
     *
     * @param logger the Logger instance to be used for logging
     */
    fun setLogger(logger: Logger) {
        this.logger = logger
    }

    /**
     * Sets the method for receiving platform messages.
     * This method should only be called by the main plugin and should not be called by adapters.
     *
     * @param expect the function to handle incoming PlatformMessage
     */
    fun setReceivePlatformMessage(expect: (PlatformMessage) -> Unit) {
        receivePlatformMessage = expect
    }

    /**
     * Registers a filter for processing messages.
     *
     * @param filter a function that takes a Message and returns a filtered Message or null
     */
    fun registerFilter(filter: (message: Message) -> Message?) {
        this.filter = filter
    }

    /**
     * Registers an adapter extension.
     * If an adapter with the same identifier is already registered, it will be shut down before registering the new one.
     *
     * @param adapter the AdapterExtension to be registered
     */
    fun registerAdapter(adapter: AdapterExtension) {
        if (registeredAdapters.isNotEmpty()) {
            registeredAdapters.forEach {
                if (it.getIdentifier() == adapter.getIdentifier()) {
                    if (it.getStatus()) it.shutdown()
                    registeredAdapters.remove(it)
                    return@forEach
                }
            }
        }
        registeredAdapters.add(adapter)
    }

    /**
     * Unregisters an adapter extension.
     *
     * @param adapter the AdapterExtension to be unregistered
     */
    fun unregisterAdapter(adapter: AdapterExtension) {
        unregisterAdapter(adapter.getIdentifier())
    }

    /**
     * Unregisters an adapter extension.
     *
     * @param identifier the Adapter Identifier to be unregistered
     */
    fun unregisterAdapter(identifier: String) {
        for (adapter in registeredAdapters) {
            if (adapter.getIdentifier() == identifier) {
                if (adapter.getStatus()) adapter.shutdown()
                registeredAdapters.remove(adapter)
                break
            }
        }
    }

    /**
     * Clears all registered adapters, shutting them down if they are active.
     */
    fun clearAllAdapters() {
        if (registeredAdapters.isEmpty()) return
        for (adapter in registeredAdapters) {
            if (adapter.getStatus()) adapter.shutdown()
        }
        registeredAdapters.clear()
    }

    /**
     * Sends a message to all registered adapters.
     * If a filter is registered, the message will be processed by it before being sent.
     *
     * @param message the Message to be sent to all adapters
     */
    fun sendMessageToAllAdapters(message: Message) {
        val msg = if (::filter.isInitialized) filter(message) else message
        if (msg == null) return
        for (adapter in registeredAdapters) {
            adapter.handleMessage(msg)
        }
    }

    /**
     * Receives and processes a message from adapters.
     * If the message is a PlatformMessage, it will be sent to the registered receive method.
     *
     * @param message the Message received from an adapter
     */
    fun receiveAdaptersMessage(message: Message) {
        if (message is PlatformMessage) {
            if (!this::receivePlatformMessage.isInitialized) return
            val msg: PlatformMessage? = if (::filter.isInitialized) filter(message) as PlatformMessage? else message
            if (msg == null) return
            receivePlatformMessage(msg)
        }
    }

    /**
     * Retrieves an adapter by its identifier.
     *
     * @param id the identifier of the adapter
     * @return the AdapterExtension if found, null otherwise
     */
    fun getAdapterById(id: String): AdapterExtension? {
        return registeredAdapters.find { it.getIdentifier() == id }
    }
}
