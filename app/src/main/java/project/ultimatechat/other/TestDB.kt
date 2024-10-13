package project.ultimatechat.other

import androidx.compose.runtime.MutableState
import project.ultimatechat.entities.StoreableMessage

class TestDB() {
    private lateinit var temporaryListOfMessages2 : MutableState<List<StoreableMessage>>
    /*
    constructor(messageList : MutableState<List<StoreableMessage>>) : this() {
        temporaryListOfMessages2 = messageList
    }


    companion object{
        fun execute(context: Context, onMessageChange: (String) -> Unit){
            val localUser = LocalUser(2,"Krystin", System.currentTimeMillis(),
                "losowyurl", System.currentTimeMillis(), context = context)
            //localUser.sendMessage("My message is \"fuck you\"","")
           // val messages = localUser.getMessages(0, 10, 0)
            localUser.addContact(StoreableContact(1182,"Agnieszka", MyTime.getTime(),
                "emptypath", MyTime.getTime()))
            localUser.sendMessage("Wiadomosn21",112)
            //onMessageChange(localUser.getAllContacts().size.toString())
        }
        fun exec2(context: Context, onMessageChange: (String) -> Unit){
            val localUser = LocalUser(112,"Agnieszka", System.currentTimeMillis(),
                "losowyurl", System.currentTimeMillis(), context = context)
            localUser.addContact(StoreableContact(1182,"Krystian", MyTime.getTime(),
                "emptypath", MyTime.getTime()))
            //localUser.sendMessageSecondApproach("Wiadomosn21", localUser.getAllContacts().last().id)
            //localUser.sendMessageSecondApproach("hej", localUser.getAllContacts().last().id)
            //localUser.sendMessageSecondApproach("czesc", localUser.getAllContacts().last().id)

        }
    }

     */
}