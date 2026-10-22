package com.wanted.android.wanted.design.feedback.toast

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable


/**
 * class WantedToastVisuals
 *
 * 커스텀 Toast를 위한 시각적 구성 클래스입니다.
 *
 * 메시지, 아이콘, variant 설정 등을 포함하며 SnackbarVisuals를 확장합니다.
 * WantedToast 는 message·variant·icon 만 화면에 그리고, 토스트 폭은 최대 420dp 입니다.
 *
 * @param message String: 토스트에 표시할 메시지입니다. 최대 두 줄까지 표시되고 넘으면 말줄임(…)됩니다.
 * @param actionLabel String: SnackbarVisuals 계약을 맞추기 위한 값입니다. Toast 는 액션 버튼을 그리지 않으므로 화면에 표시되지 않습니다.
 * @param duration SnackbarDuration: 토스트 노출 시간입니다.
 * @param withDismissAction Boolean: SnackbarVisuals 계약을 맞추기 위한 값입니다. Toast 는 닫기 버튼을 그리지 않으므로 효과가 없습니다.
 * @param variant WantedToastVariant: 메시지 타입을 나타내는 스타일 variant입니다.
 * @param icon (@Composable () -> Unit)?: 사용자 정의 아이콘 슬롯입니다. variant 가 Normal(deprecated Message 포함)일 때만 표시되고, 그 외 variant 는 variant 고유 아이콘이 사용되어 무시됩니다.
 */
@Stable
class WantedToastVisuals(
    override val message: String,
    override val actionLabel: String = "",
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false,
    val variant: WantedToastVariant = WantedToastVariant.Normal,
    val icon: @Composable (() -> Unit)? = null
) : SnackbarVisuals
