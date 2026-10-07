package com.wanted.android.wanted.design.actions.button

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
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButton
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonDefault
import com.wanted.android.wanted.design.actions.button.textbutton.WantedTextButtonDefaults
import com.wanted.android.wanted.design.actions.button.textbutton.toWantedTextButtonColor
import com.wanted.android.wanted.design.actions.button.textbutton.toWantedTextButtonSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonSize
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.OPACITY_12

/**
 * WantedButton
 *
 * 다양한 스타일의 버튼을 생성하는 공통 Compose 함수입니다.
 * ButtonVariant에 따라 Solid, Outlined, Text 버튼을 선택하여 렌더링합니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedButton(
 *     text = "확인",
 *     variant = ButtonVariant.SOLID,
 *     type = ButtonType.PRIMARY,
 *     size = ButtonSize.LARGE,
 *     onClick = { /* 클릭 이벤트 처리 */ }
 * )
 * ```
 *
 * @param text String: 버튼에 표시할 텍스트입니다.
 * @param modifier Modifier: Modifier를 통해 버튼 외형을 조정합니다.
 * @param type ButtonType: 버튼의 타입(PRIMARY, ASSISTIVE)을 지정합니다.
 * @param size ButtonSize: 버튼의 크기(LARGE, MEDIUM, SMALL)를 지정합니다.
 * @param variant ButtonVariant: 버튼의 형태(SOLID, OUTLINED, TEXT)를 지정합니다.
 * @param enabled Boolean: 버튼의 활성화 여부를 지정합니다.
 * @param isLoading Boolean: 로딩 상태를 표시할지 여부입니다.
 * @param leadingDrawable Int?: 버튼 왼쪽에 표시할 Drawable 리소스 ID입니다.
 * @param trailingDrawable Int?: 버튼 오른쪽에 표시할 Drawable 리소스 ID입니다.
 * @param onClick () -> Unit: 버튼 클릭 시 호출되는 콜백입니다.
 */
@Composable
fun WantedButton(
    text: String,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    variant: ButtonVariant = ButtonVariant.SOLID,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingDrawable: Int? = null,
    trailingDrawable: Int? = null,
    onClick: () -> Unit = {}
) {
    when (variant) {
        ButtonVariant.SOLID -> {
            WantedSolidButton(
                modifier = modifier,
                text = text,
                type = type,
                size = size,
                enabled = enabled,
                isLoading = isLoading,
                buttonDefault = WantedButtonDefaults.getDefault(
                    variant = variant,
                    type = type,
                    enabled = enabled,
                    size = size
                ),
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                onClick = onClick,
            )
        }

        ButtonVariant.OUTLINED -> {
            WantedOutlinedButton(
                modifier = modifier,
                text = text,
                size = size,
                type = type,
                enabled = enabled,
                isLoading = isLoading,
                buttonDefault = WantedButtonDefaults.getDefault(
                    variant = variant,
                    type = type,
                    enabled = enabled,
                    size = size
                ),
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                onClick = onClick,
            )
        }

        ButtonVariant.TEXT -> {
            WantedTextButton(
                modifier = modifier,
                text = text,
                size = size.toWantedTextButtonSize(),
                color = type.toWantedTextButtonColor(),
                enabled = enabled,
                isLoading = isLoading,
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                onClick = onClick,
            )
        }
    }
}

/**
 * WantedButton
 *
 * WantedButton을 더 세밀하게 제어하기 위한 Compose 함수입니다.
 * WantedButtonDefault를 직접 주입하여 스타일과 상태를 설정할 수 있습니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedButton(
 *     text = "삭제",
 *     buttonDefault = WantedButtonDefaults.getDefault(ButtonVariant.OUTLINED),
 *     onClick = { /* 클릭 처리 */ }
 * )
 * ```
 *
 * @param text String: 버튼에 표시할 텍스트입니다.
 * @param modifier Modifier: Modifier를 통해 버튼 외형을 조정합니다.
 * @param isLoading Boolean: 로딩 상태를 표시할지 여부입니다.
 * @param leadingDrawable Int?: 버튼 왼쪽에 표시할 Drawable 리소스 ID입니다.
 * @param trailingDrawable Int?: 버튼 오른쪽에 표시할 Drawable 리소스 ID입니다.
 * @param buttonDefault WantedButtonDefault: 버튼 기본 스타일 설정 객체입니다.
 * @param onClick () -> Unit: 버튼 클릭 시 호출되는 콜백입니다.
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

    when (buttonDefault.variant) {
        ButtonVariant.SOLID -> {
            WantedSolidButton(
                modifier = modifier,
                text = text,
                isLoading = isLoading,
                enabled = buttonDefault.enabled,
                buttonDefault = buttonDefault,
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                onClick = onClick,
            )
        }

        ButtonVariant.OUTLINED -> {
            WantedOutlinedButton(
                modifier = modifier,
                text = text,
                isLoading = isLoading,
                enabled = buttonDefault.enabled,
                buttonDefault = buttonDefault,
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                onClick = onClick,
            )
        }

        ButtonVariant.TEXT -> {
            WantedTextButton(
                modifier = modifier,
                text = text,
                isLoading = isLoading,
                enabled = buttonDefault.enabled,
                buttonDefault = buttonDefault.toWantedTextButtonDefault(),
                leadingDrawable = leadingDrawable,
                trailingDrawable = trailingDrawable,
                onClick = onClick,
            )
        }
    }
}


// 폐기된 ButtonVariant.TEXT 를 담은 WantedButtonDefault 를 WantedTextButtonDefault 로 옮긴다.
// 이 경로는 호출부가 WantedButtonDefaults.getDefault(variant = ButtonVariant.TEXT, ...).copy(...) 로
// 스타일을 덮어 쓰던 레거시 진입점이므로, 호출부가 덮을 수 있는 색·타이포는 그대로 옮긴다.
// 다만 loadingSize 는 SOLID/OUTLINED 와 공유하는 로더(LocalWantedButtonLoading)에서 오는 값이라
// Text Button 스펙(medium 16dp)과 어긋난다. 넘기지 않고 WantedTextButtonDefaults 값을 쓴다.
@Composable
private fun WantedButtonDefault.toWantedTextButtonDefault(): WantedTextButtonDefault =
    WantedTextButtonDefaults.getDefault(
        color = type.toWantedTextButtonColor(),
        size = size.toWantedTextButtonSize(),
        enabled = enabled,
        contentColor = contentColor,
        leadingIconTintColor = leftIconTintColor,
        trailingIconTintColor = rightIconTintColor,
        textStyle = textStyle,
        // 이 경로는 color 축과 무관하게 contentColor 기반 리플을 써 왔다.
        rippleColor = contentColor.copy(alpha = OPACITY_12),
        loadingColor = loadingColor
    )


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
                    variant = ButtonVariant.OUTLINED,
                    type = ButtonType.PRIMARY,
                    size = ButtonSize.MEDIUM,
                    onClick = { }
                )

                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "텍스트",
                    buttonDefault = WantedButtonDefaults.getDefault(
                        variant = ButtonVariant.OUTLINED,
                        type = ButtonType.PRIMARY,
                        size = ButtonSize.MEDIUM,
                        leftIconTintColor = DesignSystemTheme.colors.foregroundBrandPrimary
                    ),
                    onClick = { }
                )
            }
        }
    }
}