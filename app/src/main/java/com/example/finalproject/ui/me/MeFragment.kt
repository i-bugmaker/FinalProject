package com.example.finalproject.ui.me

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.databinding.FragmentMeBinding

class MeFragment : Fragment() {

    private var _binding: FragmentMeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val meViewModel =
            ViewModelProvider(this).get(MeViewModel::class.java)

        _binding = FragmentMeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textMe
//        meViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        val adapter =ArrayAdapter<String>(requireContext(), R.layout.simple_list_item_1, meViewModel.data)
        val listViewMe: ListView = binding.listviewMe
        listViewMe.adapter = adapter
        listViewMe.setOnItemClickListener { parent, view, position, id ->
            when(position) {
                0 -> Toast.makeText(requireContext(), meViewModel.data[position], Toast.LENGTH_SHORT).show()
                1 -> Toast.makeText(requireContext(), meViewModel.data[position], Toast.LENGTH_SHORT).show()                1 -> Toast.makeText(requireContext(), meViewModel.data[position], Toast.LENGTH_SHORT).show()
                2 -> Toast.makeText(requireContext(), meViewModel.data[position], Toast.LENGTH_SHORT).show()
                3 -> Toast.makeText(requireContext(), meViewModel.data[position], Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}