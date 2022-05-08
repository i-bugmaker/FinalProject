package com.example.finalproject.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PetDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val createPet = "create table Pet(" +
            "id integer primary key autoincrement ," +
            "nickname text ," +
            "sex text ," +
            "age text ," +
            "breed text ," +
            "image text" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createPet)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.disableWriteAheadLogging();
    }
}