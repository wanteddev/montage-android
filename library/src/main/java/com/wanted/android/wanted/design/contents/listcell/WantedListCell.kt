package com.wanted.android.wanted.design.contents.listcell

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.base.WantedTouchArea
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.toAnnotatedString

// 텍스트 영역의 최소 높이. 타이틀 첫 줄(22)과 상하 패딩(1)을 합친 Figma 행 높이다.
private val TEXT_CONTENT_MIN_HEIGHT: Dp = 24.dp

// 타이틀 행 상하 패딩. 한 줄 셀의 텍스트 영역을 Figma 행 높이(24)로 맞추기 위한 값으로,
// 타이틀 첫 줄은 텍스트 영역 top 에서 이만큼 내려온 위치에서 시작한다.
// Spacing 토큰 스케일(짝수)에 없는 값이라 고정값으로 둔다.
private val TITLE_VERTICAL_PADDING: Dp = 1.dp

// 타이틀 스타일의 lineHeight 를 알 수 없을 때 쓰는 첫 줄 높이. 기본 타이틀(Body 2) 의 lineHeight 와 같다.
private val TITLE_LINE_HEIGHT_FALLBACK: Dp = 22.dp

// 행 콘텐츠 간 간격. leading·텍스트·trailing 슬롯 사이와 각 슬롯 내부 항목 사이에 같은 값을 쓴다.
private val ROW_CONTENT_GAP: Dp = 8.dp

// Label Trailing 슬롯의 항목 간 간격이자 타이틀과 슬롯 사이 간격.
private val LABEL_TRAILING_GAP: Dp = 4.dp

// selected 기본 표시(Check) 아이콘 크기. Figma 의 Icon/Normal/Check 인스턴스 크기와 같다.
private val SELECTED_CHECK_ICON_SIZE: Dp = 22.dp

// chevron 아이콘 크기. 원본 drawable(12x24)을 그대로 두면 Figma(8x16)보다 1.5배 크게 그려진다.
private val CHEVRON_ICON_WIDTH: Dp = 8.dp
private val CHEVRON_ICON_HEIGHT: Dp = 16.dp

// Label Trailing 슬롯의 고정 높이. 타이틀 라인 높이와 같은 값으로 고정해
// 배지 등을 넣어도 행 높이가 밀리지 않게 한다.
private val LABEL_TRAILING_HEIGHT: Dp = 22.dp

/**
 * WantedListCell
 *
 * 텍스트와 설명, 아이콘 등의 요소를 조합하여 하나의 Cell 형태로 표현하는 컴포넌트입니다.
 *
 * String 기반 텍스트 입력을 받아 내부적으로 AnnotatedString 변환 후 처리합니다.
 * 아이콘, 설명, 클릭 이벤트, 구분선 등 다양한 UI 옵션을 제공합니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedListCell(
 *     text = "텍스트",
 *     description = "설명",
 *     variant = WantedListCellDefaults.Variant.Full,
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 * @param text String: 셀에 표시할 메인 텍스트입니다.
 * @param modifier Modifier: 셀 최외곽 노드에 적용됩니다. 클릭 영역이 이 노드 기준이며, padding 은 클릭 영역 바깥 마진이 됩니다.
 * @param textMaxLine Int: ellipsis가 true일 때 적용할 텍스트 최대 줄 수입니다. 기본값은 1입니다.
 * @param description String: 서브 텍스트(설명)로 보조 정보를 제공합니다.
 * @param variant WantedListCellDefaults.Variant: 좌우 패딩·인터랙션 영역·radius를 묶어 지정합니다. 기본값은 Inset입니다.
 * @param verticalPadding WantedListCellDefaults.VerticalPadding: 셀 상하 패딩 크기를 조정합니다. None 은 인터랙션(outset·radius·ripple)을 사용하지 않습니다.
 * @param cellDefault WantedListCellDefault: 셀 표면(배경색·모양·테두리·안쪽 여백) 설정을 담는 객체입니다.
 * @param divider Boolean: true일 경우 셀 하단에 구분선을 표시합니다.
 * @param isEnable Boolean: 셀의 활성화 여부를 설정합니다. 비활성화 시 클릭이 차단되고(onClick 미호출), 타이틀·설명·Check·chevron에 disable 색상을 적용합니다. 슬롯 콘텐츠는 자동으로 비활성 표현되지 않습니다. 아바타·썸네일처럼 색으로 표현할 수 없는 이미지 계열은 호출부에서 Opacity/43(OPACITY_43)을 적용합니다.
 * @param selected Boolean: true일 경우 메인 텍스트를 primary 색상과 Body 2 Bold로 강조 표시하고, trailingContent 가 비어 있으면 우측에 Check 아이콘을 표시합니다. chevrons 와 함께 쓰지 않습니다(함께 켜면 chevron 이 우선).
 * @param ellipsis Boolean: true일 경우 textMaxLine 줄로 제한하고 넘칠 시 생략 부호(...)를 표시합니다. false일 경우 줄 수를 제한하지 않습니다. 기본값은 false입니다.
 * @param verticalAlignCenter Boolean: leading·trailing(chevron 포함) 슬롯을 타이틀 첫 줄에 맞추는 기준입니다. 슬롯의 최소 높이는 타이틀 첫 줄 높이이고 슬롯 안 콘텐츠는 수직 중앙 정렬됩니다. false(기본값)면 슬롯 top 을 첫 줄 top 에, true 면 슬롯 center 를 첫 줄 center 에 맞춥니다. 설명·extraContent 가 있거나 타이틀이 여러 줄이어도 기준은 텍스트 영역 전체가 아니라 첫 줄입니다.
 * @param chevrons Boolean: true일 경우 우측에 chevron 아이콘을 표시합니다. selected 와 함께 켜면 Check 대신 chevron 이 표시됩니다.
 * @param labelTrailingContent (@Composable RowScope.() -> Unit)? : 타이틀 옆 배지 슬롯입니다. 다중 배치(간격 4dp), 높이 22dp 고정이며 폭은 이 슬롯이 우선입니다.
 * @param extraContent (@Composable () -> Unit)? : 설명 아래에 배치하는 자유 슬롯입니다. 폭은 부모를 채우며, 내부 구성과 타이포·색상은 사용처가 정합니다(가로 배치 시 권장 간격 6dp).
 * @param leadingContent (@Composable RowScope.() -> Unit)? : 좌측 슬롯입니다. 여러 개를 넣을 수 있고 항목 간 간격은 8dp입니다.
 * @param trailingContent (@Composable RowScope.() -> Unit)? : 우측 슬롯입니다. 여러 개를 넣을 수 있고 항목 간 간격은 8dp입니다. selected 일 때 이 슬롯을 채우면 Check 아이콘 대신 넣은 것이 보입니다.
 * @param enabledInnerTouch Boolean: true일 경우 슬롯에 넣은 컨트롤(체크박스·스위치·버튼 등)이 자기 터치를 먼저 받습니다. 기본값 false 에서는 셀 클릭 영역이 콘텐츠 위를 덮어, 슬롯 안 어디를 눌러도 [onClick] 만 호출됩니다. true 로 두면 컨트롤 영역의 터치는 컨트롤이 처리하고 [onClick] 은 호출되지 않으므로, 컨트롤 콜백에서 상태를 갱신해야 합니다.
 * @param onClick (() -> Unit)? : 셀 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedListCell(
    text: String,
    modifier: Modifier = Modifier,
    textMaxLine: Int = 1,
    description: String = "",
    variant: WantedListCellDefaults.Variant = WantedListCellDefaults.Variant.Inset,
    verticalPadding: WantedListCellDefaults.VerticalPadding = WantedListCellDefaults.VerticalPadding.Medium,
    cellDefault: WantedListCellDefault = WantedListCellDefault(),
    divider: Boolean = false,
    isEnable: Boolean = true,
    selected: Boolean = false,
    ellipsis: Boolean = false,
    verticalAlignCenter: Boolean = false,
    chevrons: Boolean = false,
    labelTrailingContent: (@Composable RowScope.() -> Unit)? = null,
    extraContent: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable RowScope.() -> Unit)? = null,
    trailingContent: (@Composable RowScope.() -> Unit)? = null,
    enabledInnerTouch: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    WantedListCell(
        modifier = modifier,
        annotatedString = text.toAnnotatedString(),
        annotatedDescription = description.toAnnotatedString(),
        textMaxLine = textMaxLine,
        variant = variant,
        verticalPadding = verticalPadding,
        cellDefault = cellDefault,
        divider = divider,
        isEnable = isEnable,
        selected = selected,
        ellipsis = ellipsis,
        verticalAlignCenter = verticalAlignCenter,
        chevrons = chevrons,
        labelTrailingContent = labelTrailingContent,
        extraContent = extraContent,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        enabledInnerTouch = enabledInnerTouch,
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
 *     annotatedDescription = AnnotatedString("설명"),
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 * @param annotatedString AnnotatedString: 표시할 메인 텍스트입니다.
 * @param modifier Modifier: 셀 최외곽 노드에 적용됩니다. 클릭 영역이 이 노드 기준이며, padding 은 클릭 영역 바깥 마진이 됩니다.
 * @param annotatedDescription AnnotatedString: 서브 텍스트(설명)입니다.
 * @param variant WantedListCellDefaults.Variant: 좌우 패딩·인터랙션 영역·radius를 묶어 지정합니다. 기본값은 Inset입니다.
 * @param verticalPadding WantedListCellDefaults.VerticalPadding: 셀 상하 패딩 크기를 조정합니다. None 은 인터랙션(outset·radius·ripple)을 사용하지 않습니다.
 * @param cellDefault WantedListCellDefault: 셀 표면(배경색·모양·테두리·안쪽 여백) 설정을 담는 객체입니다.
 * @param divider Boolean: true일 경우 셀 하단에 구분선을 표시합니다.
 * @param isEnable Boolean: 셀의 활성화 여부를 설정합니다. 비활성화 시 클릭이 차단되고(onClick 미호출), 타이틀·설명·Check·chevron에 disable 색상을 적용합니다. 슬롯 콘텐츠는 자동으로 비활성 표현되지 않습니다. 아바타·썸네일처럼 색으로 표현할 수 없는 이미지 계열은 호출부에서 Opacity/43(OPACITY_43)을 적용합니다.
 * @param selected Boolean: true일 경우 텍스트를 primary 색상과 Body 2 Bold로 강조하고, trailingContent 가 비어 있으면 우측에 Check 아이콘을 표시합니다. chevrons 와 함께 쓰지 않습니다(함께 켜면 chevron 이 우선).
 * @param ellipsis Boolean: true일 경우 textMaxLine 줄로 제한하고 넘칠 시 생략 부호(...)를 표시합니다. false일 경우 줄 수를 제한하지 않습니다. 기본값은 false입니다.
 * @param verticalAlignCenter Boolean: leading·trailing(chevron 포함) 슬롯을 타이틀 첫 줄에 맞추는 기준입니다. 슬롯의 최소 높이는 타이틀 첫 줄 높이이고 슬롯 안 콘텐츠는 수직 중앙 정렬됩니다. false(기본값)면 슬롯 top 을 첫 줄 top 에, true 면 슬롯 center 를 첫 줄 center 에 맞춥니다. 설명·extraContent 가 있거나 타이틀이 여러 줄이어도 기준은 텍스트 영역 전체가 아니라 첫 줄입니다.
 * @param chevrons Boolean: true일 경우 우측에 chevron 아이콘을 표시합니다. selected 와 함께 켜면 Check 대신 chevron 이 표시됩니다.
 * @param textMaxLine Int: ellipsis가 true일 때 적용할 텍스트 최대 줄 수입니다. 기본값은 1입니다.
 * @param titleStyle TextStyle? : 메인 텍스트의 커스텀 스타일을 설정할 수 있습니다.
 * @param descriptionStyle TextStyle? : 설명 텍스트의 커스텀 스타일을 설정할 수 있습니다.
 * @param labelTrailingContent (@Composable RowScope.() -> Unit)? : 타이틀 옆 배지 슬롯입니다. 다중 배치(간격 4dp), 높이 22dp 고정이며 폭은 이 슬롯이 우선입니다.
 * @param extraContent (@Composable () -> Unit)? : 설명 아래에 배치하는 자유 슬롯입니다. 폭은 부모를 채우며, 내부 구성과 타이포·색상은 사용처가 정합니다(가로 배치 시 권장 간격 6dp).
 * @param leadingContent (@Composable RowScope.() -> Unit)? : 좌측 슬롯입니다. 여러 개를 넣을 수 있고 항목 간 간격은 8dp입니다.
 * @param trailingContent (@Composable RowScope.() -> Unit)? : 우측 슬롯입니다. 여러 개를 넣을 수 있고 항목 간 간격은 8dp입니다. selected 일 때 이 슬롯을 채우면 Check 아이콘 대신 넣은 것이 보입니다.
 * @param enabledInnerTouch Boolean: true일 경우 슬롯에 넣은 컨트롤(체크박스·스위치·버튼 등)이 자기 터치를 먼저 받습니다. 기본값 false 에서는 셀 클릭 영역이 콘텐츠 위를 덮어, 슬롯 안 어디를 눌러도 [onClick] 만 호출됩니다. true 로 두면 컨트롤 영역의 터치는 컨트롤이 처리하고 [onClick] 은 호출되지 않으므로, 컨트롤 콜백에서 상태를 갱신해야 합니다.
 * @param onClick (() -> Unit)? : 셀 클릭 시 호출되는 콜백 함수입니다.
 */
@Composable
fun WantedListCell(
    annotatedString: AnnotatedString,
    modifier: Modifier = Modifier,
    annotatedDescription: AnnotatedString = AnnotatedString(""),
    variant: WantedListCellDefaults.Variant = WantedListCellDefaults.Variant.Inset,
    verticalPadding: WantedListCellDefaults.VerticalPadding = WantedListCellDefaults.VerticalPadding.Medium,
    cellDefault: WantedListCellDefault = WantedListCellDefault(),
    divider: Boolean = false,
    isEnable: Boolean = true,
    selected: Boolean = false,
    ellipsis: Boolean = false,
    verticalAlignCenter: Boolean = false,
    chevrons: Boolean = false,
    textMaxLine: Int = 1,
    titleStyle: TextStyle? = null,
    descriptionStyle: TextStyle? = null,
    labelTrailingContent: (@Composable RowScope.() -> Unit)? = null,
    extraContent: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable RowScope.() -> Unit)? = null,
    trailingContent: (@Composable RowScope.() -> Unit)? = null,
    enabledInnerTouch: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    // Vertical Padding None 은 인터랙션을 사용하지 않는다(Figma 4.0.0 스펙). outset·radius·ripple 모두 적용하지 않는다.
    val useInteraction = verticalPadding != WantedListCellDefaults.VerticalPadding.None

    WantedTouchArea(
        // modifier 는 최외곽(WantedTouchArea 루트)에 붙는다 — 호출부의 testTag 가 클릭 노드에 덮이지 않게 하기 위함.
        // 그래서 표면(배경·테두리)도 같은 노드에 그리고, 안쪽 여백은 cellDefault.contentPadding 으로 준다.
        modifier = modifier
            .background(color = cellDefault.backgroundColor, shape = cellDefault.shape)
            .cellBorder(cellDefault),
        shape = RoundedCornerShape(if (useInteraction) variant.interactionRadius else 0.dp),
        // 비활성이면 클릭을 막는다. 클릭 노드가 disabled semantics 를 갖게 돼 접근성 서비스에도 비활성으로 전달된다.
        enabled = isEnable,
        // false 면 클릭 레이어가 콘텐츠 위에 따로 놓여 슬롯 컨트롤까지 터치가 내려가지 않는다.
        // true 면 콘텐츠를 클릭 레이어 안에 그려 안쪽 컨트롤이 터치를 먼저 받는다(WantedCard·WantedListCard 와 같은 방식).
        enabledInnerTouch = enabledInnerTouch,
        isUseRipple = useInteraction && isEnable && onClick != null,
        // Interaction/Light 의 색(Foreground/Neutral/Primary). 지정하지 않으면 ripple 기본색이 그대로 노출돼
        // 다크 테마에서 셀이 눌릴 때 어두워진다.
        // 알파는 material3 ripple 이 RippleAlpha 로 정하고 넘긴 색의 알파는 무시하므로
        // Figma 의 Pressed 9% 대신 기본값(실측 두 테마 모두 약 10%)이 적용된다.
        // 프로젝트의 다른 컴포넌트(wantedRippleEffect·iconButtonIndication)도 같은 제약을 갖는다.
        rippleColor = DesignSystemTheme.colors.foregroundNeutralPrimary,
        horizontalPadding = if (useInteraction) variant.interactionOutset else 0.dp,
        content = {
            // 선택 상태는 클릭 노드(WantedTouchArea 의 clickable)가 자식 semantics 를 병합하므로
            // 여기 Column 에 달아 접근성 서비스에 전달한다. 별도 노드를 만들지 않아 중복도 피한다.
            Column(modifier = Modifier.semantics { this.selected = selected }) {
                WantedListCellImpl(
                    modifier = Modifier
                        .padding(cellDefault.contentPadding)
                        .padding(horizontal = variant.horizontalPadding)
                        .padding(vertical = verticalPadding.value),
                    text = annotatedString,
                    textMaxLine = textMaxLine,
                    description = annotatedDescription,
                    isEnable = isEnable,
                    selected = selected,
                    ellipsis = ellipsis,
                    verticalAlignCenter = verticalAlignCenter,
                    chevrons = chevrons,
                    titleStyle = titleStyle,
                    descriptionStyle = descriptionStyle,
                    labelTrailingContent = labelTrailingContent,
                    extraContent = extraContent,
                    leadingContent = leadingContent,
                    trailingContent = trailingContent
                )

                if (divider) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = variant.horizontalPadding),
                        color = DesignSystemTheme.colors.lineNeutralTertiary
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
    description: AnnotatedString = AnnotatedString(""),
    isEnable: Boolean = true,
    selected: Boolean = false,
    ellipsis: Boolean = false,
    verticalAlignCenter: Boolean = false,
    chevrons: Boolean = false,
    titleStyle: TextStyle? = null,
    descriptionStyle: TextStyle? = null,
    labelTrailingContent: (@Composable RowScope.() -> Unit)? = null,
    extraContent: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable RowScope.() -> Unit)? = null,
    trailingContent: (@Composable RowScope.() -> Unit)? = null,
) {
    val titleTextStyle = getTitleTextStyle(
        titleStyle = titleStyle,
        selected = selected
    )

    WantedListCellLayout(
        modifier = modifier.fillMaxWidth(),
        verticalAlignCenter = verticalAlignCenter,
        titleLineHeight = titleTextStyle.firstLineHeight(),
        text = {
            Text(
                text = text,
                maxLines = if (ellipsis) textMaxLine else Int.MAX_VALUE,
                overflow = if (ellipsis) TextOverflow.Ellipsis else TextOverflow.Clip,
                style = titleTextStyle,
                color = getTitleColor(
                    isEnable = isEnable,
                    selected = selected
                )
            )
        },
        description = if (description.isNotEmpty()) {
            {
                Text(
                    text = description,
                    maxLines = if (ellipsis) 1 else Int.MAX_VALUE,
                    overflow = if (ellipsis) TextOverflow.Ellipsis else TextOverflow.Clip,
                    style = when {
                        descriptionStyle != null -> descriptionStyle
                        else -> DesignSystemTheme.typography.label2Regular
                    },
                    color = if (isEnable) {
                        DesignSystemTheme.colors.foregroundNeutralTertiary
                    } else {
                        DesignSystemTheme.colors.foregroundDisablePrimary
                    }
                )
            }
        } else null,
        labelTrailingContent = labelTrailingContent,
        extraContent = extraContent,
        leadingContent = leadingContent,
        trailingContent = resolveTrailingContent(
            trailingContent = trailingContent,
            selected = selected,
            chevrons = chevrons,
            isEnable = isEnable
        ),
        chevrons = resolveChevronContent(
            chevrons = chevrons,
            isEnable = isEnable
        )
    )
}

// selected 는 trailing 슬롯을 그대로 쓴다 — 비어 있으면 Check 를 보여주고,
// 호출부가 슬롯을 채우면 넣은 것만 보인다(Figma 4.0.0 스펙).
//
// chevrons 와 함께 켜면 chevron 이 우선한다 — 우측에 Check 와 chevron 이 함께 보이지 않게 한다.
private fun resolveTrailingContent(
    trailingContent: (@Composable RowScope.() -> Unit)?,
    selected: Boolean,
    chevrons: Boolean,
    isEnable: Boolean
): (@Composable RowScope.() -> Unit)? = when {
    trailingContent != null -> trailingContent
    selected && !chevrons -> {
        { SelectedCheckIcon(isEnable = isEnable) }
    }

    else -> null
}

private fun resolveChevronContent(
    chevrons: Boolean,
    isEnable: Boolean
): (@Composable () -> Unit)? = if (chevrons) {
    { ChevronIcon(isEnable = isEnable) }
} else {
    null
}

@Composable
private fun SelectedCheckIcon(isEnable: Boolean) {
    Icon(
        modifier = Modifier.size(SELECTED_CHECK_ICON_SIZE),
        painter = painterResource(id = R.drawable.icon_normal_check),
        tint = if (isEnable) {
            DesignSystemTheme.colors.foregroundBrandPrimary
        } else {
            DesignSystemTheme.colors.foregroundDisablePrimary
        },
        // 선택 상태는 셀 Column 의 semantics 가 전달하므로 이 아이콘은 장식이다.
        contentDescription = null
    )
}

@Composable
private fun ChevronIcon(isEnable: Boolean) {
    Icon(
        modifier = Modifier.size(
            width = CHEVRON_ICON_WIDTH,
            height = CHEVRON_ICON_HEIGHT
        ),
        painter = painterResource(id = R.drawable.icon_normal_chevron_right_tight_small),
        tint = if (isEnable) {
            DesignSystemTheme.colors.foregroundNeutralQuaternary
        } else {
            DesignSystemTheme.colors.foregroundDisablePrimary
        },
        contentDescription = ""
    )
}


@Composable
private fun getTitleColor(
    isEnable: Boolean,
    selected: Boolean
): Color = when {
    !isEnable -> DesignSystemTheme.colors.foregroundDisablePrimary
    selected -> DesignSystemTheme.colors.foregroundBrandPrimary
    else -> DesignSystemTheme.colors.foregroundNeutralPrimary
}

@Composable
private fun getTitleTextStyle(
    titleStyle: TextStyle?,
    selected: Boolean
): TextStyle = when {
    titleStyle != null -> titleStyle
    selected -> DesignSystemTheme.typography.body2Bold
    else -> DesignSystemTheme.typography.body2Medium
}

// 타이틀 첫 줄의 높이. 타이포 토큰은 LineHeightStyle.Trim.None 이라 모든 줄이 lineHeight 높이를 가지므로
// lineHeight 가 곧 첫 줄 높이다.
//
// Text 와 같은 방식으로 계산한다 — lineHeight 를 sp 그대로 dp 로 바꾸지 않고, 글꼴 배율이 적용된 fontSize 에
// lineHeight/fontSize 비율을 곱한다. 비선형 글꼴 배율(Android 14+)에서는 sp 마다 배율이 달라
// lineHeight 를 따로 변환하면 실제 줄 높이와 어긋난다(배율 1.3 실측: 실제 줄 76px, 단독 변환 시 약 65px).
@Composable
private fun TextStyle.firstLineHeight(): Dp = with(LocalDensity.current) {
    when {
        !fontSize.isSp -> TITLE_LINE_HEIGHT_FALLBACK
        lineHeight.isSp -> fontSize.toDp() * (lineHeight.value / fontSize.value)
        lineHeight.isEm -> fontSize.toDp() * lineHeight.value
        else -> TITLE_LINE_HEIGHT_FALLBACK
    }
}

@Composable
private fun WantedListCellLayout(
    modifier: Modifier = Modifier,
    verticalAlignCenter: Boolean,
    titleLineHeight: Dp,
    text: @Composable () -> Unit,
    description: @Composable (() -> Unit)?,
    labelTrailingContent: (@Composable RowScope.() -> Unit)?,
    extraContent: (@Composable () -> Unit)?,
    leadingContent: (@Composable RowScope.() -> Unit)?,
    trailingContent: (@Composable RowScope.() -> Unit)?,
    chevrons: (@Composable () -> Unit)?,
) {
    val density = LocalDensity.current
    val titleLineTopPx = with(density) { TITLE_VERTICAL_PADDING.roundToPx() }
    val titleLineHeightPx = with(density) { titleLineHeight.roundToPx() }

    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.spacedBy(ROW_CONTENT_GAP)
    ) {
        // 슬롯과 텍스트 영역을 "타이틀 첫 줄"이라는 같은 기준선으로 맞춘다.
        // 바깥 Row 의 verticalAlignment 로 정렬하면 텍스트 영역 전체(설명·extraContent 포함)가 기준이 돼,
        // 설명이 있거나 타이틀이 여러 줄일 때 슬롯이 첫 줄에서 벗어난다.
        val slotModifier = Modifier
            .wrapContentSize()
            .heightIn(min = titleLineHeight)
            .alignBy { slot -> if (verticalAlignCenter) slot.measuredHeight / 2 else 0 }

        leadingContent?.let {
            Row(
                modifier = slotModifier,
                horizontalArrangement = Arrangement.spacedBy(ROW_CONTENT_GAP),
                verticalAlignment = Alignment.CenterVertically,
                content = it
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .heightIn(min = TEXT_CONTENT_MIN_HEIGHT)
                .alignBy {
                    if (verticalAlignCenter) titleLineTopPx + titleLineHeightPx / 2 else titleLineTopPx
                }
        ) {
            Row(
                modifier = Modifier.padding(vertical = TITLE_VERTICAL_PADDING),
                horizontalArrangement = Arrangement.spacedBy(LABEL_TRAILING_GAP),
                verticalAlignment = if (verticalAlignCenter) Alignment.CenterVertically else Alignment.Top
            ) {
                // 폭은 Label Trailing 이 우선이다. 타이틀만 남은 폭 안에서 줄바꿈되도록 fill = false 로 둔다.
                Box(modifier = Modifier.weight(1f, fill = false)) {
                    text()
                }

                labelTrailingContent?.let {
                    Row(
                        modifier = Modifier.height(LABEL_TRAILING_HEIGHT),
                        horizontalArrangement = Arrangement.spacedBy(LABEL_TRAILING_GAP),
                        verticalAlignment = Alignment.CenterVertically,
                        content = it
                    )
                }
            }

            description?.invoke()

            extraContent?.let {
                Box(modifier = Modifier.fillMaxWidth()) {
                    extraContent()
                }
            }
        }

        trailingContent?.let {
            Row(
                modifier = slotModifier,
                horizontalArrangement = Arrangement.spacedBy(ROW_CONTENT_GAP),
                verticalAlignment = Alignment.CenterVertically,
                content = it
            )
        }

        chevrons?.let {
            Box(
                modifier = slotModifier,
                contentAlignment = Alignment.Center
            ) {
                chevrons()
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
                    text = "텍스트 variant Full",
                    variant = WantedListCellDefaults.Variant.Full,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 variant Inset",
                    variant = WantedListCellDefaults.Variant.Inset,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트",
                    onClick = {},
                    divider = true
                )

                WantedListCell(
                    text = "텍스트",
                    description = "설명",
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트",
                    description = "설명",
                    selected = true,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트",
                    description = "설명",
                    selected = true,
                    isEnable = false,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트",
                    description = "설명",
                    onClick = {},
                    divider = true
                )

                WantedListCell(
                    text = "텍스트",
                    description = "설명",
                    isEnable = false,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 padding Small",
                    description = "설명",
                    verticalPadding = WantedListCellDefaults.VerticalPadding.Small,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 padding Normal",
                    description = "설명",
                    verticalPadding = WantedListCellDefaults.VerticalPadding.Medium,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 padding Medium",
                    description = "설명",
                    verticalPadding = WantedListCellDefaults.VerticalPadding.Large,
                    onClick = {}
                )

                WantedListCell(
                    text = "아주 긴 타이틀 텍스트를 넣어서 가용 폭을 넘겼을 때의 동작입니다",
                    description = "설명",
                    labelTrailingContent = {
                        Text(
                            text = "배지",
                            style = DesignSystemTheme.typography.label2Regular,
                            color = DesignSystemTheme.colors.foregroundBrandPrimary
                        )
                    },
                    extraContent = {
                        Text(
                            text = "Extra Content",
                            style = DesignSystemTheme.typography.label2Regular,
                            color = DesignSystemTheme.colors.foregroundNeutralTertiary
                        )
                    },
                    onClick = {}
                )

                WantedListCell(
                    text = "짧은 제목",
                    labelTrailingContent = {
                        Text(
                            text = "배지",
                            style = DesignSystemTheme.typography.label2Regular,
                            color = DesignSystemTheme.colors.foregroundBrandPrimary
                        )
                    },
                    trailingContent = {
                        Text(
                            text = "값",
                            style = DesignSystemTheme.typography.label2Regular,
                            color = DesignSystemTheme.colors.foregroundNeutralTertiary
                        )
                    },
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 padding Medium variant Full",
                    description = "설명",
                    variant = WantedListCellDefaults.Variant.Full,
                    divider = true,
                    verticalPadding = WantedListCellDefaults.VerticalPadding.Large,
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 cellDefault",
                    description = "배경·모양·테두리·안쪽 여백",
                    cellDefault = WantedListCellDefaults.getDefault(
                        backgroundColor = DesignSystemTheme.colors.backgroundNeutralSecondary,
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = DesignSystemTheme.colors.lineNeutralTertiary
                        ),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ),
                    onClick = {}
                )

                WantedListCell(
                    text = "텍스트 cellDefault + variant Full",
                    description = "표면은 cellDefault, 여백·인터랙션은 variant",
                    variant = WantedListCellDefaults.Variant.Full,
                    verticalPadding = WantedListCellDefaults.VerticalPadding.Large,
                    cellDefault = WantedListCellDefaults.getDefault(
                        backgroundColor = DesignSystemTheme.colors.backgroundNeutralSecondary,
                        shape = RoundedCornerShape(20.dp)
                    ),
                    onClick = {}
                )

            }
        }
    }
}
