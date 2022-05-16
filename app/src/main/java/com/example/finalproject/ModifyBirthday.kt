package com.example.finalproject

import android.os.Bundle
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.db.UserDatabaseHelper


class ModifyBirthday : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_birthday)
        val currentUsername = intent.getStringExtra("currentUsername")

        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        val dateView = findViewById<TextView>(R.id.dateView)

        val year = datePicker.year
        val month = datePicker.month
        val day = datePicker.dayOfMonth

        dateView.text = "${year}年${month+1}月${day}日"

        datePicker.init(
            year, month, day
        ) { view, year, month, day ->
            dateView.text = "${year}年${month+1}月${day}日"
        }

        val dbHelper = UserDatabaseHelper(this, "user.db", 2)
        val db = dbHelper.writableDatabase
        db.execSQL(
            "update User set birthday = ? where username = ?",
            arrayOf(dateView.text, currentUsername)
        )
    }
}