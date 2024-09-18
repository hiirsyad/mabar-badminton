package com.lazday.mainbadminton.activity

import android.os.Bundle
import com.lazday.mainbadminton.databinding.ActivityAllBinding

class AllActivity : BaseActivity() {
    private val binding by lazy { ActivityAllBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}