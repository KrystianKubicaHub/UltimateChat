package project.ultimatechat.Composables.LoginAndRegistration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.ultimatechat.R

@Composable
fun EnterEmailOrPhoneNumber() {
    // State to track the selected option
    var isPhoneSelected by remember { mutableStateOf(true) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Switch Button Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(24.dp)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Phone Number Option
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { isPhoneSelected = true }
                    .background(if (isPhoneSelected) Color(0xFFFF5722) else Color.LightGray, RoundedCornerShape(24.dp))
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.phone), // Replace with your icon
                        contentDescription = "Phone Icon",
                        tint = if (isPhoneSelected) Color.White else Color.Black,
                        modifier = Modifier.size(30.dp).padding(start = 5.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Phone Number",
                        color = if (isPhoneSelected) Color.White else Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { isPhoneSelected = false }
                    .background(if (!isPhoneSelected) Color(0xFF6200EA) else Color.LightGray, RoundedCornerShape(24.dp))
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.email),
                        contentDescription = "Email Icon",
                        tint = if (!isPhoneSelected) Color.White else Color.Black,
                        modifier = Modifier.size(30.dp).padding(start = 5.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Email Address",
                        color = if (!isPhoneSelected) Color.White else Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            label = { Text(if (isPhoneSelected) "Enter phone number" else "Enter email address") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                if (isPhoneSelected) {
                    Icon(
                        painter = painterResource(id = R.drawable.phone), // Replace with your phone icon
                        contentDescription = "Phone Icon"
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.email), // Replace with your email icon
                        contentDescription = "Email Icon"
                    )
                }
            }
        )
    }
}
