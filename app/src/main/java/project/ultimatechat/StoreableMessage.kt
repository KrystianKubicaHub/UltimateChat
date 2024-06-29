package project.ultimatechat

class StoreableMessage: SendableMessage {
    private var belongToLocalUser: Boolean? = null;
    constructor(sendTime: Long,
                message: String,
                senderId: Int,
                timeOfReceived: Long,
                belongToLocalUser: Boolean) : super(sendTime, message, senderId, timeOfReceived){
                    this.belongToLocalUser = belongToLocalUser
                }
    constructor(sendableMessage: SendableMessage, belongToLocalUser: Boolean) : super(sendableMessage.sendTime,
        sendableMessage.message, sendableMessage.senderId, sendableMessage.timeOfReceived){
        this.belongToLocalUser = belongToLocalUser

    }
}