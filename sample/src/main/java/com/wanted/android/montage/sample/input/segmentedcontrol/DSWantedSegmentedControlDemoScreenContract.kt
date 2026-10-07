package com.wanted.android.montage.sample.input.segmentedcontrol

import androidx.annotation.DrawableRes
import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.montage.sample.R
import com.wanted.android.wanted.design.input.segmentedcontrol.WantedSegmentedDefaults.SegmentedSize

/**
 * SegmentedControl 데모에서 표현할 항목 구성 모드입니다.
 * - Text: 텍스트만
 * - IconText: 아이콘 + 텍스트
 * - IconOnly: 아이콘만 (고정 너비 세그먼트)
 */
enum class SegmentedContentMode(val label: String) {
    Text("Text"),
    IconText("Icon + Text"),
    IconOnly("Icon Only"),
}

/**
 * SegmentedControl 데모에서 세그먼트별로 선택할 수 있는 아이콘 후보입니다.
 * - label: 선택 메뉴에 노출되는 이름
 * - resId: 실제 표시할 drawable 리소스
 */
enum class SegmentedDemoIcon(val label: String, @DrawableRes val resId: Int) {
    Home("Home", R.drawable.icon_normal_home),
    List("List", R.drawable.icon_normal_list),
    Bell("Bell", R.drawable.icon_normal_bell),
    Star("Star", R.drawable.icon_normal_star),
    Search("Search", R.drawable.icon_normal_search),
    Heart("Heart", R.drawable.icon_normal_heart),
    Bookmark("Bookmark", R.drawable.icon_normal_bookmark),
    Calendar("Calendar", R.drawable.icon_normal_calendar),
}

object DSWantedSegmentedControlDemoScreenContract {

    sealed interface DSWantedSegmentedControlDemoEvent : BaseEvent {
        data class ShowCode(val isShowCode: Boolean) : DSWantedSegmentedControlDemoEvent
        data object CopyCode : DSWantedSegmentedControlDemoEvent
        data class SetSize(val size: SegmentedSize) : DSWantedSegmentedControlDemoEvent
        data class SetMode(val mode: SegmentedContentMode) : DSWantedSegmentedControlDemoEvent
        data class SetSelectedIndex(val index: Int) : DSWantedSegmentedControlDemoEvent
        data class SetIcon(val index: Int, val icon: SegmentedDemoIcon) : DSWantedSegmentedControlDemoEvent
    }

    data class DSWantedSegmentedControlDemoViewState(
        val isShowCode: Boolean = false,
        val code: String = "",

        val sizeList: List<SegmentedSize> = SegmentedSize.entries.toList(),
        val selectedSize: SegmentedSize = SegmentedSize.Medium,

        val modeList: List<SegmentedContentMode> = SegmentedContentMode.entries.toList(),
        val selectedMode: SegmentedContentMode = SegmentedContentMode.Text,

        val items: List<String> = listOf("텍스트1", "텍스트2", "텍스트3"),
        val selectedIndex: Int = 0,

        val iconOptions: List<SegmentedDemoIcon> = SegmentedDemoIcon.entries.toList(),
        val selectedIcons: List<SegmentedDemoIcon> = listOf(
            SegmentedDemoIcon.Home,
            SegmentedDemoIcon.List,
            SegmentedDemoIcon.Bell,
        ),
    ) : BaseViewState

    sealed interface DSWantedSegmentedControlDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedSegmentedControlDemoSideEffect
    }

    sealed interface DSWantedSegmentedControlDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedSegmentedControlDemoViewEvent
        data object OnClickShowCode : DSWantedSegmentedControlDemoViewEvent
        data object OnClickCopyCode : DSWantedSegmentedControlDemoViewEvent
        data class OnSelectSize(val size: SegmentedSize) : DSWantedSegmentedControlDemoViewEvent
        data class OnSelectMode(val mode: SegmentedContentMode) : DSWantedSegmentedControlDemoViewEvent
        data class OnClickSegment(val index: Int) : DSWantedSegmentedControlDemoViewEvent
        data class OnSelectIcon(val index: Int, val icon: SegmentedDemoIcon) : DSWantedSegmentedControlDemoViewEvent
    }
}
