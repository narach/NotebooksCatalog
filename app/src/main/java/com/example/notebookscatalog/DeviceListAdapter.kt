package com.example.notebookscatalog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.notebookscatalog.databinding.ItemDeviceBinding
import com.example.notebookscatalog.db.entities.Device
import com.example.notebookscatalog.interfaces.IFragmentCommunication

class DeviceListAdapter(
        private val navigation: IFragmentCommunication) :
    ListAdapter<Device, DeviceListAdapter.DevicesViewHolder>(DeviceComparator()) {

    class DevicesViewHolder(private val itemDeviceBinding: ItemDeviceBinding)
        : RecyclerView.ViewHolder(itemDeviceBinding.root) {

        fun bind(deviceItem: Device, context: Context) {
            Glide.with(context).load(deviceItem.imgUri).into(itemDeviceBinding.ivDevice)
            itemDeviceBinding.tvModel.text = "${deviceItem.brandName}: ${deviceItem.model} "
            itemDeviceBinding.tvScreen.text = deviceItem.screen
            itemDeviceBinding.tvHardware.text = deviceItem.hardware
        }

        companion object {
            fun create(parent: ViewGroup): DevicesViewHolder {
                val itemDeviceBinding = ItemDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return DevicesViewHolder(itemDeviceBinding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesViewHolder {
        return DevicesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) {
        val context = holder.itemView.context
        val deviceItem = getItem(position)
        holder.bind(deviceItem, context)
        holder.itemView.setOnClickListener {
            navigation.updateDevice(position)
        }
    }

    class DeviceComparator : DiffUtil.ItemCallback<Device>() {
        override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem.brandName == newItem.brandName &&
                    oldItem.model == newItem.model &&
                    oldItem.deviceType == newItem.deviceType &&
                    oldItem.hardware == newItem.hardware &&
                    oldItem.screen == newItem.screen
        }

    }
}