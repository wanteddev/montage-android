package com.wanted.android.wanted.design.feedback.toast

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.wanted.android.designsystem.R

/**
 * 토스트의 메시지 스타일 유형을 정의하는 sealed 클래스입니다.
 *
 * @property resourceId Int: 표시할 아이콘 리소스 ID입니다.
 * @property tinColor Int: 아이콘에 적용할 색상 리소스 ID입니다.
 */
sealed class WantedToastVariant(
    @DrawableRes val resourceId: Int,
    @ColorRes val tinColor: Int
) {
    data object Message : WantedToastVariant(-1, -1)
    data object Positive : WantedToastVariant(
        R.drawable.ic_normal_circle_check_fill_svg,
        R.color.green_60
    )

    data object Cautionary : WantedToastVariant(
        R.drawable.ic_normal_circle_exclamation_fill_svg,
        R.color.orange_60
    )

    data object Negative : WantedToastVariant(
        R.drawable.ic_normal_circle_exclamation_fill_svg,
        R.color.red_60
    )
}