package project.ultimatechat.Composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import project.ultimatechat.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "search for a person",
    active: Boolean = false,
    onActiveChange: (Boolean) -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    query: String = ""
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 5.dp, 20.dp, 10.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        TextField(
            value = query,
            onValueChange = {
                onQueryChange(it)
            },
            placeholder = { Text(hint, color = Color.Gray) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Send Icon",
                    tint = Color.Gray,
                    modifier = Modifier.padding(5.dp)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp)
        )
    }
}
