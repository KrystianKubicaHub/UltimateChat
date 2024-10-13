package project.ultimatechat.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import project.ultimatechat.entities.StoreableMessage

@Composable
fun MessageBubble(message: StoreableMessage) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        contentAlignment = if (message.belongToLocalUser) Alignment.CenterStart else Alignment.CenterEnd
    ) {

        val bubbleColor = if (message.belongToLocalUser) Color.White else Color.LightGray

        Box(
            modifier = Modifier
                .background(bubbleColor, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .wrapContentHeight()
                .widthIn(min = 0.dp, max = (0.8 * LocalConfiguration.current.screenWidthDp).dp)
        ) {
            Text(
                text = message.message,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}