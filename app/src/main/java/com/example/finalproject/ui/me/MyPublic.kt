package com.example.finalproject.ui.me

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.MyPublicCard
import com.example.finalproject.R
import com.example.finalproject.User
import com.example.finalproject.db.PetDatabaseHelper
import com.example.finalproject.db.PublicDatabaseHelper
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.lang.ref.SoftReference


class MyPublic : AppCompatActivity() {

    private val myPublicList = ArrayList<MyPublicCard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_public)
        initPublicList()
        val layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview_mp)
        recyclerView.layoutManager = layoutManager
        val adapter = MyPublicAdapter(myPublicList)
        recyclerView.adapter = adapter
    }

    @SuppressLint("Range")
    private fun initPublicList() {
        val petIdList = ArrayList<String>()
        val petDbHelper = PetDatabaseHelper(this, "pet.db", 2)
        val petDb = petDbHelper.writableDatabase
        val publicDbHelper = PublicDatabaseHelper(this, "public.db", 2)
        val publicDb = publicDbHelper.writableDatabase

        val public_cursor1 = publicDb.rawQuery(
            "select pet_id from Public where contact = ?",
            arrayOf(User.getCurrentUsername())
        )
        if (public_cursor1.moveToFirst()) {
            do {
                val petId = public_cursor1.getString(public_cursor1.getColumnIndex("pet_id"))
                petIdList.add(petId)
            } while (public_cursor1.moveToNext())
        }
        public_cursor1.close()

        for (petId in petIdList) {
            val pet_cursor = petDb.rawQuery("select * from Pet where id = ?", arrayOf(petId))
            pet_cursor.moveToFirst()
            val nickname = pet_cursor.getString(pet_cursor.getColumnIndex("nickname"))
            val breed = pet_cursor.getString(pet_cursor.getColumnIndex("breed"))
            val age = pet_cursor.getString(pet_cursor.getColumnIndex("age"))
            val sex = pet_cursor.getString(pet_cursor.getColumnIndex("sex"))
            val image_byte = pet_cursor.getBlob(pet_cursor.getColumnIndex("image"))
            val image_bitmap = byteToBitmap(image_byte)

            val public_cursor2 = publicDb.rawQuery(
                "select * from Public where pet_id = ?", arrayOf(petId)
            )
            public_cursor2.moveToFirst()
            val date = public_cursor2.getString(public_cursor2.getColumnIndex("date"))
            val description = public_cursor2.getString(public_cursor2.getColumnIndex("description"))

            myPublicList.add(
                MyPublicCard(nickname, breed, age, sex, image_bitmap!!, date, description)
            )
        }
    }

    fun byteToBitmap(imgByte: ByteArray?): Bitmap? {
        var imgByte = imgByte
        var input: InputStream? = null
        var bitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        options.inSampleSize = 1
        input = ByteArrayInputStream(imgByte)
        val softRef = SoftReference(
            BitmapFactory.decodeStream(
                input, null, options
            )
        )
        bitmap = softRef.get()
        if (imgByte != null) {
            imgByte = null
        }
        try {
            if (input != null) {
                input.close()
            }
        } catch (e: IOException) {
            // 异常捕获
            e.printStackTrace()
        }
        return bitmap
    }
}