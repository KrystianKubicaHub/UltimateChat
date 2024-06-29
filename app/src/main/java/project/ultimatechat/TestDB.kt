package project.ultimatechat

import android.content.Context

class TestDB() {
    companion object{
        fun execute(context: Context, onMessageChange: (String) -> Unit){
            val localUser = LocalUser(2,"Krystin", System.currentTimeMillis(),
                "losowyurl", System.currentTimeMillis(), context = context)
            //localUser.sendMessage("My message is \"fuck you\"","")
           // val messages = localUser.getMessages(0, 10, 0)
            localUser.sendMessage("Wiadomosn1",112)
            //onMessageChange(localUser.getAllContacts().size.toString())
        }
    }

}