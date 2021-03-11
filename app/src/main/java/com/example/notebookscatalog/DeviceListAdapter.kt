package com.example.notebookscatalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notebookscatalog.data.DeviceItem
import com.example.notebookscatalog.databinding.ItemDeviceBinding
import com.example.notebookscatalog.interfaces.IFragmentCommunication

class DeviceListAdapter(
    private var devicesList: List<DeviceItem>,
    private val navigation: IFragmentCommunication) :
    RecyclerView.Adapter<DeviceListAdapter.DevicesViewHolder>()  {

    inner class DevicesViewHolder(private val itemDeviceBinding: ItemDeviceBinding)
        : RecyclerView.ViewHolder(itemDeviceBinding.root) {

        fun bind(deviceItem: DeviceItem) {
            itemDeviceBinding.ivDevice.setImageDrawable(deviceItem.img)
            itemDeviceBinding.tvModel.text = deviceItem.model
            itemDeviceBinding.tvScreen.text = deviceItem.screen
            itemDeviceBinding.tvHardware.text = deviceItem.hardware
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemDeviceBinding = ItemDeviceBinding.inflate(layoutInflater, parent, false)
        return DevicesViewHolder(itemDeviceBinding)
    }

    override fun getItemCount(): Int {
        return devicesList.size
    }

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) {
        holder.bind(devicesList[position])
        holder.itemView.setOnClickListener {
            navigation.updateDevice(position)
        }
    }
}