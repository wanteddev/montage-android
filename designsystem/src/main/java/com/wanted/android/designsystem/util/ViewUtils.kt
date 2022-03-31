package com.wanted.android.designsystem.util

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.ImageViewCompat

/** DP to Pixel */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = this * Resources.getSystem().displayMetrics.density

val Int.sp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

val Float.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        this,
        Resources.getSystem().displayMetrics
    )


var Drawable.tint: Int?
    get() = 0
    set(color) {
        color?.let {
            DrawableCompat.setTint(this, color)
        } ?: run {
            DrawableCompat.setTintList(this, null)
        }
    }

fun Context.makeDrawable(
    @ColorRes backgroundColor: Int,
    @ColorRes strokeColor: Int? = null,
    radius: Float = 25f.dp,
): GradientDrawable = GradientDrawable().apply {
    cornerRadius = radius
    setColor(ContextCompat.getColor(this@makeDrawable, backgroundColor))
    if (strokeColor != null)
        setStroke(1f.dp.toInt(), ContextCompat.getColor(this@makeDrawable, strokeColor))
}

var ImageView.tint: Int?
    get() = 0
    set(color) {
        color?.let {
            ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(it))
        } ?: run {
            ImageViewCompat.setImageTintList(this, null)
        }
    }
