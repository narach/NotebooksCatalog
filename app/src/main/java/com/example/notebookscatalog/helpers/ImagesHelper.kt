package com.example.notebookscatalog.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import com.example.notebookscatalog.R

object ImagesHelper {

    fun uriToDrawable(uri: String?, context: Context): Drawable? {
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(Uri.parse(uri))
            return Drawable.createFromStream(inputStream, uri)
        }
        return ContextCompat.getDrawable(context, R.drawable.empty_device);
    }

    fun uriToBitmap(imgUri: Uri, context: Context) : Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, imgUri))
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, imgUri)
        }
    }
}