package com.wanted.android.designsystem.sample.component

import android.content.Intent
import android.os.Bundle
import com.wanted.android.designsystem.sample.BindingActivity
import com.wanted.android.designsystem.sample.databinding.DsActivityComponentBinding

class DSComponentActivity :
    BindingActivity<DsActivityComponentBinding>(DsActivityComponentBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottomNavigation.setOnClickListener {
            startActivity(Intent(this, DSBottomNavigationActivity::class.java))
        }
    }
}