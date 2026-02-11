package com.wanted.android.wanted.design.presentation.modal.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBar
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonType
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.clickOnce


@Composable
internal fun WantedDialogTwoButtonImpl(
    modifier: Modifier = Modifier,
    background: Color = DesignSystemTheme.colors.backgroundElevatedNormal,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    topBar: @Composable (() -> Unit)? = null,
    positiveButtonType: ButtonType = ButtonType.PRIMARY,
    negativeButtonType: ButtonType = ButtonType.PRIMARY,
    positive: String? = null,
    negative: String? = null,
    onClickPositive: (() -> Unit)? = null,
    onClickNegative: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    WantedDialogLayout(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(background),
        modalSize = ModalSize.Medium,
        shape = shape,
        topBar = topBar,
        content = {
            Box(modifier = Modifier.padding(horizontal = ModalSize.Medium.contentPadding)) {
                content()
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                onClickPositive?.let {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = positive.orEmpty(),
                        type = positiveButtonType,
                        onClick = {
                            onClickPositive()
                        }
                    )
                }

                onClickNegative?.let {
                    WantedButton(
                        modifier = Modifier.fillMaxWidth(),
                        variant = ButtonVariant.OUTLINED,
                        text = negative.orEmpty(),
                        type = negativeButtonType,
                        onClick = {
                            onClickNegative()
                        }
                    )
                }
            }
        }
    )
}


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Composable
private fun WantedDialogPreview() {
    DesignSystemTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DesignSystemTheme.colorsOpacity.backgroundElevatedNormalOpacity12)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            WantedDialogTwoButtonImpl(
                positive = "확인",
                onClickPositive = {},
                content = {
                    Text(text = "다이얼로그 내용")
                }
            )

            WantedDialogTwoButtonImpl(
                positive = "확인",
                negative = "취소",
                onClickPositive = {},
                onClickNegative = {},
                content = {

                    Text(text = "다이얼로그 내용")
                },
            )

            WantedDialogTwoButtonImpl(
                positive = "확인",
                negative = "취소",
                onClickPositive = {},
                onClickNegative = {},
                content = {

                    Text(text = "다이얼로그 내용")
                },
            )
        }
    }
}

@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Composable
private fun WantedDialogSizePreview() {
    DesignSystemTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DesignSystemTheme.colorsOpacity.backgroundElevatedNormalOpacity12)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            WantedDialogTwoButtonImpl(
                positive = "확인",
                onClickPositive = {},
                content = {
                    Text(text = "다이얼로그 내용")
                }
            )

            WantedDialogTwoButtonImpl(
                positive = "확인",
                onClickPositive = {},
                content = {

                    Text(text = "다이얼로그 내용")
                }
            )

            WantedDialogTwoButtonImpl(
                positive = "확인",
                onClickPositive = {},
                content = {

                    Text(text = "다이얼로그 내용")
                }
            )

            WantedDialogTwoButtonImpl(
                positive = "확인",
                onClickPositive = {},
                content = {

                    Text(text = "다이얼로그 내용")
                }
            )
        }
    }
}


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Composable
private fun WantedDialogScrollablePreview() {
    DesignSystemTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DesignSystemTheme.colorsOpacity.backgroundElevatedNormalOpacity12)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            WantedDialogTwoButtonImpl(
                positive = "확인",
                onClickPositive = {},
                content = {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(20.dp)
                    ) {
                        items(10) {
                            Text(text = "다이얼로그 내용")
                        }
                    }
                }
            )
        }
    }
}


@Preview("light", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ko")
@Composable
private fun WantedDialogTopBarPreview() {
    DesignSystemTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DesignSystemTheme.colorsOpacity.backgroundElevatedNormalOpacity12)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            WantedDialogTwoButtonImpl(
                topBar = {
                    WantedTopAppBar(
                        title = { Text(text = "다이얼로그 타이틀") },
                    )
                },
                positive = "확인",
                onClickPositive = {},
                content = {
                    Text(text = "다이얼로그 내용")
                }
            )

            WantedDialogTwoButtonImpl(
                topBar = {
                    WantedTopAppBar(
                        title = { Text(text = "다이얼로그 타이틀") },
                        actions = {
                            Icon(
                                modifier = Modifier.clickOnce { },
                                painter = painterResource(id = R.drawable.icon_normal_close),
                                contentDescription = ""
                            )
                        }
                    )
                },
                positive = "확인",
                onClickPositive = {},
                content = {
                    Text(text = "다이얼로그 내용")
                }
            )

            WantedDialogTwoButtonImpl(
                topBar = {
                    WantedTopAppBar(
                        navigationIcon = {
                            Icon(
                                modifier = Modifier.clickOnce { },
                                painter = painterResource(id = R.drawable.icon_normal_arrow_left),
                                contentDescription = ""
                            )
                        },
                        title = { Text(text = "다이얼로그 타이틀") },
                    )
                },
                positive = "확인",
                onClickPositive = {},
                content = {
                    Text(text = "다이얼로그 내용")
                }
            )
        }
    }
}

