package com.wanted.android.wanted.design.contents.sectionheader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.contents.sectionheader.WantedSectionHeaderDefaults.Size
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.DevicePreviews
import com.wanted.android.wanted.design.util.WantedTextStyle

/**
 * 섹션 타이틀 및 부가 아이콘, 행동 요소를 포함하는 섹션 헤더 컴포저블입니다.
 *
 * 다양한 크기 옵션과 좌측/우측 컴포저블 삽입을 통해 유연하게 커스터마이징이 가능하며,
 * 일반적으로 리스트, 섹션 구분, 콘텐츠 제목 등에 사용됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * WantedSectionHeader(
 *     title = "제목",
 *     size = Size.Medium,
 *     headingContents = {
 *         Icon(painter = painterResource(R.drawable.ic_info), contentDescription = null)
 *     },
 *     trailingContent = {
 *         Icon(painter = painterResource(R.drawable.ic_more), contentDescription = null)
 *     },
 *     modifier = Modifier
 * )
 * ```
 *
 * @param title String: 섹션 제목으로 표시되는 텍스트입니다.
 * @param modifier Modifier: 외형, 정렬, 배치를 제어하는 Modifier입니다.
 * @param size Size: 헤더의 텍스트 스타일 크기를 결정합니다 (XSmall, Small, Medium, Large).
 * @param textStyle TextStyle?: 커스텀 텍스트 스타일을 지정할 수 있습니다. 기본값은 크기에 따라 자동 설정됩니다.
 * @param headingContents (@Composable () -> Unit)?: 제목 오른쪽에 배치될 부가 정보 아이콘 등 컴포저블 콘텐츠입니다.
 * @param trailingContent (@Composable RowScope.() -> Unit)?: 오른쪽 끝에 배치되는 행동 버튼 또는 아이콘입니다.
 */
@Composable
fun WantedSectionHeader(
    title: String,
    modifier: Modifier,
    size: Size = Size.Medium,
    textStyle: TextStyle? = null,
    headingContents: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (RowScope.() -> Unit)? = null
) {
    val density = LocalDensity.current
    val style = textStyle ?: run {
        WantedTextStyle(
            colorRes = if (size == Size.XSmall) {
                R.color.label_alternative
            } else {
                R.color.label_strong
            },
            style = when (size) {
                Size.Large -> DesignSystemTheme.typography.title3Bold
                Size.Medium -> DesignSystemTheme.typography.heading2Bold
                Size.Small -> DesignSystemTheme.typography.headline2Bold
                Size.XSmall -> DesignSystemTheme.typography.label1Bold
            }
        )
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f, false),
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = if (headingContents != null) 1 else Int.MAX_VALUE,
                style = style
            )

            headingContents?.let {
                Box(
                    modifier = Modifier.height(with(density) { style.lineHeight.toDp() }),
                    contentAlignment = Alignment.BottomStart
                ) {
                    headingContents()
                }
            }
        }

        trailingContent?.let {
            Row(
                modifier = Modifier.height(with(density) { style.lineHeight.toDp() }),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                trailingContent.invoke(this)
            }
        }
    }
}

@DevicePreviews
@Composable
private fun WantedSectionHeaderPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목",
                    headingContents = {
                        Icon(
                            painter = painterResource(R.drawable.icon_normal_circle_exclamation_fill),
                            contentDescription = ""
                        )
                    },
                    trailingContent = {
                        Icon(
                            painter = painterResource(R.drawable.icon_normal_circle_exclamation_fill),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목",
                )


                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목",
                    headingContents = {
                        Icon(
                            painter = painterResource(R.drawable.icon_normal_circle_exclamation_fill),
                            contentDescription = ""
                        )
                    },
                    trailingContent = {
                        Icon(
                            painter = painterResource(R.drawable.icon_normal_circle_exclamation_fill),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목제목",
                    trailingContent = {
                        Icon(
                            painter = painterResource(R.drawable.icon_normal_circle_exclamation_fill),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목",
                    trailingContent = {
                        Icon(
                            modifier = Modifier,
                            painter = painterResource(R.drawable.icon_normal_circle_exclamation_fill),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목",
                    size = Size.XSmall,
                    headingContents = {
                        Icon(
                            painter = painterResource(R.drawable.icon_normal_circle_exclamation_fill),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목",
                    size = Size.Small,
                    headingContents = {
                        Icon(
                            painter = painterResource(R.drawable.icon_normal_circle_exclamation_fill),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목",
                    size = Size.Medium,
                    headingContents = {
                        Icon(
                            painter = painterResource(R.drawable.icon_normal_circle_exclamation_fill),
                            contentDescription = ""
                        )
                    }
                )

                WantedSectionHeader(
                    modifier = Modifier,
                    title = "제목",
                    size = Size.Large,
                    headingContents = {
                        Icon(
                            painter = painterResource(R.drawable.icon_normal_circle_exclamation_fill),
                            contentDescription = ""
                        )
                    }
                )

            }
        }
    }
}