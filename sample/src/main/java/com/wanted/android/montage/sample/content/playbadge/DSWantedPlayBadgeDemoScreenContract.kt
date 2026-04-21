package com.wanted.android.montage.sample.content.playbadge

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.contents.playtime.playbadge.WantedPlayBadgeDefaults.Size

object DSWantedPlayBadgeDemoScreenContract {
	sealed interface DSWantedPlayBadgeDemoEvent : BaseEvent {
		data class ShowCode(val isShowCode: Boolean) : DSWantedPlayBadgeDemoEvent
		data object CopyCode : DSWantedPlayBadgeDemoEvent
		data class SetSize(val size: Size) : DSWantedPlayBadgeDemoEvent
		data class SetIsAlternative(val isAlternative: Boolean) : DSWantedPlayBadgeDemoEvent
	}

	data class DSWantedPlayBadgeDemoViewState(
		val isShowCode: Boolean = false,
		val code: String = "",

		val sizeList: List<Size> = Size.entries.toList(),
		val selectedSize: Size = Size.Medium,

		val isAlternative: Boolean = false,
	) : BaseViewState

	sealed interface DSWantedPlayBadgeDemoSideEffect : BaseSideEffect {
		data class CopyCode(val code: String) : DSWantedPlayBadgeDemoSideEffect
	}

	sealed interface DSWantedPlayBadgeDemoViewEvent : ViewEvent {
		data object OnClickBack : DSWantedPlayBadgeDemoViewEvent
		data object OnClickShowCode : DSWantedPlayBadgeDemoViewEvent
		data object OnClickCopyCode : DSWantedPlayBadgeDemoViewEvent
		data class OnSelectSize(val size: Size) : DSWantedPlayBadgeDemoViewEvent
		data class OnChangeIsAlternative(val isAlternative: Boolean) : DSWantedPlayBadgeDemoViewEvent
	}
}
