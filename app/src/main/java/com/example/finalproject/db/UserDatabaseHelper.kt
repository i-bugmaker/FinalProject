package com.example.finalproject.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.finalproject.Login
import com.example.finalproject.User

class UserDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val createUser = "create table User(" +
            "id integer primary key autoincrement ," +
            "username text ," +
            "sex text ," +
            "password text ," +
            "birthday text ," +
            "avatar text" +
            ")"

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.disableWriteAheadLogging();
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createUser)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}