package com.zkv.tfsfeed.presentation.extensions

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.zkv.tfsfeed.R
import java.io.File

fun Context.makeToast(@StringRes text: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Context.checkPermissionGranted(permission: String): Boolean =
    checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

fun Activity.downloadImageWithExternalStorage(bitmap: Bitmap) {
    try {
        if (checkPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            MediaStore.Images.Media.insertImage(contentResolver, bitmap, "", "")
            makeToast(R.string.toast_successfully_download)
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                0)
        }
    } catch (e: Exception) {
        makeToast(R.string.toast_faild_load)
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
fun Context.downloadImageWithMediaStore(bitmap: Bitmap) {
    try {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        val collection =
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val itemUri = contentResolver.insert(collection, values)
        itemUri?.let { contentResolver.openOutputStream(it) }
            .use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
        makeToast(R.string.toast_successfully_download)
    } catch (e: Exception) {
        makeToast(R.string.toast_faild_load)
    }
}

fun Context.loadImage(url: String, onSuccess: (file: File) -> Unit) {
    Glide.with(this)
        .asFile()
        .load(url)
        .addListener(object : RequestListener<File> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<File>?,
                isFirstResource: Boolean,
            ) = false

            override fun onResourceReady(
                resource: File,
                model: Any?,
                target: Target<File>?,
                dataSource: DataSource?,
                isFirstResource: Boolean,
            ): Boolean {
                onSuccess(resource)
                return true
            }
        })
        .submit()
}