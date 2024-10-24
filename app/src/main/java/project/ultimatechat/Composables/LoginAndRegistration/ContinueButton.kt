package project.ultimatechat.Composables.LoginAndRegistration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.ultimatechat.entities.SendableContact



@Composable
fun ContinueButton(VIEW_OPTION: MutableIntState){
    Button(
        onClick = { VIEW_OPTION.intValue++},
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007FFF))
    ) {
        Text("Proceed", color = Color.White, fontSize = 16.sp)
    }
}
@Composable
fun DisabledContinueButton(errorMessage: MutableState<String>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (errorMessage.value.isNotEmpty()) {
            Text(
                text = errorMessage.value,
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6F6F6F)),
            enabled = false
        ) {
            Text("Proceed", color = Color.White, fontSize = 16.sp)
        }
    }
}