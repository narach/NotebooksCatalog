package com.example.notebookscatalog.data

import android.graphics.drawable.Drawable
import android.os.Parcelable
import java.io.Serializable

data class DeviceItem(
    var img: Drawable?,
    var model: String,
    var screen: String?,
    var hardware: String?
) : Serializable
