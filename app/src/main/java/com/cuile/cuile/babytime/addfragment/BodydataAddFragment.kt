package com.cuile.cuile.babytime.addfragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.BottomSheetDialog
import android.util.Log.i
import android.widget.Button
import com.bumptech.glide.Glide
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.utils.PhotoUtil
import com.cuile.cuile.babytime.R
import kotlinx.android.synthetic.main.fragment_bodydata_add.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast

/**
 * Created by cuile on 18-6-4.
 *
 */
class BodydataAddFragment: BaseFragment() {


    companion object {
        const val ARG_PARAM = "BodydataFragment_param_key"
        fun getInstance(param: String = ""): BodydataAddFragment {
            val fragment = BodydataAddFragment()
            fragment.arguments = Bundle().apply { putString(ARG_PARAM, param) }
            return fragment
        }
    }

    private lateinit var cameraFileName: String

    override fun initViews() {

        cameraFileName = System.currentTimeMillis().toString().plus(".jpg")
        bodydataBabyImgContainer.setOnClickListener {
            fillPhotoDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == PhotoUtil.CAMERA_TAKE_PHOTO_REQUEST_CODE) {
            Glide.with(act)
                    .load(PhotoUtil.getImageFullPath(this, cameraFileName))
                    .into(bodydataBabyImg)
        } else if (requestCode == PhotoUtil.CHOOSE_PHOTO_REQUEST_CODE) {
            val selectedPhotoUri = data?.data

            val cursor = act.contentResolver.query(
                    selectedPhotoUri,
                    arrayOf(MediaStore.Images.Media.DATA),
                    null, null, null
            )
            cursor.moveToFirst()
            val photoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            i("photoPath:", photoPath)
            cursor.close()

            Glide.with(act).load(photoPath).into(bodydataBabyImg)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PhotoUtil.CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PhotoUtil.checkAndTakePhoto(this, cameraFileName)
            } else {
                toast("CAMERA PERMISSION DENIED")
            }
            return
        } else if (requestCode == PhotoUtil.READ_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PhotoUtil.choosePhoto(this)
            } else {
                toast("STORAGE PERMISSION DENIED")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun fillPhotoDialog() {
        val bottomSheetDialog = BottomSheetDialog(this.context!!).apply {
            setCancelable(true)
            setContentView(R.layout.dialog_bodydataadd_photo_src_choose_layout)
        }
        bottomSheetDialog.show()

        bottomSheetDialog.find<Button>(R.id.bodydata_dialog_take_photo).setOnClickListener {
            PhotoUtil.checkAndTakePhoto(this, cameraFileName)
            bottomSheetDialog.cancel()
        }
        bottomSheetDialog.find<Button>(R.id.bodydata_dialog_cancel_photo).setOnClickListener {
            bottomSheetDialog.cancel()
        }
        bottomSheetDialog.find<Button>(R.id.bodydata_dialog_choose_photo).setOnClickListener {
            PhotoUtil.choosePhoto(this)
            bottomSheetDialog.cancel()
        }
    }

    override fun getLayout(): Int = R.layout.fragment_bodydata_add

}