package com.wanted.android.wanted.design.presentation.modal.bottomsheet

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarDefaults
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.pxToDp

/**
 * object WantedModalDefaults
 *
 * Bottom sheet 상단에 표시되는 드래그 핸들 컴포넌트입니다.
 */
object WantedBottomSheetDefaults {
    /**
     * fun DragHandle(...)
     *
     * BottomSheet 상단에 표시되는 드래그 핸들 컴포넌트입니다.
     *
     * 사용 예시:
     * ```kotlin
     * WantedModalDefaults.DragHandle()
     * ```
     *
     * @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
     * @param color Color: 핸들의 배경 색상입니다.
     * @param shape Shape: 핸들의 모양입니다.
     */
    @Composable
    fun DragHandle(
        modifier: Modifier = Modifier.Companion,
        color: Color = DesignSystemTheme.colors.surfaceElevatedPrimary,
        shape: Shape = MaterialTheme.shapes.extraLarge,
    ) {
        Surface(
            modifier = modifier.padding(top = 7.dp),
            color = color,
            contentColor = color,
            shape = shape
        ) {
            Box(
                Modifier.Companion
                    .size(width = 40.dp, height = 5.dp)
                    .clip(RoundedCornerShape(1000.dp))
                    .background(
                        DesignSystemTheme.colors.surfaceNeutralStrong,
                        androidx.compose.foundation.shape.RoundedCornerShape(1000.dp)
                    )

            )
        }
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    @Composable
    internal fun heightModifier(
        type: WantedModalContract.ModalType,
        maxHeight: Dp? = null,
        configuration: Configuration = LocalConfiguration.current,
        windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets
    ): Modifier {

        val windowInset = windowInsets.getTop(LocalDensity.current).pxToDp()
        val screenHeight = configuration.screenHeightDp.dp

        return when (type) {
            is WantedModalContract.ModalType.Fixed -> {
                Modifier.Companion.height(min(type.height, maxHeight ?: type.height))
            }

            is WantedModalContract.ModalType.FixedFullScreen -> {
                val height = screenHeight - windowInset
                Modifier.Companion.height(min(height, maxHeight ?: height))
            }

            is WantedModalContract.ModalType.FixedRatio -> {
                val height =
                    screenHeight - windowInset - 10.dp - DRAG_HANDLE_SIZE_DP.dp
                val ratioHeight = screenHeight * type.ratio
                val result = min(ratioHeight, height)

                Modifier.Companion.height(min(result, maxHeight ?: result))
            }

            else -> {
                val result =
                    screenHeight - windowInset - 10.dp - DRAG_HANDLE_SIZE_DP.dp
                Modifier.Companion.heightIn(max = min(result, maxHeight ?: result))
            }
        }
    }

    /**
     * fun getDefault(...)
     *
     * Bottom Sheet 의 기본 설정을 생성합니다. (디자인 4.0.0)
     *
     * 사용 예시:
     * ```kotlin
     * // 본문 상하 여백까지 켠다 (좌우는 기본값 28)
     * WantedModalBottomSheet(
     *     isShow = true,
     *     onDismissRequest = { },
     *     sheetDefault = WantedBottomSheetDefaults.getDefault(
     *         contentVerticalPadding = WantedBottomSheetDefaults.CONTENT_VERTICAL_PADDING
     *     ),
     *     content = { }
     * )
     * ```
     *
     * @param shape RoundedCornerShape: Bottom Sheet 의 모서리 둥글기입니다.
     * @param navigationPadding Dp: Navigation 영역에 추가로 얹는 여백입니다.
     * @param contentHorizontalPadding Dp: Content 영역의 좌우 여백입니다.
     * @param contentVerticalPadding Dp: Content 영역의 상하 여백입니다.
     * @param actionHorizontalPadding Dp: Action Area 의 좌우 여백입니다.
     * @param actionVerticalPadding Dp: Action Area 의 상하 여백입니다.
     * @return WantedBottomSheetDefault: 설정된 WantedBottomSheetDefault 인스턴스를 반환합니다.
     *
     * @see WantedBottomSheetDefault
     */
    fun getDefault(
        shape: RoundedCornerShape = RoundedCornerShape(
            topStart = SHEET_RADIUS_DP.dp,
            topEnd = SHEET_RADIUS_DP.dp
        ),
        navigationPadding: Dp = 0.dp,
        contentHorizontalPadding: Dp = CONTENT_HORIZONTAL_PADDING,
        contentVerticalPadding: Dp = 0.dp,
        actionHorizontalPadding: Dp = 24.dp,
        actionVerticalPadding: Dp = 20.dp
    ) = WantedBottomSheetDefault(
        shape = shape,
        navigationPadding = navigationPadding,
        contentHorizontalPadding = contentHorizontalPadding,
        contentVerticalPadding = contentVerticalPadding,
        actionHorizontalPadding = actionHorizontalPadding,
        actionVerticalPadding = actionVerticalPadding
    )

    /**
     * fun getFullDefault(...)
     *
     * Full(전체화면) 의 기본 설정을 생성합니다. 여백은 Bottom Sheet 와 다른 값을 쓰고, 모서리는 Bottom Sheet 와 같은 Sheet Radius(32) 를 씁니다. (디자인 4.0.0)
     *
     * 사용 예시:
     * ```kotlin
     * WantedModalBottomSheet(
     *     isShow = true,
     *     onDismissRequest = { },
     *     type = WantedModalContract.ModalType.FixedFullScreen(),
     *     sheetDefault = WantedBottomSheetDefaults.getFullDefault(),
     *     content = { }
     * )
     * ```
     *
     * @param shape RoundedCornerShape: Full 의 모서리 둥글기입니다. Android 의 Full 은 상태바 아래에서 시작해 상단 모서리가 보이므로 Bottom Sheet 와 같은 값을 씁니다.
     * @param navigationPadding Dp: Navigation 영역에 추가로 얹는 여백입니다.
     * @param contentHorizontalPadding Dp: Content 영역의 좌우 여백입니다.
     * @param contentVerticalPadding Dp: Content 영역의 상하 여백입니다.
     * @param actionHorizontalPadding Dp: Action Area 의 좌우 여백입니다.
     * @param actionVerticalPadding Dp: Action Area 의 상하 여백입니다.
     * @return WantedBottomSheetDefault: 설정된 WantedBottomSheetDefault 인스턴스를 반환합니다.
     *
     * @see WantedBottomSheetDefault
     */
    fun getFullDefault(
        shape: RoundedCornerShape = RoundedCornerShape(
            topStart = SHEET_RADIUS_DP.dp,
            topEnd = SHEET_RADIUS_DP.dp
        ),
        navigationPadding: Dp = 0.dp,
        contentHorizontalPadding: Dp = FULL_CONTENT_HORIZONTAL_PADDING,
        contentVerticalPadding: Dp = FULL_CONTENT_VERTICAL_PADDING,
        actionHorizontalPadding: Dp = 20.dp,
        actionVerticalPadding: Dp = 20.dp
    ) = WantedBottomSheetDefault(
        shape = shape,
        navigationPadding = navigationPadding,
        contentHorizontalPadding = contentHorizontalPadding,
        contentVerticalPadding = contentVerticalPadding,
        actionHorizontalPadding = actionHorizontalPadding,
        actionVerticalPadding = actionVerticalPadding
    )

    /**
     * fun getWithoutContentPadding()
     *
     * Content 여백만 0 으로 지운 설정을 생성합니다. Navigation · Action Area 여백은 스펙 값을 그대로 씁니다.
     *
     * 호출부가 본문 여백을 직접 주고 있는 화면에서 이중 패딩을 피하려고 씁니다. 신규 화면은 [getDefault] · [getFullDefault] 를 그대로 쓰세요.
     *
     * 사용 예시:
     * ```kotlin
     * WantedModalBottomSheet(
     *     isShow = true,
     *     onDismissRequest = { },
     *     sheetDefault = WantedBottomSheetDefaults.getWithoutContentPadding(),
     *     content = { Column(modifier = Modifier.padding(horizontal = 20.dp)) { } }
     * )
     * ```
     *
     * @param type ModalType: 모달의 형태입니다. Full 만 별도 여백 값을 씁니다.
     * @return WantedBottomSheetDefault: Content 여백이 0 인 WantedBottomSheetDefault 인스턴스를 반환합니다.
     *
     * @see WantedBottomSheetDefault
     */
    fun getWithoutContentPadding(
        type: WantedModalContract.ModalType = WantedModalContract.ModalType.Flexible
    ) = forType(type).copy(
        contentHorizontalPadding = 0.dp,
        contentVerticalPadding = 0.dp
    )

    // ModalType 에 맞는 기본 설정을 고른다. Full 만 별도 값을 쓰고 나머지는 Bottom Sheet 값이다.
    internal fun forType(type: WantedModalContract.ModalType) =
        when (type) {
            is WantedModalContract.ModalType.FixedFullScreen -> getFullDefault()
            else -> getDefault()
        }

    // Content 여백 기본값은 아래 스펙 토큰이다. 호출부가 여백을 직접 주는 화면은 [getWithoutContentPadding] 으로 0 을 넘긴다.

    // Bottom Sheet Content 의 스펙 좌우 여백 (Figma Margin/Content/Content Horizontal)
    val CONTENT_HORIZONTAL_PADDING = 28.dp

    // Bottom Sheet Content 의 스펙 상하 여백 (Figma Margin/Content/Content Vertical)
    val CONTENT_VERTICAL_PADDING = 24.dp

    // Full Content 의 스펙 좌우 여백 (Figma Margin/Content/Content Horizontal, None)
    val FULL_CONTENT_HORIZONTAL_PADDING = 24.dp

    // Full Content 의 스펙 상하 여백 (Figma Margin/Content/Content Vertical, None)
    val FULL_CONTENT_VERTICAL_PADDING = 20.dp

    private const val DRAG_HANDLE_SIZE_DP = 19

    // Figma(4.0.0) Modal/Sheet Radius 토큰. Popup 의 Modal/Radius(24) 와 분리된 값이다.
    private const val SHEET_RADIUS_DP = 32
}