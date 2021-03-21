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
import com.bumptech.glide.Glide
import com.example.notebookscatalog.R
import com.example.notebookscatalog.databinding.FragmentEditBinding
import com.example.notebookscatalog.db.entities.Device
import com.example.notebookscatalog.interfaces.IFragmentCommunication
import com.example.notebookscatalog.viewmodels.DeviceViewModel

class EditFragment(
    private val navigation: IFragmentCommunication,
    private val deviceViewModel: DeviceViewModel
) : Fragment(R.layout.fragment_edit) {

    private var imgUri: Uri? = null

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    lateinit var selectedItem: Device

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
            etModelEdit.setText(selectedItem.model)
            etScreenEdit.setText(selectedItem.screen)
            etHardwareEdit.setText(selectedItem.hardware)

            ivDeviceEdit.setImageURI(Uri.parse(selectedItem.imgUri))
            Glide.with(fContext).load(selectedItem.imgUri).into(ivDeviceEdit)

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

        }
    }

}