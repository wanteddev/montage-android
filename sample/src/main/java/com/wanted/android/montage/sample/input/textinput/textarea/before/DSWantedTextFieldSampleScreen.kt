package com.wanted.android.montage.sample.input.textinput.textarea.before

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.wanted.design.actions.chip.WantedChip
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControl
import com.wanted.android.wanted.design.input.formcontrol.WantedFormControlDefaults
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextArea
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextAreaDefaults
import com.wanted.android.wanted.design.presentation.modal.popup.WantedPopup
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
internal fun DSWantedTextAreaSampleModal(
    onDismissRequest: () -> Unit
) {
    WantedPopup(
        positive = "확인",
        onClickPositive = {
            onDismissRequest()
        },
        onDismissRequest = {
            onDismissRequest()
        },
        content = {
            DSWantedTextFieldSampleScreen()
        }
    )
}

@Composable
private fun DSWantedTextFieldSampleScreen(
) {
    var text1 by remember { mutableStateOf(TextFieldValue("asdf")) }
    var text2 by remember { mutableStateOf(TextFieldValue()) }
    var description2 by remember { mutableStateOf("") }

    var text3 by remember { mutableStateOf(TextFieldValue()) }
    var text4 by remember { mutableStateOf(TextFieldValue()) }
    var description4 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        WantedFormControl(
            label = "테스트 TextField maxLine 8",
            description = "테스트 test TextField 입니다."
        ) {
            WantedTextArea(
                value = text1,
                placeholder = "텍스트를 입력해 주세요.",
                maxWordCount = 10,
                enabledOverflowText = true,
                onValueChange = { value ->
                    text1 = value
                }
            )
        }

        WantedFormControl(
            label = "확인 : Description 변경 maxWord 20",
            required = true,
            description = description2.ifEmpty { null }
        ) {
            WantedTextArea(
                value = text2,
                maxWordCount = 10,
                enabledOverflowText = true,
                isGraphemeClusterCount = true,
                placeholder = "텍스트를 입력해 주세요.",
                button = "확인",
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Ascii),
                onValueChange = { value ->
                    text2 = value
                },
                onClickButton = {
                    description2 = text2.text
                }
            )
        }

        WantedFormControl(
            label = "error + description + leadingContent",
            description = "error description",
            status = WantedFormControlDefaults.Status.Negative
        ) {
            WantedTextArea(
                value = text3,
                placeholder = "텍스트를 입력해 주세요.",
                status = WantedTextAreaDefaults.Status.Negative,
                leadingContent = {
                    WantedChip(text = "WantedActionChip")
                },
                onValueChange = { value ->
                    text3 = value
                }
            )
        }

        WantedFormControl(
            label = "State Error + Description 변경",
            description = description4.ifEmpty { null },
            status = WantedFormControlDefaults.Status.Negative
        ) {
            WantedTextArea(
                value = text4,
                placeholder = "텍스트를 입력해 주세요.",
                button = "텍스트",
                status = WantedTextAreaDefaults.Status.Negative,
                onValueChange = { value ->
                    text4 = value
                },
                onClickButton = {
                    description4 = text4.text
                }
            )
        }

        WantedFormControl(
            label = "State Enable False (값 있음)",
            status = WantedFormControlDefaults.Status.Negative,
            enabled = false
        ) {
            WantedTextArea(
                value = TextFieldValue("입력한 텍스트"),
                enabled = false,
                placeholder = "텍스트를 입력해 주세요.",
                button = "텍스트",
                status = WantedTextAreaDefaults.Status.Negative,
                onValueChange = {}
            )
        }

        WantedFormControl(
            label = "State Enable False (값 없음)",
            status = WantedFormControlDefaults.Status.Negative,
            enabled = false
        ) {
            WantedTextArea(
                value = TextFieldValue(),
                enabled = false,
                placeholder = "텍스트를 입력해 주세요.",
                button = "텍스트",
                status = WantedTextAreaDefaults.Status.Negative,
                onValueChange = {}
            )
        }
    }
}


@DevicePreviews
@Composable
private fun TextFieldScreenPreview() {
    DesignSystemTheme {
        DSWantedTextFieldSampleScreen(
        )
    }
}
