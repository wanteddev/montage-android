package com.wanted.android.montage.sample.input.textinput.textfield.before

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.wanted.design.contents.listcell.WantedListCell
import com.wanted.android.wanted.design.input.textinput.autocompletetextfield.WantedAutoCompleteTextField
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextField
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults
import com.wanted.android.wanted.design.input.textinput.textfield.WantedTextFieldDefaults.RightVariant
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
internal fun DSWantedTextFieldSampleModal(
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

    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(text = "CheckBox Small")

        WantedAutoCompleteTextField(
            modifier = Modifier.fillMaxWidth(),
            text = text1,
            anchorPadding = 8.dp,
            placeholder = "텍스트를 입력해 주세요.",
            rightButton = "텍스트",
            expanded = expanded,
            onValueChange = {
                text1 = it
            },
            onClickRightButton = {
                expanded = true
            },
            sectionCount = 3,
            sectionTitle = {
                when (it) {
                    0 -> "Section 1"
                    1 -> "Section 2"
                    2 -> "Section 3"
                    else -> ""
                }
            },
            sectionItemCount = { 10 },
            sectionItem = { section, index ->
                WantedListCell(
                    modifier = Modifier.fillMaxWidth(),
                    fillWidth = true,
                    text = "dropdown"
                ) {
                    expanded = false
                }
            },
            topDirectInput = {
                WantedListCell(
                    modifier = Modifier.fillMaxWidth(),
                    fillWidth = true,
                    text = "dropdown"
                ) {
                    expanded = false
                }
            },
            onExpandedChange = {
                expanded = it
            }
        )

        WantedTextField(
            title = "테스트 TextFiled",
            text = text1,
            placeholder = "텍스트를 입력해 주세요.",
            description = "테스트 test TextFiled 입니다.",
            onValueChange = { value ->
                text1 = value
            }
        )

        WantedTextField(
            title = "테스트 TextFiled",
            text = text1,
            maxLines = 10,
            enabledOverflowText = true,
            placeholder = "텍스트를 입력해 주세요.",
            description = "테스트 test TextFiled 입니다.",
            onValueChange = { value ->
                text1 = value
            }
        )


        WantedTextField(
            title = "확인 : Description 변경",
            requiredBadge = true,
            description = description2.ifEmpty { null },
            text = text2,
            placeholder = "텍스트를 입력해 주세요.",
            rightButton = "확인",
            onValueChange = { value ->
                text2 = value
            },
            onClickRightButton = {
                description2 = text2
            }
        )

        WantedTextField(
            title = "State Complete",
            text = text3,
            placeholder = "텍스트를 입력해 주세요.",
            status = WantedTextFieldDefaults.Status.Positive,
            onValueChange = { value ->
                text3 = value
            }
        )

        WantedTextField(
            title = "State Error + Description 변경",
            text = text4,
            placeholder = "텍스트를 입력해 주세요.",
            rightButton = "텍스트",
            description = description4.ifEmpty { null },
            status = WantedTextFieldDefaults.Status.Negative,
            onValueChange = { value ->
                text4 = value
            },
            onClickRightButton = {
                description4 = text4
            }
        )

        WantedTextField(
            title = "State Enable False",
            text = "입력한 텍스트",
            enabled = false,
            placeholder = "텍스트를 입력해 주세요.",
            rightButton = "텍스트",
            status = WantedTextFieldDefaults.Status.Negative
        )

        WantedTextField(
            text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
            placeholder = "텍스트를 입력해 주세요.",
            rightButton = "텍스트",
            rightButtonVariant = RightVariant.Assistive
        )

        WantedTextField(
            text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
            placeholder = "텍스트를 입력해 주세요.",
            rightButton = "텍스트",
            rightButtonVariant = RightVariant.Assistive,
            rightButtonEnabled = false
        )

        WantedTextField(
            text = "텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요. 텍스트를 입력해 주세요.",
            placeholder = "텍스트를 입력해 주세요.",
            rightButton = "텍스트",
            rightButtonEnabled = false
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