package com.wanted.android.wanted.design.presentation.modal.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.presentation.modal.WantedModalContract.ModalSize
import com.wanted.android.wanted.design.theme.DesignSystemTheme


@Composable
internal fun WantedDialogLayout(
    // 여백을 직접 넘기지 않는 레거시(WantedBottomSheetLayout)용 기본값이다. Popup·Bottom Sheet 는 padding 을 직접 넘긴다.
    modalSize: ModalSize = ModalSize.Medium,
    modifier: Modifier = Modifier,
    backgroundColor: Color = DesignSystemTheme.colors.surfaceElevatedPrimary,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    topBarPadding: PaddingValues = PaddingValues(
        vertical = modalSize.titleVerticalPadding,
        horizontal = modalSize.titleHorizontalPadding
    ),
    bottomBarPadding: PaddingValues = PaddingValues(modalSize.bottomBarPadding),
    contentFillHeight: Boolean = false,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    var topBarHeight by remember { mutableStateOf(0.dp) }
    val localDensity = LocalDensity.current

    Box(
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            topBar?.let {
                // topBar 공간 확보
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(topBarHeight)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = contentFillHeight)
            ) {
                content()
            }

            bottomBar?.let {
                Box(
                    modifier = Modifier
                        .padding(bottomBarPadding)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    bottomBar()
                }
            }
        }

        // topBar를 overlay로 배치 (gradient가 content 위에 그려질 수 있도록)
        topBar?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(topBarPadding)
                    .onGloballyPositioned { coordinates ->
                        topBarHeight = with(localDensity) { coordinates.size.height.toDp() }
                    }
            ) {
                topBar()
            }
        }
    }
}
