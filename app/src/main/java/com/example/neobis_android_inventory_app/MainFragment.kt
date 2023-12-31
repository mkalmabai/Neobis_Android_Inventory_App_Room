package com.example.neobis_android_inventory_app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
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
   return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecyclerViewAdapter(this)
        binding.recyclerview.adapter =adapter

        getAllProducts()
        search()

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment3)
        }



    }

    private fun search() {
        binding.mainSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let{
                    presenter.searchProduct(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    presenter.searchProduct(it)
                }
                return false
            }
        })
    }

    private fun getAllProducts() {
        presenter = ProductPresenter(requireContext())
        presenter.attachView(this)
        presenter.getAllProducts()
    }


    override fun showProducts(products: List<DataProduct>) {
        adapter.updateProduct(products)
    }


    override fun onBottomSheetClick(position: Int, dataProduct: DataProduct) {
        val dialog = BottomSheetDialog(requireContext())
        val view  = layoutInflater.inflate(R.layout.main_bottom_sheet,null)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

        val archive = dialog.findViewById<TextView>(R.id.bottom_sheet_archive)
        archive?.setOnClickListener {
            dialog.dismiss()
            showArchiveDialog(dataProduct)
        }
    }

    private fun showArchiveDialog(dataProduct: DataProduct) {
        AlertDialog.Builder(requireContext())
            .setTitle("Архивировать ${dataProduct.name} из каталога?")
            .setPositiveButton("Да") { _, _ ->
                presenter.archiveProduct(dataProduct)
                presenter.getAllProducts()

            }
            .setNegativeButton("Нет", null)
            .create()
            .show()
    }

    override fun onRecyclerViewItemClick( dataProduct: DataProduct) {
        val action = MainFragmentDirections.actionMainFragmentToUpdateFragment(dataProduct)
        findNavController().navigate(action)
    }

    override fun showError(message: String) {}

}