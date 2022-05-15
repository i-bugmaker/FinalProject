package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.finalproject.R
import com.example.finalproject.db.UserDatabaseHelper

class ModifyName : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_name)

        val currentUsername = intent.getStringExtra("currentUsername")
        val modifynameEt = findViewById<EditText>(R.id.modifyname)
        modifynameEt.hint = currentUsername

        val saveNameBtn = findViewById<Button>(R.id.save_name_btn)
        saveNameBtn.setOnClickListener(View.OnClickListener {
            if (modifynameEt.text != null) {
                val dbHelper = UserDatabaseHelper(this, "user.db", 2)
                val db = dbHelper.writableDatabase
                db.execSQL(
                    "update User set username = ? where username = ?",
                    arrayOf(modifynameEt.text, currentUsername)
                )
            }
        })
    }
}