package com.example.notebookscatalog.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.notebookscatalog.DeviceListAdapter
import com.example.notebookscatalog.NotebooksApplication
import com.example.notebookscatalog.R
import com.example.notebookscatalog.data.DeviceItem
import com.example.notebookscatalog.databinding.FragmentEditBinding
import com.example.notebookscatalog.databinding.FragmentListBinding
import com.example.notebookscatalog.db.entities.Brand
import com.example.notebookscatalog.helpers.UriToDrawableConverter
import com.example.notebookscatalog.interfaces.IFragmentCommunication
import com.example.notebookscatalog.viewmodels.BrandViewModel
import com.example.notebookscatalog.viewmodels.BrandViewModelFactory
import com.example.notebookscatalog.viewmodels.DeviceListviewModel

class EditFragment(
    private val navigation: IFragmentCommunication,
    private val brandViewModel: BrandViewModel
) : Fragment(R.layout.fragment_edit) {

    private var imgUri: Uri? = null

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private val deviceListViewModel: DeviceListviewModel by activityViewModels()
    private var selectedItem: DeviceItem = DeviceItem(null, "", null, null)

    private lateinit var fContext: Context

    private val selImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imgUri = data?.data
            binding.ivDeviceEdit.setImageURI(imgUri)
            selectedItem.img = UriToDrawableConverter.uriToDrawable(imgUri.toString(), requireContext())
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
        val selItemId = deviceListViewModel.selectedIndex.value!!
        selectedItem = deviceListViewModel.getDeviceAtPosition(selItemId)

        with(binding) {
            etModelEdit.setText(selectedItem.model)
            etScreenEdit.setText(selectedItem.screen)
            etHardwareEdit.setText(selectedItem.hardware)
            ivDeviceEdit.setImageDrawable(selectedItem.img)

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