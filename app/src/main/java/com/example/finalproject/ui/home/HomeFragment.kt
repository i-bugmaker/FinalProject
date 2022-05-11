package com.example.finalproject.ui.home

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.Card
import com.example.finalproject.CardList
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.databinding.FragmentInfoBinding
import com.example.finalproject.db.PetDatabaseHelper
import com.example.finalproject.db.PublicDatabaseHelper

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
        val pet_cursor = petDb.rawQuery("select * from Pet", null)

        val publicDbHelper = PublicDatabaseHelper(requireContext(), "public.db", 2)
        val publicDb = publicDbHelper.writableDatabase


        CardList.cardList.clear()
        if (pet_cursor.moveToFirst()) {
            do {
// 遍历Cursor对象，取出数据并打印
                val imageHex = pet_cursor.getBlob(pet_cursor.getColumnIndex("image"))
                val bit = BitmapFactory.decodeByteArray(imageHex, 0, imageHex.size)
                val nickname = pet_cursor.getString(pet_cursor.getColumnIndex("nickname"))
                val breed = pet_cursor.getString(pet_cursor.getColumnIndex("breed"))
                val age = pet_cursor.getString(pet_cursor.getColumnIndex("age"))
                val sex = pet_cursor.getString(pet_cursor.getColumnIndex("sex"))
                val pet_id = pet_cursor.getString(pet_cursor.getColumnIndex("id"))
                val public_cursor = publicDb.rawQuery("select * from Public where id = ?", arrayOf(pet_id))
                public_cursor.moveToFirst()
                val description = public_cursor.getString(public_cursor.getColumnIndex("description"))
                val publicTime = public_cursor.getString(public_cursor.getColumnIndex("date"))
                val username = public_cursor.getString(public_cursor.getColumnIndex("contact"))
                CardList.cardList.add(0, Card(pet_id,nickname, breed, age, sex, bit,description,publicTime,username))
                val adapter = CardAdapter(requireContext(),cardList)
                println(requireContext())
                binding.recyclerView.adapter = adapter
            } while (pet_cursor.moveToNext())
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