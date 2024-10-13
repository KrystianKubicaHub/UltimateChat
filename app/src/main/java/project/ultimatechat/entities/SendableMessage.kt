package project.ultimatechat.entities

open class SendableMessage(
    val sendTime: Long,
    val message: String,
    val senderId: Int,
    val timeOfReceived: Long) {
    override fun toString(): String {
        return "SendableMessage(sendTime=$sendTime, message='$message', senderId=$senderId, timeOfReceived=$timeOfReceived)"
    }
}