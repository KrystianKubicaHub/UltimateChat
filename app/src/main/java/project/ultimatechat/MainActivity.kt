package project.ultimatechat

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import project.ultimatechat.ui.theme.UltimateChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UltimateChatTheme {
                var message by remember { mutableStateOf("Próba...") }
                val database = Firebase.database("https://ultimatechat-51396-default-rtdb.europe-west1.firebasedatabase.app")
                val myRef = database.getReference("message")
                Log.e("ó", myRef.toString())
                TestDB.execute()

                myRef.setValue("Hello, World!")
                    .addOnSuccessListener {
                        message = "Zapis do bazy danych się udał!"
                    }
                    .addOnFailureListener { error ->
                        message = "Błąd zapisu: ${error.message}"
                    }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(message)
                }
            }

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UltimateChatTheme {
        Greeting("Android")
    }
}