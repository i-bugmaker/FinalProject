package com.example.finalproject.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PublicDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val createPublic = "create table Public(" +
            "id integer primary key autoincrement ," +
            "date text," +
            "description text ," +
            "pet_id integer," +
            "contact text," +
            "phone text" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createPublic)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("drop table if exists Public")
        onCreate(db)
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.disableWriteAheadLogging();
    }
}