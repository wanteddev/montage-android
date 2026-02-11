package com.wanted.android.wanted.design.actions.button.textbutton

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.dp
import com.wanted.android.wanted.design.util.sp

class TextButton(context: Context, attrs: AttributeSet? = null) :
    AppCompatTextView(context, attrs) {

    enum class Type {
        PRIMARY, GRAY
    }

    var type: Type = Type.PRIMARY
        set(value) {
            field = value
            setTextColor(getTypeColor())
        }

    private fun getTypeColor() = ContextCompat.getColor(
        context, if (type == Type.PRIMARY) R.color.primary_normal else R.color.label_alternative
    )

    init {
        gravity = Gravity.CENTER
        background = null
        textSize = 14f.sp
        typeface = ResourcesCompat.getFont(context, R.font.bold)
        firstBaselineToTopHeight = 0
        includeFontPadding = false

        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.TextButton).run {
                text = getString(R.styleable.TextButton_text)
                type = Type.entries[getInteger(R.styleable.TextButton_type_text, 0)]
                if (getBoolean(R.styleable.TextButton_arrow, false)) setDrawableArrow(context)
                recycle()
            }
        }
    }

    private fun setDrawableArrow(context: Context) {
        val drawable = ContextCompat.getDrawable(context, R.drawable.icon_1_line_arrow_right_12)
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
        compoundDrawablePadding = 2.dp
        compoundDrawableTintList = ColorStateList.valueOf(getTypeColor())

    }
}