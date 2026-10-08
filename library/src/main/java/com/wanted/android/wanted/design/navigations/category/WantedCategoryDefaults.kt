package com.wanted.android.wanted.design.navigations.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipSize
import com.wanted.android.wanted.design.actions.chip.WantedChipContract.ChipVariant
import com.wanted.android.wanted.design.actions.chip.WantedChipDefault
import com.wanted.android.wanted.design.actions.chip.WantedChipDefaults
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.OPACITY_43

/**
 * data class WantedCategoryDefault
 *
 * WantedCategory 의 표시 스펙을 지정하는 데이터 클래스입니다.
 *
 * 항목 크기·여백과 좌우 그라디언트 색상을 함께 담습니다. 기본값은 [WantedCategoryDefaults.getDefault] 로 생성합니다.
 *
 * @param size Size: 카테고리 항목의 크기 및 여백입니다.
 * @param horizontalPadding Boolean: 좌우 여백 적용 여부입니다.
 * @param isVerticalPadding Boolean: 상하 여백 적용 여부입니다.
 * @param gradientColor Color: 좌우 그라디언트 색상입니다.
 */
data class WantedCategoryDefault(
    val size: WantedCategoryDefaults.Size = WantedCategoryDefaults.Size.Medium,
    val horizontalPadding: Boolean = false,
    val isVerticalPadding: Boolean = false,
    val gradientColor: Color
)

/**
 * object WantedCategoryDefaults
 *
 * 카테고리 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
 */
object WantedCategoryDefaults {

    /**
     * enum class Size
     *
     * 카테고리 항목의 크기 및 여백을 정의하는 Enum 클래스입니다.
     *
     * 카테고리 항목의 시각적 크기와 간격을 결정할 때 사용됩니다. UI 요구사항에 따라 다음의 네 가지 옵션을 제공합니다:
     * - Small: 작은 크기의 카테고리 항목입니다.
     * - Medium: 중간 크기의 카테고리 항목입니다.
     * - Large: 큰 크기의 카테고리 항목입니다.
     * - XLarge: 매우 큰 크기의 카테고리 항목입니다.
     *
     * @property verticalPadding Dp: 상하 여백 적용 시 LazyRow 의 상하 여백입니다.
     * @property horizontalSpacing Dp: 항목 간 가로 간격입니다.
     * @property rightIconSize Dp: 우측 아이콘 슬롯의 크기입니다.
     *
     * @see WantedCategoryDefault
     */
    enum class Size(
        val verticalPadding: Dp,
        val horizontalSpacing: Dp,
        val rightIconSize: Dp,
    ) {
        Small(8.dp, 4.dp, 20.dp),
        Medium(8.dp, 6.dp, 22.dp),
        Large(10.dp, 8.dp, 24.dp),
        XLarge(10.dp, 10.dp, 24.dp)
    }

    // 좌우 여백 적용 시 LazyRow 의 좌우 contentPadding 입니다.
    internal val contentHorizontalPadding: Dp = 20.dp

    internal val gradientWidth: Dp = 48.dp

    // 그라디언트가 항목 하단 1px 라인을 덮지 않도록 띄우는 하단 여백입니다.
    internal val gradientBottomPadding: Dp = 1.dp

    internal val rightIconStartPadding: Dp = 12.dp
    internal val rightIconEndPadding: Dp = 8.dp

    /**
     * fun getDefault(...)
     *
     * WantedCategory 의 기본 표시 스펙을 생성합니다.
     *
     * 사용 예시:
     * ```kotlin
     * WantedCategory(
     *     categoryDefault = WantedCategoryDefaults.getDefault(
     *         size = WantedCategoryDefaults.Size.Large,
     *         gradientColor = DesignSystemTheme.colors.surfaceElevatedPrimary
     *     )
     * ) {
     *     items(tagList) { tag -> WantedChip(text = tag) }
     * }
     * ```
     *
     * @param size Size: 카테고리 항목의 크기 및 여백입니다. 기본값은 Medium 입니다.
     * @param horizontalPadding Boolean: 좌우 여백 적용 여부입니다. 기본값은 false 입니다.
     * @param isVerticalPadding Boolean: 상하 여백 적용 여부입니다. 기본값은 false 입니다.
     * @param gradientColor Color: 좌우 그라디언트 색상입니다. 기본값은 backgroundNeutralPrimary 입니다.
     * @return WantedCategoryDefault: 표시 스펙 객체를 반환합니다.
     */
    @Composable
    fun getDefault(
        size: Size = Size.Medium,
        horizontalPadding: Boolean = false,
        isVerticalPadding: Boolean = false,
        gradientColor: Color = DesignSystemTheme.colors.backgroundNeutralPrimary
    ): WantedCategoryDefault = WantedCategoryDefault(
        size = size,
        horizontalPadding = horizontalPadding,
        isVerticalPadding = isVerticalPadding,
        gradientColor = gradientColor
    )

    /**
     * sealed class Variant
     *
     * 카테고리에서 선택된 항목의 스타일을 정의하는 sealed 클래스입니다. (Figma Category/Resource/Chip Normal · Alternative)
     *
     * - Normal: 선택 항목을 채워진 배경으로 표시합니다. 선택 항목 색상을 커스텀할 수 있습니다.
     * - Alternative: 선택 항목을 primary 테두리·옅은 배경으로 표시합니다. 색상 커스텀을 제공하지 않습니다.
     *
     * 사용 예시:
     * ```kotlin
     * WantedCategory(
     *     itemList = itemList,
     *     selectedList = selectedList,
     *     variant = WantedCategoryDefaults.Variant.Normal(
     *         activeBackgroundColor = DesignSystemTheme.colors.surfaceBrandPrimary,
     *         activeContentColor = DesignSystemTheme.colors.staticWhite
     *     ),
     *     onClick = { _, _ -> }
     * )
     * ```
     */
    sealed class Variant {
        /**
         * data class Normal
         *
         * 선택 항목을 채워진 배경으로 표시합니다. 테두리는 없습니다.
         *
         * 지정하지 않은 색상(Color.Unspecified)은 기본 토큰으로 표시합니다.
         *
         * @param activeBackgroundColor Color: 선택된 항목의 배경 색상입니다. 기본값은 foregroundNeutralStrong 입니다.
         * @param activeContentColor Color: 선택된 항목의 텍스트·아이콘 색상입니다. 기본값은 foregroundNeutralInverse 입니다.
         */
        data class Normal(
            val activeBackgroundColor: Color = Color.Unspecified,
            val activeContentColor: Color = Color.Unspecified
        ) : Variant()

        /**
         * data object Alternative
         *
         * 선택 항목을 surfaceBrandPrimary 5% 배경 + surfaceBrandPrimary 43% 테두리로 표시합니다.
         */
        data object Alternative : Variant()

        companion object {
            // 선택 UI(데모 등) 에서 쓰는 preset 목록입니다.
            val presets: List<Variant> = listOf(Normal(), Alternative)
        }
    }

    /**
     * fun getChipDefault(...)
     *
     * Category 항목 Chip 스타일을 생성합니다.
     *
     * 미선택 상태는 Outlined Chip 기본값과 같고, 선택 상태만 [Variant] 에 따라 칠합니다.
     * - Normal 선택: [Variant.Normal] 의 배경·콘텐츠 색, 테두리 없음
     * - Normal 선택 + 비활성: surfaceDisablePrimary 배경, 테두리 없음
     * - Alternative 선택: surfaceBrandPrimary 5% 배경 + surfaceBrandPrimary 43% 테두리 (Outlined Chip 기본 테두리는 28%)
     *
     * @param size Size: 카테고리 항목의 크기입니다.
     * @param variant Variant: 선택 항목 스타일입니다.
     * @param isActive Boolean: 선택 여부입니다.
     * @param isEnable Boolean: 사용 가능 여부입니다.
     * @return WantedChipDefault: Category 항목 Chip 스타일을 반환합니다.
     */
    @Composable
    fun getChipDefault(
        size: Size,
        variant: Variant,
        isActive: Boolean,
        isEnable: Boolean
    ): WantedChipDefault {
        val default = WantedChipDefaults.getDefault(
            size = when (size) {
                Size.Small -> ChipSize.XSmall
                Size.Medium -> ChipSize.Small
                Size.Large -> ChipSize.Medium
                Size.XLarge -> ChipSize.Large
            },
            variant = ChipVariant.Outlined,
            isActive = isActive,
            isEnable = isEnable
        )

        if (!isActive) return default

        return when (variant) {
            is Variant.Normal -> if (isEnable) {
                val contentColor = variant.activeContentColor.takeOrElse { DesignSystemTheme.colors.foregroundNeutralInverse }
                default.copy(
                    iconColor = contentColor,
                    backgroundColor = variant.activeBackgroundColor.takeOrElse { DesignSystemTheme.colors.foregroundNeutralStrong },
                    borderColor = DesignSystemTheme.colors.transparent,
                    textStyle = default.textStyle.copy(color = contentColor)
                )
            } else {
                default.copy(
                    backgroundColor = DesignSystemTheme.colors.surfaceDisablePrimary,
                    borderColor = DesignSystemTheme.colors.transparent
                )
            }

            Variant.Alternative -> if (isEnable) {
                default.copy(borderColor = DesignSystemTheme.colors.surfaceBrandPrimary.copy(alpha = OPACITY_43))
            } else {
                default
            }
        }
    }
}
