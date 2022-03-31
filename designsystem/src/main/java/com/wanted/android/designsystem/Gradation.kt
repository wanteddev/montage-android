package com.wanted.android.designsystem

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.wanted.android.designsystem.util.dp

class Gradation(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    enum class Type {
        CTA
    }

    var type: Type = Type.CTA
        set(value) {
            field = value
            updateBackground()
        }

    init {
        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.Gradation).run {
                type = Type.values()[getInteger(R.styleable.Gradation_type_background, 0)]
            }
        }
    }

    private fun updateBackground() {
        when (type) {
            Type.CTA -> {
                setBackgroundResource(R.drawable.gradation_cta)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
            30.dp
        )
    }

}