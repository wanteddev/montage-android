package com.wanted.android.montage.sample.feedback.pushbadge

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgePosition
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeSize
import com.wanted.android.wanted.design.feedback.pushbadge.PushBadgeTypes.PushBadgeVariant

/**
 * outlineBorder 색상으로 선택할 수 있는 후보입니다.
 * - label: 선택 메뉴에 노출되는 이름
 * - codeExpression: 코드 미리보기에 표기할 색상 표현식
 */
enum class OutlineBorderColorOption(val label: String, val codeExpression: String) {
	Default("Default", "DesignSystemTheme.colors.backgroundNeutralPrimary"),
	White("White", "DesignSystemTheme.colors.staticWhite"),
	Black("Black", "DesignSystemTheme.colors.staticBlack"),
	Primary("Primary", "DesignSystemTheme.colors.surfaceBrandPrimary"),
}

object DSWantedPushBadgeDemoScreenContract {
	sealed interface DSWantedPushBadgeDemoEvent : BaseEvent {
		data class ShowCode(val isShowCode: Boolean) : DSWantedPushBadgeDemoEvent
		data object CopyCode : DSWantedPushBadgeDemoEvent
		data class SetVariant(val variant: PushBadgeVariant) : DSWantedPushBadgeDemoEvent
		data class SetSize(val size: PushBadgeSize) : DSWantedPushBadgeDemoEvent
		data class SetPosition(val position: PushBadgePosition) : DSWantedPushBadgeDemoEvent
		data class SetOutlineBorder(val outlineBorder: Boolean) : DSWantedPushBadgeDemoEvent
		data class SetOutlineBorderColor(val outlineBorderColor: OutlineBorderColorOption) : DSWantedPushBadgeDemoEvent
		data class SetInset(val insetEnabled: Boolean) : DSWantedPushBadgeDemoEvent
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

		val outlineBorder: Boolean = false,
		val outlineBorderColorList: List<OutlineBorderColorOption> = OutlineBorderColorOption.entries.toList(),
		val selectedOutlineBorderColor: OutlineBorderColorOption = OutlineBorderColorOption.Default,
		val insetEnabled: Boolean = false,

		val sampleText: String = "N",
		val sampleCount: Int = 128,
		val sampleMaxCount: Int = 99,
		val sampleInset: Int = 4,
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
		data class OnChangeOutlineBorder(val outlineBorder: Boolean) : DSWantedPushBadgeDemoViewEvent
		data class OnSelectOutlineBorderColor(val outlineBorderColor: OutlineBorderColorOption) : DSWantedPushBadgeDemoViewEvent
		data class OnChangeInset(val insetEnabled: Boolean) : DSWantedPushBadgeDemoViewEvent
	}
}
