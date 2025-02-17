package com.wanted.android.wanted.design.input.textinput

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.contents.cell.WantedCell
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
fun WantedAutoCompleteTextInput(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    rightButton: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    rightButtonVariant: WantedTextInputRightVariant = WantedTextInputRightVariant.Normal,
    status: WantedTextInputContract.Status = WantedTextInputContract.Status.Normal,
    enabled: Boolean = true,
    rightButtonEnabled: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,
    maxWordCount: Int = 2000,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_normal_normal),
    onClickRightButton: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
    expended: Boolean = false,
    anchorPadding: Dp = 0.dp,
    dropDownMaxHeight: Dp = 200.dp,
    autoCompleteContent: @Composable ColumnScope.() -> Unit,
    onExpandedChange: (Boolean) -> Unit
) {

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expended,
        onExpandedChange = {
            onExpandedChange(it)
        }
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            WantedTextField(
                modifier = Modifier
                    .padding(vertical = anchorPadding)
                    .fillMaxWidth()
                    .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = text.isNotEmpty()),
                title = title,
                text = text,
                description = description,
                requiredBadge = requiredBadge,
                status = status,
                enabled = enabled,
                rightButtonEnabled = rightButtonEnabled,
                focused = focused,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                background = background,
                rightButton = rightButton,
                rightButtonVariant = rightButtonVariant,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                rightContent = rightContent,
                onClickRightButton = onClickRightButton,
                onValueChange = { text ->
                    onExpandedChange(text.isNotEmpty())
                    onValueChange(text)
                },
            )

            ExposedDropdownMenu(
                modifier = Modifier
                    .width(maxWidth)
                    .heightIn(max = dropDownMaxHeight),
                containerColor = colorResource(R.color.background_normal_normal),
                shape = RoundedCornerShape(20.dp),
                expanded = expended,
                onDismissRequest = {
                    onExpandedChange(false)
                }
            ) {
                autoCompleteContent()
            }
        }
    }
}

@Composable
fun WantedAutoCompleteTextInput(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    rightButton: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null,
    rightButtonVariant: WantedTextInputRightVariant = WantedTextInputRightVariant.Normal,
    status: WantedTextInputContract.Status = WantedTextInputContract.Status.Normal,
    enabled: Boolean = true,
    rightButtonEnabled: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,
    maxWordCount: Int = 2000,
    requiredBadge: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focused: State<Boolean> = interactionSource.collectIsFocusedAsState(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    background: Color = colorResource(id = R.color.background_normal_normal),
    onClickRightButton: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit = {},
    expended: Boolean = false,
    anchorPadding: Dp = 0.dp,
    dropDownMaxHeight: Dp = 200.dp,
    autoCompleteContent: @Composable ColumnScope.() -> Unit,
    onExpandedChange: (Boolean) -> Unit
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expended,
        onExpandedChange = {
            onExpandedChange(it)
        }
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            WantedTextField(
                modifier = Modifier
                    .padding(vertical = anchorPadding)
                    .fillMaxWidth()
                    .menuAnchor(
                        type = MenuAnchorType.PrimaryEditable,
                        enabled = value.text.isNotEmpty()
                    ),
                title = title,
                value = value,
                description = description,
                requiredBadge = requiredBadge,
                status = status,
                enabled = enabled,
                rightButtonEnabled = rightButtonEnabled,
                focused = focused,
                maxLines = maxLines,
                minLines = minLines,
                maxWordCount = maxWordCount,
                interactionSource = interactionSource,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                background = background,
                rightButton = rightButton,
                rightButtonVariant = rightButtonVariant,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                rightContent = rightContent,
                onClickRightButton = onClickRightButton,
                onValueChange = { text ->
                    onExpandedChange(text.text.isNotEmpty())
                    onValueChange(text)
                },
            )

            ExposedDropdownMenu(
                modifier = Modifier
                    .width(maxWidth)
                    .heightIn(max = dropDownMaxHeight),
                containerColor = colorResource(R.color.background_normal_normal),
                shape = RoundedCornerShape(20.dp),
                expanded = expended,
                onDismissRequest = {
                    onExpandedChange(expended)
                }
            ) {
                autoCompleteContent()
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedAutoCompleteTextInputPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedAutoCompleteTextInput(
                    modifier = Modifier.fillMaxWidth(),
                    text = "ㅁㄴㅇ",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    expended = false,
                    onValueChange = {
                    },
                    onClickRightButton = {
                    },
                    autoCompleteContent = {
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = {
                                WantedCell(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "as;dlkfj"
                                ) {}
                            },
                            onClick = {
                            }
                        )
                    },
                    onExpandedChange = {

                    }
                )
            }
        }
    }
}