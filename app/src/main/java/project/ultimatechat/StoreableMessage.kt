package project.ultimatechat

import kotlin.properties.Delegates

class StoreableMessage: SendableMessage {
    private var belongToLocalUser by Delegates.notNull<Boolean>();
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


    public fun GetBelongToLocalUser(): Boolean{
        return  belongToLocalUser
    }
}