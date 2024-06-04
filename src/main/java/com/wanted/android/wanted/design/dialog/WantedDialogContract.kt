package com.wanted.android.wanted.design.dialog

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ModalSize(
    val contentPadding: Dp,
    val bottomBarPadding: Dp,
) {
    Small(contentPadding = 20.dp, bottomBarPadding = 16.dp),
    Normal(contentPadding = 20.dp, bottomBarPadding = 20.dp),
    Medium(contentPadding = 24.dp, bottomBarPadding = 24.dp),
    Large(contentPadding = 32.dp, bottomBarPadding = 32.dp),
    Custom(contentPadding = 0.dp, bottomBarPadding = 0.dp)
}