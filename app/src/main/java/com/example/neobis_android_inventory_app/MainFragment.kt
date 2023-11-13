package com.example.neobis_android_inventory_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neobis_android_inventory_app.Adapter.RecyclerViewAdapter

import com.example.neobis_android_inventory_app.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        val adapter = RecyclerViewAdapter()
        val recyclerview = binding.recyclerview
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(requireContext())



        binding.floatingActionButton.setOnClickListener {
           findNavController().navigate(R.id.action_mainFragment_to_addFragment3)
        }

        return view
    }
}