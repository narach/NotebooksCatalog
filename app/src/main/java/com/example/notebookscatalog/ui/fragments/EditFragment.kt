package com.example.notebookscatalog.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.notebookscatalog.R
import com.example.notebookscatalog.databinding.FragmentEditBinding
import com.example.notebookscatalog.db.entities.Brand
import com.example.notebookscatalog.db.entities.Device
import com.example.notebookscatalog.db.enums.DeviceType
import com.example.notebookscatalog.interfaces.IFragmentCommunication
import com.example.notebookscatalog.viewmodels.BrandViewModel

class EditFragment(
    private val navigation: IFragmentCommunication,
    private val brandViewModel: BrandViewModel
) : Fragment(R.layout.fragment_edit) {

    private var imgUri: Uri? = null

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

//    private var selectedItem: DeviceItem = DeviceItem(null, "", null, null)
    private val selectedItem: Device = Device(null, "", "", null, "", "", DeviceType.NOTEBOOK)

    private lateinit var fContext: Context

    private val selImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imgUri = data?.data
            binding.ivDeviceEdit.setImageURI(imgUri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.fContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            etModelEdit.setText(selectedItem.model)
            etScreenEdit.setText(selectedItem.screen)
            etHardwareEdit.setText(selectedItem.hardware)
            ivDeviceEdit.setImageURI(Uri.parse(selectedItem.imgUri))

            binding.btnSave.setOnClickListener {
                selectedItem.model = etModelEdit.text.toString()
                selectedItem.hardware = etHardwareEdit.text.toString()
                selectedItem.screen = etScreenEdit.text.toString()
                navigation.listDevices()
            }

            ivDeviceEdit.setOnClickListener {
                Intent(Intent.ACTION_PICK).also {
                    it.type = "image/*"
                    selImageLauncher.launch(it)
                }
            }

            brandViewModel.allBrands.observe(
                viewLifecycleOwner, Observer { brands ->
                    val spBrandsAdapter = ArrayAdapter(
                        fContext,
                        android.R.layout.simple_spinner_item,
                        brands
                    )
                    spBrand.adapter = spBrandsAdapter
                }
            )

            spBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val brand = spBrand.selectedItem as Brand
                    selectedItem.model = brand.name
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedItem.model = ""
                }
            }

        }
    }

}