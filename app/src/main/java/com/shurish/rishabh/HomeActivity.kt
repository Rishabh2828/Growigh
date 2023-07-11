package com.shurish.rishabh

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView


class HomeActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var uploadimgbtn: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        imageView = findViewById(R.id.feedbtn)
        uploadimgbtn = findViewById(R.id.uploadImgBtn)

        imageView.setOnClickListener {
            val intent = Intent(this@HomeActivity, RetriveImageActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        uploadimgbtn.setOnClickListener {
            val intent = Intent(this@HomeActivity, UploadImageActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}