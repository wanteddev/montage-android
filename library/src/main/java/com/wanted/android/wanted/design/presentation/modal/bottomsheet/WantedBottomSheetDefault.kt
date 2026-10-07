package com.wanted.android.wanted.design.presentation.modal.bottomsheet

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp

/**
 * data class WantedBottomSheetDefault
 *
 * Bottom Sheet / Full 의 모양과 여백을 정의하는 데이터 클래스입니다.
 *
 * 값을 바꿔 쓸 때는 [WantedBottomSheetDefaults.getDefault] · [WantedBottomSheetDefaults.getFullDefault] 의 인자로 넘기거나 `copy` 를 사용합니다.
 *
 * 여백은 디자인 4.0.0 기준으로 ModalSize 와 무관하게 통일되어 있고, Full 만 별도 값을 사용합니다.
 *
 * @property shape RoundedCornerShape: Bottom Sheet 의 모서리 둥글기입니다. Full 은 화면 전체를 덮으므로 0dp 입니다.
 * @property navigationPadding Dp: Navigation 영역에 Bottom Sheet 가 추가로 얹는 여백입니다. 여백 자체는 Navigation 컴포넌트가 소유하므로 기본값은 0dp 입니다.
 * @property contentHorizontalPadding Dp: Content 영역의 좌우 여백입니다.
 * @property contentVerticalPadding Dp: Content 영역의 상하 여백입니다.
 * @property actionHorizontalPadding Dp: Action Area 의 좌우 여백입니다.
 * @property actionVerticalPadding Dp: Action Area 의 상하 여백입니다.
 *
 * @see WantedBottomSheetDefaults
 */
data class WantedBottomSheetDefault(
    val shape: RoundedCornerShape,
    val navigationPadding: Dp,
    val contentHorizontalPadding: Dp,
    val contentVerticalPadding: Dp,
    val actionHorizontalPadding: Dp,
    val actionVerticalPadding: Dp
) {
    internal val actionPadding: PaddingValues
        get() = PaddingValues(
            start = actionHorizontalPadding,
            end = actionHorizontalPadding,
            top = actionVerticalPadding,
            bottom = actionVerticalPadding
        )

    internal val contentPadding: PaddingValues
        get() = PaddingValues(
            horizontal = contentHorizontalPadding,
            vertical = contentVerticalPadding
        )
}
