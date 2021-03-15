package com.example.notebookscatalog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.notebookscatalog.NotebooksApplication
import com.example.notebookscatalog.R
import com.example.notebookscatalog.data.DeviceItem
import com.example.notebookscatalog.databinding.ActivityMainBinding
import com.example.notebookscatalog.fragments.EditFragment
import com.example.notebookscatalog.fragments.ListFragment
import com.example.notebookscatalog.helpers.InitHelper
import com.example.notebookscatalog.interfaces.IFragmentCommunication
import com.example.notebookscatalog.viewmodels.*

class MainActivity : AppCompatActivity(), IFragmentCommunication  {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fDevicesList: ListFragment
    private lateinit var fDeviceEdit: EditFragment

    private val deviceListviewModel: DeviceListviewModel by viewModels()

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

        deviceListviewModel.loadDevices(InitHelper.initDevicesList(this))

        fDevicesList = ListFragment(this, deviceViewModel)
        fDeviceEdit = EditFragment(this, brandViewModel)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.miList -> setCurrentFragment(fDevicesList)
                R.id.miEdit -> setCurrentFragment(fDeviceEdit)
                R.id.miAdd -> Toast.makeText(this, "Fragment is not created yet", Toast.LENGTH_SHORT).show()
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
//        setCurrentFragment()
    }

    override fun updateDevice(index: Int) {
        deviceListviewModel.selectItem(index)
        binding.bottomNavigationView.selectedItemId = R.id.miEdit
        setCurrentFragment(fDeviceEdit)
    }

    override fun listDevices() {
        binding.bottomNavigationView.selectedItemId = R.id.miEdit
        setCurrentFragment(fDevicesList)
    }

    override fun onDeviceCreated(device: DeviceItem?) {
        TODO("Not yet implemented")
    }
}