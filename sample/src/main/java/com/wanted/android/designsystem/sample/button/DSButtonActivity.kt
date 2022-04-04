package com.wanted.android.designsystem.sample.button

import android.content.Intent
import android.os.Bundle
import com.wanted.android.designsystem.sample.BindingActivity
import com.wanted.android.designsystem.sample.databinding.DsActivityButtonBinding

class DSButtonActivity :
    BindingActivity<DsActivityButtonBinding>(DsActivityButtonBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.buttons.setOnClickListener {
            startActivity(Intent(this, DSButtonButtonsActivity::class.java))
        }

        binding.cta.setOnClickListener {
            startActivity(Intent(this, DSButtonCTAActivity::class.java))
        }

        binding.iconButton.setOnClickListener {
            startActivity(Intent(this, DSButtonIconActivity::class.java))
        }

        binding.textButton.setOnClickListener {
            startActivity(Intent(this, DSButtonTextActivity::class.java))
        }
    }

}