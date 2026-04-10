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
import androidx.compose.ui.unit.dp
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.wanted.design.actions.chip.WantedChip
import com.wanted.android.wanted.design.input.textinput.textarea.WantedTextArea
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
internal fun DSWantedTextAreaSampleModal(
    onDismissRequest: () -> Unit
) {
    WantedModal(
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
    var text1 by remember { mutableStateOf("asdf") }
    var text2 by remember { mutableStateOf("") }
    var description2 by remember { mutableStateOf("") }

    var text3 by remember { mutableStateOf("") }
    var text4 by remember { mutableStateOf("") }
    var description4 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        WantedTextArea(
            title = "테스트 TextFiled maxLine 8",
            text = text1,
            placeholder = "텍스트를 입력해 주세요.",
            maxWordCount = 10,
            enabledOverflowText = true,
            description = "테스트 test TextFiled 입니다.",
            onValueChange = { value ->
                text1 = value
            },
            leadingContent = null
        )

        WantedTextArea(
            title = "확인 : Description 변경 maxWord 20",
            requiredBadge = true,
            description = description2.ifEmpty { null },
            text = text2,
            maxWordCount = 10,
            enabledOverflowText = true,
            isGraphemeClusterCount = true,
            placeholder = "텍스트를 입력해 주세요.",
            rightButton = "확인",
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Ascii),
            onValueChange = { value ->
                text2 = value
            },
            onClickRightButton = {
                description2 = text2
            }
        )

        WantedTextArea(
            title = "error + description + leftContent",
            text = text3,
            description = "error description",
            placeholder = "텍스트를 입력해 주세요.",
            negative = true,
            onValueChange = { value ->
                text3 = value
            },
            leadingContent = {
                WantedChip(text = "WantedActionChip")
            }
        )

        WantedTextArea(
            title = "State Error + Description 변경",
            text = text4,
            placeholder = "텍스트를 입력해 주세요.",
            rightButton = "텍스트",
            description = description4.ifEmpty { null },
            negative = true,
            onValueChange = { value ->
                text4 = value
            },
            onClickRightButton = {
                description4 = text4
            }
        )

        WantedTextArea(
            title = "State Enable False",
            text = "입력한 텍스트",
            enabled = false,
            placeholder = "텍스트를 입력해 주세요.",
            rightButton = "텍스트",
            negative = true
        )

        WantedTextArea(
            title = "State Enable False",
            text = "",
            enabled = false,
            placeholder = "텍스트를 입력해 주세요.",
            rightButton = "텍스트",
            negative = true
        )
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