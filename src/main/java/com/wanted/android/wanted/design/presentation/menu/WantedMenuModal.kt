package com.wanted.android.wanted.design.presentation.menu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.wanted.design.contents.listcell.WantedListCell
import com.wanted.android.wanted.design.input.control.WantedCheckBox
import com.wanted.android.wanted.design.input.control.WantedRadioButton


/**
 * 문자열 리스트를 바탕으로 구성되는 기본 메뉴 모달입니다.
 *
 * 항목 클릭 시 index와 문자열 값을 반환합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedMenuModal(
 *     items = listOf("Option 1", "Option 2"),
 *     onDismissRequest = { },
 *     onClick = { index, value -> }
 * )
 * ```
 *
 * @param items List<String>: 표시할 문자열 항목 리스트입니다.
 * @param onDismissRequest () -> Unit: 모달 외부 클릭 시 호출되는 콜백입니다.
 * @param properties DialogProperties: 다이얼로그 속성입니다.
 * @param onClick (index: Int, value: String) -> Unit: 항목 클릭 시 콜백입니다.
 */
@Composable
fun WantedMenuModal(
    items: List<String>,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    onClick: (index: Int, value: String) -> Unit = { _, _ -> }
) {
    WantedMenuModal(
        properties = properties,
        sectionCount = 2,
        itemCount = { items.size },
        onBindSectionTitle = null,
        onBindSectionItem = { _, index ->
            WantedListCell(
                text = items[index],
                onClick = {
                    onClick(index, items[index])
                }
            )
        },
        onDismissRequest = onDismissRequest
    )
}

/**
 * 리스트 유형(Radio, Check, Normal)에 따라 구성되는 메뉴 모달입니다.
 *
 * 각 항목에 라디오 버튼, 체크박스를 표시할 수 있으며 클릭 시 index와 값을 반환합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedMenuModal(
 *     items = listOf("One", "Two"),
 *     listType = WantedMenuContract.ListType.Check,
 *     onDismissRequest = { },
 *     onClick = { index, value -> }
 * )
 * ```
 *
 * @param items List<String>: 표시할 문자열 항목 리스트입니다.
 * @param onDismissRequest () -> Unit: 다이얼로그 해제 콜백입니다.
 * @param properties DialogProperties: 다이얼로그 속성입니다.
 * @param listType ListType: 리스트 스타일입니다. (Normal, Radio, Check)
 * @param onClick (index: Int, value: String) -> Unit: 클릭 이벤트 콜백입니다.
 */
@Composable
fun WantedMenuModal(
    items: List<String>,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    listType: WantedMenuContract.ListType = WantedMenuContract.ListType.Normal,
    onClick: (index: Int, value: String) -> Unit = { _, _ -> }
) {
    WantedMenuModal(
        properties = properties,
        sectionCount = 2,
        itemCount = { items.size },
        onBindSectionTitle = null,
        onBindSectionItem = { _, index ->
            when (listType) {
                WantedMenuContract.ListType.Normal -> {
                    WantedListCell(
                        text = items[index],
                        onClick = {
                            onClick(index, items[index])
                        }
                    )
                }

                WantedMenuContract.ListType.Radio -> {
                    WantedListCell(
                        text = items[index],
                        trailingContent = {
                            WantedRadioButton(
                                checked = false,
                                onCheckedChange = {

                                }
                            )
                        },
                        onClick = {
                            onClick(index, items[index])
                        }
                    )
                }

                WantedMenuContract.ListType.Check -> {
                    WantedListCell(
                        text = items[index],
                        leadingContent = {
                            WantedCheckBox(
                                checked = false,
                                onCheckedChange = {

                                }
                            )
                        },
                        onClick = {
                            onClick(index, items[index])
                        }
                    )
                }
            }

        },
        onDismissRequest = onDismissRequest
    )
}

/**
 * 커스텀 섹션 타이틀 및 아이템을 사용하는 메뉴 다이얼로그입니다.
 *
 * 복잡한 레이아웃이 필요한 경우 사용합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedMenuModal(
 *     sectionCount = 2,
 *     itemCount = { 3 },
 *     onBindSectionItem = { section, index -> Text(\"아이템\") },
 *     onDismissRequest = { }
 * )
 * ```
 *
 * @param sectionCount Int: 섹션 수입니다.
 * @param itemCount (Int) -> Int: 섹션별 아이템 수를 반환합니다.
 * @param onBindSectionItem @Composable (Int, Int) -> Unit: 섹션 아이템 구성 함수입니다.
 * @param properties DialogProperties: 다이얼로그 속성입니다.
 * @param onBindSectionTitle @Composable ((Int) -> Unit)? : 섹션 타이틀 구성 함수입니다.
 * @param onDismissRequest () -> Unit: 다이얼로그 닫힘 콜백입니다.
 */
@Composable
fun WantedMenuModal(
    sectionCount: Int,
    itemCount: (section: Int) -> Int,
    onBindSectionItem: @Composable (section: Int, index: Int) -> Unit,
    properties: DialogProperties = DialogProperties(),
    onBindSectionTitle: @Composable ((section: Int) -> Unit)? = null,
    onDismissRequest: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = properties
    ) {
        WantedMenu(
            modifier = Modifier,
            sectionCount = sectionCount,
            itemCount = itemCount,
            onBindSectionTitle = onBindSectionTitle,
            onBindSectionItem = onBindSectionItem,
        )
    }
}
