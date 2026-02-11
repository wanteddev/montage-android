package com.wanted.android.wanted.design.feedback.toast

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.wanted.android.wanted.design.theme.DesignSystemTheme


/**
 * object WantedGlobalToastManager
 *
 * 앱 전역에서 Toast를 관리하는 싱글톤 매니저입니다.
 *
 * Activity 생명주기를 추적하여 현재 활성화된 화면에 Toast를 표시하며,
 * 키보드 및 Navigation bar 높이에 맞춰 자동으로 패딩을 조정합니다.
 *
 * 사용 예시:
 * ```
 * // Application 클래스에서 초기화
 * WantedGlobalToastManager.initialize(application)
 *
 * // 토스트 표시
 * WantedGlobalToastManager.showToast(
 *     text = "저장되었습니다.",
 *     variant = WantedToastVariant.Positive
 * )
 * ```
 */
@SuppressLint("StaticFieldLeak")
object WantedGlobalToastManager {
    private var isInitialized: Boolean = false
    private var currentActivity: Activity? = null
    private var toastView: ComposeView? = null
    private var toastContent: @Composable (() -> Unit)? = null
    private val handler = Handler(Looper.getMainLooper())
    private val onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener =
        ViewTreeObserver.OnGlobalLayoutListener {
            addBottomPadding()
        }

    /**
     * fun initialize(...)
     *
     * Toast 매니저를 초기화합니다.
     *
     * Application 생명주기에 콜백을 등록하여 Activity 상태를 추적합니다.
     * 반드시 Application 클래스에서 한 번만 호출해야 합니다.
     *
     * @param application Application Application 인스턴스입니다.
     */
    fun initialize(application: Application) {
        unregisterLifecycle(application)
        registerLifecycle(application)
        isInitialized = true
    }

    private fun registerLifecycle(application: Application) {
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    private fun unregisterLifecycle(application: Application) {
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    private val activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            currentActivity = activity
        }

        override fun onActivityResumed(activity: Activity) {
            currentActivity = activity
            attachToastToActivity()
            attachLayoutChange()
        }

        override fun onActivityPaused(activity: Activity) {
            if (currentActivity == activity) {
                detachLayoutChange()
                removeToast()
                currentActivity = null
            }
        }

        override fun onActivityDestroyed(activity: Activity) = Unit
        override fun onActivityStarted(activity: Activity) = Unit
        override fun onActivityStopped(activity: Activity) = Unit
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit
    }

    /**
     *  fun showToast(...)
     *
     * 커스텀 컴포넌트 콘텐츠로 Toast를 표시합니다.
     *
     * 이전에 표시 중이던 Toast는 자동으로 취소되며, 지정된 시간 후에 자동으로 사라집니다.
     *
     * @param duration ToastDuration: 토스트가 표시될 시간입니다. 기본값은 Short(3초)입니다.
     * @param content (@Composable () -> Unit): 표시할 컴포넌트 콘텐츠입니다.
     */
    fun showToast(duration: ToastDuration = ToastDuration.Short, content: @Composable () -> Unit) {
        if (!isInitialized) return
        cancelPreviousToast()
        updateToastContent(content)
        attachToastToActivity()
        scheduleToastRemoval(duration)
    }

    private fun cancelPreviousToast() {
        handler.removeCallbacksAndMessages(null)
    }

    private fun updateToastContent(content: @Composable () -> Unit) {
        toastContent = content
    }

    private fun scheduleToastRemoval(duration: ToastDuration) {
        handler.postDelayed({
            finishToast()
        }, duration.mills)
    }

    private fun attachToastToActivity() {
        removeToastFromParent()
        createToastView()
        addToastToActivity()
    }

    /**
     * fun showToast(...)
     *
     * 텍스트와 스타일을 지정하여 Toast를 표시합니다.
     *
     * Variant를 통해 메시지 타입(긍정, 주의, 부정 등)을 지정할 수 있습니다.
     *
     * @param text String: 토스트에 표시할 메시지입니다.
     * @param duration ToastDuration: 토스트가 표시될 시간입니다. 기본값은 Short(3초)입니다.
     * @param padding PaddingValues: 토스트에 적용할 패딩입니다. 기본값은 하단 20dp입니다.
     * @param variant WantedToastVariant: 토스트 스타일입니다. 기본값은 Message입니다.
     * @param icon (@Composable () -> Unit)?: 사용자 정의 아이콘입니다.
     */
    fun showToast(
        text: String,
        duration: ToastDuration = ToastDuration.Short,
        padding: PaddingValues = PaddingValues(bottom = 20.dp),
        variant: WantedToastVariant = WantedToastVariant.Message,
        icon: @Composable (() -> Unit)? = null
    ) {
        showToast(duration) {
            DesignSystemTheme {
                WantedToastImpl(
                    text = text,
                    modifier = Modifier.padding(padding),
                    variant = variant,
                    icon = icon
                )
            }
        }
    }

    private fun createToastView() {
        val activity = currentActivity ?: return
        val content = toastContent ?: return
        if (toastView == null) {
            toastView = ComposeView(activity).apply {
                setContent(content)
            }
        } else {
            toastView?.setContent(content) // 내용 변경
        }
    }


    private fun addToastToActivity() {
        val rootView = getRootView() ?: return

        toastView?.let { view ->
            removeToastFromParent() // 기존 부모에서 제거
            if (view.parent != null) return // 중복 추가 방지
            addViewToRoot(rootView, view)
        }
    }

    private fun getRootView(): FrameLayout? {
        val activity = currentActivity ?: return null
        return activity.findViewById<ViewGroup>(android.R.id.content) as? FrameLayout
    }


    private fun addViewToRoot(rootView: FrameLayout, view: View) {
        rootView.addView(view, createLayoutParams())
    }

    private fun createLayoutParams(): FrameLayout.LayoutParams {
        return FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = android.view.Gravity.BOTTOM or android.view.Gravity.CENTER
        }
    }

    private fun attachLayoutChange() {
        val rootView = getRootView() ?: return
        rootView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    private fun detachLayoutChange() {
        val rootView = getRootView() ?: return
        rootView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }


    private fun removeToastFromParent() {
        toastView?.let { view ->
            (view.parent as? ViewGroup)?.removeView(view) // 부모에서 안전하게 제거
        }
    }

    private fun removeToast() {
        removeToastFromParent()
        toastView = null // 완전히 제거가 필요할 경우 null로 설정
    }

    private fun finishToast() {
        removeToast()
        toastContent = null
    }

    private fun addBottomPadding() {
        val activity = currentActivity ?: return
        val rootView = getRootView() ?: return
        toastView ?: return

        val navigationBarHeight = activity.getNavigationBarHeight()
        val navigationDiff = (navigationBarHeight - rootView.paddingBottom).coerceAtLeast(0)
        val bottomPadding = navigationDiff.coerceAtLeast(activity.imePadding())
        toastView?.setPadding(0, 0, 0, bottomPadding)
    }
}


/**
 * enum class ToastDuration
 *
 * 토스트가 화면에 표시될 시간을 정의합니다.
 *
 * - Short: 짧은 시간 (3초)입니다.
 * - Long: 긴 시간 (10초)입니다.
 */
enum class ToastDuration(val mills: Long) {
    Short(3000L),
    Long(10000L);
}

private fun Activity.getNavigationBarHeight(): Int {
    val insets = ViewCompat.getRootWindowInsets(window.decorView)
        ?.getInsets(WindowInsetsCompat.Type.navigationBars())
    val navigationBarHeight = insets?.bottom ?: 0
    return navigationBarHeight
}

private fun Activity.imePadding(): Int {
    val insets = ViewCompat.getRootWindowInsets(window.decorView)
        ?.getInsets(WindowInsetsCompat.Type.ime())
    val imeHeight = insets?.bottom ?: 0
    return imeHeight
}