package project.ultimatechat.entities

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.core.text.isDigitsOnly
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import project.ultimatechat.SQL.ContactsDatabaseHelper
import project.ultimatechat.other.MyTime


class LocalUser: SendableContact {
    private val contacts : MutableList<StoreableContact> = mutableListOf()
    private var currentNumberOfMessages = 0
    private lateinit var context : Context
    public lateinit var temporaryListOfMessages : MutableState<List<StoreableMessage>>

    constructor(
        id: String,
        nickName: String,
        dataOfRegistration: Long,
        pathToProfilePicture: String,
        lastActivityTime: Long,
        context: Context,
        emptyMutableMessageList: MutableState<List<StoreableMessage>>
    ) : super(id, nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime){
        this.context = context
        loadAllContactsFromSQL(context)
        loadAllMessagesFromSQL(context)

        temporaryListOfMessages = emptyMutableMessageList
        getMessagesFromFB(currentNumberOfMessages + 1)
    }
    constructor(
        id: String,
        nickName: String,
        dataOfRegistration: Long,
        pathToProfilePicture: String,
        lastActivityTime: Long,
    ) : super(id, nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime){
    }

    private fun loadAllMessagesFromSQL(context: Context) {
        currentNumberOfMessages = 0
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
                //Toast.makeText(context, "Contact added to database", Toast.LENGTH_SHORT).show()
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

                val contact = StoreableContact(id.toString(), nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime)
                contactsList.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        //Toast.makeText(context, "Contact list size is " + contactsList.size.toString(), Toast.LENGTH_LONG).show()
        contacts.addAll(contactsList)
    }

    private fun getMessageFromFireBase(){
        val path = this.pathToProfilePicture
        val currentNumberOfMessages = this.currentNumberOfMessages
    }

    fun sendMessageSecondApproach(message: String, receiverId: String) {
        val wrappedMessage = SendableMessage(MyTime.getTime(), message, this.id, 0)
        val database = Firebase.database("https://ultimatechat-51396-default-rtdb.europe-west1.firebasedatabase.app")
        val foreignUserPath = database.getReference("messages").child(receiverId)
        val indexPath = foreignUserPath.child("index")

        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               // Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show()

                foreignUserPath.child("index").removeEventListener(this)

                var messageIndexOfForeign = 0

                val value = snapshot.value.toString()
                if (value.isNullOrEmpty() || !value.isDigitsOnly()) {
                    messageIndexOfForeign = 1
                } else {
                    messageIndexOfForeign = value.toInt() + 1
                }

                val newRef = foreignUserPath.child(messageIndexOfForeign.toString())

                newRef.setValue(wrappedMessage)

                    .addOnSuccessListener {
                        indexPath.setValue(messageIndexOfForeign.toString())
                            .addOnSuccessListener {
                                //Toast.makeText(context, "Message has been sent", Toast.LENGTH_SHORT).show()
                                //Toast.makeText(context, "Index set to $messageIndexOfForeign", Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener { error ->
                        Toast.makeText(context, "Failed to send a message", Toast.LENGTH_LONG).show()
                    }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Sending a message impossible due to complicated reasons", Toast.LENGTH_LONG).show()
            }
        }

        // Add the event listener
        foreignUserPath.child("index").addValueEventListener(valueEventListener)
    }

    private fun getMessagesFromFB(index: Int) {
        val database = Firebase.database("https://ultimatechat-51396-default-rtdb.europe-west1.firebasedatabase.app")
        val myRef = database.getReference("messages").child(this.id).child(index.toString())

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val v = snapshot.value.toString().removeSurrounding("{","}")
                val ele = v.split(",")

                var senderID = "-1"
                var message = ""
                var timeOfRecived : Long= -1L
                var sendTime : Long = -1L
                for(i in ele){
                    i.removeSurrounding(" ")
                    val keyAndValue = i.split("=")
                    val key = keyAndValue.first().uppercase().removePrefix(" ")
                    val value = keyAndValue.last().uppercase()



                        //Toast.makeText(context, key, Toast.LENGTH_LONG).show()


                    Log.e("PPP", keyAndValue.first().toString())
                    if(key == "SENDERID"){
                        senderID = value
                    }else if(key == "MESSAGE"){
                        message = value
                    }else if(key == "SENDTIME"){
                        sendTime = value.toLong()
                    }else if(key == "TIMEOFRECEIVED"){
                        timeOfRecived = value.toLong()
                    }else{
                        //Toast.makeText(context, i, Toast.LENGTH_LONG).show()
                        //Toast.makeText(context, key.first().toString(), Toast.LENGTH_LONG).show()

                    }
                }
                //Toast.makeText(context, recievedMessage.toString(), Toast.LENGTH_LONG).show()
                if(sendTime != -1L && message != "" && senderID != "-1" && timeOfRecived != -1L){
                    val recievedMessage = StoreableMessage(sendTime, message, senderID, timeOfRecived, false)
                    currentNumberOfMessages++
                    temporaryListOfMessages.value = temporaryListOfMessages.value + recievedMessage
                }


                if(snapshot.value != null){
                    myRef.removeEventListener(this)
                    getMessagesFromFB(index + 1)
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load messages: ${error.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    fun sendMessage(message: String, receiverId: Int) {
        val newMessageForFirebase = SendableMessage(MyTime.getTime(), message, this.id, 0)
        val newMessageForSQL = StoreableMessage(newMessageForFirebase, true)

        val dbHelper = ContactsDatabaseHelper(context, this.id.toString())
        val db = dbHelper.writableDatabase

        val cursor = db.query(
            "contacts", null, "id = ?", arrayOf(receiverId.toString()), null, null, null
        )


        if (cursor.moveToFirst()){
            //Toast.makeText(context, "First condition is true", Toast.LENGTH_LONG).show()
            val nickName = cursor.getString(cursor.getColumnIndexOrThrow("nickName"))
            val dataOfRegistration = cursor.getLong(cursor.getColumnIndexOrThrow("dataOfRegistration"))
            val pathToProfilePicture = cursor.getString(cursor.getColumnIndexOrThrow("pathToProfilePicture"))
            val lastActivityTime = cursor.getLong(cursor.getColumnIndexOrThrow("lastActivityTime"))

            val newContact = StoreableContact(receiverId.toString(), nickName, dataOfRegistration, pathToProfilePicture, lastActivityTime)
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
            //Toast.makeText(context, "First condition is false", Toast.LENGTH_LONG).show()
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