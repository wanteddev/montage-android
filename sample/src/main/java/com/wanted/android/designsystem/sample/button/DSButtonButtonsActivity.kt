package com.wanted.android.designsystem.sample.button

import android.os.Bundle
import com.wanted.android.designsystem.sample.BindingActivity
import com.wanted.android.designsystem.sample.R
import com.wanted.android.designsystem.sample.databinding.DsActivityButtonButtonsBinding

class DSButtonButtonsActivity :
    BindingActivity<DsActivityButtonButtonsBinding>(DsActivityButtonButtonsBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbar.apply {
            inflateMenu(R.menu.ds_activity_button)
//            setOnMenuItemClickListener {
//                if (it.itemId == R.id.edit_text) {
//                    showInputDialog(
//                        title = "텍스트 변경",
//                        hintText = "버튼에 표시할 텍스트를 입력하세요.",
//                        positiveText = "확인",
//                        positiveAction = {
//                            binding.linearLayout.children.forEach { view ->
//                                (view as? Button)?.text = it
//                            }
//                        }
//                    )
//                } else if (it.itemId == R.id.edit_enable) {
//                    it.isChecked = !it.isChecked
//                    val isChecked = it.isChecked
//                    binding.linearLayout.children.forEach { view ->
//                        (view as? Button)?.isEnabled = isChecked
//                    }
//                }
//                false
//            }
        }

    }

}