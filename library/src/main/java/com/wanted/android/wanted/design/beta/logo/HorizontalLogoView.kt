package com.wanted.android.wanted.design.beta.logo

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.wanted.android.designsystem.R

class HorizontalLogoView : FrameLayout {

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
    }

    private fun initialize() {
        addView(
            LayoutInflater.from(context)
                .inflate(R.layout.design_system_logo_horizontal, null, false)
        )
    }
}