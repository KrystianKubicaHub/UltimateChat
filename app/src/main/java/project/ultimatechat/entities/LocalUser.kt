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
        loadAllMessagesFromSQL(context)

        temporaryListOfMessages = emptyMutableMessageList
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

    private fun sendMessageWithId(id: String, ){

    }

    fun sendMessageSecondApproach(wrappedMessage: SendableMessage, receiverId: String) {
        val database = Firebase.database("https://ultimatechat-51396-default-rtdb.europe-west1.firebasedatabase.app")
        val foreignUserPath = database.getReference("messages").child(receiverId)
        val indexPath = foreignUserPath.child("index")

        val indexEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                foreignUserPath.child("index").removeEventListener(this)

                var messageIndexOfForeign = 0

                val value = snapshot.value.toString()
                if (value.isEmpty() || !value.isDigitsOnly()) {
                    messageIndexOfForeign = 1
                } else {
                    messageIndexOfForeign = value.toInt() + 1
                }
                // var messageIndexOfForeign = snapshot.value.toString().toIntOrNull()?.plus(1) ?: 1

                val refForForeignUserMessage = foreignUserPath.child(messageIndexOfForeign.toString())

                refForForeignUserMessage.setValue(wrappedMessage)
                    .addOnSuccessListener {
                        indexPath.setValue(messageIndexOfForeign.toString())
                            .addOnSuccessListener {}
                    }
                    .addOnFailureListener { error ->
                        Toast.makeText(context, "Failed to send a message \n $error", Toast.LENGTH_LONG).show()
                    }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Sending a message impossible due to complicated reasons", Toast.LENGTH_LONG).show()
            }
        }
        foreignUserPath.child("index").addValueEventListener(indexEventListener)
    }



    /*
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
                var receiverId = ""
                for(i in ele){
                    i.removeSurrounding(" ")
                    val keyAndValue = i.split("=")
                    val key = keyAndValue.first().uppercase().removePrefix(" ")
                    val value = keyAndValue.last().uppercase()


                    Log.e("PPP", keyAndValue.first().toString())
                    if(key == "SENDERID"){
                        senderID = value
                    }else if(key == "MESSAGE"){
                        message = value
                    }else if(key == "SENDTIME"){
                        sendTime = value.toLong()
                    }else if(key == "TIMEOFRECEIVED"){
                        timeOfRecived = value.toLong()
                    }else if(key == "RECEIVERID"){
                        receiverId = value
                    }else{
                        //Toast.makeText(context, i, Toast.LENGTH_LONG).show()
                        //Toast.makeText(context, key.first().toString(), Toast.LENGTH_LONG).show()

                    }
                }
                //Toast.makeText(context, recievedMessage.toString(), Toast.LENGTH_LONG).show()
                if(sendTime != -1L && message != "" && senderID != "-1" && timeOfRecived != -1L){
                    val belongToLocalUser = (senderID == this@LocalUser.id)
                    val recievedMessage = StoreableMessage(sendTime, message, senderID, timeOfRecived, belongToLocalUser, receiverId)
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

    fun sendMessage(message: String, receiverId: Int) {
        val newMessageForFirebase = SendableMessage(MyTime.getTime(), message, this.id, 0)
        val newMessageForSQL = StoreableMessage(newMessageForFirebase)

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

     */
}