package com.example.finalproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.Card
import com.example.finalproject.R

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

//    val cardList = ArrayList<Card>()


//            cardList.add(Card("Apple", R.drawable.banner1))
//            cardList.add(Card("Banana", R.drawable.banner1))
//            cardList.add(Card("Orange", R.drawable.banner1))
//            cardList.add(Card("Watermelon", R.drawable.banner1))
//            cardList.add(Card("Pear", R.drawable.banner1))
//            cardList.add(Card("Grape", R.drawable.banner1))
//            cardList.add(Card("Pineapple", R.drawable.banner1))
//            cardList.add(Card("Strawberry", R.drawable.banner1))
//            cardList.add(Card("Cherry", R.drawable.banner1))
//            cardList.add(Card("Mango", R.drawable.banner1))


}