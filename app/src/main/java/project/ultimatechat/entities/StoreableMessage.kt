package project.ultimatechat.entities


class StoreableMessage: SendableMessage {
    var belongToLocalUser = true
    constructor(sendTime: Long,
                message: String,
                senderId: String,
                timeOfReceived: Long,
                belongToLocalUser: Boolean,
                receiverId: String) : super(sendTime, message, senderId, timeOfReceived, receiverId){
                    this.belongToLocalUser = belongToLocalUser
                }
    constructor(sendableMessage: SendableMessage) : super(sendableMessage.sendTime,
        sendableMessage.message, sendableMessage.senderId, sendableMessage.timeOfReceived, sendableMessage.receiverId){
    }


    fun GetBelongToLocalUser(): Boolean{
        return  belongToLocalUser
    }
}