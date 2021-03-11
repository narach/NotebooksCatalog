package com.example.notebookscatalog.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.notebookscatalog.DeviceListAdapter
import com.example.notebookscatalog.R
import com.example.notebookscatalog.data.DeviceItem
import com.example.notebookscatalog.databinding.FragmentEditBinding
import com.example.notebookscatalog.databinding.FragmentListBinding
import com.example.notebookscatalog.helpers.UriToDrawableConverter
import com.example.notebookscatalog.interfaces.IFragmentCommunication
import com.example.notebookscatalog.viewmodels.DeviceListviewModel

class EditFragment(private val navigation: IFragmentCommunication) : Fragment(R.layout.fragment_edit) {

    private var imgUri: Uri? = null
    private val selImgCode = 1

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private val deviceListViewModel: DeviceListviewModel by activityViewModels()
    private var selectedItem: DeviceItem = DeviceItem(null, "", null, null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
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
//                deviceListViewModel.updateDeviceAtPosition(se)
                navigation.listDevices()
            }

            ivDeviceEdit.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, selImgCode)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == AppCompatActivity.RESULT_OK && requestCode == selImgCode) {
            imgUri = data?.data
            binding.ivDeviceEdit.setImageURI(imgUri)
            selectedItem.img = UriToDrawableConverter.uriToDrawable(imgUri.toString(), requireContext())
        }
    }

}