package com.wanted.android.designsystem.navigation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.wanted.android.designsystem.R

class BottomNavigationItem : FrameLayout {

    @DrawableRes
    private var iconNormal: Int = 0

    @DrawableRes
    private var iconSelected: Int = 0

    @ColorInt
    private var itemColor: Int = 0

    private var itemText: String = ""

    private var itemSelected: Boolean = false

    var showBadge: Boolean = false
        set(value) {
            field = value
            updateBadge()
        }

    lateinit var view: View
    lateinit var icon: ImageView
    lateinit var text: TextView
    lateinit var badge: ImageView

    constructor(context: Context) : super(context) {
        initialize(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet?) {
        view =
            LayoutInflater.from(context)
                .inflate(R.layout.design_system_bottom_navigation_item, null, false)
        icon = view.findViewById(R.id.icon)
        text = view.findViewById(R.id.text)
        badge = view.findViewById(R.id.badge)
        addView(view)

        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.BottomNavigationItem).run {
                iconNormal = getResourceId(R.styleable.BottomNavigationItem_icon_normal, 0)
                iconSelected = getResourceId(R.styleable.BottomNavigationItem_icon_selected, 0)
                itemColor = getColor(R.styleable.BottomNavigationItem_item_color, 0)
                itemText = getString(R.styleable.BottomNavigationItem_text) ?: ""
                itemSelected = getBoolean(R.styleable.BottomNavigationItem_is_selected, false)
                showBadge = getBoolean(R.styleable.BottomNavigationItem_show_badge, false)
            }
            updateIconState()
            updateText()
            updateBadge()
            updateColorState()
        }
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        this.itemSelected = selected
        updateIconState()
    }

    override fun isSelected(): Boolean = itemSelected

    private fun updateIconState() {
        icon.setImageResource(
            when {
                itemSelected -> if (iconSelected != 0) iconSelected else iconNormal
                else -> iconNormal
            }
        )
    }

    private fun updateText() {
        text.text = itemText
    }

    private fun updateColorState() {
        text.setTextColor(itemColor)
        icon.setColorFilter(itemColor)
    }

    private fun updateBadge() {
        badge.visibility = if (showBadge) View.VISIBLE else View.GONE
    }
}