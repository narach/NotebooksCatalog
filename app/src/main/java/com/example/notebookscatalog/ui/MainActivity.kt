package com.example.notebookscatalog.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notebookscatalog.NotebooksApplication
import com.example.notebookscatalog.R
import com.example.notebookscatalog.databinding.ActivityMainBinding
import com.example.notebookscatalog.firestore.entities.NoteBrand
import com.example.notebookscatalog.interfaces.IFragmentCommunication
import com.example.notebookscatalog.ui.fragments.AddFragment
import com.example.notebookscatalog.ui.fragments.EditFragment
import com.example.notebookscatalog.ui.fragments.ListFragment
import com.example.notebookscatalog.viewmodels.BrandViewModel
import com.example.notebookscatalog.viewmodels.BrandViewModelFactory
import com.example.notebookscatalog.viewmodels.DeviceViewModel
import com.example.notebookscatalog.viewmodels.DeviceViewModelFactory
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), IFragmentCommunication  {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fDevicesList: ListFragment
    private lateinit var fDeviceEdit: EditFragment
    private lateinit var fDeviceAdd: AddFragment

    private val brandsCollectionRef = Firebase.firestore.collection("brands")

    private val brandViewModel: BrandViewModel by viewModels {
        BrandViewModelFactory((application as NotebooksApplication).brandRepository)
    }

    private val deviceViewModel: DeviceViewModel by viewModels {
        DeviceViewModelFactory((application as NotebooksApplication).deviceRepository)
    }

    // Save new brand in DB
    private fun saveBrand(brand: NoteBrand) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                brandsCollectionRef.add(brand).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Successfully saved data.", Toast.LENGTH_LONG).show()
                }
            } catch(e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        val newBrand = NoteBrand("HP")
        saveBrand(newBrand)

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

    override fun updateDevice() {
        binding.bottomNavigationView.selectedItemId = R.id.miEdit
        setCurrentFragment(fDeviceEdit)
    }

    override fun listDevices() {
        binding.bottomNavigationView.selectedItemId = R.id.miList
        setCurrentFragment(fDevicesList)
    }

}