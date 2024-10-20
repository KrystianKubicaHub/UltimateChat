package project.ultimatechat

import android.app.Activity
import androidx.navigation.NavHostController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

object AuthServices {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun checkIfUserLoggedIn(): Boolean{
        return auth.currentUser != null
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
