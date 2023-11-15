package com.example.neobis_android_inventory_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neobis_android_inventory_app.Adapter.RecyclerViewAdapter
import com.example.neobis_android_inventory_app.Presenter.ProductContract
import com.example.neobis_android_inventory_app.Presenter.ProductPresenter
import com.example.neobis_android_inventory_app.database.DataProduct

import com.example.neobis_android_inventory_app.databinding.FragmentMainBinding

class MainFragment : Fragment(),ProductContract.View {
    private lateinit var binding: FragmentMainBinding
    private lateinit var presenter: ProductPresenter
    var products = emptyList<DataProduct>()
    private lateinit var adapter: RecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = RecyclerViewAdapter()
        val recyclerview = binding.recyclerview
        recyclerview.adapter = adapter
        recyclerview.layoutManager = GridLayoutManager(requireContext(),2)

        getAllProducts()



        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment3)
        }

        return view
    }

    private fun getAllProducts() {
        presenter = ProductPresenter(requireContext())
        presenter.attachView(this)
        presenter.getAllProducts()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showProducts(products: List<DataProduct>) {
        adapter.dataProduct = products
        adapter.notifyDataSetChanged()
        Log.e("Test", "showAllProductsFragment")
    }

    override fun showInsertSuccessMessage() {
        TODO("Not yet implemented")
    }

    override fun showUpdateSuccessMessage() {
        TODO("Not yet implemented")
    }

    override fun showDeleteSuccessMessage() {
        TODO("Not yet implemented")
    }


}