package com.wanted.android.wanted.design.presentation.modal.popup

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actions.actionarea.ActionAreaType

/**
 * data class WantedPopupDefault
 *
 * Popup 의 모양과 여백을 정의하는 데이터 클래스입니다.
 *
 * 값을 바꿔 쓸 때는 [WantedPopupDefaults.getDefault] 의 인자로 넘기거나 `copy` 를 사용합니다.
 *
 * Popup 의 여백은 디자인 4.0.0 기준으로 ModalSize 와 무관하게 통일되어 있고,
 * 모바일 Popup 은 Medium 한 가지만 사용하므로 [width] 도 하나로 고정입니다.
 * (BottomSheet 는 계속 ModalSize 의 padding 값을 사용하므로 그쪽 값은 건드리지 않습니다.)
 *
 * @property shape RoundedCornerShape: Popup 의 모서리 둥글기입니다.
 * @property width Dp: Popup 의 최대 너비입니다. 실제 너비는 Dialog 창 너비로 한 번 더 제한되므로, 폰에서는 창 너비를 그대로 따르고 큰 화면에서만 이 값으로 잘립니다.
 * @property navigationPadding Dp: Navigation 영역에 Popup 이 추가로 얹는 여백입니다. 여백 자체는 Navigation 컴포넌트가 소유하므로 기본값은 0dp 입니다.
 * @property contentHorizontalPadding Dp: Content 영역의 좌우 여백입니다.
 * @property contentVerticalPadding Dp: Content 영역의 상하 여백입니다.
 * @property actionHorizontalPadding Dp: Action Area 의 좌우 여백입니다.
 * @property actionVerticalPadding Dp: Action Area 의 상하 여백입니다.
 * @property actionBottomPadding Dp: Action Area 하단에 추가로 붙는 여백입니다.
 * @property actionAreaType ActionAreaType: Action Area 의 타입입니다.
 *
 * @see WantedPopupDefaults
 */
data class WantedPopupDefault(
    val shape: RoundedCornerShape,
    val width: Dp,
    val navigationPadding: Dp,
    val contentHorizontalPadding: Dp,
    val contentVerticalPadding: Dp,
    val actionHorizontalPadding: Dp,
    val actionVerticalPadding: Dp,
    val actionBottomPadding: Dp,
    val actionAreaType: ActionAreaType
) {
    // Action Area 에 적용할 여백입니다. 하단에는 [actionBottomPadding] 이 더해집니다.
    internal val actionPadding: PaddingValues
        get() = PaddingValues(
            start = actionHorizontalPadding,
            end = actionHorizontalPadding,
            top = actionVerticalPadding,
            bottom = actionVerticalPadding + actionBottomPadding
        )
}

/**
 * object WantedPopupDefaults
 *
 * [WantedPopupDefault] 의 기본값을 제공하는 객체입니다.
 *
 * @see WantedPopupDefault
 */
object WantedPopupDefaults {

    /**
     * fun getDefault(...)
     *
     * Popup 의 기본 설정을 생성합니다.
     *
     * 사용 예시:
     * ```kotlin
     * // 본문 좌우 여백만 없애고 나머지는 기본값을 쓴다
     * WantedPopup(
     *     onDismissRequest = { },
     *     popupDefault = WantedPopupDefaults.getDefault(contentHorizontalPadding = 0.dp),
     *     content = { }
     * )
     * ```
     *
     * @param shape RoundedCornerShape: Popup 의 모서리 둥글기입니다.
     * @param width Dp: Popup 의 최대 너비입니다.
     * @param navigationPadding Dp: Navigation 영역에 Popup 이 추가로 얹는 여백입니다.
     * @param contentHorizontalPadding Dp: Content 영역의 좌우 여백입니다.
     * @param contentVerticalPadding Dp: Content 영역의 상하 여백입니다.
     * @param actionHorizontalPadding Dp: Action Area 의 좌우 여백입니다.
     * @param actionVerticalPadding Dp: Action Area 의 상하 여백입니다.
     * @param actionBottomPadding Dp: Action Area 하단에 추가로 붙는 여백입니다.
     * @param actionAreaType ActionAreaType: Action Area 의 타입입니다.
     * @return WantedPopupDefault: 설정된 WantedPopupDefault 인스턴스를 반환합니다.
     *
     * @see WantedPopupDefault
     */
    fun getDefault(
        shape: RoundedCornerShape = RoundedCornerShape(RADIUS_DP.dp),
        width: Dp = WIDTH_DP.dp,
        navigationPadding: Dp = 0.dp,
        contentHorizontalPadding: Dp = 28.dp,
        contentVerticalPadding: Dp = 0.dp,
        actionHorizontalPadding: Dp = 24.dp,
        actionVerticalPadding: Dp = 20.dp,
        actionBottomPadding: Dp = 4.dp,
        actionAreaType: ActionAreaType = ActionAreaType.Strong
    ) = WantedPopupDefault(
        shape = shape,
        width = width,
        navigationPadding = navigationPadding,
        contentHorizontalPadding = contentHorizontalPadding,
        contentVerticalPadding = contentVerticalPadding,
        actionHorizontalPadding = actionHorizontalPadding,
        actionVerticalPadding = actionVerticalPadding,
        actionBottomPadding = actionBottomPadding,
        actionAreaType = actionAreaType
    )

    private const val RADIUS_DP = 24
    private const val WIDTH_DP = 360
}
