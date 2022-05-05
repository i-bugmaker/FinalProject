package com.example.finalproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.Card
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeBinding

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
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        homeViewModel.cardList.apply {
            add(Card("Apple", R.drawable.banner1))
            add(Card("Apple", R.drawable.banner1))
            add(Card("Banana", R.drawable.banner1))
            add(Card("Orange", R.drawable.banner1))
            add(Card("Watermelon", R.drawable.banner1))
            add(Card("Pear", R.drawable.banner1))
            add(Card("Grape", R.drawable.banner1))
            add(Card("Pineapple", R.drawable.banner1))
            add(Card("Strawberry", R.drawable.banner1))
            add(Card("Cherry", R.drawable.banner1))
            add(Card("Mango", R.drawable.banner1))
        }
        val adapter = CardAdapter(homeViewModel.cardList)
        binding.recyclerView.adapter = adapter
        homeViewModel.cardList
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}