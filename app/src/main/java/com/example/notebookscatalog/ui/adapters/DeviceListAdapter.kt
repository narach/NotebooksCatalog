package com.example.notebookscatalog.ui.adapters

import android.app.AlertDialog
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
import com.example.notebookscatalog.viewmodels.DeviceViewModel

class DeviceListAdapter(
        private val navigation: IFragmentCommunication,
        private val deviceViewModel: DeviceViewModel) :
    ListAdapter<Device, DeviceListAdapter.DevicesViewHolder>(DeviceComparator()) {

    private var alertDialog: AlertDialog? = null
    private var context: Context? = null

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
        context = parent.context
        return DevicesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) {
        val context = holder.itemView.context
        val deviceItem = getItem(position)
        holder.bind(deviceItem, context)
        holder.itemView.setOnClickListener {
            deviceViewModel.selectItem(position)
            navigation.updateDevice()
        }
        holder.itemView.setOnLongClickListener {
            val selectedItem = getItem(position)
            showDeleteDialog(selectedItem)
            true
        }
    }

    private fun showDeleteDialog(deviceItem: Device) {
        val builder = AlertDialog.Builder(context)
        builder.run {
            setTitle("Delete item")
            setMessage("Are you really want to delete an item?")
            setPositiveButton("Yes") { _, _ ->
                deviceViewModel.delete(deviceItem)
            }
            setNegativeButton("Cancel") {_, _ ->
            }
        }
        alertDialog = builder.create()
        alertDialog?.show()
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