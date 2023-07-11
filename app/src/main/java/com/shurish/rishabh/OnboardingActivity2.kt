package com.shurish.rishabh

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView


class OnboardingActivity2 : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var skipIntro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding2)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        skipIntro = findViewById(R.id.skipintro_2)

        skipIntro.setOnClickListener {
            val intent = Intent(this@OnboardingActivity2, HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        imageView = findViewById(R.id.next2)
        imageView.setOnClickListener {
            val intent = Intent(this@OnboardingActivity2, OnboardingActivity3::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}