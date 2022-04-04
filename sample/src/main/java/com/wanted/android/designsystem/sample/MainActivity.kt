package com.wanted.android.designsystem.sample

import android.content.Intent
import android.os.Bundle
import com.wanted.android.designsystem.sample.button.DSButtonActivity
import com.wanted.android.designsystem.sample.component.DSComponentActivity
import com.wanted.android.designsystem.sample.databinding.DsActivityDemoBinding
import com.wanted.android.designsystem.sample.icon.DSIconActivity
import com.wanted.android.designsystem.sample.logo.DSLogoActivity
import com.wanted.android.designsystem.sample.text.DSTextActivity

class MainActivity : BindingActivity<DsActivityDemoBinding>(DsActivityDemoBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.textDemo.setOnClickListener {
            startActivity(Intent(this, DSTextActivity::class.java))
        }

        binding.buttonDemo.setOnClickListener {
            startActivity(Intent(this, DSButtonActivity::class.java))
        }

        binding.iconDemo.setOnClickListener {
            startActivity(Intent(this, DSIconActivity::class.java))
        }

        binding.componentDemo.setOnClickListener {
            startActivity(Intent(this, DSComponentActivity::class.java))
        }

        binding.logoDemo.setOnClickListener {
            startActivity(Intent(this, DSLogoActivity::class.java))
        }
    }


}