package com.example.finalproject.ui.me

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.ModifyBirthday
import com.example.finalproject.ModifyName
import com.example.finalproject.User
import com.example.finalproject.databinding.ActivityMyInfoBinding
import com.example.finalproject.db.UserDatabaseHelper


class MyInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val useridInput = binding.useridInput
        val usernameInput = binding.usernameInput
        val sexInput = binding.sexInput
        val birthdayInput = binding.birthdayInput
        val currentUsername = User.getCurrentUsername()


        //从数据获取相关值
        usernameInput.text = currentUsername
        useridInput.text = findIdByUsername(currentUsername).toString()

        val meViewModel = ViewModelProvider(this).get(MeViewModel::class.java)
        val adapter =
            ArrayAdapter<String>(this, R.layout.simple_spinner_item, meViewModel.sexOption)
        val currentSex = findSexByUsername(currentUsername)
        Toast.makeText(this, "当前数据库性别为$currentSex", Toast.LENGTH_SHORT).show()
        binding.sexInput.setSelection(adapter.getPosition(currentSex))
        birthdayInput.text = findBirthdayByUsername(currentUsername)

        //将选择的性别写会数据库
        binding.sexInput.adapter = adapter
        binding.sexInput.onItemSelectedListener = object : OnItemSelectedListener {
            @SuppressLint("Range")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.sexInput.setSelection(position)
                val dbHelper = UserDatabaseHelper(this@MyInfo, "user.db", 2)
                val db = dbHelper.writableDatabase
                db.execSQL(
                    "update User set sex = ? where username = ?",
                    arrayOf(meViewModel.sexOption[position], currentUsername)
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        usernameInput.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ModifyName::class.java)
            intent.putExtra("currentUsername", currentUsername)
            startActivity(intent)
        })

        birthdayInput.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ModifyBirthday::class.java)
            intent.putExtra("currentUsername", currentUsername)
            startActivity(intent)
        })
    }

    @SuppressLint("Range")
    fun findIdByUsername(username: String): Int {
        val dbHelper = UserDatabaseHelper(this, "user.db", 2)
        val db = dbHelper.writableDatabase
        val cursor = db.query(
            "User", null, "username=?",
            arrayOf(username), null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                val dbUsername = cursor.getString(cursor.getColumnIndex("username"))
                if (dbUsername == username) {
                    return cursor.getInt(cursor.getColumnIndex("id"))
                }
            } while (cursor.moveToNext())
        }
        return 0
    }

    @SuppressLint("Range")
    fun findSexByUsername(username: String): String {
        val dbHelper = UserDatabaseHelper(this, "user.db", 2)
        val db = dbHelper.writableDatabase
//        val cursor = db.query(
//            "User", null, "username=?",
//            arrayOf(username), null, null, null
//        )
        val cursor = db.rawQuery("select * from User where username=?", arrayOf(username))
//        if (cursor.moveToFirst()) {
//            do {
//                val dbUsername = cursor.getString(cursor.getColumnIndex("username"))
//                if (dbUsername == username) {
//                    if (cursor.getString(cursor.getColumnIndex("sex")) == null) {
//                        "null"
//                    } else {
//                        cursor.getString(cursor.getColumnIndex("sex"))
//                    }
//                }
//            } while (cursor.moveToNext())
//        }
        cursor.moveToFirst()
        val dbSex = cursor.getString(cursor.getColumnIndex("sex")) ?: "null"

        return dbSex ?: "null"
    }

    @SuppressLint("Range")
    fun findBirthdayByUsername(username: String): String {
        val dbHelper = UserDatabaseHelper(this, "user.db", 2)
        val db = dbHelper.writableDatabase
        val cursor = db.query(
            "User", null, "username=?",
            arrayOf(username), null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                val dbUsername = cursor.getString(cursor.getColumnIndex("username"))
                if (dbUsername == username) {
                    if (cursor.getString(cursor.getColumnIndex("birthday")) == null) {
                        "null"
                    } else {
                        cursor.getString(cursor.getColumnIndex("birthday"))
                    }
                }
            } while (cursor.moveToNext())
        }
        return "null"
    }
}