package com.wanted.android.designsystem.button

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.wanted.android.designsystem.R
import com.wanted.android.designsystem.util.dp
import com.wanted.android.designsystem.util.makeDrawable
import com.wanted.android.designsystem.util.tint


class IconButton(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs) {

    private val iconView: AppCompatImageView by lazy { AppCompatImageView(context) }

    enum class Type {
        PRIMARY, BLUE, BLACK
    }

    var type: Type = Type.PRIMARY
        set(value) {
            field = value
            setIconColor()
            setBackground()
        }

    init {
        minimumWidth = 40.dp
        minimumHeight = 40.dp

        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.IconButton).run {
                type = Type.values()[getInteger(R.styleable.IconButton_type_icon, 0)]
                addView(iconView, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                    .apply { gravity = Gravity.CENTER }
                )
                iconView.setImageResource(getResourceId(R.styleable.IconButton_icon, 0))
                recycle()
            }
        }

    }

    private fun setIconColor() {
        when (type) {
            Type.PRIMARY -> R.color.semantic_common_white
            Type.BLUE -> R.color.primary_blue_400
            Type.BLACK -> R.color.neutral_gray_900
        }.apply { iconView.tint = ContextCompat.getColor(context, this) }
    }

    private fun setBackground() {
        when (type) {
            Type.PRIMARY -> R.color.primary_blue_400 to null
            Type.BLUE, Type.BLACK -> R.color.neutral_white_100 to R.color.neutral_bluegray_200
        }.apply {
            val (backgroundColor, strokeColor) = this
            background = context.makeDrawable(backgroundColor, strokeColor, 20f.dp)
        }

    }
}