package project.ultimatechat.Composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import project.ultimatechat.AuthServices
import project.ultimatechat.R
import project.ultimatechat.entities.StoreableContact


@Composable
fun MainScreen(navController: NavHostController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchQuery by remember { mutableStateOf("") }
    val messages = listOf(
        StoreableContact(2,"Agnieszka", System.currentTimeMillis(),
            "", System.currentTimeMillis())
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { keyboardController?.hide() }
            )
    ){
        Box(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Messages",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(all = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.log_out),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .clickable(onClick = {
                        AuthServices.logout()
                        navController.navigate("welcomeView")
                    })
                    .align(Alignment.CenterEnd)
            )
        }


        SearchBar(
            modifier = Modifier.padding(horizontal = 16.dp),
            onQueryChange = { query ->
                searchQuery = query
                println("Searching for: $query")
            },
            query = searchQuery
        )

        LazyColumn(modifier = Modifier.clickable(
            onClick = {
                keyboardController?.hide()
            },
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )) {
            items(messages) { message ->
                ChatRow(message)
            }
        }
    }
}

@Composable
fun ChatRow(person: StoreableContact) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* Handle click event */ },
        verticalAlignment = Alignment.CenterVertically
    ) {
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
                text = person.nickName!!,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = if (person.noMessages()) "No messages Yet" else person.getLastMessage(),
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Text(
            text = person.getLastActivity(),
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}
