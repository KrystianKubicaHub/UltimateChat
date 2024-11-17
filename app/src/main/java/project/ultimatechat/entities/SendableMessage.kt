package project.ultimatechat.entities

open class SendableMessage(
    val sendTime: Long,
    val message: String,
    val senderId: String,
    val timeOfReceived: Long,
    val receiverId: String) {
    override fun toString(): String {
        return "SendableMessage(sendTime=$sendTime, message='$message', senderId=$senderId, timeOfReceived=$timeOfReceived)"
    }
}