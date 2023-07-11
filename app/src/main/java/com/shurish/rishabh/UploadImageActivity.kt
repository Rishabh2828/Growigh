package com.shurish.rishabh

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import java.io.IOException


class UploadImageActivity : AppCompatActivity() {

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    private lateinit var uploadedimageView: ImageView
    private lateinit var imageCross: ImageView
    private lateinit var selectImageButton: AppCompatButton
    private lateinit var uploadImageButton: AppCompatButton
    private lateinit var backbtn: ImageView

    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_image)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        uploadedimageView = findViewById(R.id.uploadedimageView)
        imageCross = findViewById(R.id.imageCross)
        selectImageButton = findViewById(R.id.selectimg_btn)
        uploadImageButton = findViewById(R.id.uploadimg_btn)
        backbtn = findViewById(R.id.back_btn)

        backbtn.setOnClickListener {
            val intent = Intent(this@UploadImageActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        selectImageButton.setOnClickListener {
            openGallery()
        }

        imageCross.setOnClickListener {
            removeSelectedImage()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }

    private fun removeSelectedImage() {
        selectedImageUri = null
        uploadedimageView.setImageResource(R.drawable.imggallary)
        imageCross.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data

            try {
                val bitmap: Bitmap? = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                uploadedimageView.setImageBitmap(bitmap)
                imageCross.visibility = View.VISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}