import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun DateOfBirth(selectedDateOfBirth: MutableState<Calendar>) {

    var selectedDate by remember { mutableStateOf("Select your birthdate") }
    val context = LocalContext.current

    val year = selectedDateOfBirth.value.get(Calendar.YEAR)
    val month = selectedDateOfBirth.value.get(Calendar.MONTH)
    val day = selectedDateOfBirth.value.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
            // Create a new Calendar instance and update selectedDateOfBirth
            selectedDateOfBirth.value = Calendar.getInstance().apply {
                set(selectedYear, selectedMonth, selectedDay)
            }
        }, year, month, day
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = selectedDate, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { datePickerDialog.show() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
            modifier = Modifier
                .fillMaxWidth(0.8f)
        ) {
            Text(text = "Pick Date of Birth")
        }
    }
}
