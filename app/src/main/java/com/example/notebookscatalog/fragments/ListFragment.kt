package com.example.notebookscatalog.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notebookscatalog.DeviceListAdapter
import com.example.notebookscatalog.R
import com.example.notebookscatalog.data.DeviceItem
import com.example.notebookscatalog.databinding.FragmentListBinding
import com.example.notebookscatalog.helpers.InitHelper
import com.example.notebookscatalog.interfaces.IFragmentCommunication
import com.example.notebookscatalog.viewmodels.DeviceListviewModel

class ListFragment(val navigation: IFragmentCommunication) : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var deviceListAdapter: DeviceListAdapter
    private lateinit var fContext: Context

    private val deviceListViewModel: DeviceListviewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.fContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deviceListAdapter = DeviceListAdapter(deviceListViewModel.devicesListLiveData.value!!, navigation)

        binding.rvNotesList.adapter = deviceListAdapter
        binding.rvNotesList.layoutManager = LinearLayoutManager(fContext)

        deviceListViewModel.devicesListLiveData.observe(
            viewLifecycleOwner,
            Observer<MutableList<DeviceItem>> {
                deviceListAdapter.notifyDataSetChanged()
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}