package project.ultimatechat.SQL

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactsDatabaseHelper(context: Context, databaseName: String) :
    SQLiteOpenHelper(context, databaseName, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE contacts (" +
                    "id INTEGER PRIMARY KEY," +
                    "nickName TEXT," +
                    "dataOfRegistration INTEGER," +
                    "pathToProfilePicture TEXT," +
                    "lastActivityTime INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS contacts")
        onCreate(db)
    }
}