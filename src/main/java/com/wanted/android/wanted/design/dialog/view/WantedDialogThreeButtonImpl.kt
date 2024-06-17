package com.wanted.android.wanted.design.dialog.view

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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.beta.topbar.CustomTopAppBar
import com.wanted.android.wanted.design.button.WantedOutlinedButton
import com.wanted.android.wanted.design.button.WantedSolidButton
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.dialog.ModalSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonType


@Composable
fun WantedDialogThreeButtonImpl(
    modifier: Modifier = Modifier,
    modalSize: ModalSize,
    topBar: @Composable (() -> Unit)? = null,
    positive: String? = null,
    natural: String? = null,
    negative: String? = null,
    onClickPositive: (() -> Unit)? = null,
    onClickNatural: (() -> Unit)? = null,
    onClickNegative: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    WantedDialogLayout(
        modifier = modifier,
        modalSize = modalSize,
        topBar = topBar,
        content = {
            Box(modifier = Modifier.padding(horizontal = modalSize.contentPadding)) {
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
                    WantedSolidButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = positive.orEmpty(),
                        clickListener = {
                            onClickPositive()
                        }
                    )
                }

                onClickNatural?.let {
                    WantedOutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = natural.orEmpty(),
                        type = ButtonType.ASSISTIVE,
                        clickListener = {
                            onClickNatural()
                        }
                    )
                }

                onClickNegative?.let {
                    WantedOutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = negative.orEmpty(),
                        type = ButtonType.SECONDARY,
                        clickListener = {
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
                .background(colorResource(id = R.color.background_elevated_normal_opacity12))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            WantedDialogThreeButtonImpl(
                modifier = Modifier,
                modalSize = ModalSize.Normal,
                positive = "확인",
                onClickPositive = {}
            ) {
                Text(text = "다이얼로그 내용")
            }

            WantedDialogThreeButtonImpl(
                modifier = Modifier,
                modalSize = ModalSize.Normal,
                positive = "확인",
                negative = "취소",
                onClickPositive = {},
                onClickNegative = {},
            ) {

                Text(text = "다이얼로그 내용")
            }

            WantedDialogThreeButtonImpl(
                modifier = Modifier,
                modalSize = ModalSize.Normal,
                positive = "확인",
                natural = "중립",
                negative = "취소",
                onClickPositive = {},
                onClickNatural = {},
                onClickNegative = {},
            ) {

                Text(text = "다이얼로그 내용")
            }
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
                .background(colorResource(id = R.color.background_elevated_normal_opacity12))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            WantedDialogThreeButtonImpl(
                modifier = Modifier,
                modalSize = ModalSize.Small,
                positive = "확인",
                onClickPositive = {}
            ) {
                Text(text = "다이얼로그 내용")
            }

            WantedDialogThreeButtonImpl(
                modifier = Modifier,
                modalSize = ModalSize.Normal,
                positive = "확인",
                onClickPositive = {}
            ) {

                Text(text = "다이얼로그 내용")
            }

            WantedDialogThreeButtonImpl(
                modifier = Modifier,
                modalSize = ModalSize.Medium,
                positive = "확인",
                onClickPositive = {}
            ) {

                Text(text = "다이얼로그 내용")
            }

            WantedDialogThreeButtonImpl(
                modifier = Modifier,
                modalSize = ModalSize.Large,
                positive = "확인",
                onClickPositive = {}
            ) {

                Text(text = "다이얼로그 내용")
            }
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
                .background(colorResource(id = R.color.background_elevated_normal_opacity12))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            WantedDialogThreeButtonImpl(
                modifier = Modifier,
                modalSize = ModalSize.Normal,
                positive = "확인",
                onClickPositive = {}
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(20.dp)
                ) {
                    items(10) {
                        Text(text = "다이얼로그 내용")
                    }
                }
            }
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
                .background(colorResource(id = R.color.background_elevated_normal_opacity12))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            WantedDialogThreeButtonImpl(
                modifier = Modifier,
                modalSize = ModalSize.Normal,
                positive = "확인",
                topBar = {
                    CustomTopAppBar(
                        title = { Text(text = "다이얼로그 타이틀") },
                    )
                },
                onClickPositive = {}
            ) {
                Text(text = "다이얼로그 내용")
            }

            WantedDialogThreeButtonImpl(
                modifier = Modifier,
                modalSize = ModalSize.Normal,
                positive = "확인",
                topBar = {
                    CustomTopAppBar(
                        title = { Text(text = "다이얼로그 타이틀") },
                        actions = {
                            Icon(
                                modifier = Modifier.clickOnceForDesignSystem { },
                                painter = painterResource(id = R.drawable.ic_normal_close_svg),
                                contentDescription = ""
                            )
                        }
                    )
                },
                onClickPositive = {}
            ) {
                Text(text = "다이얼로그 내용")
            }

            WantedDialogThreeButtonImpl(
                modifier = Modifier,
                modalSize = ModalSize.Normal,
                positive = "확인",
                topBar = {
                    CustomTopAppBar(
                        navigationIcon = {
                            Icon(
                                modifier = Modifier.clickOnceForDesignSystem { },
                                painter = painterResource(id = R.drawable.ic_normal_arrow_left_svg),
                                contentDescription = ""
                            )
                        },
                        title = { Text(text = "다이얼로그 타이틀") },
                    )
                },
                onClickPositive = {}
            ) {
                Text(text = "다이얼로그 내용")
            }
        }
    }
}

