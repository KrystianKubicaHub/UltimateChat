package project.ultimatechat

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast

class LocalUser: SendableContact {
    private val contacts : MutableList<StoreableContact> = mutableListOf()
    private var currentNumberOfMessages = 0
    private var context : Context

    constructor(
        id: Int,
        nickName: String,
        dataOfRegistration: Long,
        pathToProfilePicture: String,
        lastActivityTime: Long,
        context: Context
    ) : super(id, nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime){
        this.context = context
        loadAllContactsFromSQL(context)
    }

    private fun loadAllContactsFromSQL(context: Context){
        val dbHelper = ContactsDatabaseHelper(context, this.id.toString())
        val db = dbHelper.readableDatabase

        val contactsList = mutableListOf<StoreableContact>()

        val cursor = db.query("contacts", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nickName = cursor.getString(cursor.getColumnIndexOrThrow("nickName"))
                val dataOfRegistration = cursor.getLong(cursor.getColumnIndexOrThrow("dataOfRegistration"))
                val pathToProfilePicture = cursor.getString(cursor.getColumnIndexOrThrow("pathToProfilePicture"))
                val lastActivityTime = cursor.getLong(cursor.getColumnIndexOrThrow("lastActivityTime"))

                val contact = StoreableContact(id, nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime)
                contactsList.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        Toast.makeText(context, contactsList.size.toString(), Toast.LENGTH_LONG).show()
        contacts.addAll(contactsList)
    }

    private fun getMessageFromFireBase(){
        val path = this.pathToProfilePicture
        val currentNumberOfMessages = this.currentNumberOfMessages
    }
    public fun sendMessage(message: String, receiverId: Int) {
        val newMessageForFirebase = SendableMessage(MyTime.getTime(), message, this.id, 0)
        val newMessageForSQL = StoreableMessage(newMessageForFirebase, true)

        val dbHelper = ContactsDatabaseHelper(context, this.id.toString())
        val db = dbHelper.writableDatabase

        val cursor = db.query(
            "contacts", null, "id = ?", arrayOf(receiverId.toString()), null, null, null
        )
        if (cursor.moveToFirst()) {
            val contactIndex = contacts.indexOfFirst { it.id == receiverId }
            if (contactIndex != -1) {
                contacts[contactIndex].addMessage(newMessageForSQL)
            } else {

                val nickName = cursor.getString(cursor.getColumnIndexOrThrow("nickName"))
                val dataOfRegistration = cursor.getLong(cursor.getColumnIndexOrThrow("dataOfRegistration"))
                val pathToProfilePicture = cursor.getString(cursor.getColumnIndexOrThrow("pathToProfilePicture"))
                val lastActivityTime = cursor.getLong(cursor.getColumnIndexOrThrow("lastActivityTime"))

                val newContact = StoreableContact(receiverId, nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime)
                newContact.addMessage(newMessageForSQL)
                contacts.add(newContact)
            }

            val values = ContentValues().apply {
                put("sendTime", newMessageForSQL.sendTime)
                put("message", newMessageForSQL.message)
                put("senderId", newMessageForSQL.senderId)
                put("timeOfReceived", newMessageForSQL.timeOfReceived)
                put("belongToLocalUser", if (newMessageForSQL.GetBelongToLocalUser()) 1 else 0)
            }
            db.insert("messages", null, values)
        }
        cursor.close()
        db.close()
    }

    fun getAllContacts(): List<StoreableContact>{
        return contacts
    }
    public fun getMessages(beginIndex: Int, numberOfMessages: Int, contactId: Int): MutableList<StoreableMessage> {
        val message = SendableMessage(MyTime.getTime(), "this is a message", this.id, 1230)
        val messages: MutableList<StoreableMessage> = mutableListOf()
        return messages
    }
}