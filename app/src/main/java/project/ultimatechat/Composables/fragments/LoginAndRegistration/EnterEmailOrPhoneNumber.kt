package project.ultimatechat.Composables.fragments.LoginAndRegistration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.ultimatechat.R

@Composable
fun EnterEmailOrPhoneNumber(
    userHasSelectedEmail: MutableState<Boolean>,
    typedEmail: MutableState<String>,
    typedPhoneNumber: MutableState<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(24.dp)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { userHasSelectedEmail.value = false }
                    .background(if (!userHasSelectedEmail.value) Color(0xFFFF5722) else Color.LightGray, RoundedCornerShape(24.dp))
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
                        tint = if (!userHasSelectedEmail.value) Color.White else Color.Black,
                        modifier = Modifier.size(30.dp).padding(start = 5.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Phone Number",
                        color = if (!userHasSelectedEmail.value) Color.White else Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { userHasSelectedEmail.value = true }
                    .background(if (userHasSelectedEmail.value) Color(0xFF6200EA) else Color.LightGray, RoundedCornerShape(24.dp))
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
                        tint = if (userHasSelectedEmail.value) Color.White else Color.Black,
                        modifier = Modifier.size(30.dp).padding(start = 5.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Email Address",
                        color = if (userHasSelectedEmail.value) Color.White else Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = if(userHasSelectedEmail.value) typedEmail.value else typedPhoneNumber.value,
            onValueChange = { if(userHasSelectedEmail.value) typedEmail.value = it else typedPhoneNumber.value = it },
            label = { Text(if (!userHasSelectedEmail.value) "Enter phone number" else "Enter email address") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                if (!userHasSelectedEmail.value) {
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
