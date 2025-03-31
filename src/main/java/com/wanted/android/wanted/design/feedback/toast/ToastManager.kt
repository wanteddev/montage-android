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


@SuppressLint("StaticFieldLeak")
object ToastManager {
    private var isInitialized: Boolean = false
    private var currentActivity: Activity? = null
    private var toastView: ComposeView? = null
    private var toastContent: @Composable (() -> Unit)? = null
    private val handler = Handler(Looper.getMainLooper())
    private val onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener =
        ViewTreeObserver.OnGlobalLayoutListener {
            addBottomPadding()
        }

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

    fun showToast(
        text: String,
        duration: ToastDuration = ToastDuration.Short,
        padding: PaddingValues = PaddingValues(bottom = 20.dp),
        variant: WantedToastVariant = WantedToastVariant.Message,
        icon: @Composable (() -> Unit)? = null
    ) {
        showToast(duration) {
            WantedToast(
                text = text,
                modifier = Modifier.padding(padding),
                variant = variant,
                icon = icon
            )
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


enum class ToastDuration(val mills: kotlin.Long) {
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