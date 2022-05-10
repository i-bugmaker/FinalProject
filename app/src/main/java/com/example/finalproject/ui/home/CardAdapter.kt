package com.example.finalproject.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.*
import com.example.finalproject.db.PublicDatabaseHelper

class CardAdapter(val cardList: List<Card>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val petImage: ImageView = view.findViewById(R.id.cardImage)
        val petBreed: TextView = view.findViewById(R.id.cardBreed)
        val description: TextView = view.findViewById(R.id.description)
        val publicTime:TextView=view.findViewById(R.id.public_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
        val card = cardList[position]
        holder.petImage.setImageBitmap(card.image)
        holder.petBreed.text = card.breed
        holder.description.text = card.description
        holder.publicTime.text=card.publicTime
    }

    override fun getItemCount(): Int =
        cardList.size


}