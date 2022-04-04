package com.wanted.android.designsystem.sample.button

import android.os.Bundle
import android.widget.ArrayAdapter
import com.wanted.android.designsystem.sample.BindingActivity
import com.wanted.android.designsystem.sample.databinding.DsActivityButtonCtaBinding

class DSButtonCTAActivity
    : BindingActivity<DsActivityButtonCtaBinding>(DsActivityButtonCtaBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.listView.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mutableListOf<String>().apply {
                (1..30).forEach {
                    add("Item $it")
                }
            }
        )
    }
}