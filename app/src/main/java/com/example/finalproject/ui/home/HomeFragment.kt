package com.example.finalproject.ui.home

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.Card
import com.example.finalproject.CardList
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.databinding.FragmentInfoBinding
import com.example.finalproject.db.PetDatabaseHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val binding2 = FragmentInfoBinding.inflate(inflater, container, false)
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val cardList = CardList.cardList
//        if (cardList.isEmpty()) {
        Toast.makeText(requireContext(), "初始化数据", Toast.LENGTH_SHORT).show()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
//            cardList.apply {
//                add(Card("狗", R.drawable.banner0))
//                add(Card("猫", R.drawable.banner1))
//                add(Card("鹦鹉", R.drawable.banner2))
//                add(Card("金鱼", R.drawable.banner3))
//
//            }
        val petDbHelper = PetDatabaseHelper(requireContext(), "pet.db", 2)
        val petDb = petDbHelper.writableDatabase

//        val nickname = binding2.nickname.text.toString()
//        val breed = binding2.breed.text.toString()
//        val age = binding2.age.text.toString()
//        val sex =
//            if (binding2.rbFemale.isChecked) binding2.rbFemale.text.toString() else binding2.rbMale.text.toString()
        val cursor = petDb.rawQuery("select * from Pet", null)
        CardList.cardList.clear()
        if (cursor.moveToFirst()) {
            do {
// 遍历Cursor对象，取出数据并打印
                val imageHex = cursor.getBlob(cursor.getColumnIndex("image"))
                val bit = BitmapFactory.decodeByteArray(imageHex, 0, imageHex.size)
                val nickname = cursor.getString(cursor.getColumnIndex("nickname"))
                val breed = cursor.getString(cursor.getColumnIndex("breed"))
                val age = cursor.getString(cursor.getColumnIndex("age"))
                val sex = cursor.getString(cursor.getColumnIndex("sex"))
                CardList.cardList.add(0, Card(nickname, breed, age, sex, bit))
                val adapter = CardAdapter(cardList)
                binding.recyclerView.adapter = adapter
            } while (cursor.moveToNext())
        }

//        }

//        homeViewModel.cardList
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}