package project.ultimatechat.Composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import project.ultimatechat.MainViewModel
import project.ultimatechat.R // Upewnij się, że masz obraz profilowy w resources
import project.ultimatechat.entities.UserLibEntity

@Composable
fun SearchItem(user: UserLibEntity, navMenager: NavController, viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 2.dp)
            .background(Color(0xFFD4FAFF), shape = RoundedCornerShape(12.dp))
            .padding(8.dp)
            .clickable {
                navMenager.navigate("chat")
                viewModel.setCurrentChatMate(user)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.person),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.LightGray, shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = user.name,
                fontSize = 18.sp, // Większa czcionka
                fontWeight = FontWeight.Medium, // Pogrubiona
                overflow = TextOverflow.Ellipsis, // Jeśli tekst jest zbyt długi, obetnij
                maxLines = 1 // Ograniczenie do jednej linii
            )
        }
    }
}
