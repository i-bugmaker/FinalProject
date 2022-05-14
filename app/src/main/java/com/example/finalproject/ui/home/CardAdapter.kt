package com.example.finalproject.ui.home

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.*
import com.example.finalproject.db.AdoptDatabaseHelper
import com.example.finalproject.db.PetDatabaseHelper
import java.security.AccessController.getContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CardAdapter(mContext: Context, val cardList: List<Card>) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    val adoptDbHelper = AdoptDatabaseHelper(mContext, "adopt.db", 2)
    val adoptDb = adoptDbHelper.writableDatabase
    val adopt_cursor = adoptDb.rawQuery("select * from Adopt", null)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val petImage: ImageView = view.findViewById(R.id.cardImage)
        val petBreed: TextView = view.findViewById(R.id.cardBreed)
        val description: TextView = view.findViewById(R.id.description)
        val publicTime: TextView = view.findViewById(R.id.public_time)
        val adopt_btn: Button = view.findViewById(R.id.adopt_btn)
        val adopt_text: TextView = view.findViewById(R.id.adopt)
        val sex:ImageView = view.findViewById(R.id.sexPic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item, parent, false)

        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        val card = cardList[position]
        println("cardList.size = "+ cardList.size)


        holder.petImage.setImageBitmap(card.image)
        holder.petBreed.text = card.breed
        holder.description.text = card.description
        holder.publicTime.text = card.publicTime
        holder.adopt_btn.setOnClickListener {
            holder.adopt_text.text = "已领养"
            holder.adopt_btn.isEnabled = false
            card.isAdopted = true
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formatted = current.format(formatter)

            adopt_cursor.moveToFirst()
            adoptDb.execSQL(
                "insert into Adopt (username,pet_id,date) values(?, ?, ?)",
                arrayOf(card.username, card.pet_id, formatted)
            )
        }
        holder.adopt_btn.isEnabled = !card.isAdopted
        holder.adopt_text.text = if (card.isAdopted == true) "已领养" else "待领养"
        if (card.sex=="雌性") holder.sex.setImageResource(R.drawable.ic_female)
    }

    override fun getItemCount(): Int =
        cardList.size
}
