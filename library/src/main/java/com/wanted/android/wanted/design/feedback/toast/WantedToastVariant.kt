package com.wanted.android.wanted.design.feedback.toast

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.wanted.android.designsystem.R

/**
 * sealed class WantedToastVariant
 *
 * Toast 메시지의 시각적 스타일을 정의하는 sealed 클래스입니다.
 *
 * 각 타입은 고유의 아이콘 리소스 및 색상 리소스를 포함하고 있으며,
 * 사용자 피드백의 성격(정보, 긍정, 경고, 부정 등)을 나타냅니다.
 *
 * 제공되는 Toast 스타일은 다음과 같습니다:
 * - Message: 일반 메시지 (아이콘 없음)입니다.
 * - Positive: 긍정적인 메시지 (체크 아이콘, 초록색)입니다.
 * - Cautionary: 주의 메시지 (느낌표 아이콘, 주황색)입니다.
 * - Negative: 부정적인 메시지 (X 아이콘, 빨간색)입니다.
 *
 * @property resourceId Int: 표시할 아이콘 리소스의 ID입니다.
 * @property tinColor Int: 아이콘에 적용될 색상 리소스의 ID입니다.
 * @property backgroundResourceId Int?: 배경 이미지 리소스의 ID입니다.
 * @property backgroundTintColor Int: 배경 이미지 리소스에 적용될 색상 리소스의 ID입니다.
 */
sealed class WantedToastVariant(
    @DrawableRes val resourceId: Int,
    @ColorRes val tinColor: Int,
    @DrawableRes val backgroundResourceId: Int? = null,
    @ColorRes val backgroundTintColor: Int = R.color.static_white
) {
    /**
     * data object Message
     *
     * 일반 텍스트 메시지를 표시하는 스타일입니다. 아이콘은 표시되지 않습니다.
     */
    data object Message : WantedToastVariant(-1, -1)

    /**
     * data object Positive
     *
     * 긍정적인 알림을 위한 스타일입니다.
     * 체크 아이콘과 초록색 색상이 사용됩니다.
     */
    data object Positive : WantedToastVariant(
        R.drawable.icon_normal_circle_check_fill,
        R.color.green_60,
        backgroundResourceId = R.drawable.icon_normal_circle_check_filler_svg
    )

    /**
     * data object Cautionary
     *
     * 주의가 필요한 상황을 나타내는 스타일입니다.
     * 삼각형 느낌표 아이콘과 주황색 색상이 사용됩니다.
     */
    data object Cautionary : WantedToastVariant(
        R.drawable.icon_normal_triangle_exclamation_fill,
        R.color.orange_60,
        backgroundResourceId = R.drawable.icon_normal_triangle_exclamation_filler_svg
    )

    /**
     * data object Negative
     *
     * 부정적인 상황을 나타내는 스타일입니다.
     * X 아이콘과 빨간색 색상이 사용됩니다.
     */
    data object Negative : WantedToastVariant(
        R.drawable.icon_normal_circle_close_fill,
        R.color.red_60,
        backgroundResourceId = R.drawable.icon_normal_circle_exclamation_filler_svg
    )
}