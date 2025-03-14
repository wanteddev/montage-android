package com.wanted.android.wanted.design.feedback.pushbadge

class PushBadgeContract {
    enum class PushBadgeVariant {
        Dot,
        Number,
        New
    }

    enum class PushBadgeSize {
        XSmall,
        Small,
        Medium
    }

    enum class PushBadgePosition {
        TopStart,
        TopCenter,
        TopEnd,
        MiddleStart,
        MiddleCenter,
        MiddleEnd,
        BottomStart,
        BottomCenter,
        BottomEnd
    }
}