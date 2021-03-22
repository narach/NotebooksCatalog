package com.example.notebookscatalog.ui.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.notebookscatalog.R
import com.example.notebookscatalog.databinding.FragmentEditBinding
import com.example.notebookscatalog.interfaces.IFragmentCommunication
import com.example.notebookscatalog.viewmodels.DeviceViewModel

class EditFragment(
    private val navigation: IFragmentCommunication,
    private val deviceViewModel: DeviceViewModel
) : Fragment(R.layout.fragment_edit) {

    private var imgUri: Uri? = null

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

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
    ): View {
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

            deviceViewModel.selectedItem.observe(viewLifecycleOwner, Observer { device ->
                etModelEdit.setText(device.model)
                etScreenEdit.setText(device.screen)
                etHardwareEdit.setText(device.hardware)
                Glide.with(fContext).load(device.imgUri).into(ivDeviceEdit)
            })

            btnSave.setOnClickListener {

                deviceViewModel.selectedItem.value?.let { device ->
                    device.model = etModelEdit.text.toString()
                    device.hardware = etHardwareEdit.text.toString()
                    device.screen = etScreenEdit.text.toString()
                    imgUri?.let {
                        device.imgUri = it.toString()
                    }
                    deviceViewModel.update(device)
                }
                navigation.listDevices()
            }

            ivDeviceEdit.setOnClickListener {
                Intent(Intent.ACTION_PICK).also {
                    it.type = "image/*"
                    selImageLauncher.launch(it)
                }
            }

        }
    }

}