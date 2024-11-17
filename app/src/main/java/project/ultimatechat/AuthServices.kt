package project.ultimatechat

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import project.ultimatechat.entities.SendableContact
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

object AuthServices {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()



    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference
    fun checkIfUserLoggedIn(): Boolean{
        return auth.currentUser != null
    }

    fun updateUserPicture(bitmap: Bitmap, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser != null) {

            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val profilePicRef = storageReference.child("profile_pictures/${currentUser.uid}.jpg")

            profilePicRef.putBytes(data)
                .addOnSuccessListener { taskSnapshot ->
                    profilePicRef.downloadUrl.addOnSuccessListener { uri ->
                        updateUserPictureURL(uri, onSuccess, onFailure)
                    }.addOnFailureListener { exception ->
                        onFailure(exception)
                    }
                }.addOnFailureListener { exception ->
                    onFailure(exception)
                }
        } else{
            onFailure(Exception("User not logged in"))
        }
    }

    private fun updateUserPictureURL(photoUri: Uri, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(photoUri)
                .build()

            currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure(task.exception ?: Exception("Failed to update profile picture"))
                    }
                }
        } else {
            onFailure(Exception("User not logged in"))
        }
    }

    fun logIn(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }
    fun initializeUserInDB(context: Context, userName: String){
        val database = Firebase.database("https://ultimatechat-51396-default-rtdb.europe-west1.firebasedatabase.app")
        val refForUsersRoster = database.getReference("usersRoster").child(auth.currentUser!!.uid)
        val refForUsersList = database.getReference("usersList").child(auth.currentUser!!.uid)
        val id = auth.currentUser!!.uid

        val sendableContact = SendableContact(id, userName, System.currentTimeMillis(), "", System.currentTimeMillis())

        refForUsersRoster.setValue(userName)
        refForUsersList.setValue(sendableContact)
    }
    fun Toast(context: Context){
        Toast.makeText(context,  auth.currentUser!!.displayName, Toast.LENGTH_LONG).show()
    }


    fun createUserWithEmail(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)

                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }

    fun updateUserName(newName: String, onSuccess: () -> Unit) {
        val user = auth.currentUser

        if (user != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build()

            user.updateProfile(profileUpdates)
                .addOnCompleteListener {onSuccess()}
        }
    }
    fun signInWithPhoneNumber(phoneNumber: String, code: String?, activity : Activity, onComplete: (Boolean, String?) -> Unit) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                onComplete(true, null)
                            } else {
                                onComplete(false, task.exception?.message.toString())
                            }
                        }
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onComplete(false, e.message)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    onComplete(true, verificationId)
                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    fun logout() {
        auth.signOut()

    }


    fun deleteUserAccount(onComplete: (Boolean, String?) -> Unit) {
        val user = auth.currentUser
        user?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onComplete(true, null)
            } else {
                onComplete(false, task.exception?.message)
            }
        }
    }
}
