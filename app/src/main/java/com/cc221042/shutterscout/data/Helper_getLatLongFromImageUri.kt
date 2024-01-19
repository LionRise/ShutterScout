package com.cc221042.shutterscout.data

import android.content.Context
import android.media.ExifInterface
import android.net.Uri
import java.io.IOException
import java.io.InputStream

fun getLatLongFromImageUri(context: Context, imageUri: Uri): Pair<Double?, Double?> {
    try {
        context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
            val exifInterface = ExifInterface(inputStream)
            val latLong = FloatArray(2)
            if (exifInterface.getLatLong(latLong)) {
                return Pair(latLong[0].toDouble(), latLong[1].toDouble())
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return Pair(null, null)
}