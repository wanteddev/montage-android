package com.wanted.android.montage.sample

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wanted.android.wanted.design.contents.listcell.WantedListCell
import com.wanted.android.wanted.design.contents.listcell.WantedListCellDefaults
import com.wanted.android.wanted.design.input.input.control.CheckBoxState
import com.wanted.android.wanted.design.input.input.WantedInput
import com.wanted.android.wanted.design.input.input.WantedInputDefaults
import com.wanted.android.wanted.design.input.input.WantedInputDefaults.WantedInputVariant


@Composable
internal fun DSWantedOptionSwitchCell(
    text: String,
    checkState: Boolean,
    onCheckChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    WantedListCell(
        modifier = modifier.fillMaxWidth(),
        text = text,
        verticalPadding = WantedListCellDefaults.VerticalPadding.Small,
        fillWidth = true,
        ellipsis = true,
        trailingContent = {
            WantedInput(
                modifier = Modifier,
                size = WantedInputDefaults.WantedInputSize.Small,
                variant = WantedInputVariant.Switch,
                checkBoxState = if (checkState) {
                    CheckBoxState.Checked
                } else {
                    CheckBoxState.Unchecked
                },
                onCheckedChange = {
                    onCheckChanged(it)
                }
            )
        }
    ) {
        onCheckChanged(!checkState)
    }
}
