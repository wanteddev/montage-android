package com.wanted.android.wanted.design.feedback

import androidx.compose.material3.SnackbarHostState
import com.wanted.android.wanted.design.feedback.snackbar.WantedSnackbarVisuals
import com.wanted.android.wanted.design.feedback.toast.WantedToastVariant
import com.wanted.android.wanted.design.feedback.toast.WantedToastVisuals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * fun SnackbarHostState.showSnackbar(...)
 *
 * Wanted 스타일의 스낵바를 표시하는 확장 함수입니다.
 *
 * WantedSnackbarVisuals를 사용하여 스낵바를 표시하며,
 * 이미 표시 중인 스낵바가 있다면 자동으로 닫고 새로운 스낵바를 표시합니다.
 *
 * 사용 예시:
 * ```
 * val snackbarHostState = remember { SnackbarHostState() }
 * val scope = rememberCoroutineScope()
 *
 * snackbarHostState.showSnackbar(
 *     scope = scope,
 *     message = "저장되었습니다."
 * )
 * ```
 *
 * @param scope CoroutineScope 코루틴을 실행할 스코프입니다.
 * @param message String 스낵바에 표시할 메시지입니다.
 */
fun SnackbarHostState.showSnackbar(
    scope: CoroutineScope,
    message: String
) {
    scope.launch {
        currentSnackbarData?.dismiss()
        showSnackbar(
            visuals = WantedSnackbarVisuals(message = message)
        )
    }
}

/**
 * fun SnackbarHostState.showToast(...)
 *
 * Wanted 스타일의 토스트를 표시하는 확장 함수입니다.
 *
 * WantedToastVisuals를 사용하여 토스트를 표시하며,
 * variant를 통해 메시지 타입(긍정, 주의, 부정 등)을 지정할 수 있습니다.
 * 이미 표시 중인 토스트가 있다면 자동으로 닫고 새로운 토스트를 표시합니다.
 *
 * 사용 예시:
 * ```
 * val snackbarHostState = remember { SnackbarHostState() }
 * val scope = rememberCoroutineScope()
 *
 * snackbarHostState.showToast(
 *     scope = scope,
 *     message = "저장되었습니다.",
 *     variant = WantedToastVariant.Positive
 * )
 * ```
 *
 * @param scope CoroutineScope 코루틴을 실행할 스코프입니다.
 * @param message String 토스트에 표시할 메시지입니다.
 * @param variant WantedToastVariant 토스트 스타일입니다. 기본값은 Message입니다.
 */
fun SnackbarHostState.showToast(
    scope: CoroutineScope,
    message: String,
    variant: WantedToastVariant = WantedToastVariant.Message,
) {
    scope.launch {
        currentSnackbarData?.dismiss()
        showSnackbar(
            visuals = WantedToastVisuals(
                message = message,
                variant = variant
            )
        )
    }
}