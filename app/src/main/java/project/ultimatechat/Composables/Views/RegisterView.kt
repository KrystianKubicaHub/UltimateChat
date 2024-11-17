package project.ultimatechat.Composables.Views

import DateOfBirth
import EnterPassword
import android.app.Activity
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
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import project.ultimatechat.AuthServices
import project.ultimatechat.Composables.fragments.ContinueButton
import project.ultimatechat.Composables.fragments.DisabledContinueButton
import project.ultimatechat.Composables.fragments.LoginAndRegistration.EnterEmailOrPhoneNumber
import project.ultimatechat.Composables.fragments.LoginAndRegistration.EnterNickName
import project.ultimatechat.Composables.fragments.LoginAndRegistration.InsertPhoto
import project.ultimatechat.MainViewModel
import project.ultimatechat.R
import java.util.Calendar
import java.util.concurrent.TimeUnit

@Composable
fun RegisterView(navController: NavHostController, viewModel: MainViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val VIEW_OPTION = remember{ mutableIntStateOf(1) }
    val TITLE = remember {mutableStateOf("Enter your nickname")}
    val SUBTITLE = remember { mutableStateOf("Nickname might be your name and last name, but i doesn't have to") }
    val IMAGE = remember { mutableIntStateOf(R.drawable.enter_nick_name) }
    val selectedImageBitmap = remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current


    val typedNickname = remember{mutableStateOf("")}
    val selectedDateOfBirth = remember {mutableStateOf(Calendar.getInstance())}
    val userHasSelectedEmail = remember {mutableStateOf(false)}
    val typedEmail = remember {mutableStateOf("")}
    val typedPhoneNumber = remember {mutableStateOf("")}
    val typedPassword = remember {mutableStateOf("")}



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
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
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
                .size(250.dp)
                .padding(top = 25.dp)
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
            when(VIEW_OPTION.intValue){
                1 -> {
                    EnterNickName(typedNickname)
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
                    DateOfBirth(selectedDateOfBirth)
                    TITLE.value = "Enter your date of birth"
                    SUBTITLE.value = "Pick your birth date"
                    IMAGE.intValue = R.drawable.birthday
                }
                4 -> {
                    EnterEmailOrPhoneNumber(userHasSelectedEmail, typedEmail, typedPhoneNumber)
                    TITLE.value = "Enter your email address or phone number"
                    SUBTITLE.value = "This will be used for verification"
                    IMAGE.intValue = R.drawable.enter_nick_name
                }
                5 -> {
                    EnterPassword(typedPassword)
                    TITLE.value = "Enter your password"
                    SUBTITLE.value = "Create a strong password"
                    IMAGE.intValue = R.drawable.enter_nick_name
                }
                6 -> {
                    if(userHasSelectedEmail.value){
                        AuthServices.createUserWithEmail(typedEmail.value, typedPassword.value) { bb, it ->
                            if(!bb){
                                Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                            }else{
                                AuthServices.updateUserName(typedNickname.value) {
                                    AuthServices.initializeUserInDB(
                                        context, typedNickname.value
                                    )
                                }


                                if(selectedImageBitmap.value != null){
                                    AuthServices.updateUserPicture(selectedImageBitmap.value!!,
                                        {
                                            Toast.makeText(context, "Udało siem", Toast.LENGTH_LONG).show()
                                            navController.navigate("mainScreen")
                                        },
                                        {e -> Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()})
                                }
                            }
                        }
                    }
                }
                7 -> {}
            }
        }


      Column(modifier = Modifier
          .fillMaxWidth()
          .align(Alignment.BottomCenter)
          .padding(bottom = 30.dp)){
          if(VIEW_OPTION.intValue == 2){
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
          if(VIEW_OPTION.intValue != 3){
              ContinueButton(VIEW_OPTION = VIEW_OPTION)
          }else{
              if(isUserAtLeast18YearsOld(selectedDateOfBirth.value)) {
                  ContinueButton(VIEW_OPTION = VIEW_OPTION)
              } else {
                  val errorMessage = remember { mutableStateOf("You MUST BE AT LEAST 18") }
                  DisabledContinueButton(errorMessage)
              }
          }


      }
    }


}



fun isUserAtLeast18YearsOld(selectedDateOfBirth: Calendar): Boolean {
    val calendar = Calendar.getInstance().apply {
        add(Calendar.YEAR, -18)
    }
    val minimumAllowedDate = calendar.timeInMillis


    return selectedDateOfBirth.timeInMillis <= minimumAllowedDate
}