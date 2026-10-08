package com.wanted.android.wanted.design.input.formcontrol

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews

/**
 * WantedFormControl
 *
 * 입력 컨트롤(TextField, TextArea, Select 등)에 Label·Required·Message·CharacterCount를
 * 붙이고, 라벨/입력/메시지의 배치를 처리하는 슬롯 기반 래퍼 컴포넌트입니다.
 *
 * 단독으로 값을 입력받지 않습니다. input 슬롯에 실제 입력 컴포넌트를 조합해서 사용합니다.
 * Web @wanteddev/wds 의 FormControl 과 1:1 대응되는 Android 구현입니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedFormControl(
 *     label = "이메일",
 *     required = true,
 *     description = "올바른 이메일 형식을 입력해 주세요.",
 *     status = WantedFormControlDefaults.Status.Negative,
 * ) {
 *     WantedTextField(
 *         text = email,
 *         onValueChange = { email = it },
 *         placeholder = "이메일을 입력해 주세요."
 *     )
 * }
 * ```
 *
 * @param modifier Modifier: Modifier를 설정합니다.
 * @param label String: 라벨 텍스트입니다. 빈 문자열이면 라벨을 렌더링하지 않습니다.
 * @param required Boolean: true이면 라벨 옆에 필수 뱃지(*)를 표시합니다. 뱃지 타이포는 size를 따릅니다.
 * @param description String?: 입력 컨트롤 하단에 표시되는 도움말 또는 에러 메시지입니다.
 * @param accessory (@Composable () -> Unit)?: 글자수 표시 슬롯입니다. WantedTextAreaCharacterCount를 주입할 수 있습니다.
 * @param size WantedFormControlDefaults.Size: 라벨·필수 뱃지 텍스트 크기를 지정합니다. (Large, Medium)
 * @param status WantedFormControlDefaults.Status: 상태에 따라 description 색상이 변경됩니다. (Normal, Positive, Negative)
 * @param labelPlacement WantedFormControlDefaults.LabelPlacement: 라벨 배치 위치를 지정합니다. (Top, Leading)
 * @param enabled Boolean: false이면 description을 비활성화 색상으로 표시합니다.
 * @param input @Composable () -> Unit: 필수 입력 컨트롤 슬롯입니다. (WantedTextField, WantedTextArea, WantedSelect 등)
 */
@Composable
fun WantedFormControl(
    modifier: Modifier = Modifier,
    label: String = "",
    required: Boolean = false,
    description: String? = null,
    accessory: @Composable (() -> Unit)? = null,
    size: WantedFormControlDefaults.Size = WantedFormControlDefaults.Size.Large,
    status: WantedFormControlDefaults.Status = WantedFormControlDefaults.Status.Normal,
    labelPlacement: WantedFormControlDefaults.LabelPlacement = WantedFormControlDefaults.LabelPlacement.Top,
    enabled: Boolean = true,
    input: @Composable () -> Unit,
) {
    WantedFormControlLayout(
        modifier = modifier,
        labelPlacement = labelPlacement,
        label = if (label.isNotEmpty()) {
            {
                WantedFormControlTitle(
                    label = label,
                    required = required,
                    size = size,
                )
            }
        } else null,
        footer = if (description != null || accessory != null) {
            {
                WantedFormControlFooter(
                    description = description,
                    accessory = accessory,
                    enabled = enabled,
                    status = status,
                )
            }
        } else null,
        input = input,
    )
}

@Composable
private fun WantedFormControlTitle(
    label: String,
    required: Boolean,
    size: WantedFormControlDefaults.Size,
) {
    val textStyle = when (size) {
        WantedFormControlDefaults.Size.Large -> DesignSystemTheme.typography.label1Bold
        WantedFormControlDefaults.Size.Medium -> DesignSystemTheme.typography.label2Bold
    }

    Row(
        modifier = Modifier.padding(horizontal = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            modifier = Modifier.weight(1f, fill = false),
            text = label,
            style = textStyle,
            color = DesignSystemTheme.colors.foregroundNeutralSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        if (required) {
            Text(
                text = "*",
                style = textStyle,
                color = DesignSystemTheme.colors.foregroundNegativePrimary,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun WantedFormControlFooter(
    description: String?,
    accessory: @Composable (() -> Unit)?,
    enabled: Boolean,
    status: WantedFormControlDefaults.Status,
) {
    val descriptionColor = when {
        !enabled -> DesignSystemTheme.colors.foregroundDisablePrimary
        status == WantedFormControlDefaults.Status.Negative -> {
            DesignSystemTheme.colors.foregroundNegativePrimary
        }

        else -> DesignSystemTheme.colors.foregroundNeutralTertiary
    }

    when {
        description != null && accessory != null -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = description,
                    style = DesignSystemTheme.typography.caption1Regular,
                    color = descriptionColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                accessory()
            }
        }

        description != null -> {
            Text(
                modifier = Modifier.padding(horizontal = 2.dp),
                text = description,
                style = DesignSystemTheme.typography.caption1Regular,
                color = descriptionColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }

        accessory != null -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                accessory()
            }
        }
    }
}


@DevicePreviews
@Composable
private fun WantedFormControlPreview() {
    DesignSystemTheme {
        Surface {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                WantedFormControl(
                    label = "라벨",
                    description = "도움말 메시지입니다.",
                    status = WantedFormControlDefaults.Status.Normal,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(DesignSystemTheme.colors.surfaceNeutralSecondary)
                    )
                }

                WantedFormControl(
                    label = "필수 항목",
                    required = true,
                    description = "이미 사용 중인 값입니다.",
                    status = WantedFormControlDefaults.Status.Negative,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(DesignSystemTheme.colors.surfaceNeutralSecondary)
                    )
                }

                WantedFormControl(
                    label = "비활성화",
                    description = "비활성화 상태 메시지입니다.",
                    enabled = false,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(DesignSystemTheme.colors.surfaceNeutralTertiary)
                    )
                }

                WantedFormControl(
                    label = "Medium 크기",
                    required = true,
                    size = WantedFormControlDefaults.Size.Medium,
                    description = "Medium 크기 라벨입니다.",
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(DesignSystemTheme.colors.surfaceNeutralSecondary)
                    )
                }

                WantedFormControl(
                    label = "Leading 배치",
                    required = true,
                    labelPlacement = WantedFormControlDefaults.LabelPlacement.Leading,
                    description = "라벨이 좌측에 배치됩니다.",
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .background(DesignSystemTheme.colors.surfaceNeutralSecondary)
                    )
                }

                WantedFormControl(
                    label = "Leading TextArea",
                    required = true,
                    labelPlacement = WantedFormControlDefaults.LabelPlacement.Leading,
                    description = "여러 줄 입력 시 라벨은 상단 영역 기준으로 정렬됩니다.",
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(116.dp)
                            .background(DesignSystemTheme.colors.surfaceNeutralSecondary)
                    )
                }
            }
        }
    }
}
