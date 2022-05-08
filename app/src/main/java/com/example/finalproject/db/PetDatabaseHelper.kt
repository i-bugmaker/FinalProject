package com.example.finalproject.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PetDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val createPet = "create table Pet(" +
            "id integer primary key autoincrement ," +
            "nickname text ," +
            "breed text ," +
            "age text ," +
            "sex text ," +
            "image text" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createPet)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("drop table if exists Pet")
        onCreate(db)
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.disableWriteAheadLogging();
    }
}