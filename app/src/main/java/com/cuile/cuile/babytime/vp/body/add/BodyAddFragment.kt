package com.cuile.cuile.babytime.vp.body.add

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.BottomSheetDialog
import android.widget.Button
import com.bumptech.glide.Glide
import com.cuile.cuile.babytime.BaseFragment
import com.cuile.cuile.babytime.R
import com.cuile.cuile.babytime.model.db.BodyData
import com.cuile.cuile.babytime.utils.PhotoUtil
import com.cuile.cuile.babytime.utils.initToolbar
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.dip
import org.jetbrains.anko.support.v4.toast
import kotlinx.android.synthetic.main.fragment_bodydata_add.*

/**
 * Created by cuile on 18-6-4.
 *
 */
class BodyAddFragment: BaseFragment(), BodyAddContract.View {
    override var isActive = false
        get() = isAdded

    override var presenter: BodyAddContract.Presenter = BodyAddPresenter(this)

    override fun showProgress() {
        bodydataFab.isClickable = false

        val animator1 = ObjectAnimator.ofInt(bodydataFab, "width", dip(56))
        val animator2 = ObjectAnimator.ofFloat(bodydataFab, "rotationY", 0f, 360f).apply {
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
        }

        AnimatorSet().playSequentially(animator1, animator2)
    }

    override fun stopProgress() {
        bodydataFab.isClickable = true
    }

    override fun turnToShowMainPage() {
        act.onBackPressed()
    }


    companion object {
        @Suppress("MemberVisibilityCanBePrivate")
        const val ARG_PARAM = "BodydataFragment_param_key"
        fun getInstance(param: String = ""): BodyAddFragment {
            val fragment = BodyAddFragment()
            fragment.arguments = Bundle().apply { putString(ARG_PARAM, param) }
            return fragment
        }
    }

    override fun initViews() {

        bodydata_add_toolbar.initToolbar(R.string.bodydata_add, act)

        bodydataBabyImgContainer.setOnClickListener {
            fillPhotoDialog()
        }
        bodydataFab.setOnClickListener {

            val bodyData = getBodyDataFromUI()
            if (bodyData == null) {
                toast("Please put in height and weight")
            } else presenter.saveData(bodyData)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == PhotoUtil.CAMERA_TAKE_PHOTO_REQUEST_CODE) {
            Glide.with(act)
                    .load(PhotoUtil.getImageFullPath(this, presenter.imageName))
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

            cursor.close()

            Glide.with(act).load(photoPath).into(bodydataBabyImg)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PhotoUtil.CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PhotoUtil.checkAndTakePhoto(this, presenter.imageName)
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
            presenter.requestCameraPhoto()
            bottomSheetDialog.cancel()
        }
        bottomSheetDialog.find<Button>(R.id.bodydata_dialog_cancel_photo).setOnClickListener {
            bottomSheetDialog.cancel()
        }
        bottomSheetDialog.find<Button>(R.id.bodydata_dialog_choose_photo).setOnClickListener {
            presenter.requestCameraPhoto()
            bottomSheetDialog.cancel()
        }
    }

    private fun getBodyDataFromUI(): BodyData? {
        val height = bodydataBabyHeight.text.toString().toFloatOrNull()
        val weight = bodydataBabyWeight.text.toString().toFloatOrNull()
        val photo = if (presenter.imageName.isNullOrEmpty()) "" else PhotoUtil.getImageFullPath(this, presenter.imageName)
        val date = bodydataTime.viewTime.time

        if (height == null || weight == null) {

            return null
        }

        return BodyData(
                name = "",
                height = height,
                weight = weight,
                photo = photo,
                date = date,
                other = ""
        )
    }

    override fun getLayout(): Int = R.layout.fragment_bodydata_add

}