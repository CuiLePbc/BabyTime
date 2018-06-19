package com.cuile.cuile.babytime

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Log
import org.jetbrains.anko.support.v4.act
import java.io.File
import java.lang.ref.WeakReference

/**
 * Created by cuile on 18-6-6.
 *
 */
object PhotoUtil {
    val CAMERA_TAKE_PHOTO_REQUEST_CODE = 1
    val CHOOSE_PHOTO_REQUEST_CODE = 2
    val CAMERA_PERMISSION_REQUEST_CODE = 11
    val READ_STORAGE_PERMISSION_REQUEST_CODE = 12

    private fun takePhoto(fragment: Fragment, cameraFileName: String) {

        val file = File(getImageFullPath(fragment, cameraFileName))

        val uri =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    FileProvider.getUriForFile(
                            fragment.context!!,
                            "com.cuile.cuile.babytime.fileprovider",
                            file)
                else
                    Uri.fromFile(file)

        val intent = Intent().apply {
            action = MediaStore.ACTION_IMAGE_CAPTURE
            putExtra(MediaStore.EXTRA_OUTPUT, uri)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        fragment.startActivityForResult(intent, CAMERA_TAKE_PHOTO_REQUEST_CODE)
    }

    fun checkAndTakePhoto(fragment: Fragment, cameraFileName: String) {
        if (ContextCompat.checkSelfPermission(fragment.act, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) run {
            ActivityCompat.requestPermissions(fragment.act, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            takePhoto(fragment, cameraFileName)
        }
    }

    fun getImageFullPath(fragment: Fragment, imageName: String): String {
        val dir = fragment.activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(dir, imageName)

        return file.absolutePath
    }

    fun choosePhoto(fragment: Fragment) {
        if (ContextCompat.checkSelfPermission(fragment.act, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) kotlin.run {
            ActivityCompat.requestPermissions(fragment.act, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_STORAGE_PERMISSION_REQUEST_CODE)
        } else {
            val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
            fragment.startActivityForResult(intent, CHOOSE_PHOTO_REQUEST_CODE)
        }
    }

}