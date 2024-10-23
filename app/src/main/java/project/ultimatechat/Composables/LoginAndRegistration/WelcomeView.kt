package project.ultimatechat.Composables.LoginAndRegistration

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import project.ultimatechat.AuthServices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeView(navController: NavHostController) {
    val focusManager = LocalFocusManager.current
    val emailOrPhone = remember{mutableStateOf("")}
    val password = remember{mutableStateOf("")}
    val errorInfo = remember{mutableStateOf("")}

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
                value = emailOrPhone.value,
                onValueChange = { v -> emailOrPhone.value = v},
                label = { Text("E-mail or Id", color = Color.White) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.5f)
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
                value = password.value,
                onValueChange = { v -> password.value = v},
                label = { Text("Password", color = Color.White) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(alpha = 0.5f)
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
                onClick = {
                    focusManager.clearFocus()
                    if((emailOrPhone.value != "") && (password.value != "")){
                        AuthServices.logIn(emailOrPhone.value, password.value) { bolion, eroCode ->
                            if (!bolion) {
                                errorInfo.value = eroCode!!
                            } else {
                                navController.navigate("mainScreen")
                            }

                        }
                    }



                },
                colors = ButtonDefaults.buttonColors(Color(0xFF007FFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Sign in", color = Color.White, fontSize = 16.sp)
            }
            if(errorInfo.value != ""){
                Text(errorInfo.value, color = Color.Red, fontSize = 16.sp, modifier = Modifier.padding(all = 5.dp))
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
                onClick = { navController.navigate("register") },
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
                val iconSize = 45.dp
                Image(
                    painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.google)),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(iconSize),
                )
                Image(
                    painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.facebook)),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(iconSize),
                )
                Image(
                    painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.twitter)),
                    contentDescription = "Google Icon",
                    modifier = Modifier.size(iconSize),
                )
            }
        }
    }
}
