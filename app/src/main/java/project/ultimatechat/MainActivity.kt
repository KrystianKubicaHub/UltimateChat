package project.ultimatechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import project.ultimatechat.Composables.Views.ChatScreen
import project.ultimatechat.Composables.Views.RegisterView
import project.ultimatechat.Composables.Views.WelcomeView
import project.ultimatechat.Composables.Views.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            val navController = rememberNavController()
            val viewModel: MainViewModel = viewModel()

            val startDestination = if (AuthServices.checkIfUserLoggedIn()) "mainScreen" else "welcomeView"

            NavHost(navController = navController, startDestination = startDestination) {
                composable("welcomeView") { WelcomeView(navController, viewModel) }
                composable("mainScreen") { MainScreen(navController, viewModel) }
                composable("register") { RegisterView(navController, viewModel) }
                composable("chat") { ChatScreen(navController, viewModel) }
            }
        }
    }
}