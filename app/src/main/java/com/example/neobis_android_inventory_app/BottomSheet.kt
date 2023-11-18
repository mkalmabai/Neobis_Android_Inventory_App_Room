package com.example.neobis_android_inventory_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.neobis_android_inventory_app.databinding.MainBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet: BottomSheetDialogFragment() {
    private lateinit var binding: MainBottomSheetBinding
    private lateinit var bottomSheetType: BottomSheetType

    enum class BottomSheetType{
        MAIN,
        ARCHIVE
    }
    companion object {
        private const val ARG_BOTTOM_SHEET_TYPE = "bottomSheetType"

        fun newInstance(bottomSheetType: BottomSheetType): BottomSheet {
            val args = Bundle().apply {
                putSerializable(ARG_BOTTOM_SHEET_TYPE, bottomSheetType)
            }
            return BottomSheet().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainBottomSheetBinding.inflate(inflater, container, false)
        isCancelable =true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // createDialog()


        bottomSheetType = arguments?.getSerializable(ARG_BOTTOM_SHEET_TYPE) as? BottomSheetType
            ?: BottomSheetType.MAIN

        if (bottomSheetType == BottomSheetType.MAIN) {
            // Set up UI for the main fragment bottom sheet
            // For example:
            // binding.textView.text = "Main Bottom Sheet"
            // Use the layout for the main_bottom_sheet
            View.inflate(context, R.layout.main_bottom_sheet, binding.root)
        } else {
            // Set up UI for the archive fragment bottom sheet
            // For example:
            // binding.textView.text = "Archive Bottom Sheet"
            val vieww = layoutInflater.inflate(R.layout.archive_bottom_sheet, null)
            dialog?.setCancelable(true)
            dialog?.setContentView(vieww)
            dialog?.show()
        }

    }
//    private fun createDialog (){
//
//
//            val dialog = BottomSheetDialog(requireContext())
//
//            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
//
//            val btnClose = binding.bottomSheetArchive
//
//            btnClose.setOnClickListener {
//
//                dialog.dismiss()
//            }
//
//            dialog.setCancelable(true)
//
//            dialog.setContentView(view)
//
//            dialog.show()
//
//    }
}