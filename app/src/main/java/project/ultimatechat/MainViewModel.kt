package project.ultimatechat

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import project.ultimatechat.entities.UserLibEntity

class MainViewModel : ViewModel() {
    private val _usersLibrary =  MutableStateFlow<MutableList<UserLibEntity>>(mutableListOf())
    val usersLibrary: StateFlow<MutableList<UserLibEntity>> get() = _usersLibrary

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    val filteredUsers = _searchQuery.map { query ->
        _usersLibrary.value.filter { user ->
            user.name.contains(query, ignoreCase = true)
        }
    }
    fun updateSearchQuery(query: String, context: Context) {
        _searchQuery.value = query
    }

    init{
        fetchUsersLibrary()
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
}