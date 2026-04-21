package com.wanted.android.montage.sample.feedback.pushbadge

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgePosition
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeSize
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeVariant

object DSWantedPushBadgeDemoScreenContract {
	sealed interface DSWantedPushBadgeDemoEvent : BaseEvent {
		data class ShowCode(val isShowCode: Boolean) : DSWantedPushBadgeDemoEvent
		data object CopyCode : DSWantedPushBadgeDemoEvent
		data class SetVariant(val variant: PushBadgeVariant) : DSWantedPushBadgeDemoEvent
		data class SetSize(val size: PushBadgeSize) : DSWantedPushBadgeDemoEvent
		data class SetPosition(val position: PushBadgePosition) : DSWantedPushBadgeDemoEvent
		data class SetBordered(val bordered: Boolean) : DSWantedPushBadgeDemoEvent
	}

	data class DSWantedPushBadgeDemoViewState(
		val isShowCode: Boolean = false,
		val code: String = "",

		val variantList: List<PushBadgeVariant> = PushBadgeVariant.entries.toList(),
		val selectedVariant: PushBadgeVariant = PushBadgeVariant.Dot,

		val sizeList: List<PushBadgeSize> = PushBadgeSize.entries.toList(),
		val selectedSize: PushBadgeSize = PushBadgeSize.XSmall,

		val positionList: List<PushBadgePosition> = PushBadgePosition.entries.toList(),
		val selectedPosition: PushBadgePosition = PushBadgePosition.TopEnd,

		val bordered: Boolean = false,
	) : BaseViewState

	sealed interface DSWantedPushBadgeDemoSideEffect : BaseSideEffect {
		data class CopyCode(val code: String) : DSWantedPushBadgeDemoSideEffect
	}

	sealed interface DSWantedPushBadgeDemoViewEvent : ViewEvent {
		data object OnClickBack : DSWantedPushBadgeDemoViewEvent
		data object OnClickShowCode : DSWantedPushBadgeDemoViewEvent
		data object OnClickCopyCode : DSWantedPushBadgeDemoViewEvent
		data class OnSelectVariant(val variant: PushBadgeVariant) : DSWantedPushBadgeDemoViewEvent
		data class OnSelectSize(val size: PushBadgeSize) : DSWantedPushBadgeDemoViewEvent
		data class OnSelectPosition(val position: PushBadgePosition) : DSWantedPushBadgeDemoViewEvent
		data class OnChangeBordered(val bordered: Boolean) : DSWantedPushBadgeDemoViewEvent
	}
}
