package com.wanted.android.designsystem.sample.button

import android.os.Bundle
import com.wanted.android.designsystem.sample.BindingActivity
import com.wanted.android.designsystem.sample.R
import com.wanted.android.designsystem.sample.databinding.DsActivityButtonTextBinding

class DSButtonTextActivity :
    BindingActivity<DsActivityButtonTextBinding>(DsActivityButtonTextBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbar.apply {
            inflateMenu(R.menu.ds_activity_button)
//            menu.findItem(R.id.edit_enable).setVisible(false)
//            setOnMenuItemClickListener {
//                if (it.itemId == R.id.edit_text) {
//                    showInputDialog(
//                        title = "텍스트 변경",
//                        hintText = "버튼에 표시할 텍스트를 입력하세요.",
//                        positiveText = "확인",
//                        positiveAction = {
//                            binding.linearLayout.children.forEach { view ->
//                                (view as? TextButton)?.text = it
//                            }
//                        }
//                    )
//                }
//                false
//            }
        }

    }

}