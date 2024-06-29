package project.ultimatechat

import java.net.URL

class StoreableContact: SendableContact {
    private val messages: MutableList<StoreableMessage> = mutableListOf()
    constructor(id: Int,
                nickName: String,
                dataOfRegistration: Long,
                pathToProfilePicture: String,
                lastActivityTime: Long): super(id, nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime){}
    public fun addMessage(message: StoreableMessage){
        messages.add(message)
    }

}