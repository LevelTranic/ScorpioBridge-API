package one.tranic.scorpio.api

import one.tranic.scorpio.api.impl.AdapterExtension
import one.tranic.scorpio.api.impl.Message
import one.tranic.scorpio.api.impl.PlatformMessage
import one.tranic.scorpio.api.impl.PlayerMessage
import org.slf4j.Logger

/**
 * Manages the lifecycle and communication of adapter extensions in the Scorpio API.
 * This object provides methods to register adapters, send messages, and manage filters.
 *
 * @since 1.0.0
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
     * @since 1.0.0
     * @param logger the Logger instance to be used for logging
     */
    fun setLogger(logger: Logger) {
        this.logger = logger
    }

    /**
     * Sets the method for receiving platform messages.
     * This method should only be called by the main plugin and should not be called by adapters.
     *
     * @since 1.0.0
     * @param expect the function to handle incoming PlatformMessage
     */
    fun setReceivePlatformMessage(expect: (PlatformMessage) -> Unit) {
        receivePlatformMessage = expect
    }

    /**
     * Registers a filter for processing messages.
     *
     * @since 1.0.0
     * @param filter a function that takes a Message and returns a filtered Message or null
     */
    fun registerFilter(filter: (message: Message) -> Message?) {
        this.filter = filter
    }

    /**
     * Registers an adapter extension.
     * If an adapter with the same identifier is already registered, it will be shut down before registering the new one.
     *
     * @since 1.0.0
     * @param adapter the AdapterExtension to be registered
     */
    fun registerAdapter(adapter: AdapterExtension) {
        getAdapterById(adapter.getIdentifier())?.let {
            if (it.getStatus()) it.shutdown()
            registeredAdapters.remove(it)
        }
        registeredAdapters.add(adapter)
    }

    /**
     * Unregisters an adapter extension.
     *
     * @since 1.0.0
     * @param adapter the AdapterExtension to be unregistered
     */
    fun unregisterAdapter(adapter: AdapterExtension) {
        unregisterAdapter(adapter.getIdentifier())
    }

    /**
     * Unregisters an adapter extension.
     *
     * @since 1.0.0
     * @param identifier the Adapter Identifier to be unregistered
     */
    fun unregisterAdapter(identifier: String) {
        getAdapterById(identifier)?.let {
            if (it.getStatus()) it.shutdown()
            registeredAdapters.remove(it)
        }
    }

    /**
     * Clears all registered adapters, shutting them down if they are active.
     *
     * @since 1.0.0
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
     * @since 1.0.0
     * @param message the [PlayerMessage] to be sent to all adapters
     */
    fun sendMessageToAllAdapters(message: PlayerMessage) {
        if (registeredAdapters.isEmpty()) return
        (if (::filter.isInitialized) filter(message) as PlayerMessage? else message)?.let {
            for (adapter in registeredAdapters) {
                adapter.handleMessage(it)
            }
        }
    }

    /**
     * Receives and processes a message from adapters.
     * If the message is a [PlatformMessage], it will be sent to the registered receive method.
     *
     * @since 1.0.0
     * @param message the [PlatformMessage] received from an adapter
     */
    fun receiveAdaptersMessage(message: PlatformMessage) {
        if (!this::receivePlatformMessage.isInitialized) return
        (if (::filter.isInitialized) filter(message) as PlatformMessage? else message)?.let {
            receivePlatformMessage(it)
        }
    }

    /**
     * Retrieves an adapter by its identifier.
     *
     * @since 1.0.0
     * @param id the identifier of the adapter
     * @return the [AdapterExtension] if found, null otherwise
     */
    fun getAdapterById(id: String): AdapterExtension? {
        if (registeredAdapters.isEmpty()) return null
        return registeredAdapters.find { it.getIdentifier() == id }
    }
}