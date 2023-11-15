package com.example.neobis_android_inventory_app

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.neobis_android_inventory_app.Presenter.ProductContract
import com.example.neobis_android_inventory_app.Presenter.ProductPresenter
import com.example.neobis_android_inventory_app.database.DataProduct
import com.example.neobis_android_inventory_app.database.ProductDatabase
import com.example.neobis_android_inventory_app.database.ProductRepository
import com.example.neobis_android_inventory_app.databinding.FragmentAddBinding
import com.github.dhaval2404.imagepicker.ImagePicker

class AddFragment : Fragment(),ProductContract.View {
    private lateinit var binding: FragmentAddBinding
    private lateinit var presenter: ProductPresenter
    private var imageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAdd.setOnClickListener {
            insertProduct()
        }
        buttonClickListener()
    }

    private fun buttonClickListener() {
        presenter = ProductPresenter(requireContext())
        presenter.attachView(this)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.buttonCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageHolder.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }


    }
    @SuppressLint("SuspiciousIndentation")
    private fun insertProduct() {
        val imagePath = imageUri.toString()
        val name = binding.inputName.text.toString()
        val priceStr = binding.inputPrice.text.toString()
        val manufacturer = binding.inputManufacturer.text.toString()
        val quantityStr = binding.inputQuantity.text.toString()
            if (inputCheck(name, priceStr, manufacturer, quantityStr)) {
                if (priceStr.isNotEmpty() && quantityStr.isNotEmpty()) {
                    val price = priceStr.toInt()
                    val quantity = quantityStr.toInt()

                    val dataproduct = DataProduct(

                        imagePath = imagePath,
                        name =name,
                        price = price,
                        manufacturer =manufacturer,
                        quantity =quantity
                    )
                    presenter.insertProduct(dataproduct)
                    Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG)
                        .show()
                findNavController().navigateUp()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
            }

    }
    private fun inputCheck(name: String,
                            priceStr: String,
                           manufacturer: String,
                           quantityStr: String): Boolean{
        return !(TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(priceStr)  ||
                TextUtils.isEmpty(manufacturer)||
                        TextUtils.isEmpty(quantityStr))
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            imageUri = data.data

            Glide.with(this).load(imageUri).into(binding.imageProduct)
        }
    }

    override fun showProducts(products: List<DataProduct>) {}
    override fun showError(message: String) {}

}