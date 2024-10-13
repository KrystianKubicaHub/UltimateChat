package project.ultimatechat.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen() {
    // Sample data for messages
    val messages = listOf(
        Message("Stephanie Pimentel", "Lorem ipsum dolor, sit amet...", "2 min ago"),
        Message("Kenneth Tyson", "Lorem ipsum dolor, sit amet...", "3h ago"),
        Message("Fatima Bernard", "Lorem ipsum dolor, sit amet...", "1d ago"),
        Message("Bruce Morrow", "Lorem ipsum dolor, sit amet...", "5m ago"),
        Message("Donez Lauba", "Lorem ipsum dolor, sit amet...", "2h ago")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Messages",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(messages) { message ->
                MessageItem(message)
            }
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Handle click event */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Placeholder for the profile image
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = message.sender,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = message.preview,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Text(
            text = message.timestamp,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

data class Message(val sender: String, val preview: String, val timestamp: String)
