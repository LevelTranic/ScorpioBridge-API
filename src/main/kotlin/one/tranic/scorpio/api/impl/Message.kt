package one.tranic.scorpio.api.impl

/**
 * Represents a message in the Scorpio API.
 * This interface defines the essential properties of a message,
 * including sender details and content.
 *
 * @since 1.0.0
 */
interface Message {
    /**
     * The unique identifier of the message sender.
     *
     * @since 1.0.0
     */
    val senderId: String

    /**
     * The name of the message sender.
     *
     * @since 1.0.0
     */
    val senderName: String

    /**
     * The content of the message.
     *
     * @since 1.0.0
     */
    val content: String

    /**
     * The platform from which the message was sent.
     *
     * @since 1.0.0
     */
    val platform: String
}

/**
 * Represents a message sent by a player in the game.
 *
 * @since 1.0.0
 * @param senderId the unique identifier of the player
 * @param senderName the name of the player
 * @param content the content of the message
 */
class PlayerMessage(
    override val senderId: String,
    override val senderName: String,
    override val content: String
) : Message {
    override val platform: String = "Minecraft"
}

/**
 * Represents a message sent from an external platform.
 *
 * @since 1.0.0
 * @param senderId the unique identifier of the message sender
 * @param senderName the name of the message sender
 * @param content the content of the message
 * @param platformName the name of the platform from which the message was sent
 */
class PlatformMessage(
    override val senderId: String,
    override val senderName: String,
    override val content: String,
    private val platformName: String
) : Message {
    override val platform: String = platformName
}
