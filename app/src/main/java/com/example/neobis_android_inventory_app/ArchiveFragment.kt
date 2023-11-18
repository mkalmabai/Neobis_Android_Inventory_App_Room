package com.example.neobis_android_inventory_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.neobis_android_inventory_app.Adapter.ClickListener
import com.example.neobis_android_inventory_app.Adapter.RecyclerViewAdapter
import com.example.neobis_android_inventory_app.Presenter.ArchivePresenter
import com.example.neobis_android_inventory_app.Presenter.BottomSheetPresenter
import com.example.neobis_android_inventory_app.Presenter.ProductContract
import com.example.neobis_android_inventory_app.Presenter.ProductPresenter
import com.example.neobis_android_inventory_app.database.DataProduct
import com.example.neobis_android_inventory_app.databinding.FragmentArchiveBinding



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

    override fun onBottomSheetClick(position: Int, dataProduct: DataProduct) {
        val dialog = BottomSheet.newInstance(BottomSheet.BottomSheetType.ARCHIVE)
        dialog.show(parentFragmentManager, getString(R.string.modal_bottom_sheet))
    }

    override fun onRecyclerViewItemClick(dataProduct: DataProduct) {
        val action =ArchiveFragmentDirections.actionArchiveFragmentToUpdateFragment(dataProduct)
        findNavController().navigate(action)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun showProducts(products: List<DataProduct>) {
        adapter.dataProduct = products
        adapter.notifyDataSetChanged()
        Log.e("Test", "showAllProductsFragment")
    }

    override fun showError(message: String) {
        TODO("Not yet implemented")
    }
}