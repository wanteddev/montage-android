package com.wanted.android.wanted.design.input.textinput.textarea

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.util.ButtonSize

/**
 * object WantedTextAreaDefaults
 *
 * TextArea 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
 *
 * Size와 Resize 열거형을 포함하며, 각 크기 및 리사이즈 동작에 따른 토큰을 제공합니다.
 */
object WantedTextAreaDefaults {

    /** 기본 최소 행 수입니다. 텍스트 2줄이 보이는 높이가 기본 높이가 됩니다. */
    const val DEFAULT_MIN_LINE = 2

    /** 기본 최대 행 수입니다. Resize.Limit 에서 이 행 수를 최대 높이로 삼습니다. */
    const val DEFAULT_MAX_LINE = 6

    /**
     * enum class Status
     *
     * TextArea의 상태를 정의하는 enum 클래스입니다. (WantedTextField 의 Status 와 동일한 축)
     * - Normal: 일반 상태입니다.
     * - Negative: 부정(에러) 상태입니다.
     */
    enum class Status {
        Normal,
        Negative
    }

    /**
     * enum class Size
     *
     * TextArea의 크기를 정의하는 enum 클래스입니다.
     * - Large: 큰 크기입니다. (콘텐츠 최소 높이 48dp, body2Regular)
     * - Medium: 중간 크기입니다. (콘텐츠 최소 높이 44dp, label1Regular)
     */
    enum class Size {
        Large,
        Medium
    }

    /**
     * enum class Resize
     *
     * TextArea의 높이 확장 동작을 정의하는 enum 클래스입니다.
     * - Normal: 사용자 입력에 따라 자유롭게 높이 확장합니다. (최소 높이만 보장)
     * - Limit: maxLines 설정값 이상으로 확장하지 않습니다. (스크롤 전환)
     * - Fixed: 높이가 고정됩니다. (최소 높이 = 최대 높이, 스크롤 전환)
     */
    enum class Resize {
        Normal,
        Limit,
        Fixed
    }
}

internal val WantedTextAreaDefaults.Size.borderRadius: Dp
    get() = when (this) {
        WantedTextAreaDefaults.Size.Large -> 14.dp
        WantedTextAreaDefaults.Size.Medium -> 12.dp
    }


internal val WantedTextAreaDefaults.Size.contentPaddingX: Dp
    get() = when (this) {
        WantedTextAreaDefaults.Size.Large -> 4.dp
        WantedTextAreaDefaults.Size.Medium -> 4.dp
    }

internal val WantedTextAreaDefaults.Size.contentMinHeight: Dp
    get() = when (this) {
        WantedTextAreaDefaults.Size.Large -> 48.dp
        WantedTextAreaDefaults.Size.Medium -> 44.dp
    }

// Size별 하단 버튼 크기 토큰입니다. (Large: 높이 32, Medium: 높이 28)
internal val WantedTextAreaDefaults.Size.buttonSize: ButtonSize
    get() = when (this) {
        WantedTextAreaDefaults.Size.Large -> ButtonSize.SMALL
        WantedTextAreaDefaults.Size.Medium -> ButtonSize.XSMALL
    }
