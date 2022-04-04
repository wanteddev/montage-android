package com.wanted.android.designsystem.sample.component

import android.os.Bundle
import com.wanted.android.designsystem.sample.BindingActivity
import com.wanted.android.designsystem.sample.databinding.DsActivitiyBottomNavigationBinding

class DSBottomNavigationActivity :
    BindingActivity<DsActivitiyBottomNavigationBinding>(DsActivitiyBottomNavigationBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listOf(
            binding.home1,
            binding.search1,
            binding.community1,
            binding.profile1,
            binding.mywanted1,
            binding.home2,
            binding.search2,
            binding.profile2,
            binding.mywanted2,
        ).forEach { navigationItem ->
            navigationItem.setOnClickListener {
                navigationItem.isSelected = !navigationItem.isSelected
                navigationItem.showBadge = false
            }
        }
    }
}