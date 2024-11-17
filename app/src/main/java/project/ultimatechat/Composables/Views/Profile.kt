package project.ultimatechat.Composables.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import project.ultimatechat.MainViewModel
import project.ultimatechat.R
import project.ultimatechat.other.MyTime.Companion.formatDateFromLong

@Composable
fun Profile(navController: NavController, viewModel: MainViewModel) {
    val currentUser by viewModel.currentUser.collectAsState()

    if (currentUser != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navController.navigate("mainScreen") },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        tint = Color(0xFF2196F3)
                    )
                }
            }

            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = rememberAsyncImagePainter(model = currentUser?.photoUrl),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Gray, CircleShape)
                )
                IconButton(
                    onClick = {
                    },
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color.White, CircleShape)
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit_icon),
                        contentDescription = "Edit Profile Picture",
                        tint = Color(0xFF2196F3)
                    )
                }
            }

            UserInfoRow("Username", currentUser!!.displayName ?: "Unknown") {
            }

            UserInfoRow("Email", currentUser!!.email ?: "No email") {
            }

            UserInfoRow("Phone", currentUser!!.phoneNumber ?: "No phone number") {
            }
            UserInfoRow("Date of Birth", formatDateFromLong(viewModel.localUser.dateOfBirth)) {
            }
        }
    }
}

@Composable
fun UserInfoRow(label: String, value: String, onEditClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
        Button(onClick = onEditClick) {
            Text("Edytuj")
        }
    }
}
