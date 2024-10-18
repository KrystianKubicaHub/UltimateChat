package project.ultimatechat.Composables.LoginAndRegistration

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import project.ultimatechat.R
import java.io.ByteArrayOutputStream
import java.util.Base64

// Global variable to store the encoded photo
var codedPhoto: String? = null

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPhoto(navController: NavHostController) {
    // State to hold selected image as a Bitmap
    val context = LocalContext.current
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val placeholderImage = painterResource(id = R.drawable.add_photo)

    // Launcher for picking an image
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
            selectedImageBitmap = bitmap

            // Convert the bitmap to a Base64 string
            codedPhoto = bitmap?.let { encodeBitmapToBase64(it) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back Arrow Icon
        IconButton(
            onClick = { navController.navigate("enterNickName") },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back), // Replace with actual resource path
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = "Insert a Photo",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle
        Text(
            text = "Choose a photo to use as your profile image.",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Placeholder for the photo with border and rounded corners
        Box(
            modifier = Modifier
                .size(120.dp)
                .border(2.dp, Color.LightGray, shape = RoundedCornerShape(16.dp))
                .background(Color.LightGray, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageBitmap != null) {
                Image(
                    bitmap = selectedImageBitmap!!.asImageBitmap(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(120.dp)
                )
            } else {
                Image(
                    painter = placeholderImage,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(120.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Button to change the photo
        Button(
            onClick = { launcher.launch("image/*") }, // Opens the photo picker
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007FFF))
        ) {
            Text("Change Photo", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to proceed
        Button(
            onClick = { navController.navigate("dateOfBirth") },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007FFF))
        ) {
            Text("Proceed", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Skip Button
        OutlinedButton(
            onClick = { navController.navigate("dateOfBirth") },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF007FFF))
        ) {
            Text("Skip", fontSize = 16.sp)
        }
    }
}

// Function to convert bitmap to Base64 string
fun encodeBitmapToBase64(bitmap: Bitmap): String? {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Base64.getEncoder().encodeToString(byteArray)
    } else {
        android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
    }
}
