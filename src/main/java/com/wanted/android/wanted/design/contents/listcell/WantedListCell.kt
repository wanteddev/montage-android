package com.wanted.android.wanted.design.contents.listcell

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.toAnnotatedString

/**
 * 텍스트와 서브 텍스트(캡션), 아이콘 등의 요소를 조합하여 하나의 셀 형태로 표현하는 컴포넌트입니다.
 *
 * `String` 기반 텍스트 입력을 받아 내부적으로 `AnnotatedString` 변환 후 처리합니다.
 * 아이콘, 캡션, 클릭 이벤트, 구분선 등의 다양한 UI 옵션을 제공합니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedListCell(
 *     text = "텍스트",
 *     caption = "캡션",
 *     fillWidth = true,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 * @param text String: 셀에 표시할 메인 텍스트입니다.
 * @param modifier Modifier: 셀 외형, 배치, 패딩 등을 조정합니다.
 * @param textMaxLine Int: 텍스트 최대 줄 수를 지정합니다. 기본값은 1입니다.
 * @param caption String: 서브 텍스트(캡션)로 보조 정보를 제공합니다.
 * @param fillWidth Boolean: true일 경우 셀이 부모 너비를 가득 채웁니다.
 * @param verticalPadding WantedListCellContract.VerticalPadding: 셀 상하 패딩 크기를 조정합니다.
 * @param interactionPadding WantedListCellContract.InteractionPadding: 터치 영역의 좌우 여백을 지정합니다.
 * @param divider Boolean: true일 경우 셀 하단에 구분선을 표시합니다.
 * @param isEnable Boolean: 셀의 활성화 여부를 설정합니다. 비활성화 시 알파값이 줄어듭니다.
 * @param selected Boolean: true일 경우 메인 텍스트 색상을 primary로 강조 표시합니다.
 * @param ellipsis Boolean: true일 경우 텍스트가 넘칠 시 생략 부호(...)로 표시됩니다.
 * @param verticalAlignCenter Boolean: true일 경우 텍스트를 수직 중앙 정렬합니다.
 * @param chevrons Boolean: true일 경우 우측에 chevron 아이콘을 표시합니다.
 * @param leadingContent Function0<Unit>?: 좌측에 추가적인 컴포저블 콘텐츠를 배치할 수 있습니다.
 * @param trailingContent Function0<Unit>?: 우측에 추가적인 컴포저블 콘텐츠를 배치할 수 있습니다.
 * @param onClick Function0<Unit>?: 셀 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedListCell(
    text: String,
    modifier: Modifier = Modifier,
    textMaxLine: Int = 1,
    caption: String = "",
    fillWidth: Boolean = false,
    verticalPadding: WantedListCellContract.VerticalPadding = WantedListCellContract.VerticalPadding.Medium,
    interactionPadding: WantedListCellContract.InteractionPadding = WantedListCellContract.InteractionPadding.Default(fillWidth),
    divider: Boolean = false,
    isEnable: Boolean = true,
    selected: Boolean = false,
    ellipsis: Boolean = true,
    verticalAlignCenter: Boolean = ellipsis,
    chevrons: Boolean = false,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    WantedListCell(
        modifier = modifier,
        annotatedString = text.toAnnotatedString(),
        annotatedCaption = caption.toAnnotatedString(),
        textMaxLine = textMaxLine,
        verticalPadding = verticalPadding,
        interactionPadding = interactionPadding,
        fillWidth = fillWidth,
        divider = divider,
        isEnable = isEnable,
        selected = selected,
        ellipsis = ellipsis,
        verticalAlignCenter = verticalAlignCenter,
        chevrons = chevrons,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        onClick = onClick
    )
}

/**
 * `AnnotatedString` 기반 텍스트와 서브 텍스트를 활용하는 셀 컴포넌트입니다.
 *
 * 보통 내부에서 String 기반 `WantedListCell` 함수로부터 호출되며, 텍스트 스타일과 애노테이션을 직접 다룰 수 있는 고급 인터페이스입니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedListCell(
 *     annotatedString = AnnotatedString("텍스트"),
 *     annotatedCaption = AnnotatedString("캡션"),
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 * @param annotatedString AnnotatedString: 표시할 메인 텍스트입니다.
 * @param modifier Modifier: 셀 외형, 배치, 패딩 등을 조정합니다.
 * @param annotatedCaption AnnotatedString: 서브 텍스트(캡션)입니다.
 * @param fillWidth Boolean: true일 경우 셀이 부모 너비를 가득 채웁니다.
 * @param verticalPadding WantedListCellContract.VerticalPadding: 셀 상하 패딩 크기를 조정합니다.
 * @param interactionPadding WantedListCellContract.InteractionPadding: 터치 영역의 좌우 여백을 지정합니다.
 * @param divider Boolean: true일 경우 셀 하단에 구분선을 표시합니다.
 * @param isEnable Boolean: 셀의 활성화 여부를 설정합니다.
 * @param selected Boolean: true일 경우 텍스트 색상을 primary로 강조합니다.
 * @param ellipsis Boolean: true일 경우 텍스트가 넘칠 시 생략 부호(...)로 표시됩니다.
 * @param verticalAlignCenter Boolean: true일 경우 텍스트를 수직 중앙 정렬합니다.
 * @param chevrons Boolean: true일 경우 우측에 chevron 아이콘을 표시합니다.
 * @param textMaxLine Int: 텍스트 최대 줄 수를 지정합니다. 기본값은 1입니다.
 * @param titleStyle TextStyle?: 메인 텍스트의 커스텀 스타일을 설정할 수 있습니다.
 * @param captionStyle TextStyle?: 캡션 텍스트의 커스텀 스타일을 설정할 수 있습니다.
 * @param leadingContent Function0<Unit>?: 좌측에 추가적인 컴포저블 콘텐츠를 배치할 수 있습니다.
 * @param trailingContent Function0<Unit>?: 우측에 추가적인 컴포저블 콘텐츠를 배치할 수 있습니다.
 * @param onClick Function0<Unit>?: 셀 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedListCell(
    annotatedString: AnnotatedString,
    modifier: Modifier = Modifier,
    annotatedCaption: AnnotatedString = AnnotatedString(""),
    fillWidth: Boolean = false,
    verticalPadding: WantedListCellContract.VerticalPadding = WantedListCellContract.VerticalPadding.Medium,
    interactionPadding: WantedListCellContract.InteractionPadding = WantedListCellContract.InteractionPadding.Default(fillWidth),
    divider: Boolean = false,
    isEnable: Boolean = true,
    selected: Boolean = false,
    ellipsis: Boolean = true,
    verticalAlignCenter: Boolean = ellipsis,
    chevrons: Boolean = false,
    textMaxLine: Int = 1,
    titleStyle: TextStyle? = null,
    captionStyle: TextStyle? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {
    WantedTouchArea(
        shape = RoundedCornerShape(12.dp),
        isUseRipple = isEnable && onClick != null,
        horizontalPadding = if (fillWidth) 0.dp else interactionPadding.padding,
        content = {
            Column {
                WantedListCellImpl(
                    modifier = modifier
                        .clip(RoundedCornerShape(12.dp))
                        .padding(horizontal = if (fillWidth) interactionPadding.padding else 0.dp)
                        .padding(vertical = verticalPadding.value),
                    text = annotatedString,
                    textMaxLine = textMaxLine,
                    caption = annotatedCaption,
                    isEnable = isEnable,
                    selected = selected,
                    ellipsis = ellipsis,
                    verticalAlignCenter = verticalAlignCenter,
                    chevrons = chevrons,
                    titleStyle = titleStyle,
                    captionStyle = captionStyle,
                    leadingContent = leadingContent,
                    trailingContent = trailingContent
                )

                if (divider) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = if (fillWidth) 20.dp else 0.dp),
                        color = colorResource(id = R.color.line_normal_alternative)
                    )
                }
            }

        }
    ) {
        onClick?.invoke()
    }
}

@Composable
private fun WantedListCellImpl(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    textMaxLine: Int = 1,
    caption: AnnotatedString = AnnotatedString(""),
    isEnable: Boolean = true,
    selected: Boolean = false,
    ellipsis: Boolean = true,
    verticalAlignCenter: Boolean = ellipsis,
    chevrons: Boolean = false,
    titleStyle: TextStyle? = null,
    captionStyle: TextStyle? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    WantedListCellLayout(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (isEnable) 1f else OPACITY_43),
        verticalAlignment = if (verticalAlignCenter) Alignment.CenterVertically else Alignment.Top,
        text = {
            Text(
                text = text,
                maxLines = textMaxLine,
                overflow = if (ellipsis) TextOverflow.Ellipsis else TextOverflow.Clip,
                style = WantedTextStyle(
                    colorRes = when {
                        selected -> R.color.primary_normal
                        else -> R.color.label_normal
                    },
                    style = when {
                        titleStyle != null -> titleStyle
                        else -> DesignSystemTheme.typography.body1Regular
                    }
                )
            )
        },
        caption = if (caption.isNotEmpty()) {
            {
                Text(
                    text = caption,
                    maxLines = if (ellipsis) 1 else Int.MAX_VALUE,
                    overflow = if (ellipsis) TextOverflow.Ellipsis else TextOverflow.Clip,
                    style = WantedTextStyle(
                        colorRes = R.color.label_alternative,
                        style = when {
                            captionStyle != null -> captionStyle
                            else -> DesignSystemTheme.typography.label2Regular
                        }
                    )
                )
            }
        } else null,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        chevrons = if (chevrons) {
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_normal_chevron_right_tight_small_svg),
                    tint = colorResource(id = R.color.label_assistive),
                    contentDescription = ""
                )
            }
        } else {
            null
        }
    )
}


@Composable
private fun WantedListCellLayout(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    text: @Composable () -> Unit,
    caption: @Composable (() -> Unit)?,
    leadingContent: (@Composable () -> Unit)?,
    trailingContent: (@Composable () -> Unit)?,
    chevrons: (@Composable () -> Unit)?,
) {
    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = verticalAlignment
    ) {

        leadingContent?.let {
            Box(
                modifier = Modifier.wrapContentSize()
            ) {
                leadingContent()
            }
        }

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            text()

            caption?.invoke()
        }

        trailingContent?.let {
            Box(
                modifier = Modifier.wrapContentSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                trailingContent()
            }
        }

        chevrons?.let {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {
                chevrons()
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedListCellPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedListCell(
                    text = "텍스트",
                    fillWidth = true,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트",
                    fillWidth = false,
                    interactionPadding = WantedListCellContract.InteractionPadding.Custom(30.dp),
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트",
                    onClick = {},
                    divider = true
                )

                WantedListCell(
                    text = "텍스트",
                    caption = "캡션",
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트",
                    caption = "캡션",
                    selected = true,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트",
                    caption = "캡션",
                    selected = true,
                    isEnable = false,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트",
                    caption = "캡션",
                    onClick = {},
                    divider = true
                )

                WantedListCell(
                    text = "텍스트",
                    caption = "캡션",
                    isEnable = false,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 padding Small",
                    caption = "캡션",
                    verticalPadding = WantedListCellContract.VerticalPadding.Small,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 padding Normal",
                    caption = "캡션",
                    verticalPadding = WantedListCellContract.VerticalPadding.Medium,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 padding Medium",
                    caption = "캡션",
                    verticalPadding = WantedListCellContract.VerticalPadding.Large,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 padding Medium paddingInset asdf asdf",
                    caption = "캡션",
                    fillWidth = true,
                    divider = true,
                    verticalPadding = WantedListCellContract.VerticalPadding.Large,
                    onClick = {}
                )

            }
        }
    }
}