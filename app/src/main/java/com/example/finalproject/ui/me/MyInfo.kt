package com.example.finalproject.ui.me

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.ModifyBirthday
import com.example.finalproject.ModifyName
import com.example.finalproject.User
import com.example.finalproject.databinding.ActivityMyInfoBinding
import com.example.finalproject.db.UserDatabaseHelper
import java.io.ByteArrayOutputStream

lateinit var imageUri : Uri
lateinit var avatar : ImageView
class MyInfo : AppCompatActivity() {

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHelper = UserDatabaseHelper(this, "user.db", 2)
        val db = dbHelper.writableDatabase
        avatar = binding.avatarimage
        val useridInput = binding.useridInput
        val usernameInput = binding.usernameInput
        val sexInput = binding.sexInput
        val birthdayInput = binding.birthdayInput
        val currentUsername = User.getCurrentUsername()




        //从数据库获取相关值
        usernameInput.text = currentUsername
        useridInput.text = findIdByUsername(currentUsername).toString()

        val meViewModel = ViewModelProvider(this).get(MeViewModel::class.java)
        val adapter =
            ArrayAdapter<String>(this, R.layout.simple_spinner_item, meViewModel.sexOption)
        val currentSex = findSexByUsername(currentUsername)

        Toast.makeText(this, "当前数据库性别为$currentSex", Toast.LENGTH_SHORT).show()
        binding.sexInput.setSelection(adapter.getPosition(currentSex))
        val cursor = db.rawQuery("select * from User where username = ?", arrayOf(currentUsername))
        cursor.moveToFirst()
        birthdayInput.text = cursor.getString(cursor.getColumnIndex("birthday"))
        val imageHex = cursor.getBlob(cursor.getColumnIndex("avatar"))
        val bit = BitmapFactory.decodeByteArray(imageHex, 0, imageHex.size)
        avatar.setImageBitmap(bit)

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

            override fun onNothingSelected(p0: AdapterView<*>?) {
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

        avatar.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            // 指定只显示图片
            intent.type = "image/*"
            startActivityForResult(intent, 1)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    imageUri = data.data!!
//                        val bitmap = getBitmapFromUri(imageUri)
                    val bitmap = BitmapFactory.decodeFileDescriptor(
                        this.contentResolver.openFileDescriptor(
                            imageUri,
                            "r"
                        )?.fileDescriptor
                    )
                    avatar.setImageBitmap(bitmap)
                    val dbHelper = UserDatabaseHelper(this, "user.db", 2)
                    val db = dbHelper.writableDatabase
                    val os = ByteArrayOutputStream()
//                    val bitmap: Bitmap =
//                        BitmapFactory.decodeStream(this.contentResolver.openInputStream(
//                            com.example.finalproject.ui.info.imageUri
//                        ))
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
                    db.execSQL("update User set avatar = ? where username = ?", arrayOf(os.toByteArray(), User.getCurrentUsername()))
                }
            }
        }
    }
}