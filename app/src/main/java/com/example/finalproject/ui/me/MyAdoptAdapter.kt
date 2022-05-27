package com.example.finalproject.ui.me

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.MyAdoptCard
import com.example.finalproject.R

class MyAdoptAdapter(val myAdoptList: List<MyAdoptCard>) :
    RecyclerView.Adapter<MyAdoptAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val public_time_ma = view.findViewById<TextView>(R.id.public_time_ma)
        val adopt_ma = view.findViewById<TextView>(R.id.adopt_ma)
        val description_ma = view.findViewById<TextView>(R.id.description_ma)
        val cardBreed_ma = view.findViewById<TextView>(R.id.cardBreed_ma)
        val cardImage_ma = view.findViewById<ImageView>(R.id.cardImage_ma)
        val sexPic_ma = view.findViewById<ImageView>(R.id.sexPic_ma)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdoptAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.my_adopt_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdoptAdapter.ViewHolder, position: Int) {
        val adopt_card = myAdoptList[position]
        holder.public_time_ma.text = adopt_card.date
        holder.cardBreed_ma.text = adopt_card.breed
        holder.description_ma.text = adopt_card.description
        if (adopt_card.sex == "雌性") {
            holder.sexPic_ma.setImageResource(R.drawable.ic_female)
        } else {
            holder.sexPic_ma.setImageResource(R.drawable.ic_male)
        }
        holder.cardImage_ma.setImageBitmap(adopt_card.image)
    }

    override fun getItemCount(): Int = myAdoptList.size
}