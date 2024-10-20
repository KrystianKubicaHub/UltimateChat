package project.ultimatechat.Composables.LoginAndRegistration

import DateOfBirth
import EnterPassword
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import project.ultimatechat.AuthServices
import project.ultimatechat.R

@Composable
fun RegisterView(navController: NavHostController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val VIEW_OPTION = remember{ mutableIntStateOf(1) }
    val TITLE = remember {mutableStateOf("Enter your nickname")}
    val SUBTITLE = remember { mutableStateOf("Nickname might be your name and last name, but i doesn't have to") }
    val IMAGE = remember { mutableIntStateOf(R.drawable.enter_nick_name) }
    val SELECTED_IMAGE = remember{ mutableStateOf("") }
    val selectedImageBitmap = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                })
            }
    ) {
        IconButton(
            onClick = { if (VIEW_OPTION.intValue > 1) VIEW_OPTION.intValue-- },
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                tint = Color.Black
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 35.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = TITLE.value,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 65.dp)
            )
            Text(
                text = SUBTITLE.value,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 5.dp)
            )
            val imageModifier = Modifier
                .size(250.dp).padding(top = 25.dp)
            if ((selectedImageBitmap.value != null) && VIEW_OPTION.intValue == 2){
                Image(
                    bitmap = selectedImageBitmap.value!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = imageModifier
                )
            } else {
                Image(
                    painter = painterResource(id = IMAGE.intValue),
                    contentDescription = null,
                    modifier = imageModifier
                )
            }
        }

        Box(modifier = Modifier
            .align(Alignment.TopStart)
            .padding(top = 400.dp, start = 25.dp, end = 25.dp)){
            when(VIEW_OPTION.intValue) {
                1 -> {
                    EnterNickName()
                    TITLE.value = "Enter your username"
                    SUBTITLE.value = "Choose a unique username"
                    IMAGE.intValue = R.drawable.enter_nick_name
                }
                2 -> {
                    InsertPhoto(selectedImageBitmap)
                    TITLE.value = "Select your profile photo"
                    SUBTITLE.value = "Choose a nice photo"
                    IMAGE.intValue = R.drawable.add_photo
                }
                3 -> {
                    DateOfBirth()
                    TITLE.value = "Enter your date of birth"
                    SUBTITLE.value = "Pick your birth date"
                    IMAGE.intValue = R.drawable.birthday
                }
                4 -> {
                    EnterEmailOrPhoneNumber()
                    TITLE.value = "Enter your email address or phone number"
                    SUBTITLE.value = "This will be used for verification"
                    IMAGE.intValue = R.drawable.enter_nick_name
                }
                5 -> {
                    EnterPassword()
                    TITLE.value = "Enter your password"
                    SUBTITLE.value = "Create a strong password"
                    IMAGE.intValue = R.drawable.enter_nick_name
                }
                6 -> {
                    AuthServices.createUserWithEmail("krykubi@wp.pl","12345678") { bb, it ->
                        if(!bb){
                            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(context, "Udało siem", Toast.LENGTH_LONG).show()
                            navController.navigate("mainScreen")

                        }
                    }

                }
            }
        }


      Column(modifier = Modifier
          .fillMaxWidth()
          .align(Alignment.BottomCenter)
          .padding(bottom = 30.dp)){
          if(VIEW_OPTION.intValue == 2) {
              OutlinedButton(
                  onClick = {VIEW_OPTION.intValue++},
                  modifier = Modifier
                      .fillMaxWidth()
                      .height(48.dp)
                      .padding(horizontal = 15.dp),
                  colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF007FFF))
              ) {
                  Text("Skip", fontSize = 16.sp)
              }
              Spacer(modifier = Modifier.height(16.dp))
          }
          ContinueButton(VIEW_OPTION = VIEW_OPTION)
      }
    }
}

fun createUsersAccountWithEmailOrPhoneNumber() {

}

