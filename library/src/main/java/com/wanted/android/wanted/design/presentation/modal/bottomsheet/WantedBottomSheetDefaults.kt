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
        color: Color = DesignSystemTheme.colors.backgroundElevatedNormal,
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
                        DesignSystemTheme.colors.fillStrong,
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

    private const val DRAG_HANDLE_SIZE_DP = 19
}