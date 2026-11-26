package com.wanted.android.wanted.design.input.select

/**
 * [WantedSelect] 선택 바텀시트 항목의 testTag.
 *
 * 소비자는 E2E(Maestro, `maestro/`)·UiAutomator 다. 항목 셀(`WantedListCell`) 글자는 터치 레이어에 가려
 * 접근성 트리에 노출되지 않아 텍스트로는 찾을 수 없다(예: 회원가입 국가 시트). 바텀시트 목록이
 * `testTagsAsResourceId` 를 켜 두므로 이 값이 그대로 resource-id 가 된다.
 */
object WantedSelectTestTags {
    /** 항목 태그 접두사. 전체 태그는 [item]. */
    const val ITEM_PREFIX = "wanted_select_item:"

    /** 항목 태그: [WantedSelectData.id], 비어 있으면 [WantedSelectData.text] 를 붙인다. 예) 국가 일본 → `wanted_select_item:jp`. */
    fun item(data: WantedSelectData): String = ITEM_PREFIX + data.id.ifEmpty { data.text }
}
