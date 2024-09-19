package com.lazday.mainbadminton.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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

        val timer = object: CountDownTimer(2500,1){
            override fun onTick(millisUntilFinished: Long) {
//                Log.d("onTick", "millisUntilFinished $millisUntilFinished")
                when {
                    millisUntilFinished < 1000L -> {
                        binding.imgLazday.visibility = View.GONE
                    }
                    millisUntilFinished < 1050L -> {
                        binding.imgPlayer.visibility = View.VISIBLE
                    }
                }
            }
            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                    .also { this@SplashActivity.finish() }
            }
        }
        timer.start()

    }
}