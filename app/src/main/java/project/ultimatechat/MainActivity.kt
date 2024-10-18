package project.ultimatechat

import DateOfBirth
import EnterPassword
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import project.ultimatechat.Composables.LoginAndRegistration.EnterNickName
import project.ultimatechat.Composables.LoginAndRegistration.InsertPhoto
import project.ultimatechat.Composables.LoginAndRegistration.WelcomeView
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
            val navController = rememberNavController()
            val startDestination = if (logInTheUser(emptyMutableList)) "mainScreen" else "welcomeView"

            NavHost(navController = navController, startDestination = startDestination) {
                composable("welcomeView") { WelcomeView(navController) }
                composable("mainScreen") { MainScreen(navController) }
                composable("enterNickName") { EnterNickName(navController) }
                composable("insertPhoto") { InsertPhoto(navController) }
                composable("enterPassword") { EnterPassword(navController) }
                composable("dateOfBirth") { DateOfBirth(navController) }
            }
        }
    }
    private fun logInTheUser(emptyMutableList: MutableState<List<StoreableMessage>>) : Boolean{
        localUser = LocalUser(112, "Krystian", System.currentTimeMillis(),
            "", System.currentTimeMillis(), applicationContext, emptyMutableList)
        return false
    }
}