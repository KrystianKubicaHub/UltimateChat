package project.ultimatechat.other

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyTime {
    companion object{
        fun getTime(): Long{
            return System.currentTimeMillis()
        }
        fun formatDateFromLong(timestamp: Long): String {
            val date = Date(timestamp)
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return format.format(date)
        }
    }
}