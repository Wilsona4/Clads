package com.decagonhq.clads.util

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun Fragment.uriToBitmap(uriImage: Uri): Bitmap? {

    val contentResolver = requireActivity().contentResolver
    var mBitmap: Bitmap? = null
    mBitmap = if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(
            contentResolver,
            uriImage
        )
    } else {
        val source = ImageDecoder.createSource(contentResolver, uriImage)
        ImageDecoder.decodeBitmap(source)
    }
    return mBitmap
}

fun Fragment.saveBitmap(bmp: Bitmap?): File? {
    val extStorageDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    var outStream: OutputStream? = null
    var file: File? = null
    val time = System.currentTimeMillis()
    val child = "JPEG_${time}_.jpg"

    // String temp = null;
    if (extStorageDirectory != null) {
        file = File(extStorageDirectory, child)
        if (file.exists()) {
            file.delete()
            file = File(extStorageDirectory, child)
        }
        try {
            outStream = FileOutputStream(file)
            bmp?.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream.flush()
            outStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
    return file
}
