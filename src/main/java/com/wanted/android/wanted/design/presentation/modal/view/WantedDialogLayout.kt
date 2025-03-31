package com.wanted.android.wanted.design.presentation.modal.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalSize


@Composable
fun WantedDialogLayout(
    modifier: Modifier = Modifier,
    backgroundColor: Color = colorResource(R.color.background_elevated_normal),
    modalSize: ModalSize,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    bottomBar: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /**
         * topBar
         */
        topBar?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = modalSize.titleVerticalPadding)
                    .padding(horizontal = modalSize.titleHorizontalPadding)
            ) {
                topBar()
            }

        }

        /**
         * content
         */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false)
        ) {
            content()
        }

        /**
         * bottomBar
         */
        bottomBar?.let {
            // bottomBar
            Box(
                modifier = Modifier
                    .padding(modalSize.bottomBarPadding)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                bottomBar()
            }
        }
    }
}
