package com.example.neobis_android_inventory_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_inventory_app.Presenter.ProductContract
import com.example.neobis_android_inventory_app.Presenter.ProductPresenter
import com.example.neobis_android_inventory_app.database.DataProduct
import com.example.neobis_android_inventory_app.database.ProductDatabase
import com.example.neobis_android_inventory_app.database.ProductRepository
import com.example.neobis_android_inventory_app.databinding.FragmentAddBinding

class AddFragment : Fragment(),ProductContract{
    private lateinit var binding: FragmentAddBinding
    private lateinit var presenter: ProductContract.Presenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        presenter = ProductPresenter(requireContext())
        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        insertProduct()
        buttonClickListener()
    }

    private fun buttonClickListener() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageHolder.setOnClickListener {

        }

    }

    private fun insertProduct() {
        binding.buttonAdd.setOnClickListener {
//            val imagePath = ""
            val name = binding.inputName.text.toString()
            val text2 = 0
            val manufacturer = "manufacturer"
            val quantity = 0

            val dataproduct = DataProduct( name = name, price = text2, manufacturer = manufacturer, quantity=quantity)
            presenter.insertProduct(dataproduct)
        }
    }

//



}