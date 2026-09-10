package com.wanted.android.wanted.design.contents.listcell

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.wanted.android.wanted.design.util.toAnnotatedString

/**
 * WantedListCell
 *
 * 텍스트와 캡션, 아이콘 등의 요소를 조합하여 하나의 Cell 형태로 표현하는 컴포넌트입니다.
 *
 * String 기반 텍스트 입력을 받아 내부적으로 AnnotatedString 변환 후 처리합니다.
 * 아이콘, 캡션, 클릭 이벤트, 구분선 등 다양한 UI 옵션을 제공합니다.
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
 * @param modifier Modifier: 셀 최외곽 노드에 적용됩니다. 배경·테두리·클릭 영역이 모두 이 노드 기준입니다.
 * @param textMaxLine Int: 텍스트 최대 줄 수를 지정합니다. 기본값은 1입니다.
 * @param caption String: 서브 텍스트(캡션)로 보조 정보를 제공합니다.
 * @param fillWidth Boolean: true일 경우 셀이 부모 너비를 가득 채웁니다.
 * @param verticalPadding WantedListCellDefaults.VerticalPadding: 셀 상하 패딩 크기를 조정합니다.
 * @param interactionPadding WantedListCellDefaults.InteractionPadding: 터치 영역의 좌우 여백을 지정합니다.
 * @param divider Boolean: true일 경우 셀 하단에 구분선을 표시합니다.
 * @param isEnable Boolean: 셀의 활성화 여부를 설정합니다. 비활성화 시 알파값이 줄어듭니다.
 * @param selected Boolean: true일 경우 메인 텍스트 색상을 primary로 강조 표시합니다.
 * @param ellipsis Boolean: true일 경우 텍스트가 넘칠 시 생략 부호(...)로 표시됩니다.
 * @param verticalAlignCenter Boolean: true일 경우 텍스트를 수직 중앙 정렬합니다.
 * @param chevrons Boolean: true일 경우 우측에 chevron 아이콘을 표시합니다.
 * @param leadingContent (@Composable () -> Unit)? : 좌측에 추가적인 컴포넌트 콘텐츠를 배치할 수 있습니다.
 * @param trailingContent (@Composable () -> Unit)? : 우측에 추가적인 컴포넌트 콘텐츠를 배치할 수 있습니다.
 * @param onClick (() -> Unit)? : 셀 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedListCell(
    text: String,
    modifier: Modifier = Modifier,
    textMaxLine: Int = 1,
    caption: String = "",
    fillWidth: Boolean = false,
    verticalPadding: WantedListCellDefaults.VerticalPadding = WantedListCellDefaults.VerticalPadding.Medium,
    interactionPadding: WantedListCellDefaults.InteractionPadding = WantedListCellDefaults.InteractionPadding.Default(
        fillWidth
    ),
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
    WantedListCellSurface(
        modifier = modifier,
        annotatedString = text.toAnnotatedString(),
        annotatedCaption = caption.toAnnotatedString(),
        textMaxLine = textMaxLine,
        fillWidth = fillWidth,
        divider = divider,
        isEnable = isEnable,
        selected = selected,
        ellipsis = ellipsis,
        verticalAlignCenter = verticalAlignCenter,
        chevrons = chevrons,
        cellDefault = WantedListCellDefaults.getDefault(
            verticalPadding = verticalPadding,
            interactionPadding = interactionPadding
        ),
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        onClick = onClick
    )
}

/**
 * WantedListCell
 *
 * 셀 스펙을 `cellDefault` 하나로 묶어 전달하는 Cell 컴포넌트입니다.
 *
 * 표면(배경색·모양·테두리·안쪽 여백)과 패딩 스펙(`verticalPadding`·`interactionPadding`)을 한 객체로 넘기고 싶을 때 사용합니다.
 * 패딩을 개별 파라미터로 지정하려면 `cellDefault` 가 없는 오버로드를 사용합니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedListCell(
 *     text = "텍스트",
 *     cellDefault = WantedListCellDefaults.getDefault(
 *         backgroundColor = DesignSystemTheme.colors.backgroundNormalAlternative,
 *         shape = RoundedCornerShape(20.dp),
 *         contentPadding = PaddingValues(horizontal = 20.dp),
 *         verticalPadding = WantedListCellDefaults.VerticalPadding.Large
 *     ),
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 * @param text String: 셀에 표시할 메인 텍스트입니다.
 * @param cellDefault WantedListCellDefault: 셀 표면(배경색·모양·테두리·안쪽 여백) 설정을 담는 객체입니다.
 * @param modifier Modifier: 셀 최외곽 노드에 적용됩니다. 배경·테두리·클릭 영역이 모두 이 노드 기준입니다.
 * @param textMaxLine Int: 텍스트 최대 줄 수를 지정합니다. 기본값은 1입니다.
 * @param caption String: 서브 텍스트(캡션)로 보조 정보를 제공합니다.
 * @param fillWidth Boolean: true일 경우 셀이 부모 너비를 가득 채웁니다.
 * @param divider Boolean: true일 경우 셀 하단에 구분선을 표시합니다.
 * @param isEnable Boolean: 셀의 활성화 여부를 설정합니다. 비활성화 시 알파값이 줄어듭니다.
 * @param selected Boolean: true일 경우 메인 텍스트 색상을 primary로 강조 표시합니다.
 * @param ellipsis Boolean: true일 경우 텍스트가 넘칠 시 생략 부호(...)로 표시됩니다.
 * @param verticalAlignCenter Boolean: true일 경우 텍스트를 수직 중앙 정렬합니다.
 * @param chevrons Boolean: true일 경우 우측에 chevron 아이콘을 표시합니다.
 * @param leadingContent (@Composable () -> Unit)? : 좌측에 추가적인 컴포넌트 콘텐츠를 배치할 수 있습니다.
 * @param trailingContent (@Composable () -> Unit)? : 우측에 추가적인 컴포넌트 콘텐츠를 배치할 수 있습니다.
 * @param onClick (() -> Unit)? : 셀 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedListCell(
    text: String,
    cellDefault: WantedListCellDefault,
    modifier: Modifier = Modifier,
    textMaxLine: Int = 1,
    caption: String = "",
    fillWidth: Boolean = false,
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
    WantedListCellSurface(
        modifier = modifier,
        annotatedString = text.toAnnotatedString(),
        annotatedCaption = caption.toAnnotatedString(),
        textMaxLine = textMaxLine,
        fillWidth = fillWidth,
        divider = divider,
        isEnable = isEnable,
        selected = selected,
        ellipsis = ellipsis,
        verticalAlignCenter = verticalAlignCenter,
        chevrons = chevrons,
        cellDefault = cellDefault,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        onClick = onClick
    )
}

/**
 * WantedListCell
 *
 * `AnnotatedString` 기반 텍스트와 서브 텍스트를 활용하는 Cell 컴포넌트입니다.
 *
 * 보통 내부에서 String 기반 `WantedListCell` 함수로부터 호출되며, 텍스트 스타일과 Annotation을 직접 다룰 수 있는 고급 인터페이스입니다.
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
 * @param modifier Modifier: 셀 최외곽 노드에 적용됩니다. 배경·테두리·클릭 영역이 모두 이 노드 기준입니다.
 * @param annotatedCaption AnnotatedString: 서브 텍스트(캡션)입니다.
 * @param fillWidth Boolean: true일 경우 셀이 부모 너비를 가득 채웁니다.
 * @param verticalPadding WantedListCellDefaults.VerticalPadding: 셀 상하 패딩 크기를 조정합니다.
 * @param interactionPadding WantedListCellDefaults.InteractionPadding: 터치 영역의 좌우 여백을 지정합니다.
 * @param divider Boolean: true일 경우 셀 하단에 구분선을 표시합니다.
 * @param isEnable Boolean: 셀의 활성화 여부를 설정합니다.
 * @param selected Boolean: true일 경우 텍스트 색상을 primary로 강조합니다.
 * @param ellipsis Boolean: true일 경우 텍스트가 넘칠 시 생략 부호(...)로 표시됩니다.
 * @param verticalAlignCenter Boolean: true일 경우 텍스트를 수직 중앙 정렬합니다.
 * @param chevrons Boolean: true일 경우 우측에 chevron 아이콘을 표시합니다.
 * @param textMaxLine Int: 텍스트 최대 줄 수를 지정합니다. 기본값은 1입니다.
 * @param titleStyle TextStyle? : 메인 텍스트의 커스텀 스타일을 설정할 수 있습니다.
 * @param captionStyle TextStyle? : 캡션 텍스트의 커스텀 스타일을 설정할 수 있습니다.
 * @param leadingContent (@Composable () -> Unit)? : 좌측에 추가적인 컴포넌트 콘텐츠를 배치할 수 있습니다.
 * @param trailingContent (@Composable () -> Unit)? : 우측에 추가적인 컴포넌트 콘텐츠를 배치할 수 있습니다.
 * @param onClick (() -> Unit)? : 셀 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedListCell(
    annotatedString: AnnotatedString,
    modifier: Modifier = Modifier,
    annotatedCaption: AnnotatedString = AnnotatedString(""),
    fillWidth: Boolean = false,
    verticalPadding: WantedListCellDefaults.VerticalPadding = WantedListCellDefaults.VerticalPadding.Medium,
    interactionPadding: WantedListCellDefaults.InteractionPadding = WantedListCellDefaults.InteractionPadding.Default(
        fillWidth
    ),
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
    WantedListCellSurface(
        modifier = modifier,
        annotatedString = annotatedString,
        annotatedCaption = annotatedCaption,
        textMaxLine = textMaxLine,
        fillWidth = fillWidth,
        divider = divider,
        isEnable = isEnable,
        selected = selected,
        ellipsis = ellipsis,
        verticalAlignCenter = verticalAlignCenter,
        chevrons = chevrons,
        titleStyle = titleStyle,
        captionStyle = captionStyle,
        cellDefault = WantedListCellDefaults.getDefault(
            verticalPadding = verticalPadding,
            interactionPadding = interactionPadding
        ),
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        onClick = onClick
    )
}

/**
 * WantedListCell
 *
 * `AnnotatedString` 기반 텍스트에 셀 스펙을 `cellDefault` 하나로 묶어 전달하는 Cell 컴포넌트입니다.
 *
 * 표면(배경색·모양·테두리·안쪽 여백)과 패딩 스펙(`verticalPadding`·`interactionPadding`)을 한 객체로 넘기고 싶을 때 사용합니다.
 * 패딩을 개별 파라미터로 지정하려면 `cellDefault` 가 없는 오버로드를 사용합니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedListCell(
 *     annotatedString = AnnotatedString("텍스트"),
 *     cellDefault = WantedListCellDefaults.getDefault(
 *         backgroundColor = DesignSystemTheme.colors.backgroundNormalAlternative,
 *         contentPadding = PaddingValues(horizontal = 20.dp),
 *         verticalPadding = WantedListCellDefaults.VerticalPadding.Large
 *     ),
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 * @param annotatedString AnnotatedString: 표시할 메인 텍스트입니다.
 * @param cellDefault WantedListCellDefault: 셀 표면(배경색·모양·테두리·안쪽 여백) 설정을 담는 객체입니다.
 * @param modifier Modifier: 셀 최외곽 노드에 적용됩니다. 배경·테두리·클릭 영역이 모두 이 노드 기준입니다.
 * @param annotatedCaption AnnotatedString: 서브 텍스트(캡션)입니다.
 * @param fillWidth Boolean: true일 경우 셀이 부모 너비를 가득 채웁니다.
 * @param divider Boolean: true일 경우 셀 하단에 구분선을 표시합니다.
 * @param isEnable Boolean: 셀의 활성화 여부를 설정합니다.
 * @param selected Boolean: true일 경우 텍스트 색상을 primary로 강조합니다.
 * @param ellipsis Boolean: true일 경우 텍스트가 넘칠 시 생략 부호(...)로 표시됩니다.
 * @param verticalAlignCenter Boolean: true일 경우 텍스트를 수직 중앙 정렬합니다.
 * @param chevrons Boolean: true일 경우 우측에 chevron 아이콘을 표시합니다.
 * @param textMaxLine Int: 텍스트 최대 줄 수를 지정합니다. 기본값은 1입니다.
 * @param titleStyle TextStyle? : 메인 텍스트의 커스텀 스타일을 설정할 수 있습니다.
 * @param captionStyle TextStyle? : 캡션 텍스트의 커스텀 스타일을 설정할 수 있습니다.
 * @param leadingContent (@Composable () -> Unit)? : 좌측에 추가적인 컴포넌트 콘텐츠를 배치할 수 있습니다.
 * @param trailingContent (@Composable () -> Unit)? : 우측에 추가적인 컴포넌트 콘텐츠를 배치할 수 있습니다.
 * @param onClick (() -> Unit)? : 셀 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedListCell(
    annotatedString: AnnotatedString,
    cellDefault: WantedListCellDefault,
    modifier: Modifier = Modifier,
    annotatedCaption: AnnotatedString = AnnotatedString(""),
    fillWidth: Boolean = false,
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
    WantedListCellSurface(
        modifier = modifier,
        annotatedString = annotatedString,
        annotatedCaption = annotatedCaption,
        textMaxLine = textMaxLine,
        fillWidth = fillWidth,
        divider = divider,
        isEnable = isEnable,
        selected = selected,
        ellipsis = ellipsis,
        verticalAlignCenter = verticalAlignCenter,
        chevrons = chevrons,
        titleStyle = titleStyle,
        captionStyle = captionStyle,
        cellDefault = cellDefault,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        onClick = onClick
    )
}

@Composable
private fun WantedListCellSurface(
    annotatedString: AnnotatedString,
    cellDefault: WantedListCellDefault,
    modifier: Modifier = Modifier,
    annotatedCaption: AnnotatedString = AnnotatedString(""),
    fillWidth: Boolean = false,
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
    val verticalPadding = cellDefault.verticalPadding
    val interactionPadding = cellDefault.interactionPadding.resolve(fillWidth)
    val interactionShape = cellDefault.interactionShape
        ?: if (fillWidth) RoundedCornerShape(12.dp) else RoundedCornerShape(0.dp)

    WantedTouchArea(
        modifier = modifier
            .background(color = cellDefault.backgroundColor, shape = cellDefault.shape)
            .cellBorder(cellDefault),
        shape = interactionShape,
        isUseRipple = isEnable && onClick != null,
        horizontalPadding = if (fillWidth) 0.dp else interactionPadding.padding,
        content = {
            Column {
                WantedListCellImpl(
                    modifier = Modifier
                        .padding(cellDefault.contentPadding)
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
                        color = DesignSystemTheme.colors.lineNormalAlternative
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
                style = when {
                    titleStyle != null -> titleStyle
                    else -> DesignSystemTheme.typography.body1Regular
                },
                color = when {
                    selected -> DesignSystemTheme.colors.primaryNormal
                    else -> DesignSystemTheme.colors.labelNormal
                }
            )
        },
        caption = if (caption.isNotEmpty()) {
            {
                Text(
                    text = caption,
                    maxLines = if (ellipsis) 1 else Int.MAX_VALUE,
                    overflow = if (ellipsis) TextOverflow.Ellipsis else TextOverflow.Clip,
                    style = when {
                        captionStyle != null -> captionStyle
                        else -> DesignSystemTheme.typography.label2Regular
                    },
                    color = DesignSystemTheme.colors.labelAlternative
                )
            }
        } else null,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        chevrons = if (chevrons) {
            {
                Icon(
                    painter = painterResource(id = R.drawable.icon_normal_chevron_right_tight_small),
                    tint = DesignSystemTheme.colors.labelAssistive,
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
                    interactionPadding = WantedListCellDefaults.InteractionPadding.Custom(30.dp),
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
                    verticalPadding = WantedListCellDefaults.VerticalPadding.Small,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 padding Normal",
                    caption = "캡션",
                    verticalPadding = WantedListCellDefaults.VerticalPadding.Medium,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 padding Medium",
                    caption = "캡션",
                    verticalPadding = WantedListCellDefaults.VerticalPadding.Large,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 padding Medium paddingInset asdf asdf",
                    caption = "캡션",
                    fillWidth = true,
                    divider = true,
                    verticalPadding = WantedListCellDefaults.VerticalPadding.Large,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 cellDefault",
                    caption = "배경·모양·테두리·안쪽 여백",
                    fillWidth = true,
                    cellDefault = WantedListCellDefaults.getDefault(
                        backgroundColor = DesignSystemTheme.colors.backgroundNormalAlternative,
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = DesignSystemTheme.colors.lineNormalAlternative
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ),
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 cellDefault + 패딩 스펙",
                    caption = "표면과 패딩을 한 객체로",
                    fillWidth = true,
                    cellDefault = WantedListCellDefaults.getDefault(
                        backgroundColor = DesignSystemTheme.colors.backgroundNormalAlternative,
                        shape = RoundedCornerShape(20.dp),
                        verticalPadding = WantedListCellDefaults.VerticalPadding.Large,
                        interactionPadding = WantedListCellDefaults.InteractionPadding.Custom(30.dp)
                    ),
                    onClick = {}
                )

            }
        }
    }
}

/**
 * Modifier.cellBorder
 *
 * 셀 표면 테두리를 배경 위에 그립니다. cellDefault.border 가 null 이면 아무것도 적용하지 않습니다.
 *
 * background 다음에 호출해야 테두리가 배경에 가려지지 않습니다.
 *
 * @param cellDefault WantedListCellDefault: 테두리와 모양 정보를 담은 설정 객체입니다.
 * @return Modifier: 테두리가 적용된 Modifier 입니다.
 */
private fun Modifier.cellBorder(cellDefault: WantedListCellDefault): Modifier =
    cellDefault.border?.let { border(border = it, shape = cellDefault.shape) } ?: this

/**
 * InteractionPadding.resolve
 *
 * 터치 영역 좌우 여백 스펙을 실제 fillWidth 값으로 해석합니다.
 *
 * Default 는 "fillWidth 에 따라 자동"이라는 의미이므로, WantedListCellDefault 에 담겨 fillWidth 를 모른 채 전달됐더라도 컴포넌트의 실제 값으로 다시 계산합니다.
 *
 * Custom 은 개발자가 직접 고정한 값이므로 그대로 사용합니다.
 *
 * @param fillWidth Boolean: 컴포넌트에 전달된 실제 fillWidth 값입니다.
 * @return WantedListCellDefaults.InteractionPadding: 해석이 끝난 여백 스펙입니다.
 */
private fun WantedListCellDefaults.InteractionPadding.resolve(
    fillWidth: Boolean
): WantedListCellDefaults.InteractionPadding = when (this) {
    is WantedListCellDefaults.InteractionPadding.Default ->
        WantedListCellDefaults.InteractionPadding.Default(fillWidth)

    is WantedListCellDefaults.InteractionPadding.Custom -> this
}
