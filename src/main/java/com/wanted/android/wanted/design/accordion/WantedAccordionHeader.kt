package com.wanted.android.wanted.design.accordion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.DevicePreviews
import com.wanted.android.wanted.design.accordion.WantedAccordionContract.VerticalPadding
import com.wanted.android.wanted.design.button.clickOnceForDesignSystem
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle


@Composable
internal fun WantedAccordionHeader(
    modifier: Modifier = Modifier,
    verticalPadding: VerticalPadding,
    title: String,
    style: TextStyle,
    fillWidth: Boolean,
    trail: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickOnceForDesignSystem(onClick = onClick)
            .wrapContentSize()
            .padding(vertical = verticalPadding.value)
            .padding(horizontal = if (fillWidth) 20.dp else 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = style,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        trail()
    }
}

@DevicePreviews
@Composable
private fun AccordionHeaderPreview() {
    DesignSystemTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                WantedAccordionHeader(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalPadding = VerticalPadding.Padding12,
                    title = "제목ㅁㄴ ㅏ이ㅓㅗ ㅁ나ㅣㅓㅇ롸ㅣㅁ 너ㅗㅇ 라ㅣ머농 라ㅣㅓㅁ노 ㅇ리ㅏㅓㅗㅁㄴ이ㅏ 러ㅗㅁ나ㅣㅓㅇ 로",
                    style = WantedTextStyle(
                        colorRes = R.color.label_normal,
                        style = DesignSystemTheme.typography.body2Bold
                    ),
                    fillWidth = false,
                    trail = {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = if (true) {
                                painterResource(R.drawable.ic_normal_chevron_up_svg)
                            } else {
                                painterResource(R.drawable.ic_normal_chevron_down_svg)
                            },
                            tint = colorResource(R.color.label_normal),
                            contentDescription = ""
                        )
                    },
                    onClick = {
                    }
                )
            }

        }
    }
}