package project.ultimatechat.Composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import project.ultimatechat.MainViewModel
import project.ultimatechat.R
import project.ultimatechat.entities.StoreableMessage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(navControler: NavController, viewModel: MainViewModel) {
    val temporaryListOfMessages: MutableState<List<StoreableMessage>> 
    var message by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3F51B5))
            .clickable(
                onClick = {
                    viewModel.giveMessageInfo(context)
                    keyboardController?.hide()
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
    )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFFFF5722))
        ) {
            IconButton(
                onClick = { navControler.navigate("mainScreen") },
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color(0xFFF0F0F0)
                )
            }

            Text(
                text = viewModel.currentChatMate.collectAsState().value.name,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }


        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .clickable(
                    onClick = {
                        keyboardController?.hide()
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            reverseLayout = true
        ) {

            items(viewModel.messages.value.reversed()) { msg ->
                MessageBubble(msg)
            }
        }
        Row(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .fillMaxWidth()
                .padding(2.dp)
                .windowInsetsPadding(WindowInsets.ime)
                .offset(y = (-5).dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                placeholder = { Text("Message...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                minLines = 1,
                maxLines = 5
            )
            Button(
                onClick = {
                    if (message.text.isNotEmpty()){
                        viewModel.sendMessage(message = message.text)
                        message = TextFieldValue("")
                    }
                },
                modifier = Modifier
                    .padding(8.dp)
                    .height(60.dp)
                    .width(60.dp),
                shape = RoundedCornerShape(50)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_send),
                    contentDescription = "Send Icon",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
