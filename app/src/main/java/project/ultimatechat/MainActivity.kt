package project.ultimatechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import project.ultimatechat.Composables.ChatScreen
import project.ultimatechat.Composables.MainScreen
import project.ultimatechat.entities.LocalUser
import project.ultimatechat.entities.SendableContact
import project.ultimatechat.entities.StoreableMessage

class MainActivity : ComponentActivity() {
    private lateinit var localUser : LocalUser
    private lateinit var talkMate : SendableContact
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val emptyMutableList = remember {mutableStateOf(listOf<StoreableMessage>())}
            logInTheUser(emptyMutableList)
            ChatScreen(localUser.temporaryListOfMessages)
            //MainScreen()
        }

    }
    private fun logInTheUser(emptyMutableList: MutableState<List<StoreableMessage>>) {
        localUser = LocalUser(112, "Krystian", System.currentTimeMillis(),
            "", System.currentTimeMillis(), applicationContext, emptyMutableList)
    }
}