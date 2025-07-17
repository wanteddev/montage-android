package com.wanted.android.wanted.design.presentation.modal

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarDefaults
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalType
import com.wanted.android.wanted.design.util.pxToDp

/**
 * object WantedModalDefaults
 *
 * 모달(Modal) UI의 기본 구성 요소 및 유틸리티 기능을 제공하는 객체입니다.
 */
object WantedModalDefaults {
    /**
     * DragHandle
     *
     * 드래그 가능한 핸들 표시를 위한 컴포저블입니다.
     *
     * 시트 상단에 표시되는 손잡이 형태의 UI를 그리며, 색상과 모양을 커스터마이징할 수 있습니다.
     *
     * 사용 예시:
     * ```kotlin
     * WantedModalDefaults.DragHandle()
     * ```
     *
     * @param modifier `Modifier`: 핸들의 위치 및 크기를 조정하기 위한 Modifier입니다.
     * @param color `Color`: 핸들의 배경 색상입니다. 기본값은 `background_elevated_normal`입니다.
     * @param shape `Shape`: 핸들의 모양입니다. 기본값은 `MaterialTheme.shapes.extraLarge`입니다.
     */
    @Composable
    fun DragHandle(
        modifier: Modifier = Modifier,
        color: Color = colorResource(id = R.color.background_elevated_normal),
        shape: Shape = MaterialTheme.shapes.extraLarge,
    ) {
        Surface(
            modifier = modifier.padding(top = 7.dp),
            color = color,
            contentColor = color,
            shape = shape
        ) {
            Box(
                Modifier
                    .size(width = 40.dp, height = 5.dp)
                    .clip(RoundedCornerShape(1000.dp))
                    .background(
                        colorResource(id = R.color.fill_strong),
                        RoundedCornerShape(1000.dp)
                    )

            )
        }
    }

    @SuppressLint("ModifierFactoryExtensionFunction")
    @Composable
    internal fun heightModifier(
        type: ModalType,
        maxHeight: Dp? = null,
        configuration: Configuration = LocalConfiguration.current,
        windowInsets: WindowInsets = WantedTopAppBarDefaults.windowInsets
    ): Modifier {

        val windowInset = windowInsets.getTop(LocalDensity.current).pxToDp()
        val screenHeight = configuration.screenHeightDp.dp

        return when (type) {
            is ModalType.Fixed -> {
                Modifier.height(min(type.height, maxHeight ?: type.height))
            }

            is ModalType.FixedFullScreen -> {
                val height = screenHeight - windowInset + 10.dp
                Modifier.height(min(height, maxHeight ?: height))
            }

            is ModalType.FixedRatio -> {
                val height =
                    screenHeight - windowInset - 10.dp - DRAG_HANDLE_SIZE_DP.dp
                val ratioHeight = screenHeight * type.ratio
                val result = min(ratioHeight, height)

                Modifier.height(min(result, maxHeight ?: result))
            }

            else -> {
                val result =
                    screenHeight - windowInset - 10.dp - DRAG_HANDLE_SIZE_DP.dp
                Modifier.heightIn(max = min(result, maxHeight ?: result))
            }
        }
    }

    private const val DRAG_HANDLE_SIZE_DP = 19
}