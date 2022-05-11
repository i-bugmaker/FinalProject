package com.example.finalproject

import android.graphics.Bitmap

//class Card(val name:String, val imageId: Int)

class Card(
    val pet_id:String,
    val nickname: String,
    val breed: String,
    val age: String,
    val sex: String,
    val image: Bitmap,

    val description:String,
    val publicTime:String,
    val username:String,
    var isAdopted:Boolean = false
)