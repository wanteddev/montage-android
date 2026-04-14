package com.wanted.android.montage.sample.feedback.alert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wanted.android.montage.sample.ui.DevicePreviews
import com.wanted.android.designsystem.R
import com.wanted.android.montage.sample.feedback.alert.DSWantedAlertDemoScreenContract.DSWantedAlertDemoEvent
import com.wanted.android.montage.sample.feedback.alert.DSWantedAlertDemoScreenContract.DSWantedAlertDemoViewEvent
import com.wanted.android.montage.sample.feedback.alert.DSWantedAlertDemoScreenContract.DSWantedAlertDemoViewState
import com.wanted.android.montage.sample.ui.DSWantedPreviewContainer
import com.wanted.android.wanted.design.actions.button.WantedButton
import com.wanted.android.wanted.design.feedback.alert.WantedAlert
import com.wanted.android.montage.sample.ui.WantedBackTopAppBar
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.ButtonVariant
import com.wanted.android.wanted.design.util.WantedTextStyle

@Composable
fun DSWantedAlertDemoScreen(
	modifier: Modifier = Modifier,
	viewModel: DSWantedAlertDemoViewModel = hiltViewModel(),
	onClickBack: () -> Unit
) {
	val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

	DSWantedAlertDemoScreenContent(
		modifier = modifier,
		viewState = viewState,
		onViewEvent = { viewEvent ->
			when (viewEvent) {
				DSWantedAlertDemoViewEvent.OnClickBack -> onClickBack()
				DSWantedAlertDemoViewEvent.OnClickShowAlert -> {
					viewModel.setEvent(DSWantedAlertDemoEvent.ShowAlert)
				}
				DSWantedAlertDemoViewEvent.OnDismissAlert -> {
					viewModel.setEvent(DSWantedAlertDemoEvent.DismissAlert)
				}
				DSWantedAlertDemoViewEvent.OnClickConfirm -> {
					viewModel.setEvent(DSWantedAlertDemoEvent.DismissAlert)
				}
				DSWantedAlertDemoViewEvent.OnClickCancel -> {
					viewModel.setEvent(DSWantedAlertDemoEvent.DismissAlert)
				}
				DSWantedAlertDemoViewEvent.OnClickToggleTitle -> {
					viewModel.setEvent(DSWantedAlertDemoEvent.ToggleTitle)
				}
				DSWantedAlertDemoViewEvent.OnClickToggleNegative -> {
					viewModel.setEvent(DSWantedAlertDemoEvent.ToggleNegative)
				}
				DSWantedAlertDemoViewEvent.OnClickToggleCancel -> {
					viewModel.setEvent(DSWantedAlertDemoEvent.ToggleCancel)
				}
			}
		}
	)
}

@Composable
private fun DSWantedAlertDemoScreenContent(
	modifier: Modifier = Modifier,
	viewState: DSWantedAlertDemoViewState,
	onViewEvent: (DSWantedAlertDemoViewEvent) -> Unit
) {
	if (viewState.isShowAlert) {
		WantedAlert(
			title = if (viewState.hasTitle) "알림 제목" else null,
			message = "알림 메시지 내용입니다.",
			confirm = "확인",
			cancel = if (viewState.hasCancel) "취소" else null,
			isNegative = viewState.isNegative,
			onClickConfirm = { onViewEvent(DSWantedAlertDemoViewEvent.OnClickConfirm) },
			onClickCancel = { onViewEvent(DSWantedAlertDemoViewEvent.OnClickCancel) },
			onDismissRequest = { onViewEvent(DSWantedAlertDemoViewEvent.OnDismissAlert) }
		)
	}

	Scaffold(
		modifier = modifier,
		topBar = {
			WantedBackTopAppBar(title = "WantedAlert") {
				onViewEvent(DSWantedAlertDemoViewEvent.OnClickBack)
			}
		}
	) { innerPadding ->
		DSWantedAlertDemoScreenLayout(
			modifier = Modifier.padding(innerPadding),
			viewState = viewState,
			onViewEvent = onViewEvent
		)
	}
}

@Composable
private fun DSWantedAlertDemoScreenLayout(
	modifier: Modifier = Modifier,
	viewState: DSWantedAlertDemoViewState,
	onViewEvent: (DSWantedAlertDemoViewEvent) -> Unit
) {
	Column(
		modifier = modifier.padding(horizontal = 20.dp),
		verticalArrangement = Arrangement.spacedBy(10.dp)
	) {
		Text(
			text = "Preview",
			style = WantedTextStyle(
				colorRes = R.color.label_strong,
				style = DesignSystemTheme.typography.heading2Bold
			)
		)

		DSWantedPreviewContainer {
			WantedButton(
				modifier = Modifier.fillMaxWidth(),
				text = "Alert 보기",
				variant = ButtonVariant.SOLID,
				onClick = { onViewEvent(DSWantedAlertDemoViewEvent.OnClickShowAlert) }
			)
		}

		Spacer(Modifier.size(10.dp))

		Column(
			modifier = Modifier
				.verticalScroll(rememberScrollState())
				.padding(vertical = 24.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Text(
				modifier = Modifier.align(Alignment.Start),
				text = "Option",
				style = WantedTextStyle(
					colorRes = R.color.label_strong,
					style = DesignSystemTheme.typography.heading2Bold
				)
			)

			WantedButton(
				modifier = Modifier.fillMaxWidth(),
				text = "제목 표시",
				variant = if (viewState.hasTitle) ButtonVariant.SOLID else ButtonVariant.OUTLINED,
				onClick = { onViewEvent(DSWantedAlertDemoViewEvent.OnClickToggleTitle) }
			)

			WantedButton(
				modifier = Modifier.fillMaxWidth(),
				text = "Negative 스타일",
				variant = if (viewState.isNegative) ButtonVariant.SOLID else ButtonVariant.OUTLINED,
				onClick = { onViewEvent(DSWantedAlertDemoViewEvent.OnClickToggleNegative) }
			)

			WantedButton(
				modifier = Modifier.fillMaxWidth(),
				text = "취소 버튼 표시",
				variant = if (viewState.hasCancel) ButtonVariant.SOLID else ButtonVariant.OUTLINED,
				onClick = { onViewEvent(DSWantedAlertDemoViewEvent.OnClickToggleCancel) }
			)
		}
	}
}

@DevicePreviews
@Composable
private fun DSWantedAlertDemoScreenPreview() {
	DesignSystemTheme {
		DSWantedAlertDemoScreenContent(
			viewState = DSWantedAlertDemoViewState(),
			onViewEvent = {}
		)
	}
}
