package project.ultimatechat

import java.net.URL

class LocalUser: SendableContact {
    private val contacts : MutableList<StoreableContact> = mutableListOf()
    private var currentNumberOfMessages = 0

    constructor(
        id: Int,
        nickName: String,
        dataOfRegistration: Long,
        pathToProfilePicture: URL,
        lastActivityTime: Long
    ) : super(id, nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime)

    private fun getMessageFromFireBase(){
        val path = this.pathToProfilePicture
        val currentNumberOfMessages = this.currentNumberOfMessages
    }
    public fun sendMessage(message: String, path: String){
        val newMessage = SendableMessage(MyTime.getTime(), message, this.id, 0)
    }
    public fun getMessages(beginIndex: Int, numberOfMessages: Int, contactId: Int): MutableList<StoreableMessage> {
        val message = SendableMessage(MyTime.getTime(), "this is a message", this.id, 1230)
        val messages: MutableList<StoreableMessage> = mutableListOf()
        return messages
    }
    public fun addContact(contact : StoreableContact){
        contacts.add(contact)
    }
}