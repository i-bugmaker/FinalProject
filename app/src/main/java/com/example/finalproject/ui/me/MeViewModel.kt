package com.example.finalproject.ui.me

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MeViewModel() : ViewModel() {
    //    private val _text = MutableLiveData<String>().apply {
//        value = "This is me Fragment"
//    }
//    val text: LiveData<String> = _text
    val data = listOf(
        "个人信息", "领养信息",  "我的发布"
    )

    val sexOption = listOf(
        "男", "女","null"
    )

}