package com.shurish.rishabh

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var skipIntro: TextView
    private val prefsName = "MyPrefsFile"
    private val keyFirstLaunch = "first_launch"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences: SharedPreferences = getSharedPreferences(prefsName, MODE_PRIVATE)
        val isFirstLaunch = preferences.getBoolean(keyFirstLaunch, true)

        if (isFirstLaunch) {

            setContentView(R.layout.activity_main)


            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putBoolean(keyFirstLaunch, false)
            editor.apply()
        } else {

            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.next1)
        skipIntro = findViewById(R.id.skipintro_1)

        skipIntro.setOnClickListener {
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        imageView.setOnClickListener {
            val intent = Intent(this@MainActivity, OnboardingActivity2::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}