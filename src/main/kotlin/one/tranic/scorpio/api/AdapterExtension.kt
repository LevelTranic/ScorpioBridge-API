package one.tranic.scorpio.api

import one.tranic.scorpio.api.impl.Message

/**
 * This interface represents an extension for adapters in the Scorpio API.
 * Implementations of this interface must provide functionality for handling messages
 * and managing the adapter's lifecycle.
 */
interface AdapterExtension {

    /**
     * Returns a unique identifier for this adapter extension.
     *
     * @return the identifier as a String
     */
    fun getIdentifier(): String

    /**
     * Handles a message received by the adapter.
     *
     * @param message the message to be processed
     */
    fun handleMessage(message: Message)

    /**
     * Retrieves the current status of the adapter.
     *
     * @return true if the adapter is active, false otherwise
     */
    fun getStatus(): Boolean

    /**
     * Shuts down the adapter, releasing any resources and stopping operations.
     */
    fun shutdown()
}
