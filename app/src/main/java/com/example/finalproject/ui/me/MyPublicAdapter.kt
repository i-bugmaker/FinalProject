package com.example.finalproject.ui.me

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.MyPublicCard
import com.example.finalproject.R

class MyPublicAdapter(val myPublicList: List<MyPublicCard>) :
    RecyclerView.Adapter<MyPublicAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val public_time_mp = view.findViewById<TextView>(R.id.public_time_mp)
        val adopt_mp = view.findViewById<TextView>(R.id.adopt_mp)
        val description_mp = view.findViewById<TextView>(R.id.description_mp)
        val cardBreed_mp = view.findViewById<TextView>(R.id.cardBreed_mp)
        val cardImage_mp = view.findViewById<ImageView>(R.id.cardImage_mp)
        val sexPic_mp = view.findViewById<ImageView>(R.id.sexPic_mp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPublicAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.my_public_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyPublicAdapter.ViewHolder, position: Int) {
        val public_card = myPublicList[position]
        holder.public_time_mp.text = public_card.date
        holder.cardBreed_mp.text = public_card.breed
        holder.description_mp.text = public_card.description
        if (public_card.sex == "雌性") {
            holder.sexPic_mp.setImageResource(R.drawable.ic_female)
        } else {
            holder.sexPic_mp.setImageResource(R.drawable.ic_male)
        }
        holder.cardImage_mp.setImageBitmap(public_card.image)
    }

    override fun getItemCount(): Int = myPublicList.size
}