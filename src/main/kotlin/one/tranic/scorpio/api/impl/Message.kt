package one.tranic.scorpio.api.impl

/**
 * Represents a message in the Scorpio API.
 * This interface defines the essential properties of a message,
 * including sender details and content.
 */
interface Message {
    /**
     * The unique identifier of the message sender.
     */
    val senderId: String

    /**
     * The name of the message sender.
     */
    val senderName: String

    /**
     * The content of the message.
     */
    val content: String

    /**
     * The platform from which the message was sent.
     */
    val platform: String
}

/**
 * Represents a message sent by a player in the game.
 *
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
 * @param senderId the unique identifier of the message sender
 * @param senderName the name of the message sender
 * @param content the content of the message
 * @param platformName the name of the platform from which the message was sent
 */
class PlatformMessage(
    override val senderId: String,
    override val senderName: String,
    override val content: String,
    val platformName: String
) : Message {
    override val platform: String = platformName
}
