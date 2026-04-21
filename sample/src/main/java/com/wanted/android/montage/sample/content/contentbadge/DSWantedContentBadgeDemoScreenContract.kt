package com.wanted.android.montage.sample.content.contentbadge

import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeColor
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeSize
import com.wanted.android.wanted.design.contents.contentbadge.ContentBadgeType

object DSWantedContentBadgeDemoScreenContract {
	sealed interface DSWantedContentBadgeDemoEvent : BaseEvent {
		data class InitState(val viewState: DSWantedContentBadgeDemoViewState) : DSWantedContentBadgeDemoEvent
		data class ShowCode(val isShowCode: Boolean) : DSWantedContentBadgeDemoEvent
		data object CopyCode : DSWantedContentBadgeDemoEvent
		data class SetType(val type: ContentBadgeType) : DSWantedContentBadgeDemoEvent
		data class SetSize(val size: ContentBadgeSize) : DSWantedContentBadgeDemoEvent
		data class SetColor(val color: ContentBadgeColor) : DSWantedContentBadgeDemoEvent
		data class SetLeadingIcon(val hasLeadingIcon: Boolean) : DSWantedContentBadgeDemoEvent
		data class SetTrailingIcon(val hasTrailingIcon: Boolean) : DSWantedContentBadgeDemoEvent
	}

	data class DSWantedContentBadgeDemoViewState(
		val isShowCode: Boolean = false,
		val code: String = "",

		val typeList: List<ContentBadgeType> = ContentBadgeType.entries.toList(),
		val selectedType: ContentBadgeType = ContentBadgeType.Solid,

		val sizeList: List<ContentBadgeSize> = ContentBadgeSize.entries.toList(),
		val selectedSize: ContentBadgeSize = ContentBadgeSize.Small,

		val colorList: List<ContentBadgeColor> = ContentBadgeColor.entries.toList(),
		val selectedColor: ContentBadgeColor = ContentBadgeColor.Neutral,

		val hasLeadingIcon: Boolean = false,
		val hasTrailingIcon: Boolean = false,
	) : BaseViewState

	sealed interface DSWantedContentBadgeDemoSideEffect : BaseSideEffect {
		data class CopyCode(val code: String) : DSWantedContentBadgeDemoSideEffect
	}

	sealed interface DSWantedContentBadgeDemoViewEvent : ViewEvent {
		data object OnClickBack : DSWantedContentBadgeDemoViewEvent
		data object OnClickShowCode : DSWantedContentBadgeDemoViewEvent
		data object OnClickCopyCode : DSWantedContentBadgeDemoViewEvent
		data class OnSelectType(val type: ContentBadgeType) : DSWantedContentBadgeDemoViewEvent
		data class OnSelectSize(val size: ContentBadgeSize) : DSWantedContentBadgeDemoViewEvent
		data class OnSelectColor(val color: ContentBadgeColor) : DSWantedContentBadgeDemoViewEvent
		data class OnChangeLeadingIcon(val hasLeadingIcon: Boolean) : DSWantedContentBadgeDemoViewEvent
		data class OnChangeTrailingIcon(val hasTrailingIcon: Boolean) : DSWantedContentBadgeDemoViewEvent
	}
}
