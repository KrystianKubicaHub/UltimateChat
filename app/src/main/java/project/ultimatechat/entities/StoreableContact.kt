package project.ultimatechat.entities

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    public fun noMessages(): Boolean{
        if(messages.size == 0){
            return true
        }else{
            return false
        }
    }

    override fun toString(): String {
        return "StoreableContact(messages=${messages.size})"
    }

    fun getLastMessage(): String {
        return messages.last().message
    }

    fun getLastActivity(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        val date = Date(this.lastActivityTime!!)
        return dateFormat.format(date)
    }

}