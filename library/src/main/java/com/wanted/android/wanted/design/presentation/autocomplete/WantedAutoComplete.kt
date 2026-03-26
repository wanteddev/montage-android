package com.wanted.android.wanted.design.presentation.autocomplete

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import com.wanted.android.wanted.design.theme.DesignSystemTheme

/**
 * WantedAutoComplete
 *
 * 섹션별 아이템을 구성할 수 있는 자동 완성 Dropdown 컴포넌트입니다.
 *
 * 섹션 타이틀과 아이템을 표시하고, 상단 및 하단에 커스텀 콘텐츠를 배치할 수 있습니다.
 * 스크롤 시 현재 섹션의 타이틀이 상단에 고정됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * var expanded by remember { mutableStateOf(false) }
 *
 * ExposedDropdownMenuBox(
 *     expanded = expanded,
 *     onExpandedChange = { expanded = it }
 * ) {
 *     WantedAutoComplete(
 *         expanded = expanded,
 *         onExpandedChange = { expanded = it },
 *         sectionCount = 2,
 *         sectionTitle = { section -> "섹션 $section" },
 *         sectionItemCount = { section -> 3 },
 *         sectionItem = { section, index ->
 *             Text("아이템 $index")
 *         }
 *     )
 * }
 * ```
 *
 * @receiver ExposedDropdownMenuBoxScope: 드롭다운 메뉴 범위 내에서 호출되어야 합니다.
 * @param expanded Boolean: 드롭다운 확장 여부입니다.
 * @param onDismissRequest (Boolean) -> Unit: 확장 상태가 변경될 때 호출되는 콜백입니다.
 * @param sectionCount Int: 표시할 섹션의 총 개수입니다.
 * @param sectionItemCount (Int) -> Int: 각 섹션별 아이템 개수를 반환하는 함수입니다.
 * @param sectionItem (@Composable (Int, Int) -> Unit): 섹션별 아이템 컴포넌트입니다. 첫 번째 파라미터는 섹션 인덱스, 두 번째는 아이템 인덱스입니다.
 * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
 * @param containerColor Color: 드롭다운 배경 색상입니다.
 * @param sectionTitleHorizontalPadding Dp: 섹션 타이틀의 좌우 패딩입니다.
 * @param sectionTitle ((Int) -> String)?: 섹션별 타이틀 텍스트를 반환하는 함수입니다.
 * @param topDirectInput (@Composable () -> Unit)?: 드롭다운 상단 고정 영역 콘텐츠입니다.
 * @param bottomDirectInput (@Composable () -> Unit)?: 드롭다운 하단 영역 콘텐츠입니다.
 */
@Composable
fun ExposedDropdownMenuBoxScope.WantedAutoComplete(
    expanded: Boolean,
    onDismissRequest: (Boolean) -> Unit,
    sectionCount: Int,
    sectionItemCount: (section: Int) -> Int,
    sectionItem: @Composable (section: Int, index: Int) -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = DesignSystemTheme.colors.backgroundElevatedNormal,
    sectionTitleHorizontalPadding: Dp = 20.dp,
    sectionTitle: ((section: Int) -> String)? = null,
    anchorPadding: Dp = 0.dp,
    topDirectInput: @Composable (() -> Unit)? = null,
    bottomDirectInput: @Composable (() -> Unit)? = null
) {
    val scrollState = rememberScrollState()

    val topDirectInputSize = remember { mutableIntStateOf(0) }
    val currentSection = remember { mutableIntStateOf(-1) }
    val sectionOffsets = remember { mutableStateMapOf<Int, Int>() }
    val density = LocalDensity.current
    val title = remember { mutableStateOf("") }

    LaunchedEffect(currentSection.intValue) {
        title.value = if (currentSection.intValue == -1) {
            ""
        } else {
            sectionTitle?.invoke(currentSection.intValue) ?: ""
        }
    }

    LaunchedEffect(scrollState.value) {
        currentSection.intValue = sectionOffsets.filter {
            it.value <= (scrollState.value + with(density) {
                4.dp.toPx()
                +if (title.value.isEmpty()) {
                    0.dp.toPx()
                } else {
                    4.dp.toPx() + 4.dp.toPx()
                }
            })
        }.maxOfOrNull { it.key } ?: -1
    }

    DropdownMenu(
        modifier = modifier
            .padding(horizontal = 8.dp),
        scrollState = scrollState,
        containerColor = containerColor,
        shape = RoundedCornerShape(16.dp),
        expanded = expanded,
        shadowElevation = 1.dp,
        border = BorderStroke(1.dp, DesignSystemTheme.colors.lineSolidNormal),
        offset = DpOffset(x = 0.dp, y = anchorPadding),
        properties = PopupProperties(focusable = false),
        onDismissRequest = {
            onDismissRequest(false)
        }
    ) {
        topDirectInput?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {
                        topDirectInputSize.intValue = it.size.height
                    }
                    .zIndex(1000f)
                    .background(containerColor)

            ) {
                it()
                Spacer(modifier = Modifier.size(4.dp))
            }
        }

        if (title.value.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        IntOffset(0, scrollState.value - topDirectInputSize.intValue)
                    }
                    .zIndex(1001f)
                    .background(containerColor)
                    .padding(horizontal = sectionTitleHorizontalPadding)
                    .padding(horizontal = 1.dp)
                    .padding(vertical = 4.dp),
                text = title.value,
                style = DesignSystemTheme.typography.caption1Bold,
                color = DesignSystemTheme.colors.labelAlternative
            )

            Spacer(modifier = Modifier.size(4.dp))

        }

        repeat(sectionCount) { section ->
            if (section != 0 || title.value.isEmpty()) {
                sectionTitle?.let {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned {
                                Log.d(
                                    "_SMY",
                                    "onGloballyPositioned: ${it.positionInParent().y.toInt()}, ${it.positionInRoot().y.toInt()}, ${scrollState.value} "
                                )
                                sectionOffsets[section] = it.positionInParent().y.toInt()
                            }
                            .padding(horizontal = sectionTitleHorizontalPadding)
                            .padding(horizontal = 1.dp)
                            .padding(vertical = 4.dp),
                        text = sectionTitle(section),
                        style = DesignSystemTheme.typography.caption1Bold,
                        color = DesignSystemTheme.colors.labelAlternative
                    )
                }

                Spacer(modifier = Modifier.size(4.dp))
            }

            val itemCount = sectionItemCount(section)
            repeat(itemCount) { index ->
                sectionItem(section, index)

                if (itemCount != index - 1) {
                    Spacer(modifier = Modifier.size(4.dp))
                }
            }
        }

        bottomDirectInput?.let {
            it()
        }
    }
}