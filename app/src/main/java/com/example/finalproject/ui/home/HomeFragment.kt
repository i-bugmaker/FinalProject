package com.example.finalproject.ui.home

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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val cardList = CardList.cardList
//        if (cardList.isEmpty()) {
            Toast.makeText(requireContext(),"初始化数据",Toast.LENGTH_SHORT).show()
            val layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.layoutManager = layoutManager
//            cardList.apply {
//                add(Card("狗", R.drawable.banner0))
//                add(Card("猫", R.drawable.banner1))
//                add(Card("鹦鹉", R.drawable.banner2))
//                add(Card("金鱼", R.drawable.banner3))
//
//            }
            val adapter = CardAdapter(cardList)
            binding.recyclerView.adapter = adapter
//        }

//        homeViewModel.cardList
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}