package com.wanted.android.wanted.design.feedback.fallback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedFallbackView
 *
 * 제목, 설명, 버튼을 조합하여 비어 있는 상태를 안내하는 컴포넌트입니다.
 *
 * 주로 데이터가 없거나 결과가 없을 때 사용자에게 피드백을 제공하는 용도로 사용됩니다.
 * 제목, 설명, 버튼을 선택적으로 구성할 수 있으며, 버튼 클릭 시 콜백을 전달할 수 있습니다.
 * 버튼은 Assistive 색상만 사용하며, 좌우 여백은 호출하는 화면에서 배치합니다.
 * 상하 여백은 [padding] 으로 지정합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedFallbackView(
 *     padding = WantedFallbackPadding.Normal,
 *     heading = "데이터가 없습니다.",
 *     description = "새로운 데이터를 추가해보세요.",
 *     main = "추가하기",
 *     onClickMain = { /* 버튼 클릭 처리 */ }
 * )
 * ```
 *
 * @param modifier Modifier: 외형 및 배치를 조정하는 Modifier입니다.
 * @param padding WantedFallbackPadding: 콘텐츠 상하 여백을 지정합니다 (Normal, Compact).
 * @param buttonVariant WantedFallbackButtonVariant: 버튼 배치 방식을 지정합니다 (Single, Horizontal, Vertical).
 * @param heading String?: 상단에 강조 텍스트(제목)를 표시합니다.
 * @param description String?: 제목 아래에 설명 텍스트를 표시합니다. 최대 두 줄까지만 표시됩니다.
 * @param main String?: 메인 액션 버튼에 표시될 텍스트입니다. null일 경우 버튼은 렌더링되지 않습니다.
 * @param alternative String?: 대체 액션 버튼에 표시될 텍스트입니다. null일 경우 버튼은 렌더링되지 않습니다.
 * @param onClickMain () -> Unit: 메인 액션 버튼 클릭 시 호출되는 콜백입니다.
 * @param onClickAlternative () -> Unit: 대체 액션 버튼 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedFallbackView(
    modifier: Modifier = Modifier,
    padding: WantedFallbackPadding = WantedFallbackPadding.Normal,
    buttonVariant: WantedFallbackButtonVariant = WantedFallbackButtonVariant.Single,
    heading: String? = null,
    description: String? = null,
    main: String? = null,
    alternative: String? = null,
    onClickMain: () -> Unit = {},
    onClickAlternative: () -> Unit = {}
) {
    WantedFallbackContent(
        modifier = modifier,
        verticalPadding = padding.verticalPadding,
        buttonVariant = buttonVariant,
        heading = heading,
        description = description,
        main = main,
        alternative = alternative,
        onClickMain = onClickMain,
        onClickAlternative = onClickAlternative
    )
}

/**
 * 이미지(일러스트) 슬롯이 포함된 [WantedFallbackView] 입니다.
 *
 * 디자인 4.0.0 에서 이미지 슬롯이 제거되어 더 이상 사용하지 않습니다.
 * 기존 화면 호환을 위해서만 유지하며, 이미지 없는 오버로드로 이관합니다.
 *
 * @param image (@Composable () -> Unit): 텍스트 위에 표시될 이미지 컴포넌트입니다.
 * @param modifier Modifier: 외형 및 배치를 조정하는 Modifier입니다.
 * @param buttonVariant WantedFallbackButtonVariant: 버튼 배치 방식을 지정합니다 (Single, Horizontal, Vertical).
 * @param heading String?: 상단에 강조 텍스트(제목)를 표시합니다.
 * @param description String?: 제목 아래에 설명 텍스트를 표시합니다. 최대 두 줄까지만 표시됩니다.
 * @param main String?: 메인 액션 버튼에 표시될 텍스트입니다. null일 경우 버튼은 렌더링되지 않습니다.
 * @param alternative String?: 대체 액션 버튼에 표시될 텍스트입니다. null일 경우 버튼은 렌더링되지 않습니다.
 * @param onClickMain () -> Unit: 메인 액션 버튼 클릭 시 호출되는 콜백입니다.
 * @param onClickAlternative () -> Unit: 대체 액션 버튼 클릭 시 호출되는 콜백입니다.
 */
@Deprecated(DEPRECATED_IMAGE_MESSAGE, level = DeprecationLevel.WARNING)
@Composable
fun WantedFallbackView(
    image: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    buttonVariant: WantedFallbackButtonVariant = WantedFallbackButtonVariant.Single,
    heading: String? = null,
    description: String? = null,
    main: String? = null,
    alternative: String? = null,
    onClickMain: () -> Unit = {},
    onClickAlternative: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = DesignSystemTheme.spacing.spacing20),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(DEPRECATED_IMAGE_SIZE),
            contentAlignment = Alignment.Center
        ) {
            image()
        }

        // 이미지 오버로드는 기존 화면 호환용이라 4.0.0 의 상하 여백을 적용하지 않는다.
        WantedFallbackContent(
            verticalPadding = DesignSystemTheme.spacing.spacing0,
            buttonVariant = buttonVariant,
            heading = heading,
            description = description,
            main = main,
            alternative = alternative,
            onClickMain = onClickMain,
            onClickAlternative = onClickAlternative
        )
    }
}

@Composable
private fun WantedFallbackContent(
    verticalPadding: Dp,
    buttonVariant: WantedFallbackButtonVariant,
    heading: String?,
    description: String?,
    main: String?,
    alternative: String?,
    onClickMain: () -> Unit,
    onClickAlternative: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding),
        verticalArrangement = Arrangement.spacedBy(DesignSystemTheme.spacing.spacing24),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WantedFallbackTexts(
            heading = heading,
            description = description
        )

        when (buttonVariant) {
            WantedFallbackButtonVariant.Single -> {
                WantedFallbackSingleButton(
                    main = main,
                    onClickMain = onClickMain
                )
            }

            WantedFallbackButtonVariant.Horizontal -> {
                WantedFallbackHorizontalButtons(
                    main = main,
                    alternative = alternative,
                    onClickMain = onClickMain,
                    onClickAlternative = onClickAlternative
                )
            }

            WantedFallbackButtonVariant.Vertical -> {
                WantedFallbackVerticalButtons(
                    main = main,
                    alternative = alternative,
                    onClickMain = onClickMain,
                    onClickAlternative = onClickAlternative
                )
            }
        }
    }
}

@Composable
private fun WantedFallbackTexts(
    heading: String?,
    description: String?,
    modifier: Modifier = Modifier
) {
    if (heading == null && description == null) return

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(DesignSystemTheme.spacing.spacing12),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        heading?.let {
            Text(
                text = it,
                style = DesignSystemTheme.typography.headline1Bold,
                color = DesignSystemTheme.colors.foregroundNeutralPrimary,
                textAlign = TextAlign.Center
            )
        }

        description?.let {
            Text(
                text = it,
                style = DesignSystemTheme.typography.body2ReadingRegular,
                color = DesignSystemTheme.colors.foregroundNeutralSecondary,
                textAlign = TextAlign.Center,
                maxLines = DESCRIPTION_MAX_LINES,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun WantedFallbackSingleButton(
    main: String?,
    onClickMain: () -> Unit,
    modifier: Modifier = Modifier
) {
    main?.let {
        WantedButton(
            modifier = modifier,
            text = it,
            variant = ButtonVariant.OUTLINED,
            type = ButtonType.ASSISTIVE,
            size = ButtonSize.MEDIUM,
            onClick = onClickMain
        )
    }
}

@Composable
private fun WantedFallbackHorizontalButtons(
    main: String?,
    alternative: String?,
    onClickMain: () -> Unit,
    onClickAlternative: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (main == null && alternative == null) return

    Row(
        modifier = modifier.width(intrinsicSize = IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(DesignSystemTheme.spacing.spacing10),
        verticalAlignment = Alignment.CenterVertically
    ) {
        alternative?.let {
            WantedButton(
                modifier = Modifier.weight(1f),
                text = it,
                variant = ButtonVariant.OUTLINED,
                type = ButtonType.ASSISTIVE,
                size = ButtonSize.MEDIUM,
                onClick = onClickAlternative
            )
        }

        main?.let {
            WantedButton(
                modifier = Modifier.weight(1f),
                text = it,
                variant = ButtonVariant.OUTLINED,
                type = ButtonType.ASSISTIVE,
                size = ButtonSize.MEDIUM,
                onClick = onClickMain
            )
        }
    }
}

@Composable
private fun WantedFallbackVerticalButtons(
    main: String?,
    alternative: String?,
    onClickMain: () -> Unit,
    onClickAlternative: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (main == null && alternative == null) return

    Column(
        modifier = modifier.width(intrinsicSize = IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(DesignSystemTheme.spacing.spacing8),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        main?.let {
            WantedButton(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                variant = ButtonVariant.OUTLINED,
                type = ButtonType.ASSISTIVE,
                size = ButtonSize.MEDIUM,
                onClick = onClickMain
            )
        }

        alternative?.let {
            WantedButton(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                variant = ButtonVariant.OUTLINED,
                type = ButtonType.ASSISTIVE,
                size = ButtonSize.MEDIUM,
                onClick = onClickAlternative
            )
        }
    }
}

enum class WantedFallbackButtonVariant {
    Single,
    Horizontal,
    Vertical
}

/**
 * enum class WantedFallbackPadding
 *
 * 콘텐츠 상하 여백 옵션입니다. Figma `Fallback View` 의 `Padding` 속성과 1:1 대응합니다.
 * 값은 Spacing 토큰(최대 80dp)으로 표현할 수 없어 시안 실측값을 그대로 사용합니다.
 *
 * @property verticalPadding Dp: 콘텐츠 위·아래에 각각 적용되는 여백입니다.
 */
enum class WantedFallbackPadding(internal val verticalPadding: Dp) {
    Normal(verticalPadding = 160.dp),
    Compact(verticalPadding = 80.dp)
}

private const val DESCRIPTION_MAX_LINES = 2

private val DEPRECATED_IMAGE_SIZE = 160.dp

private const val DEPRECATED_IMAGE_MESSAGE =
    "이미지(일러스트) 슬롯이 있는 WantedFallbackView 는 더 이상 사용하지 않습니다. " +
            "이미지 없이 heading/description/버튼만 조합하는 WantedFallbackView 를 사용하세요."

@DevicePreviews
@Composable
private fun WantedFallbackViewPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedFallbackView(
                    heading = "타이틀이 들어가요.",
                    description = "상황에 대한 설명이 들어가요.\n" +
                            "설명은 최대 두 줄로 작성해요.",
                    main = "메인 액션",
                    onClickMain = {}
                )

                WantedFallbackView(
                    padding = WantedFallbackPadding.Compact,
                    heading = "타이틀이 들어가요.",
                    description = "상황에 대한 설명이 들어가요.\n" +
                            "설명은 최대 두 줄로 작성해요."
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedFallbackViewHorizontalPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedFallbackView(
                    buttonVariant = WantedFallbackButtonVariant.Horizontal,
                    heading = "타이틀이 들어가요.",
                    description = "상황에 대한 설명이 들어가요.\n" +
                            "설명은 최대 두 줄로 작성해요.",
                    main = "메인 액션",
                    alternative = "대체 액션",
                    onClickMain = {}
                )
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedFallbackViewVerticalPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedFallbackView(
                    buttonVariant = WantedFallbackButtonVariant.Vertical,
                    heading = "타이틀이 들어가요.",
                    description = "상황에 대한 설명이 들어가요.\n" +
                            "설명은 최대 두 줄로 작성해요.",
                    main = "메인 액션",
                    alternative = "대체 액션",
                    onClickMain = {}
                )
            }
        }
    }
}
