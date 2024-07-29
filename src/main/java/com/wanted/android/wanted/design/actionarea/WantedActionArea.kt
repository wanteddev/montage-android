package com.wanted.android.wanted.design.actionarea

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actionarea.WantedActionAreaContract.ActionAreaType
import com.wanted.android.wanted.design.button.WantedButton
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonShape

@Composable
fun WantedActionArea(
    modifier: Modifier = Modifier,
    safeArea: Boolean = true,
    type: ActionAreaType = ActionAreaType.Strong,
    positiveShape: ButtonShape = LocalWantedActionAreaButtonStatus.current.getPositiveButtonShape(type),
    negativeShape: ButtonShape = LocalWantedActionAreaButtonStatus.current.getNegativeButtonShape(type),
    neutralShape: ButtonShape = LocalWantedActionAreaButtonStatus.current.getNeutralButtonShape(type),
    positive: String,
    negative: String? = null,
    neutral: String? = null,
    onClickPositive: () -> Unit,
    onClickNegative: (() -> Unit)? = null,
    onClickNeutral: (() -> Unit)? = null,
) {
    WantedActionAreaLayout(
        modifier = modifier,
        type = type,
        safeArea = safeArea,
        positive = {
            WantedButton(
                modifier = Modifier.fillMaxWidth(),
                text = positive,
                buttonShape = positiveShape,
                type = LocalWantedActionAreaButtonStatus.current.getPositiveButtonType(type),
                size = LocalWantedActionAreaButtonStatus.current.getPositiveButtonSize(type),
                onClick = onClickPositive
            )
        },
        negative = onClickNegative?.let {
            {
                WantedButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = negative.orEmpty(),
                    buttonShape = negativeShape,
                    type = LocalWantedActionAreaButtonStatus.current.getNegativeButtonType(type),
                    size = LocalWantedActionAreaButtonStatus.current.getNegativeButtonSize(type),
                    onClick = onClickNegative
                )
            }
        },
        neutral = onClickNeutral?.let {
            {
                WantedButton(
                    modifier = if (type == ActionAreaType.Strong) {
                        Modifier
                    } else {
                        Modifier.fillMaxWidth()
                    },
                    text = neutral.orEmpty(),
                    buttonShape = neutralShape,
                    type = LocalWantedActionAreaButtonStatus.current.getNeutralButtonType(type),
                    size = LocalWantedActionAreaButtonStatus.current.getNeutralButtonSize(type),
                    onClick = onClickNeutral
                )
            }
        }
    )
}


@Composable
private fun WantedActionAreaLayout(
    modifier: Modifier = Modifier,
    safeArea: Boolean = true,
    type: ActionAreaType = ActionAreaType.Strong,
    positive: @Composable () -> Unit,
    negative: @Composable (() -> Unit)? = null,
    neutral: @Composable (() -> Unit)? = null
) {

    val safeAreaModifier = if (safeArea) {
        Modifier.padding(20.dp)
    } else {
        Modifier
    }

    when (type) {
        ActionAreaType.Strong -> {
            WantedActionStrongAreaLayout(
                modifier = modifier.then(safeAreaModifier),
                positive = positive,
                negative = negative,
                neutral = neutral
            )
        }

        ActionAreaType.Neutral -> {
            WantedActionNeutralAreaLayout(
                modifier = modifier.then(safeAreaModifier),
                positive = positive,
                negative = negative,
                neutral = neutral
            )
        }

        ActionAreaType.Compact -> {
            WantedActionCompactAreaLayout(
                modifier = modifier.then(safeAreaModifier),
                positive = positive,
                neutral = neutral
            )
        }

        ActionAreaType.Single -> {
            WantedActionStrongAreaLayout(
                modifier = modifier.then(safeAreaModifier),
                positive = positive,
                negative = null,
                neutral = null
            )
        }
    }
}

@Composable
private fun WantedActionStrongAreaLayout(
    modifier: Modifier = Modifier,
    positive: @Composable () -> Unit,
    negative: @Composable (() -> Unit)? = null,
    neutral: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        positive()

        negative?.invoke()

        neutral?.invoke()
    }
}

@Composable
private fun WantedActionNeutralAreaLayout(
    modifier: Modifier = Modifier,
    positive: @Composable () -> Unit,
    negative: @Composable (() -> Unit)? = null,
    neutral: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .weight(119f)
                .wrapContentHeight()
        ) {
            neutral?.invoke()
        }

        Box(
            modifier = Modifier
                .weight(90f)
                .wrapContentHeight()
        ) {
            negative?.invoke()
        }

        Box(
            modifier = Modifier
                .weight(90f)
                .wrapContentHeight()
        ) {
            positive()
        }
    }
}

@Composable
private fun WantedActionCompactAreaLayout(
    modifier: Modifier = Modifier,
    positive: @Composable () -> Unit,
    neutral: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.End),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.weight(64f))

        Box(
            modifier = Modifier
                .weight(119f)
                .wrapContentHeight()
        ) {
            neutral?.invoke()
        }

        Box(
            modifier = Modifier
                .weight(119f)
                .wrapContentHeight()
        ) {
            positive()
        }
    }
}


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ko")
@Preview(
    "foldableLight",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ko",
    device = Devices.FOLDABLE
)
@Composable
private fun WantedActionAreaPreview() {
    DesignSystemTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedActionArea(
                    type = ActionAreaType.Strong,
                    positive = "메인 액션",
                    negative = "대체 액션",
                    neutral = "보조 액션",
                    onClickPositive = {},
                    onClickNegative = {},
                    onClickNeutral = {}
                )

                WantedActionArea(
                    type = ActionAreaType.Neutral,
                    positive = "메인 액션",
                    negative = "대체 액션",
                    neutral = "보조 액션",
                    onClickPositive = {},
                    onClickNegative = {},
                    onClickNeutral = {}
                )
                WantedActionArea(
                    type = ActionAreaType.Compact,
                    positive = "메인 액션",
                    negative = "대체 액션",
                    neutral = "보조 액션",
                    onClickPositive = {},
                    onClickNegative = {},
                    onClickNeutral = {}
                )
            }
        }
    }
}