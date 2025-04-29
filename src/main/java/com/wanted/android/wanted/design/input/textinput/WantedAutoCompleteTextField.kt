package com.wanted.android.wanted.design.input.textinput

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import com.wanted.android.wanted.design.input.textinput.WantedTextFieldContract.RightVariant
import com.wanted.android.wanted.design.presentation.autocomplete.WantedAutoComplete
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews


@Composable
fun WantedAutoCompleteTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    rightButton: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    rightContent: @Composable ((Dp) -> Unit)? = null,
    rightButtonVariant: RightVariant = RightVariant.Normal,
    status: WantedTextFieldContract.Status = WantedTextFieldContract.Status.Normal,
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
    sectionTitleHorizontalPadding: Dp = 20.dp,
    sectionCount: Int = 1,
    sectionTitle: ((section: Int) -> String)? = null,
    sectionItemCount: (section: Int) -> Int,
    sectionItem: @Composable (section: Int, index: Int) -> Unit,
    topDirectInput: @Composable (() -> Unit)? = null,
    bottomDirectInput: @Composable (() -> Unit)? = null,
    onExpandedChange: (Boolean) -> Unit
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expended,
        onExpandedChange = {
            onExpandedChange(it && text.isNotEmpty())
        }
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            WantedTextField(
                modifier = Modifier
                    .padding(vertical = anchorPadding)
                    .fillMaxWidth()
                    .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true),
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

            WantedAutoComplete(
                modifier = Modifier
                    .width(maxWidth)
                    .heightIn(max = dropDownMaxHeight),
                containerColor = colorResource(R.color.background_normal_normal),
                expended = expended,
                onExpandedChange = {
                    onExpandedChange(false)
                },
                sectionTitleHorizontalPadding = sectionTitleHorizontalPadding,
                sectionCount = sectionCount,
                sectionTitle = sectionTitle,
                sectionItemCount = sectionItemCount,
                sectionItem = sectionItem,
                topDirectInput = topDirectInput,
                bottomDirectInput = bottomDirectInput
            )
        }
    }
}

@Composable
fun WantedAutoCompleteTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: String = "",
    title: String = "",
    description: String? = null,
    rightButton: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    rightContent: @Composable ((Dp) -> Unit)? = null,
    rightButtonVariant: RightVariant = RightVariant.Normal,
    status: WantedTextFieldContract.Status = WantedTextFieldContract.Status.Normal,
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
    sectionTitleHorizontalPadding: Dp = 20.dp,
    sectionCount: Int = 1,
    sectionTitle: ((section: Int) -> String)? = null,
    sectionItemCount: (section: Int) -> Int,
    sectionItem: @Composable (section: Int, index: Int) -> Unit,
    topDirectInput: @Composable (() -> Unit)? = null,
    bottomDirectInput: @Composable (() -> Unit)? = null,
    onExpandedChange: (Boolean) -> Unit
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expended,
        onExpandedChange = {
            onExpandedChange(it && value.text.isNotEmpty())
        }
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            WantedTextField(
                modifier = Modifier
                    .padding(vertical = anchorPadding)
                    .fillMaxWidth()
                    .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true),
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

            WantedAutoComplete(
                modifier = Modifier
                    .width(maxWidth)
                    .heightIn(max = dropDownMaxHeight),
                containerColor = colorResource(R.color.background_normal_normal),
                expended = expended,
                onExpandedChange = {
                    onExpandedChange(false)
                },
                sectionTitleHorizontalPadding = sectionTitleHorizontalPadding,
                sectionCount = sectionCount,
                sectionTitle = sectionTitle,
                sectionItemCount = sectionItemCount,
                sectionItem = sectionItem,
                topDirectInput = topDirectInput,
                bottomDirectInput = bottomDirectInput
            )
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
                WantedAutoCompleteTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = "ㅁㄴㅇ",
                    placeholder = "텍스트를 입력해 주세요.",
                    rightButton = "텍스트",
                    expended = false,
                    onValueChange = {
                    },
                    onClickRightButton = {
                    },
                    onExpandedChange = {

                    },
                    sectionItem = { section, index ->
                    },
                    sectionItemCount = { 0 }
                )
            }
        }
    }
}