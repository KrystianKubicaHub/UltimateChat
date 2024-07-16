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
    fun addContact(contact: StoreableContact){
        if (contacts.none { it.id == contact.id }) {
            contacts.add(contact)

            // Otwórz bazę danych w trybie zapisu
            val dbHelper = ContactsDatabaseHelper(context, this.id.toString())
            val db = dbHelper.writableDatabase

            // Utwórz wartości dla nowego kontaktu
            val values = ContentValues().apply {
                put("id", contact.id)
                put("nickName", contact.nickName)
                put("dataOfRegistration", contact.dateOfRegistration)
                put("pathToProfilePicture", contact.pathToProfilePicture)
                put("lastActivityTime", contact.lastActivityTime)
            }

            val newRowId = db.insert("contacts", null, values)

            // Zamknij bazę danych
            db.close()

            // Informacyjny log i toast
            if (newRowId != -1L) {
                Log.i("LocalUser", "Contact added to database with ID: $newRowId")
                Toast.makeText(context, "Contact added to database", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("LocalUser", "Error adding contact to database")
                Toast.makeText(context, "Error adding contact to database", Toast.LENGTH_SHORT).show()
            }
        }
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
    fun sendMessage(message: String, receiverId: Int) {
        val newMessageForFirebase = SendableMessage(MyTime.getTime(), message, this.id, 0)
        val newMessageForSQL = StoreableMessage(newMessageForFirebase, true)

        val dbHelper = ContactsDatabaseHelper(context, this.id.toString())
        val db = dbHelper.writableDatabase

        val cursor = db.query(
            "contacts", null, "id = ?", arrayOf(receiverId.toString()), null, null, null
        )
        val contactIndex = contacts.indexOfFirst { it.id == receiverId }


        if (cursor.moveToFirst()){
            Toast.makeText(context, "First condition is true", Toast.LENGTH_LONG).show()
            val nickName = cursor.getString(cursor.getColumnIndexOrThrow("nickName"))
            val dataOfRegistration = cursor.getLong(cursor.getColumnIndexOrThrow("dataOfRegistration"))
            val pathToProfilePicture = cursor.getString(cursor.getColumnIndexOrThrow("pathToProfilePicture"))
            val lastActivityTime = cursor.getLong(cursor.getColumnIndexOrThrow("lastActivityTime"))

            val newContact = StoreableContact(receiverId, nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime)
            newContact.addMessage(newMessageForSQL)
            contacts.add(newContact)


            val values = ContentValues().apply {
                put("sendTime", newMessageForSQL.sendTime)
                put("message", newMessageForSQL.message)
                put("senderId", newMessageForSQL.senderId)
                put("timeOfReceived", newMessageForSQL.timeOfReceived)
                put("belongToLocalUser", if (newMessageForSQL.GetBelongToLocalUser()) 1 else 0)
            }
            db.insert("messages", null, values)
        }else{
            Toast.makeText(context, "First condition is false", Toast.LENGTH_LONG).show()
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