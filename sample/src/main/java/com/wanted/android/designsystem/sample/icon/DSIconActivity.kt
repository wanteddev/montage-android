package com.wanted.android.designsystem.sample.icon

import android.content.Intent
import android.os.Bundle
import com.wanted.android.designsystem.sample.BindingActivity
import com.wanted.android.designsystem.sample.databinding.DsActivityIconBinding

class DSIconActivity
    : BindingActivity<DsActivityIconBinding>(DsActivityIconBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startIconListActivity: (Int) -> Unit = { size ->
            if (!listOf(24, 18, 12).contains(size))
                throw IllegalArgumentException("Desigy System > Size [$size] no exist")

            startActivity(Intent(this, DSIconListActivity::class.java).apply {
                putExtra(DSIconListActivity.PARAM_SIZE, size)
            })
        }

        binding.icon24.setOnClickListener {
            startIconListActivity(24)
        }

        binding.icon18.setOnClickListener {
            startIconListActivity(18)
        }

        binding.icon12.setOnClickListener {
            startIconListActivity(12)
        }

    }

}