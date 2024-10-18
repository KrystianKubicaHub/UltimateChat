import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import project.ultimatechat.R
import java.util.*

@Composable
fun DateOfBirth(navController: NavHostController) {
    // Remembering the selected date
    var selectedDate by remember { mutableStateOf("Select your birthdate") }

    // Get context
    val context = LocalContext.current

    // Calendar instance
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Date Picker Dialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display a random candle image at the center
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.LightGray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.search), // Placeholder for a random candle image
                contentDescription = "Candle Icon",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Display selected date
        Text(text = selectedDate, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Button to open the date picker
        Button(onClick = { datePickerDialog.show() }) {
            Text(text = "Pick Date of Birth")
        }
    }
}