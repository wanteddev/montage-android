package com.wanted.android.wanted.design.util

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
    radius: Float = 10f.dp,
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

const val OPACITY_0 = 0f
const val OPACITY_5 = 0.05f
const val OPACITY_8 = 0.08f
const val OPACITY_12 = 0.12f
const val OPACITY_16 = 0.16f
const val OPACITY_22 = 0.22f
const val OPACITY_28 = 0.28f
const val OPACITY_35 = 0.35f
const val OPACITY_43 = 0.43f
const val OPACITY_52 = 0.52f
const val OPACITY_61 = 0.61f
const val OPACITY_70 = 0.70f
const val OPACITY_74 = 0.74f
const val OPACITY_88 = 0.88f
const val OPACITY_97 = 0.97f
const val OPACITY_100 = 1f