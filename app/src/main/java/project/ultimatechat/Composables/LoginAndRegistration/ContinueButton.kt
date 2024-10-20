package project.ultimatechat.Composables.LoginAndRegistration

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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