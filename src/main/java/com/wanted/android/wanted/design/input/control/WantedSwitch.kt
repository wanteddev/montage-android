package com.wanted.android.wanted.design.input.control

import android.content.Context
import android.util.AttributeSet
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.android.material.switchmaterial.SwitchMaterial
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.clickOnce
import com.wanted.android.wanted.design.util.dpToPx
import kotlinx.coroutines.launch

class WantedSwitch : SwitchMaterial {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    init {
        scaleX = 0.7F
        scaleY = 0.7F
    }
}


/**
 * 커스텀 스타일의 토글 스위치 컴포저블입니다.
 *
 * 머터리얼 스위치와 유사하지만, 색상/사이즈/애니메이션 등을 사용자 정의한 컴포넌트입니다.
 * 선택 여부 및 활성화 상태에 따라 thumb 위치와 색상이 전환됩니다.
 *
 * 사용 예시 :
 * ```kotlin
 * WantedSwitch(
 *     checked = true,
 *     size = CheckBoxSize.Normal,
 *     enabled = true,
 *     onCheckedChange = { toggled -> /* 상태 처리 */ }
 * )
 * ```
 *
 * @param checked Boolean: 스위치가 켜진 상태인지 여부입니다.
 * @param modifier Modifier: 외형 및 배치를 제어하는 Modifier입니다.
 * @param enabled Boolean: 스위치 활성화 여부입니다.
 * @param size CheckBoxSize: 스위치의 크기 설정 (Normal, Small).
 * @param interactionSource MutableInteractionSource: 상호작용 효과(리플 등)를 처리하기 위한 인터랙션 소스입니다.
 * @param onCheckedChange (Boolean) -> Unit: 상태가 변경될 때 호출되는 콜백입니다.
 */
@Composable
fun WantedSwitch(
    checked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: CheckBoxSize = CheckBoxSize.Normal,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onCheckedChange: (Boolean) -> Unit = {},
) {
    val thumbSize = remember(size) {
        if (size == CheckBoxSize.Normal) 24.dp else 18.dp
    }

    val width = remember(size) {
        if (size == CheckBoxSize.Normal) 52.dp else 39.dp
    }

    val animatedOffsetX = remember { Animatable(0f) }
    val offset = if (!checked) {
        0f
    } else {
        thumbSize.value.dpToPx() - (if (size == CheckBoxSize.Normal) 4 else 3).dpToPx().toFloat()
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(checked) {
        scope.launch {
            animatedOffsetX.animateTo(
                targetValue = offset,
                animationSpec = tween(durationMillis = 200)
            )
        }
    }

    var isChecked by remember { mutableStateOf(checked) }

    LaunchedEffect(animatedOffsetX.isRunning) {
        if (!animatedOffsetX.isRunning) {
            isChecked = checked
        }
    }


    val toggleableModifier = Modifier.clickOnce(
        enabled = enabled,
        interactionSource = interactionSource,
        indication = null
    ) { onCheckedChange(!isChecked) }

    Box(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.43f)
            .clip(RoundedCornerShape(32.dp))
            .background(
                if (isChecked) {
                    colorResource(id = R.color.primary_normal)
                } else {
                    colorResource(id = R.color.fill_strong)
                }
            )
            .width(width)
            .height(if (size == CheckBoxSize.Normal) 32.dp else 24.dp)
            .then(toggleableModifier)
            .padding(if (size == CheckBoxSize.Normal) 4.dp else 3.dp),
    ) {
        Box(
            modifier = Modifier
                .size(thumbSize)
                .offset { IntOffset(animatedOffsetX.value.toInt(), 0) }
                .indication(
                    interactionSource = interactionSource,
                    indication = ripple(
                        bounded = false,
                        radius = thumbSize / 2
                    )
                )
                .background(colorResource(id = R.color.static_white), CircleShape),

            contentAlignment = Alignment.Center
        ) {
        }
    }
}

@DevicePreviews
@Composable
private fun WantedSwitchPreview() {
    DesignSystemTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.background_normal_normal))
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                CheckBox()
            }
        }
    }
}

@Composable
private fun CheckBox(
    style: CheckBoxStyle = CheckBoxStyle.Switch,
) {
    val checked = remember { mutableStateOf(CheckBoxState.Unchecked) }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Small,
                style = style,
                checkState = checked.value,
                onCheckedChange = {
                    checked.value = if (it) {
                        CheckBoxState.Checked
                    } else {
                        CheckBoxState.Unchecked
                    }
                }
            )

            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Small,
                style = style,
                checkState = checked.value,
                onCheckedChange = {
                    checked.value = if (it) {
                        CheckBoxState.Checked
                    } else {
                        CheckBoxState.Unchecked
                    }
                }
            )
            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Small,
                style = style,
                checkState = checked.value,
                onCheckedChange = {
                    checked.value = if (it) {
                        CheckBoxState.Checked
                    } else {
                        CheckBoxState.Unchecked
                    }
                }
            )

            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Normal,
                style = style,
                checkState = checked.value,
                onCheckedChange = {
                    checked.value = if (it) {
                        CheckBoxState.Checked
                    } else {
                        CheckBoxState.Unchecked
                    }
                }
            )

            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Normal,
                style = style,
                checkState = checked.value,
                onCheckedChange = {
                    checked.value = if (it) {
                        CheckBoxState.Checked
                    } else {
                        CheckBoxState.Unchecked
                    }
                }
            )
            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Normal,
                style = style,
                checkState = checked.value,
                onCheckedChange = {
                    checked.value = if (it) {
                        CheckBoxState.Checked
                    } else {
                        CheckBoxState.Unchecked
                    }
                }
            )

        }

        // ------- enable
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Small,
                style = style,
                enabled = false,
                checkState = checked.value,
                onCheckedChange = {
                    checked.value = if (it) {
                        CheckBoxState.Checked
                    } else {
                        CheckBoxState.Unchecked
                    }
                }
            )

            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Small,
                style = style,
                enabled = false,
                checkState = checked.value,
                onCheckedChange = {
                    checked.value = if (it) {
                        CheckBoxState.Checked
                    } else {
                        CheckBoxState.Unchecked
                    }
                }
            )
            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Small,
                style = style,
                enabled = false,
                checkState = checked.value,
                onCheckedChange = {
                    checked.value = if (it) {
                        CheckBoxState.Checked
                    } else {
                        CheckBoxState.Unchecked
                    }
                }
            )

            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Normal,
                style = style,
                enabled = false,
                checkState = checked.value,
                onCheckedChange = {
                    checked.value = if (it) {
                        CheckBoxState.Checked
                    } else {
                        CheckBoxState.Unchecked
                    }
                }
            )

            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Normal,
                style = style,
                enabled = false,
                checkState = checked.value,
                onCheckedChange = {
                    checked.value = if (it) {
                        CheckBoxState.Checked
                    } else {
                        CheckBoxState.Unchecked
                    }
                }
            )
            WantedCheckBox(
                modifier = Modifier,
                size = CheckBoxSize.Normal,
                style = style,
                enabled = false,
                checkState = checked.value,
                onCheckedChange = {
                    checked.value = if (it) {
                        CheckBoxState.Checked
                    } else {
                        CheckBoxState.Unchecked
                    }
                }
            )

        }
    }
}
