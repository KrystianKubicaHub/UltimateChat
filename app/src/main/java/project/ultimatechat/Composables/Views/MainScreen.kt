package project.ultimatechat.Composables.Views

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import project.ultimatechat.AuthServices
import project.ultimatechat.Composables.fragments.MySearchBar
import project.ultimatechat.Composables.fragments.SearchItem
import project.ultimatechat.MainViewModel
import project.ultimatechat.R
import project.ultimatechat.entities.StoreableContact


@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {

    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredUsers by viewModel.filteredUsers.collectAsState(emptyList())
    val currentUser by viewModel.currentUser.collectAsState()

    val users by viewModel.users.collectAsState()
    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current

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

            Image(
                painter = rememberAsyncImagePainter(model = currentUser?.photoUrl),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.Gray, CircleShape)
                    .clickable { navController.navigate("profile") }
            )
            Text(
                text = "Chats",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(all = 16.dp)
                    .clickable {
                        viewModel.Toast(context)
                    },
                color = Color(0xFFFF5722)
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


        MySearchBar(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onQueryChange = { query ->
                viewModel.updateSearchQuery(query)
            },
            query = searchQuery
        )
        if (filteredUsers.isNotEmpty() && searchQuery.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(filteredUsers) { user ->
                    SearchItem(user, navController, viewModel)
                }
            }
        } else if(searchQuery != "") {
            Text(
                text = "No users found",
                modifier = Modifier.padding(16.dp)
            )
        }

        LazyColumn(modifier = Modifier.clickable(
            onClick = {
                keyboardController?.hide()
            },
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )) {

            items(users) { uUu ->
                ChatRow(uUu, navController, viewModel)
            }

        }
    }
}

@Composable
fun ChatRow(person: StoreableContact, navController: NavHostController, viewModel: MainViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("chat")
                viewModel.setCurrentChatMate(person)},
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
