package com.example.finalproject.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdoptDatabaseHelper(val context: Context?, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val createAdopt = "create table Adopt(" +
            "id integer primary key autoincrement ," +
            "username text ," +
            "pet_id integer ," +
            "date text" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createAdopt)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("drop table if exists Adopt")
        onCreate(db)
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.disableWriteAheadLogging();
    }
}