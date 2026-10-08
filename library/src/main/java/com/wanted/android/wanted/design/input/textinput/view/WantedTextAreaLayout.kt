package com.wanted.android.wanted.design.input.textinput.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
internal fun WantedTextAreaLayout(
    modifier: Modifier = Modifier,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    textField: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        textField()

        if (leadingContent != null || trailingContent != null) {
            Row(
                modifier = Modifier.fillMaxWidth(1f),
                verticalAlignment = Alignment.CenterVertically,
                // leadingContent 가 없을 때 trailingContent(버튼)가 좌측에 붙지 않도록 우측 정렬한다.
                // leadingContent 가 있으면 그쪽이 weight 로 남는 폭을 채우므로 정렬 기준은 영향이 없다.
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
            ) {
                leadingContent?.let {
                    // wrapContentSize() 기본값은 가운데 정렬이라, leadingContent 만 있을 때
                    // 콘텐츠가 폭 가운데에 놓인다. 좌측 기준으로 정렬한다.
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentSize(Alignment.CenterStart)
                    ) {
                        leadingContent()
                    }
                }

                trailingContent?.let {
                    Box(modifier = Modifier.wrapContentSize()) {
                        trailingContent()
                    }
                }
            }
        }
    }
}
