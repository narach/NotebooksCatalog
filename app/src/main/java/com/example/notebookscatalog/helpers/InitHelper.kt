package com.example.notebookscatalog.helpers

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.notebookscatalog.R
import com.example.notebookscatalog.data.DeviceItem

object InitHelper {
    fun initDevicesList(context: Context): MutableList<DeviceItem> {
        return mutableListOf(
            DeviceItem(
                ContextCompat.getDrawable(context, R.drawable.asus_tuf),
                "Asus, TUF Gaming F15",
                "15.6, 1920 x 1080",
                "Intel Core I5, 16Gb RAM, 512Gb SSD, 4Gb Video"
            ),
            DeviceItem(
                ContextCompat.getDrawable(context, R.drawable.honor_magic_book),
                "Honor, Magic 14 2020 53010 VTY",
                "14.0, 1920 x 1080",
                "AMD Ryzen 5, 8Gb RAM, 512Gb SSD"
            ),
            DeviceItem(
                ContextCompat.getDrawable(context, R.drawable.apple_mac_book),
                "Apple, Macbook Air 13 M1 2020",
                "13.3, 2560 x 1600",
                "Apple M1, 8Gb RAM, 256Gb SSD, M1 GPU Video"
            )
        )
    }
}