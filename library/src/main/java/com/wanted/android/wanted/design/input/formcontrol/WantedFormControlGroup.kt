package com.wanted.android.wanted.design.input.formcontrol

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedFormControlGroup
 *
 * Leading 배치 WantedFormControl 여러 개를 하나로 묶어 라벨 열 폭을 맞추는 컴포넌트입니다.
 *
 * 묶인 FormControl의 입력 시작 위치가 같은 세로선에 놓이도록 라벨 열 폭을 하나로 정합니다.
 * Top 배치 FormControl에는 영향이 없습니다. size·status·labelPlacement는 그룹이 아니라 각 FormControl이 정합니다.
 * iOS·Web 의 FormControlGroup 과 같은 동작입니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedFormControlGroup {
 *     WantedFormControl(
 *         label = "이름",
 *         required = true,
 *         labelPlacement = WantedFormControlDefaults.LabelPlacement.Leading,
 *     ) {
 *         WantedTextField(text = name, onValueChange = { name = it })
 *     }
 *     WantedFormControl(
 *         label = "담당자 연락처",
 *         labelPlacement = WantedFormControlDefaults.LabelPlacement.Leading,
 *     ) {
 *         WantedTextField(text = phone, onValueChange = { phone = it })
 *     }
 * }
 * ```
 *
 * @param modifier Modifier: Modifier를 설정합니다.
 * @param labelWidth Dp?: 라벨 열 폭입니다. null이면 묶인 Leading FormControl 중 가장 긴 라벨의 폭(필수 표시 * 포함)을 씁니다. 값을 주면 그 폭으로 고정하고, 넘치는 라벨은 1줄로 말줄임(…) 처리하며 필수 표시 *는 말줄임한 텍스트 바로 뒤에 붙습니다. 개별 FormControl의 labelWidth가 이 값보다 우선합니다.
 * @param spacing Dp: FormControl 사이 세로 간격입니다. 기본값은 spacing16입니다.
 * @param content @Composable ColumnScope.() -> Unit: 묶을 WantedFormControl을 배치하는 슬롯입니다.
 */
@Composable
fun WantedFormControlGroup(
    modifier: Modifier = Modifier,
    labelWidth: Dp? = null,
    spacing: Dp = DesignSystemTheme.spacing.spacing16,
    content: @Composable ColumnScope.() -> Unit,
) {
    val labelWidthRegistry = remember { WantedFormControlGroupLabelWidthRegistry() }
    val groupState = remember(labelWidth, labelWidthRegistry) {
        WantedFormControlGroupState(labelWidth = labelWidth, registry = labelWidthRegistry)
    }

    CompositionLocalProvider(LocalWantedFormControlGroupState provides groupState) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(spacing),
            content = content,
        )
    }
}

// 그룹이 묶인 FormControl에 내려주는 값입니다.
//
// labelWidth가 있으면 고정 폭, 없으면 registry에 모인 라벨 자연 폭의 최댓값을 공유 폭으로 씁니다.
internal class WantedFormControlGroupState(
    val labelWidth: Dp?,
    val registry: WantedFormControlGroupLabelWidthRegistry,
)

// 그룹 안 Leading FormControl의 라벨 자연 폭(px)을 모읍니다.
//
// 각 FormControl이 composition 단계(SideEffect)에서 등록하고, 측정 단계에서 최댓값을 읽습니다.
// SideEffect는 같은 프레임의 측정보다 먼저 실행되므로 첫 프레임부터 폭이 맞춰집니다.
// 측정 단계에서만 읽으므로 값이 바뀌어도 recomposition 없이 재측정만 일어납니다.
internal class WantedFormControlGroupLabelWidthRegistry {
    private val widths = mutableStateMapOf<Any, Int>()

    fun register(key: Any, widthPx: Int) {
        if (widths[key] != widthPx) widths[key] = widthPx
    }

    fun unregister(key: Any) {
        widths.remove(key)
    }

    fun maxWidthPx(): Int = widths.values.maxOrNull() ?: 0
}

internal val LocalWantedFormControlGroupState =
    compositionLocalOf<WantedFormControlGroupState?> { null }

@DevicePreviews
@Composable
private fun WantedFormControlGroupPreview() {
    DesignSystemTheme {
        Surface {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                // labelWidth = null: 가장 긴 라벨("담당자 연락처*") 폭으로 맞춘다.
                WantedFormControlGroup {
                    PreviewLeadingFormControl(label = "이름", required = true)
                    PreviewLeadingFormControl(label = "담당자 연락처", required = true)
                    PreviewLeadingFormControl(label = "메모")
                }

                // labelWidth 고정: 넘치는 라벨은 말줄임하고 * 는 텍스트 바로 뒤에 붙는다.
                WantedFormControlGroup(labelWidth = 64.dp) {
                    PreviewLeadingFormControl(label = "이름", required = true)
                    PreviewLeadingFormControl(label = "담당자 연락처", required = true)
                    PreviewLeadingFormControl(label = "메모")
                }
            }
        }
    }
}

@Composable
private fun PreviewLeadingFormControl(
    label: String,
    required: Boolean = false,
) {
    WantedFormControl(
        label = label,
        required = required,
        labelPlacement = WantedFormControlDefaults.LabelPlacement.Leading,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(DesignSystemTheme.colors.surfaceNeutralSecondary)
        )
    }
}
