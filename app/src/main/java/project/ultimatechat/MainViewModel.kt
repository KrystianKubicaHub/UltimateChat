package project.ultimatechat

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import project.ultimatechat.entities.LocalUser
import project.ultimatechat.entities.SendableContact
import project.ultimatechat.entities.SendableMessage
import project.ultimatechat.entities.StoreableContact
import project.ultimatechat.entities.StoreableMessage
import project.ultimatechat.entities.UserLibEntity
import project.ultimatechat.other.MyTime

class MainViewModel : ViewModel() {

    var textToToast = "1"

    private val _users = MutableStateFlow<List<StoreableContact>>(emptyList())
    val users: StateFlow<List<StoreableContact>> = _users


    private val currentUser = Firebase.auth.currentUser
    private lateinit var localUser: LocalUser
    private val _usersLibrary =  MutableStateFlow<MutableList<UserLibEntity>>(mutableListOf())
    val usersLibrary: StateFlow<MutableList<UserLibEntity>> get() = _usersLibrary

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _currentChatMate = MutableStateFlow(StoreableContact("-1",
        "Example707",
        0L,
        "",
        0L))
    val currentChatMate: StateFlow<StoreableContact> get() = _currentChatMate

    val filteredUsers = _searchQuery.map { query ->
        _usersLibrary.value.filter { user ->
            user.name.contains(query, ignoreCase = true)
        }
    }
    init{
        if(currentUser != null){
            setLocalUser()
            fetchUsersLibrary()
            fetchLocalUserMessages()
        }
    }

    private fun setLocalUser() {
        if(currentUser != null){
            localUser = LocalUser(
                currentUser.uid,
                currentUser.displayName.toString(),
                0L,
                "",
                0L,
            )
        }
    }
    fun setCurrentChatMate(user: StoreableContact){
        _currentChatMate.value = user
    }

    fun setCurrentChatMate(userLibEntity: UserLibEntity, function: () -> Unit){
        val user = findUserById(userLibEntity.uid)
        if(user != null){
            _currentChatMate.value = user
        }else{
            getUserById(userLibEntity.uid) {
                if (it == null) {
                    textToToast = "Brak kompatyfilności wstecznej, użytkownik o tym id nie ma w bazie danych swojej reprezentacji"
                }else{
                    _currentChatMate.value = it
                    function.invoke()
                }
            }
        }

    }
    fun updateSearchQuery(query: String, context: Context) {
        _searchQuery.value = query
    }



    private fun fetchLocalUserMessages() {
        getMessagesFromFB(1)
    }
    private fun getMessagesFromFB(index: Int) {
        val database = Firebase.database("https://ultimatechat-51396-default-rtdb.europe-west1.firebasedatabase.app")
        val myRef = database.getReference("messages").child(currentUser!!.uid).child(index.toString())

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataString = snapshot.value?.toString()?.removeSurrounding("{", "}") ?: ""
                val elements = dataString.split(",")

                var senderID = "-1"
                var message = ""
                var timeOfReceived: Long = -1L
                var sendTime: Long = -1L
                var receiverID: String = ""

                for (element in elements) {
                    val keyAndValue = element.split("=")
                    val key = keyAndValue.first().uppercase().trim()
                    val value = keyAndValue.last().trim()

                    when (key) {
                        "SENDERID" -> senderID = value
                        "MESSAGE" -> message = value
                        "SENDTIME" -> sendTime = value.toLongOrNull() ?: -1L
                        "TIMEOFRECEIVED" -> timeOfReceived = value.toLongOrNull() ?: -1L
                        "RECEIVERID" -> receiverID = value
                    }
                }

                if (sendTime != -1L && message.isNotEmpty() && senderID != "-1" && timeOfReceived != -1L) {
                    val belongToLocalUser = if(senderID == localUser.id) true else false
                    val receivedMessage = StoreableMessage(sendTime, message, senderID, timeOfReceived, belongToLocalUser, receiverID)

                    if(belongToLocalUser){
                        val user = findUserById(receiverID)
                        if(user == null){
                            getUserById(receiverID) {
                                it?.addMessage(receivedMessage) ?: run {
                                    Log.e("MainViewModel", "User not found: unable to add message.")
                                }
                            }
                        }else{
                            user.addMessage(receivedMessage)
                        }
                    }else{
                        val user = findUserById(senderID)
                        if(user == null){
                            getUserById(senderID) {
                                it?.addMessage(receivedMessage) ?: run {
                                    Log.e("MainViewModel", "User not found: unable to add message.")
                                }
                            }
                        }else{
                            user.addMessage(receivedMessage)
                        }
                    }


                }

                if (snapshot.value != null) {
                    myRef.removeEventListener(this)
                    getMessagesFromFB(index + 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainViewModel", "Failed to load messages: ${error.message}")
            }
        })
    }

    fun Toast(context: Context, len: Int = Toast.LENGTH_LONG){
        textToToast = users.value.toString()
        Toast.makeText(context, textToToast, len).show()
    }

    fun getUserById(userId: String, onUserFetched: (StoreableContact?) -> Unit) {
        val database = Firebase.database("https://ultimatechat-51396-default-rtdb.europe-west1.firebasedatabase.app")
        val userRef = database.getReference("usersList").child(userId)

        userRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val sendableContact = snapshot.getValue(SendableContact::class.java)
                if (sendableContact != null) {

                    val storeableContact = StoreableContact(
                        id = sendableContact.id,
                        nickName = sendableContact.nickName!!,
                        dataOfRegistration = sendableContact.dateOfRegistration!!,
                        pathToProfilePicture = sendableContact.pathToProfilePicture!!,
                        lastActivityTime = sendableContact.lastActivityTime!!,
                    )
                    _users.value = _users.value + storeableContact
                    onUserFetched(storeableContact)
                } else {
                    onUserFetched(null)
                }
            } else {
                onUserFetched(null)
            }
        }.addOnFailureListener {
            println("Failed to fetch user: ${it.message}")
            onUserFetched(null)
        }
    }


    fun sendMessage(message: String){
        val wrappedMessage = SendableMessage(MyTime.getTime(), message, localUser.id, 0, currentChatMate.value.id)
        localUser.sendMessageSecondApproach(wrappedMessage, currentChatMate.value.id)
        localUser.sendMessageSecondApproach(wrappedMessage, localUser.id)
    }

    private fun fetchUsersLibrary() {
        val database = Firebase.database("https://ultimatechat-51396-default-rtdb.europe-west1.firebasedatabase.app")
        val myRef = database.getReference("usersRoster")

        myRef.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if((!snapshot.key.isNullOrEmpty()) && (!snapshot.value.toString().isNullOrEmpty())){
                    val newLibEntity = UserLibEntity(snapshot.key.toString(), snapshot.value.toString())
                    _usersLibrary.value.add(newLibEntity)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    fun findUserById(userId: String): StoreableContact? {
        if(_users.value.size == 1) {
            textToToast = "$userId\n${_users.value.get(0)}"
        }
        return _users.value.find { it.id == userId }
    }


}