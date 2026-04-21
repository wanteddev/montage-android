package com.wanted.android.montage.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.wanted.android.wanted.design.actions.actionarea.WantedActionArea
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.input.picker.numberpicker.WantedNumberPicker
import com.wanted.android.wanted.design.navigations.topbar.dialogtopbar.WantedDialogTopAppBar
import com.wanted.android.wanted.design.presentation.modal.popup.WantedModal


@Composable
fun DSWantedOptionPicker(
    title: String,
    selectedValue: Int = 0,
    start: Int,
    end: Int,
    onSelect: (value: Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selected = remember(selectedValue) { mutableIntStateOf(selectedValue) }

    WantedModal(
        modifier = modifier.padding(horizontal = 20.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        topBar = {
            WantedDialogTopAppBar(title = title)
        },
        bottomBar = {
            WantedActionArea(
                modifier = Modifier.wrapContentHeight(),
                safeArea = false,
                positive = {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.action_select_complete),
                        onClick = {
                            onSelect(selected.intValue)
                            onDismissRequest()
                        }
                    )
                },
            )
        },
        onDismissRequest = onDismissRequest,
        content = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    WantedNumberPicker(
                        modifier = Modifier.weight(1f),
                        start = start,
                        end = end,
                        step = 1,
                        onSelect = { _, value, _ ->
                            selected.intValue = value
                        }
                    )
                }
            }
        }
    )
}