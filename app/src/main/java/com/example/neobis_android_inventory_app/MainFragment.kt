package com.example.neobis_android_inventory_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neobis_android_inventory_app.Adapter.ClickListener
import com.example.neobis_android_inventory_app.Adapter.RecyclerViewAdapter
import com.example.neobis_android_inventory_app.Presenter.ProductContract
import com.example.neobis_android_inventory_app.Presenter.ProductPresenter
import com.example.neobis_android_inventory_app.database.DataProduct

import com.example.neobis_android_inventory_app.databinding.FragmentMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainFragment : Fragment(),ProductContract.MainView,ClickListener {
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
        //Adapter Recyclerview
        adapter = RecyclerViewAdapter(this)
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

    override fun onBottomSheetClick(position: Int, dataProduct: DataProduct) {
        val dialog = BottomSheet.newInstance(BottomSheet.BottomSheetType.MAIN)
        dialog.show(parentFragmentManager, getString(R.string.modal_bottom_sheet))
    }

    override fun onRecyclerViewItemClick( dataProduct: DataProduct) {
        val action = MainFragmentDirections.actionMainFragmentToUpdateFragment(dataProduct)
        findNavController().navigate(action)
    }

    override fun showError(message: String) {}

}