package com.example.neobis_android_inventory_app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.neobis_android_inventory_app.Presenter.ProductContract
import com.example.neobis_android_inventory_app.Presenter.ProductPresenter
import com.example.neobis_android_inventory_app.database.DataProduct
import com.example.neobis_android_inventory_app.databinding.FragmentArchiveBinding
import com.example.neobis_android_inventory_app.databinding.FragmentUpdateBinding
import com.github.dhaval2404.imagepicker.ImagePicker


class UpdateFragment : Fragment(),ProductContract.MainView {
    private lateinit var binding: FragmentUpdateBinding
    private  val args by navArgs<UpdateFragmentArgs>()
    private lateinit var presenter: ProductPresenter
    private var changedImage = false
    private var imageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        binding.apply {
            Glide.with(binding.updateImageProduct).load(Uri.parse(args.dataProdactArg.imagePath)).into(binding.updateImageProduct)
            updateInputName.setText(args.dataProdactArg.name)
            updateInputPrice.setText(args.dataProdactArg.price.toString())
            updateInputManufacturer.setText(args.dataProdactArg.manufacturer)
            updateInputQuantity.setText(args.dataProdactArg.quantity.toString())

        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUpdate.setOnClickListener {
            updateProduct()
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
        binding.updateimageHolder.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }
    }
    private fun updateProduct() {
        val imagePath = if(changedImage){ imageUri.toString()
        }else{args.dataProdactArg.imagePath}
        val name = binding.updateInputName.text.toString()
        val price = binding.updateInputPrice.text.toString()
        val manufacturer = binding.updateInputManufacturer.text.toString()
        val quantity = binding.updateInputQuantity.text.toString()
        println(manufacturer)
        if (inputCheck(name, price, manufacturer, quantity)) {
                val dataproduct = DataProduct(
                    args.dataProdactArg.id,
                    imagePath = imagePath,
                    name =name,
                    price = price,
                    manufacturer =manufacturer,
                    quantity =quantity,
                    args.dataProdactArg.archive
                )
                showUpdateDialog(dataproduct)
            } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }
    @SuppressLint("InflateParams")
    private fun showUpdateDialog(dataproduct: DataProduct) {
        val inflater = requireActivity().layoutInflater
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(inflater.inflate(R.layout.dialog_update,null))
            .setPositiveButton(R.string.save) { dialog, _ ->
                presenter.updateProduct(dataproduct)
                Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel_dialog) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    private fun inputCheck(name: String, price: String, manufacturer: String, quantity: String): Boolean{
        return !(TextUtils.isEmpty(name) ||TextUtils.isEmpty(price)  || TextUtils.isEmpty(manufacturer)|| TextUtils.isEmpty(quantity))
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            imageUri = data.data
            Glide.with(this).load(imageUri).into(binding.updateImageProduct)
            changedImage =true
        }
    }
    override fun showProducts(products: List<DataProduct>) {}
    override fun showError(message: String) {}

}