package com.lazday.mainbadminton.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.lazday.mainbadminton.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar!!.hide()
        Handler(Looper.myLooper()!!).postDelayed({
            binding.imgPlayer.visibility = View.GONE
            binding.imgLazday.visibility = View.VISIBLE
        }, 2500)
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            this.finish()
        }, 4000)
    }
}