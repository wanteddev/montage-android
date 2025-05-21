package com.wanted.android.wanted.design.actions.button.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefault
import com.wanted.android.wanted.design.actions.button.config.WantedButtonDefaults
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonShape
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * 다양한 스타일의 버튼을 생성하는 공통 Compose 함수입니다.
 * ButtonShape에 따라 Solid, Outlined, Text 버튼을 선택하여 렌더링합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedButton(
 *     text = "확인",
 *     buttonShape = ButtonShape.SOLID,
 *     type = ButtonType.PRIMARY,
 *     size = ButtonSize.LARGE,
 *     onClick = { /* 클릭 이벤트 처리 */ }
 * )
 * ```
 *
 * @param text 버튼에 표시할 텍스트입니다.울라 ㅇㅜㄹ 3트
 * @param modifier Modifier를 통해 버튼 외형을 조정합니다.
 * @param buttonShape 버튼의 형태(SOLID, OUTLINED, TEXT)를 지정합니다.
 * @param type 버튼의 타입(PRIMARY, SECONDARY, ASSISTIVE)을 지정합니다.
 * @param size 버튼의 크기(LARGE, MEDIUM, SMALL)를 지정합니다.
 * @param enabled 버튼 활성화 여부를 지정합니다.
 * @param isLoading 로딩 상태를 표시할지 여부입니다.
 * @param leadingDrawable 버튼 왼쪽에 표시할 Drawable 리소스 ID입니다.
 * @param trailingDrawable 버튼 오른쪽에 표시할 Drawable 리소스 ID입니다.
 * @param onClick 버튼 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedButton(
    text: String,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    buttonShape: ButtonShape = ButtonShape.SOLID,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingDrawable: Int? = null,
    trailingDrawable: Int? = null,
    onClick: () -> Unit = {}
) {
    when (buttonShape) {
        ButtonShape.SOLID -> {
            WantedSolidButton(
                modifier = modifier,
                text = text,
                type = type,
                size = size,
                enabled = enabled,
                isLoading = isLoading,
                buttonDefault = WantedButtonDefaults.getDefault(
                    shape = buttonShape,
                    type = type,
                    enabled = enabled,
                    size = size
                ),
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                onClick = onClick,
            )
        }

        ButtonShape.OUTLINED -> {
            WantedOutlinedButton(
                modifier = modifier,
                text = text,
                size = size,
                type = type,
                enabled = enabled,
                isLoading = isLoading,
                buttonDefault = WantedButtonDefaults.getDefault(
                    shape = buttonShape,
                    type = type,
                    enabled = enabled,
                    size = size
                ),
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                onClick = onClick,
            )
        }

        ButtonShape.TEXT -> {
            WantedTextButton(
                modifier = modifier,
                text = text,
                size = size,
                type = type,
                enabled = enabled,
                isLoading = isLoading,
                buttonDefault = WantedButtonDefaults.getDefault(
                    shape = buttonShape,
                    type = type,
                    enabled = enabled,
                    size = size
                ),
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                onClick = onClick,
            )
        }
    }
}

/**
 * WantedButton을 더 세밀하게 제어하기 위한 Compose 함수입니다.
 * ButtonDefault를 직접 주입하여 스타일과 상태를 설정할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedButton(
 *     text = "삭제",
 *     buttonDefault = WantedButtonDefaults.getDefault(ButtonShape.OUTLINED),
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param modifier Modifier를 통해 버튼 외형을 조정합니다.
 * @param text 버튼에 표시할 텍스트입니다.
 * @param isLoading 로딩 상태를 표시할지 여부입니다.
 * @param buttonDefault 버튼 기본 스타일 설정 객체입니다.
 * @param leadingDrawable 버튼 왼쪽에 표시할 Drawable 리소스 ID입니다.
 * @param trailingDrawable 버튼 오른쪽에 표시할 Drawable 리소스 ID입니다.
 * @param onClick 버튼 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedButton(
    text: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    leadingDrawable: Int? = null,
    trailingDrawable: Int? = null,
    buttonDefault: WantedButtonDefault = WantedButtonDefaults.getDefault(),
    onClick: () -> Unit = {}
) {

    when (buttonDefault.shape) {
        ButtonShape.SOLID -> {
            WantedSolidButton(
                modifier = modifier,
                text = text,
                isLoading = isLoading,
                buttonDefault = buttonDefault,
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                onClick = onClick,
            )
        }

        ButtonShape.OUTLINED -> {
            WantedOutlinedButton(
                modifier = modifier,
                text = text,
                isLoading = isLoading,
                buttonDefault = buttonDefault,
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                onClick = onClick,
            )
        }

        ButtonShape.TEXT -> {
            WantedTextButton(
                modifier = modifier,
                text = text,
                isLoading = isLoading,
                buttonDefault = buttonDefault,
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                onClick = onClick,
            )
        }
    }
}


@DevicePreviews
@Composable
private fun WantedAvatarPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "텍스트",
                    buttonShape = ButtonShape.OUTLINED,
                    type = ButtonType.SECONDARY,
                    size = ButtonSize.MEDIUM,
                    onClick = { }
                )

                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "텍스트",
                    buttonDefault = WantedButtonDefaults.getDefault(
                        shape = ButtonShape.OUTLINED,
                        type = ButtonType.SECONDARY,
                        size = ButtonSize.MEDIUM
                    ),
                    onClick = { }
                )
            }
        }
    }
}