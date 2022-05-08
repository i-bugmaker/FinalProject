package com.example.finalproject.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.Card
import com.example.finalproject.CardList
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentHomeBinding
import com.example.finalproject.databinding.FragmentInfoBinding
import com.example.finalproject.ui.home.CardAdapter
import com.example.finalproject.ui.home.HomeViewModel

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val infoViewModel =
            ViewModelProvider(this).get(InfoViewModel::class.java)

        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textInfo
//        infoViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val binding2 = FragmentHomeBinding.inflate(inflater, container, false)
        //发布按钮事件
        binding.publish.setOnClickListener {
            val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
            val nickname = binding.nickname.text.toString()
//            val image = binding.image.toString().toInt()
            CardList.cardList.add(0,Card(nickname, R.drawable.banner3))
            val adapter = CardAdapter(CardList.cardList)
            adapter.notifyItemInserted(0)
            Toast.makeText(requireContext(), "发布成功", Toast.LENGTH_SHORT).show()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}