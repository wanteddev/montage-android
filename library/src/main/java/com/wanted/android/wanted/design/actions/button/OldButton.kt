package com.wanted.android.wanted.design.actions.button

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.dp
import com.wanted.android.wanted.design.util.makeDrawable
import com.wanted.android.wanted.design.util.sp

@Deprecated("")
class OldButton(context: Context, private val attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    private var iconViewLeft: ImageView? = null
    private var iconViewRight: ImageView? = null
    private val textView: AppCompatTextView by lazy {
        AppCompatTextView(context).apply {
            gravity = Gravity.CENTER
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
        }
    }

    enum class Type {
        FILL_BLUE, LINE_PRIMARY, LINE_BLUE, LINE_BLACK
    }

    var type: Type = Type.FILL_BLUE
        set(value) {
            field = value
            setTextColor()
            setBackground()
        }


    enum class Size {
        LARGE, MEDIUM, SMALL
    }

    private var minWidth: Int = 0
    private var minHeight: Int = 0

    var size: Size = Size.LARGE
        set(value) {
            field = value
            when (value) {
                Size.LARGE -> Triple(345.dp, 48.dp, 16f.sp)
                Size.MEDIUM -> Triple(135.dp, 40.dp, 15f.sp)
                Size.SMALL -> Triple(70.dp, 32.dp, 14f.sp)
            }.apply {
                val (width, height, textSize) = this
                minimumWidth = if (minWidth > 0) minWidth else width
                minimumHeight = if (minHeight > 0) minHeight else height
                textView.textSize = textSize
            }
        }

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        isClickable = true
        clipToOutline = true
        stateListAnimator = null
        setPadding(20.dp, 0, 20.dp, 0)

        textView.typeface =
            ResourcesCompat.getFont(context, R.font.regular)
        textView.firstBaselineToTopHeight = 0
        textView.includeFontPadding = false

        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.Button).run {
                isEnabled = getBoolean(R.styleable.Button_enabled, true)
                setViews(
                    getDrawable(R.styleable.Button_icon_left),
                    getDrawable(R.styleable.Button_icon_right)
                )
                textView.text = getString(R.styleable.Button_text)
                type = Type.entries[getInteger(R.styleable.Button_oldButtonType, 0)]

                minWidth = getDimensionPixelSize(R.styleable.Button_min_width, 0)
                minHeight = getDimensionPixelSize(R.styleable.Button_min_height, 0)
                size = Size.entries[getInteger(R.styleable.Button_oldButtonSize, 0)]
                recycle()
            }
        }

    }


    private fun setViews(leftIcon: Drawable? = null, rightIcon: Drawable? = null) {
        addView(textView, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        addIconView(leftIcon, true)
        addIconView(rightIcon, false)
    }

    private fun addIconView(icon: Drawable?, isLeft: Boolean = true) {
        if (size == Size.SMALL || icon == null) return
        iconViewRight = ImageView(context).apply { setImageDrawable(icon) }
        addView(iconViewRight, if (isLeft) 0 else childCount,
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                if (isLeft) marginEnd = 3.dp else marginStart = 3.dp
            })
    }

    ///////////////////////////////////////////////////////////////////////////
    // Background
    ///////////////////////////////////////////////////////////////////////////

    private data class BackgroundColor(
        @ColorRes val normal: Int = android.R.color.transparent,
        @ColorRes val stroke: Int? = null,
        @ColorRes val pressed: Int = android.R.color.transparent,
        @ColorRes val pressedStroke: Int? = null,
        @ColorRes val disable: Int = R.color.fill_alternative,
        @ColorRes val disableStroke: Int = R.color.line_normal_alternative,
    )

    private fun setBackground() {
        when (type) {
            Type.FILL_BLUE -> BackgroundColor(
                normal = R.color.primary_normal,
                pressed = R.color.primary_heavy,
            )
            Type.LINE_PRIMARY -> BackgroundColor(
                stroke = R.color.primary_normal,
                pressedStroke = R.color.primary_heavy,
            )
            Type.LINE_BLUE, Type.LINE_BLACK -> BackgroundColor(
                stroke = R.color.line_normal_normal,
                pressedStroke = R.color.line_normal_normal,
            )
        }.run {
            ViewCompat.setBackground(this@OldButton, StateListDrawable().apply {
                addState(
                    intArrayOf(android.R.attr.state_pressed),
                    context.makeDrawable(pressed, pressedStroke)
                )
                addState(
                    intArrayOf(-android.R.attr.state_enabled),
                    context.makeDrawable(disable, disableStroke)
                )
                addState(intArrayOf(), context.makeDrawable(normal, stroke))
            })
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Text
    ///////////////////////////////////////////////////////////////////////////

    private data class TextColor(
        @ColorRes val normal: Int,
        @ColorRes val pressed: Int,
        @ColorRes val disable: Int = R.color.label_assistive
    )

    private fun getTextColor(): TextColor {
        return when (type) {
            Type.FILL_BLUE -> TextColor(
                normal = R.color.static_white,
                pressed = R.color.static_white,
            )
            Type.LINE_PRIMARY, Type.LINE_BLUE -> TextColor(
                normal = R.color.primary_normal,
                pressed = R.color.primary_heavy,
            )
            Type.LINE_BLACK -> TextColor(
                normal = R.color.label_normal,
                pressed = R.color.primary_heavy,
            )
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        attrs?.let { setTextColor() }
        textView.isEnabled = enabled
        iconViewLeft?.isEnabled = enabled
        iconViewRight?.isEnabled = enabled
    }

    private fun setTextColor() {
        getTextColor().apply {
            val color = ContextCompat.getColor(context, if (isEnabled) normal else disable)
            textView.setTextColor(color)
            iconViewLeft?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            iconViewRight?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }

    var text: CharSequence
        get() = textView.text
        set(value) {
            textView.text = value
        }

    var iconDrawable: Drawable?
        get() = iconViewLeft?.drawable ?: iconViewRight?.drawable
        set(value) {
            iconViewLeft?.setImageDrawable(value)
            iconViewRight?.setImageDrawable(value)
        }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isEnabled) return false
        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val pressed = ContextCompat.getColor(context, getTextColor().pressed)
                textView.setTextColor(pressed)
                iconViewLeft?.setColorFilter(pressed, PorterDuff.Mode.SRC_IN)
                iconViewRight?.setColorFilter(pressed, PorterDuff.Mode.SRC_IN)
            }
            else -> setTextColor()
        }
        return super.onTouchEvent(event)
    }

}