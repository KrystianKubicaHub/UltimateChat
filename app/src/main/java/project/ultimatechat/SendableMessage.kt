package project.ultimatechat

open class SendableMessage(
    val sendTime: Long,
    val message: String,
    val senderId: Int,
    val timeOfReceived: Long) {
}