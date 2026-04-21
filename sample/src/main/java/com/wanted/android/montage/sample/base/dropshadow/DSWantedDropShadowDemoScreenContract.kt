package com.wanted.android.montage.sample.base.dropshadow

import androidx.compose.ui.graphics.Color
import com.wanted.android.montage.sample.base.BaseEvent
import com.wanted.android.montage.sample.base.BaseSideEffect
import com.wanted.android.montage.sample.base.BaseViewState
import com.wanted.android.montage.sample.base.ViewEvent
import com.wanted.android.wanted.design.base.WantedDropShadowDefaults.WantedShadowStyle

object DSWantedDropShadowDemoScreenContract {
    sealed interface DSWantedDropShadowDemoEvent : BaseEvent {
        data class InitState(val viewState: DSWantedDropShadowDemoViewState) :
            DSWantedDropShadowDemoEvent

        data object CopyCode : DSWantedDropShadowDemoEvent
        data class ShowCode(val isShow: Boolean) : DSWantedDropShadowDemoEvent
        data class SetStyle(val style: WantedShadowStyle) : DSWantedDropShadowDemoEvent
        data class SetContentBackground(val background: ShadowSampleContentBackground) :
            DSWantedDropShadowDemoEvent

        data class SetPreviewBackground(val background: ShadowPreviewBackground) :
            DSWantedDropShadowDemoEvent
    }

    data class DSWantedDropShadowDemoViewState(
        val isLoading: Boolean = true,
        val isShowCode: Boolean = false,
        val code: String = "",

        val style: WantedShadowStyle = WantedShadowStyle.XLarge(),
        val styleSizeList: List<WantedShadowStyle> = listOf(
            WantedShadowStyle.XSmall(),
            WantedShadowStyle.Small(),
            WantedShadowStyle.Medium(),
            WantedShadowStyle.Large(),
            WantedShadowStyle.XLarge()
        ),

        val contentBackground: ShadowSampleContentBackground = ShadowSampleContentBackground.White,
        val previewBackground: ShadowPreviewBackground = ShadowPreviewBackground.White
    ) : BaseViewState

    sealed interface DSWantedDropShadowDemoSideEffect : BaseSideEffect {
        data class CopyCode(val code: String) : DSWantedDropShadowDemoSideEffect
    }


    sealed interface DSWantedDropShadowDemoViewEvent : ViewEvent {
        data object OnClickBack : DSWantedDropShadowDemoViewEvent
        data object OnClickShowCode : DSWantedDropShadowDemoViewEvent
        data object OnClickCopyCode : DSWantedDropShadowDemoViewEvent
        data class OnClickStyle(val style: WantedShadowStyle) : DSWantedDropShadowDemoViewEvent
        data class OnClickContentBackground(val background: ShadowSampleContentBackground) :
            DSWantedDropShadowDemoViewEvent

        data class OnClickPreviewBackground(val background: ShadowPreviewBackground) :
            DSWantedDropShadowDemoViewEvent
    }

    enum class ShadowSampleContentBackground(val color: Color) {
        White(color = Color.White),
        Red(color = Color.Red),
        Black(color = Color.Black),
        Transparent(color = Color.Transparent)
    }

    enum class ShadowPreviewBackground(val color: Color) {
        White(color = Color.White),
        Red(color = Color.Red),
        Black(color = Color.Black),
        Transparent(color = Color.Transparent),
        ContentBackground(Color.White)
    }
}