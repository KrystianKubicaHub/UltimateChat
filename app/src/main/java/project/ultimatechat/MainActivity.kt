package project.ultimatechat

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import project.ultimatechat.Composables.ChatScreen
import project.ultimatechat.Composables.LoginAndRegistration.RegisterView
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

            val context = LocalContext.current
            val emptyMutableList = remember {mutableStateOf(listOf<StoreableMessage>())}
            val navController = rememberNavController()
            fakeLogInTheUser(emptyMutableList, context)
            val viewModel: MainViewModel = viewModel()

            val startDestination = if (AuthServices.checkIfUserLoggedIn()) "mainScreen" else "welcomeView"

            NavHost(navController = navController, startDestination = startDestination) {
                composable("welcomeView") { WelcomeView(navController) }
                composable("mainScreen") { MainScreen(navController, viewModel) }
                composable("register") { RegisterView(navController) }
                composable("chat") { ChatScreen(navController) }
            }
        }
    }
    private fun fakeLogInTheUser(
        emptyMutableList: MutableState<List<StoreableMessage>>,
        context: Context
    ) : Boolean{
        localUser = LocalUser(112, "Krystian", System.currentTimeMillis(),
            "", System.currentTimeMillis(), applicationContext, emptyMutableList)
        val check = AuthServices.checkIfUserLoggedIn()
        //if(!check) Toast.makeText(context, "Użytkownik nie zalogowany", Toast.LENGTH_LONG).show()

        return check
    }
}