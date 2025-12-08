package com.wanted.android.wanted.design.feedback.snackbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

/**
 * WantedSnackbarVisuals
 *
 * Wanted 전용 Snackbar 메시지와 UI 요소를 지정하는 클래스입니다.
 *
 * 기본 메시지 외에 부가 설명, 아이콘, 패딩 등을 커스터마이징할 수 있습니다.
 *
 * @param message String 스낵바에 표시될 기본 메시지입니다.
 * @param actionLabel String? 버튼에 표시될 텍스트입니다.
 * @param duration SnackbarDuration 스낵바 노출 지속 시간입니다.
 * @param withDismissAction Boolean 닫기 액션 포함 여부입니다.
 * @param description String 추가 설명 텍스트입니다.
 * @param padding PaddingValues 스낵바 콘텐츠에 적용될 패딩입니다.
 * @param icon (@Composable () -> Unit)? 스낵바 좌측에 표시할 아이콘입니다.
 */
@Stable
class WantedSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val withDismissAction: Boolean = false,
    val description: String = "",
    val padding: PaddingValues = PaddingValues(),
    val icon: @Composable (() -> Unit)? = null,
) : SnackbarVisuals
