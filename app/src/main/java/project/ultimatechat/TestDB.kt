package project.ultimatechat

import android.content.Context
import androidx.compose.material3.TimeInput

class TestDB() {
    companion object{
        fun execute(context: Context, onMessageChange: (String) -> Unit){
            val localUser = LocalUser(2,"Krystin", System.currentTimeMillis(),
                "losowyurl", System.currentTimeMillis(), context = context)
            //localUser.sendMessage("My message is \"fuck you\"","")
           // val messages = localUser.getMessages(0, 10, 0)
            localUser.addContact(StoreableContact(112,"Agnieszka", MyTime.getTime(),
                "emptypath", MyTime.getTime()))
            localUser.sendMessage("Wiadomosn1",112)
            //onMessageChange(localUser.getAllContacts().size.toString())
        }
    }
}