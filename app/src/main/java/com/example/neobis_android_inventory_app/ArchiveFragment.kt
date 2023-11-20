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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.neobis_android_inventory_app.Adapter.ClickListener
import com.example.neobis_android_inventory_app.Adapter.RecyclerViewAdapter
import com.example.neobis_android_inventory_app.Presenter.ArchivePresenter
import com.example.neobis_android_inventory_app.Presenter.ProductContract
import com.example.neobis_android_inventory_app.database.DataProduct
import com.example.neobis_android_inventory_app.databinding.FragmentArchiveBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


class ArchiveFragment : Fragment(), ProductContract.MainView, ClickListener {
    private lateinit var binding: FragmentArchiveBinding
    private lateinit var presenter: ArchivePresenter
    private lateinit var adapter: RecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentArchiveBinding.inflate(inflater, container, false)

        adapter = RecyclerViewAdapter(this)
        val recyclerview = binding.recyclerview
        recyclerview.adapter = adapter
        recyclerview.layoutManager = GridLayoutManager(requireContext(),2)


        getAllProducts()
        search()

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_archiveFragment_to_addFragment)
        }
        return binding.root

    }
    private fun getAllProducts() {
        presenter = ArchivePresenter(requireContext())
        presenter.attachView(this)
        presenter.getAllProducts()
    }
    private fun search() {
        binding.archiveSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
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
    override fun onBottomSheetClick(position: Int, dataProduct: DataProduct) {
        val dialog = BottomSheetDialog(requireContext())
        val view  = layoutInflater.inflate(R.layout.archive_bottom_sheet,null)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()

        val restore =  dialog.findViewById<TextView>(R.id.bottom_sheet_restore)
        val delete = dialog.findViewById<TextView>(R.id.bottom_sheet_delete)
        restore?.setOnClickListener {
            dialog.dismiss()
            showRestoreDialog(dataProduct)
        }
        delete?.setOnClickListener {
            dialog.dismiss()
            showDeleteDialog(dataProduct)
        }
    }

    private fun showDeleteDialog(dataProduct: DataProduct) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удалить ${dataProduct.name} из архива?")
            .setPositiveButton("Удалить") { _, _ ->
              presenter.deleteProduct(dataProduct)
                presenter.getAllProducts()
            }
            .setNegativeButton("Отмена", null)
            .create()
            .show()
    }

    private fun showRestoreDialog(dataProduct: DataProduct) {
        AlertDialog.Builder(requireContext())
            .setTitle("Архивировать ${dataProduct.name} из каталога?")
            .setPositiveButton("Да") { _, _ ->
            presenter.restoreProduct(dataProduct)
                presenter.getAllProducts()
            }
            .setNegativeButton("Нет", null)
            .create()
            .show()
    }

    override fun onRecyclerViewItemClick(dataProduct: DataProduct) {
        val action =ArchiveFragmentDirections.actionArchiveFragmentToUpdateFragment(dataProduct)
        findNavController().navigate(action)
    }


    override fun showProducts(products: List<DataProduct>) {
        adapter.updateProduct(products)
    }
    override fun showError(message: String) {
        TODO("Not yet implemented")
    }
}