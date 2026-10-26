package com.wanted.android.wanted.design.input.select

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 * data class WantedSelectData
 *
 * WantedSelect(단일·다중 선택)에서 사용되는 선택 항목 데이터 모델입니다.
 *
 * 선택 가능한 항목을 구성할 때 텍스트, 아이콘 리소스, URL, 부가 데이터 등을 함께 담을 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * val item = WantedSelectData(
 *     id = "01",
 *     text = "디자인",
 *     iconUrl = "https://icon.url",
 *     iconRes = R.drawable.icon_normal_bookmark,
 *     tint = R.color.foreground_brand_primary,
 *     any = "ui"
 * )
 * ```
 *
 * @property id String: 항목의 고유 ID입니다.
 * @property text String: 사용자에게 표시할 텍스트입니다.
 * @property iconUrl String: 아이콘 이미지 URL입니다. 다중 선택의 Chip 렌더에서만 표시되며, iconRes 와 함께 지정하면 iconUrl 이 우선합니다.
 * @property any Any?: 부가 데이터를 담기 위한 확장 필드입니다.
 * @property iconRes Int: Drawable 리소스 ID입니다. 다중 선택의 Chip 렌더에서만 표시되며 iconUrl 이 비어 있을 때 사용됩니다.
 * @property tint Int: iconRes 아이콘에 적용할 색상 리소스 ID입니다. 0 이면 기본 콘텐츠 색을 쓰며 iconUrl 이미지에는 적용되지 않습니다.
 */
data class WantedSelectData(
    val id: String = "",
    val text: String = "",
    val iconUrl: String = "",
    val any: Any? = null,

    @DrawableRes val iconRes: Int = 0,
    @ColorRes val tint: Int = 0
)