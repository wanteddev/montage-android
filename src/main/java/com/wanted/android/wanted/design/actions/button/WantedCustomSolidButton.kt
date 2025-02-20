package com.wanted.android.wanted.design.actions.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonStatus
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.util.getButtonDrawableSize
import com.wanted.android.wanted.design.util.getButtonHeight
import com.wanted.android.wanted.design.util.getButtonHorizontalPadding
import com.wanted.android.wanted.design.util.getButtonRadius
import com.wanted.android.wanted.design.util.getButtonSpaceBetweenTextAndIcon
import com.wanted.android.wanted.design.util.getButtonTypography
import com.wanted.android.wanted.design.util.wantedRippleEffect

@Composable
fun WantedCustomSolidButton(
    text: String,
    size: ButtonSize = ButtonSize.LARGE,
    status: ButtonStatus = ButtonStatus.ENABLE,
    modifier: Modifier = Modifier.wrapContentWidth(align = Alignment.CenterHorizontally),
    enableBackgroundColor: Int = R.color.primary_normal,
    disableBackgroundColor: Int = R.color.interaction_disable,
    enableTextColor: Int = R.color.static_white,
    disableTextColor: Int = R.color.label_assistive,
    leftDrawable: Int? = null,
    rightDrawable: Int? = null,
    isClickOnce: Boolean = true,
    clickListener: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(size = getButtonRadius(ButtonShape.SOLID, size = size)))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = if (status == ButtonStatus.ENABLE) wantedRippleEffect() else null,
                onClick = {
                    if (status == ButtonStatus.ENABLE) {
                        if (isClickOnce) {
                            clickListener?.clickOnce()
                        } else {
                            clickListener?.invoke()
                        }
                    }
                }
            )
            .background(
                colorResource(
                    id = if (status == ButtonStatus.ENABLE) enableBackgroundColor else disableBackgroundColor
                ),
                RoundedCornerShape(size = getButtonRadius(ButtonShape.SOLID, size = size))
            )
            .height(getButtonHeight(shape = ButtonShape.SOLID, size = size))
            .padding(
                horizontal = getButtonHorizontalPadding(
                    shape = ButtonShape.SOLID,
                    size = size
                )
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftDrawable?.let {
            Image(
                painter = painterResource(id = it),
                modifier = getButtonDrawableSize(shape = ButtonShape.SOLID, size = size),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(
                    color = colorResource(
                        id = if (status == ButtonStatus.ENABLE) {
                            enableTextColor
                        } else disableTextColor
                    )
                )
            )
            Spacer(
                modifier = Modifier.width(
                    getButtonSpaceBetweenTextAndIcon(
                        shape = ButtonShape.SOLID,
                        size = size
                    )
                )
            )
        }
        Text(
            text = text,
            modifier = Modifier
                .wrapContentHeight(),
            style = getButtonTypography(shape = ButtonShape.SOLID, ButtonType.PRIMARY, size = size),
            color = if (status == ButtonStatus.ENABLE) {
                colorResource(id = enableTextColor)
            } else colorResource(id = disableTextColor),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        rightDrawable?.let {
            Spacer(
                modifier = Modifier.width(
                    getButtonSpaceBetweenTextAndIcon(
                        shape = ButtonShape.SOLID,
                        size = size
                    )
                )
            )
            Image(
                painter = painterResource(id = it),
                modifier = getButtonDrawableSize(shape = ButtonShape.SOLID, size = size),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(
                    color = colorResource(
                        id = if (status == ButtonStatus.ENABLE) {
                            enableTextColor
                        } else disableTextColor
                    )
                )
            )
        }
    }
}