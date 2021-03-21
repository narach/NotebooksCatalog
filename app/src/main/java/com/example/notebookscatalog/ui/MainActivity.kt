package com.example.notebookscatalog.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notebookscatalog.NotebooksApplication
import com.example.notebookscatalog.R
import com.example.notebookscatalog.databinding.ActivityMainBinding
import com.example.notebookscatalog.db.entities.Device
import com.example.notebookscatalog.interfaces.IFragmentCommunication
import com.example.notebookscatalog.ui.fragments.AddFragment
import com.example.notebookscatalog.ui.fragments.EditFragment
import com.example.notebookscatalog.ui.fragments.ListFragment
import com.example.notebookscatalog.viewmodels.BrandViewModel
import com.example.notebookscatalog.viewmodels.BrandViewModelFactory
import com.example.notebookscatalog.viewmodels.DeviceViewModel
import com.example.notebookscatalog.viewmodels.DeviceViewModelFactory

class MainActivity : AppCompatActivity(), IFragmentCommunication  {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fDevicesList: ListFragment
    private lateinit var fDeviceEdit: EditFragment
    private lateinit var fDeviceAdd: AddFragment

    private val brandViewModel: BrandViewModel by viewModels {
        BrandViewModelFactory((application as NotebooksApplication).brandRepository)
    }

    private val deviceViewModel: DeviceViewModel by viewModels {
        DeviceViewModelFactory((application as NotebooksApplication).deviceRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        fDevicesList = ListFragment(this, deviceViewModel)
        fDeviceEdit = EditFragment(this, deviceViewModel)
        fDeviceAdd = AddFragment(this, brandViewModel, deviceViewModel)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.miList -> setCurrentFragment(fDevicesList)
                R.id.miEdit -> setCurrentFragment(fDeviceEdit)
                R.id.miAdd -> setCurrentFragment(fDeviceAdd)
            }
            true
        }

        setCurrentFragment(fDevicesList)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }

    override fun createDevice() {
        binding.bottomNavigationView.selectedItemId = R.id.miAdd
    }

    override fun updateDevice(deviceItem: Device) {
        binding.bottomNavigationView.selectedItemId = R.id.miEdit
        fDeviceEdit.selectedItem = deviceItem
        setCurrentFragment(fDeviceEdit)
    }

    override fun listDevices() {
        binding.bottomNavigationView.selectedItemId = R.id.miList
        setCurrentFragment(fDevicesList)
    }

}