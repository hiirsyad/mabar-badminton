package com.lazday.mainbadminton.activity

import android.os.Bundle
import com.lazday.mainbadminton.databinding.ActivityUpdateBinding

class UpdateActivity : BaseActivity() {
    private val binding by lazy { ActivityUpdateBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}