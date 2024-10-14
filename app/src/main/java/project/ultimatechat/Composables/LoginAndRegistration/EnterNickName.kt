package project.ultimatechat.Composables.LoginAndRegistration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import project.ultimatechat.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EnterNickName(navController: NavHostController) {
    val nickname = remember { mutableStateOf("") }
    val nicknameError = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Back Arrow Icon at the Top
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate("welcomeView") }) {
                Icon(
                    painter = painterResource(id = R.drawable.back), // Replace with your icon resource
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }

        // Title
        Text(
            text = "Enter your nick name",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Centered Subtitle
        Text(
            text = "Nickname might be your name and last name but doesn't have to",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Rounded Corners Image within Box
        Box(
            modifier = Modifier
                .size(190.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.enter_nick_name), // Replace with your image resource
                contentDescription = "Profile Picture",
                modifier = Modifier.size(190.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Text Field for nickname input
        OutlinedTextField(
            value = nickname.value,
            onValueChange = {
                nickname.value = it
                nicknameError.value = if (it.length < 3) "Nickname is too short" else ""
            },
            label = { Text("Nickname") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Error message below the text field
        if (nicknameError.value.isNotEmpty()) {
            Text(
                text = nicknameError.value,
                fontSize = 12.sp,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Button to proceed
        Button(
            onClick = { navController.navigate("insertPhoto") },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007FFF))
        ) {
            Text("Proceed", color = Color.White, fontSize = 16.sp)
        }
    }
}
