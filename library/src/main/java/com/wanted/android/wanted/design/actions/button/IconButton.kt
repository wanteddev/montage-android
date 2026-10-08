package com.wanted.android.wanted.design.actions.button

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.dp
import com.wanted.android.wanted.design.util.makeDrawable
import com.wanted.android.wanted.design.util.tint


@Deprecated("use WantedIconButtonSolid, Outlined, Normal")
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
                type = Type.entries[getInteger(R.styleable.IconButton_type_icon, 0)]
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
            Type.PRIMARY -> R.color.static_white
            Type.BLUE -> R.color.foreground_brand_primary
            Type.BLACK -> R.color.foreground_neutral_primary
        }.apply { iconView.tint = ContextCompat.getColor(context, this) }
    }

    private fun setBackground() {
        when (type) {
            Type.PRIMARY -> R.color.surface_brand_primary to null
            Type.BLUE, Type.BLACK -> R.color.background_neutral_primary to R.color.line_neutral_primary
        }.apply {
            val (backgroundColor, strokeColor) = this
            background = context.makeDrawable(backgroundColor, strokeColor, 20f.dp)
        }

    }
}