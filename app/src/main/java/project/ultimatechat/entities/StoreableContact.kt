package project.ultimatechat.entities

import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StoreableContact(
    id: String,
    nickName: String,
    dataOfRegistration: Long,
    pathToProfilePicture: String,
    lastActivityTime: Long
) : SendableContact(id, nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime) {
    val messages = MutableStateFlow<List<StoreableMessage>>(emptyList())


    public fun addMessage(message: StoreableMessage){
        messages.value = messages.value + message
    }
    public fun noMessages(): Boolean{
        if(messages.value.size == 0){
            return true
        }else{
            return false
        }
    }

    override fun toString(): String {
        return "StoreableContact(messages=${messages.value.size})"
    }

    fun getLastMessage(): String {
        return messages.value.last().message
    }

    fun getLastActivity(): String {
        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        val date = Date(this.lastActivityTime!!)
        return dateFormat.format(date)
    }

}