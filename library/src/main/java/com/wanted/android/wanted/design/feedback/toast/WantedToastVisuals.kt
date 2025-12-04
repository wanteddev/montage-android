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
 *
 * @param message String: 토스트에 표시할 메시지입니다.
 * @param actionLabel String: 액션 버튼에 표시할 텍스트입니다.
 * @param duration SnackbarDuration: 토스트 노출 시간입니다.
 * @param withDismissAction Boolean: 닫기 액션 여부입니다.
 * @param variant WantedToastVariant: 메시지 타입을 나타내는 스타일 variant입니다.
 * @param icon (@Composable () -> Unit)?: 사용자 정의 아이콘 슬롯입니다.
 */
@Stable
class WantedToastVisuals(
    override val message: String,
    override val actionLabel: String = "",
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false,
    val variant: WantedToastVariant = WantedToastVariant.Message,
    val icon: @Composable (() -> Unit)? = null
) : SnackbarVisuals
