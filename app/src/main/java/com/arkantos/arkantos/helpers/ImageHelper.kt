package com.arkantos.arkantos.helpers

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

object ImageHelper {
    fun convertBitmapImageToBase64Encoded(bitmapImage: Bitmap?): String? {
        if(bitmapImage == null) return null
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}