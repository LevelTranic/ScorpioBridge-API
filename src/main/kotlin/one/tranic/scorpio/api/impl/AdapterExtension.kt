package one.tranic.scorpio.api.impl

/**
 * This interface represents an extension for adapters in the Scorpio API.
 * Implementations of this interface must provide functionality for handling messages
 * and managing the adapter's lifecycle.
 *
 * @since 1.1.0
 */
interface AdapterExtension {

    /**
     * Returns a unique identifier for this adapter extension.
     *
     * @since 1.1.0
     * @return the identifier as a String
     */
    fun getIdentifier(): String

    /**
     * Handles a [PlayerMessage] received from the bridge plugin
     *
     * @since 1.1.0
     * @param message the message to be processed
     */
    fun handleMessage(message: PlayerMessage)

    /**
     * Retrieves the current status of the adapter.
     *
     * @since 1.1.0
     * @return true if the adapter is active, false otherwise
     */
    fun getStatus(): Boolean

    /**
     * Shuts down the adapter, releasing any resources and stopping operations.
     *
     * @since 1.1.0
     */
    fun shutdown()
}