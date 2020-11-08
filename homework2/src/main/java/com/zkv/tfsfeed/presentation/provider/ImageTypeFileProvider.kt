package com.zkv.tfsfeed.presentation.provider

import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.bumptech.glide.load.ImageHeaderParser.ImageType
import com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser
import java.io.FileInputStream

class ImageTypeFileProvider : FileProvider() {

    private val mImageHeaderParser = DefaultImageHeaderParser()

    override fun getType(uri: Uri): String? {
        var type: String? = super.getType(uri)
        if (type != TYPE_OCTET) return type
        try {
            val parcelFileDescriptor = openFile(uri, "r") ?: return type
            type = parcelFileDescriptor.use { parcelFileDesc ->
                FileInputStream(
                    parcelFileDesc.fileDescriptor).use { fileInputStr ->
                    getTypeFromImageType(mImageHeaderParser.getType(
                        fileInputStr), type)
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return type
    }

    private fun getTypeFromImageType(imageType: ImageType, defaultType: String?): String? {
        val extension: String = when (imageType) {
            ImageType.GIF -> EXT_GIX
            ImageType.JPEG -> EXT_JPG
            ImageType.PNG_A, ImageType.PNG -> EXT_PNG
            ImageType.WEBP_A, ImageType.WEBP -> EXT_WEBP
            else -> return defaultType
        }
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }

    companion object {
        const val EXT_GIX = "gif"
        const val EXT_JPG = "jpg"
        const val EXT_PNG = "png"
        const val EXT_WEBP = "webp"
        const val TYPE_OCTET = "application/octet-stream"
    }
}