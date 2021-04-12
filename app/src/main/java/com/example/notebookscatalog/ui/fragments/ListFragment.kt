package com.example.notebookscatalog.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notebookscatalog.ui.adapters.DeviceListAdapter
import com.example.notebookscatalog.R
import com.example.notebookscatalog.databinding.FragmentListBinding
import com.example.notebookscatalog.interfaces.IFragmentCommunication
import com.example.notebookscatalog.viewmodels.DeviceViewModel

class ListFragment(
    private val navigation: IFragmentCommunication,
    private val deviceViewModel: DeviceViewModel)
    : Fragment(R.layout.fragment_list) {

    private val logTag = "DevicesListFragment"

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var deviceListAdapter: DeviceListAdapter
    private lateinit var fContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.fContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deviceListAdapter = DeviceListAdapter(navigation, deviceViewModel)

        binding.rvNotesList.adapter = deviceListAdapter
        binding.rvNotesList.layoutManager = LinearLayoutManager(fContext)

        deviceViewModel.devicesData.observe(
            viewLifecycleOwner,
            Observer { devices ->
                Log.d(logTag, "Devices list updated to: $devices")
                devices?.let {
                    deviceListAdapter.submitList(it)
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}