package project.ultimatechat

import java.net.URL

class TestDB() {
    companion object{
        public fun execute(){
            val localUser = LocalUser(1,"Krystin", System.currentTimeMillis(),
                URL("losowyurl"), System.currentTimeMillis())
            localUser.sendMessage("My message is \"fuck you\"","")
            val messages = localUser.getMessages(0, 10, 0)
        }
    }

}