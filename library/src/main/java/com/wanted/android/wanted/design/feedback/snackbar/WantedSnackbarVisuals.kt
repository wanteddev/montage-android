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
 * 기본 메시지 외에 부가 설명, 아이콘을 지정할 수 있습니다.
 * WantedSnackBar 는 message·description·actionLabel·icon 만 화면에 그리며, 스낵바 폭은 최대 420dp 입니다.
 *
 * @param message String: 스낵바에 표시될 기본 메시지입니다. 한 줄까지만 표시되고 넘으면 말줄임(…)되며, 빈 문자열이면 생략됩니다.
 * @param actionLabel String?: 버튼에 표시될 텍스트입니다. 한 줄까지만 표시되고, null 이거나 빈 문자열이면 버튼이 생략됩니다.
 * @param duration SnackbarDuration: 스낵바 노출 지속 시간입니다.
 * @param withDismissAction Boolean: SnackbarVisuals 계약을 맞추기 위한 값입니다. WantedSnackBar 는 닫기 버튼을 그리지 않으므로 효과가 없습니다.
 * @param description String: 추가 설명 텍스트입니다. 메시지 아래에 최대 두 줄까지 표시되고 넘으면 말줄임(…)되며, 빈 문자열이면 생략됩니다.
 * @param padding PaddingValues: 현재 WantedSnackBar 가 사용하지 않아 효과가 없습니다. 바깥 여백은 WantedSnackBar 의 modifier 로 더할 수 있으며, 기본 20dp 보다 줄일 수는 없습니다.
 * @param icon (@Composable () -> Unit)?: 스낵바 좌측에 표시할 아이콘입니다.
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
