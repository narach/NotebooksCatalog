package com.example.notebookscatalog.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.notebookscatalog.R
import com.example.notebookscatalog.databinding.FragmentAddBinding
import com.example.notebookscatalog.db.entities.Brand
import com.example.notebookscatalog.db.entities.Device
import com.example.notebookscatalog.db.enums.DeviceType
import com.example.notebookscatalog.interfaces.IFragmentCommunication
import com.example.notebookscatalog.viewmodels.BrandViewModel
import com.example.notebookscatalog.viewmodels.DeviceViewModel

class AddFragment(
    private val navigation: IFragmentCommunication,
    private val brandViewModel: BrandViewModel,
    private val deviceViewModel: DeviceViewModel
) : Fragment(R.layout.fragment_add) {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var fContext: Context
    private var newDevice: Device = Device(null, "", "", "", "", "", DeviceType.Notebook)

    private val selImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imgUri = data?.data
            newDevice.imgUri = imgUri.toString()
            binding.ivDeviceAdd.setImageURI(imgUri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.fContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            // Brand selection processing
            brandViewModel.allBrands.observe(
                viewLifecycleOwner, Observer { brands ->
                    val spBrandsAdapter = ArrayAdapter(
                        fContext,
                        android.R.layout.simple_spinner_item,
                        brands
                    )
                    spBrandAdd.adapter = spBrandsAdapter
                }
            )
            spBrandAdd.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val brand = spBrandAdd.selectedItem as Brand
                    newDevice.brandName = brand.name
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    newDevice.brandName = ""
                }
            }

            // Model selection processing
            etModelAdd.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    newDevice.model = s.toString()
                }
            })

            // Device Type change processing
            rgDeviceType.setOnCheckedChangeListener { _, checkedId ->
                val selTypeButton = activity?.findViewById<RadioButton>(checkedId)
                val btnValue = selTypeButton?.text.toString()
                val deviceType = DeviceType.valueOf(btnValue)
                newDevice.deviceType = deviceType
            }

            ivDeviceAdd.setOnClickListener {
                Intent(Intent.ACTION_PICK).also {
                    it.type = "image/*"
                    selImageLauncher.launch(it)
                }
            }

            btnAddNew.setOnClickListener {
                newDevice.hardware = etHardwareAdd.text.toString()
                newDevice.screen = etScreenAdd.text.toString()
                deviceViewModel.insert(newDevice)
                navigation.listDevices()
            }
        }


    }

}