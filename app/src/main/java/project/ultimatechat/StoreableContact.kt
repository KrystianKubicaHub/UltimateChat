package project.ultimatechat

import java.net.URL

class StoreableContact: SendableContact {
    private var messages: MutableList<StoreableMessage> = mutableListOf()
    constructor(id: Int,
                nickName: String,
                dataOfRegistration: Long,
                pathToProfilePicture: String,
                lastActivityTime: Long): super(id, nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime)

    constructor(id: Int,
                nickName: String,
                dataOfRegistration: Long,
                pathToProfilePicture: String,
                lastActivityTime: Long,
                messages: MutableList<StoreableMessage>): super(
        id, nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime){
                    this.messages = messages
                }

    public fun addMessage(message: StoreableMessage){
        messages.add(message)
    }

    override fun toString(): String {
        return "StoreableContact(messages=${messages.size})"
    }

}