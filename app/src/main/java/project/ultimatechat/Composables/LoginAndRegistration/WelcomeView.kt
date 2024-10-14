package project.ultimatechat.Composables.LoginAndRegistration

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.ultimatechat.R
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeView(navController: NavHostController) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1931))
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null // Wyłączenie efektu migania
            ) {
                focusManager.clearFocus()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sign in to Continue",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("E-mail or Id", color = Color.White) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    disabledTextColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { focusManager.clearFocus() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Password", color = Color.White) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    disabledTextColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) { focusManager.clearFocus() },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { focusManager.clearFocus() },
                colors = ButtonDefaults.buttonColors(Color(0xFF007FFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Sign in", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            ClickableText(
                text = AnnotatedString("Forget password?"),
                onClick = { focusManager.clearFocus() },
                style = LocalTextStyle.current.copy(color = Color.White)
            )

            Spacer(modifier = Modifier.height(8.dp))

            ClickableText(
                text = AnnotatedString("Sign up"),
                onClick = { navController.navigate("enterNickName") },
                style = LocalTextStyle.current.copy(color = Color(0xFF007FFF))
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "or sign up with",
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(painter = painterResource(id = R.drawable.search), contentDescription = null, tint = Color.White)
                Icon(painter = painterResource(id = R.drawable.search), contentDescription = null, tint = Color.White)
                Icon(painter = painterResource(id = R.drawable.search), contentDescription = null, tint = Color.White)
                Icon(painter = painterResource(id = R.drawable.search), contentDescription = null, tint = Color.White)
            }
        }
    }
}
