package com.wanted.android.wanted.design.feedback.alert

/**
 * [WantedAlert] 버튼의 testTag.
 *
 * 소비자는 E2E(Maestro, `maestro/`)·UiAutomator 다. 버튼(`WantedTextButton`) 글자는 터치 레이어에 가려
 * 접근성 트리에 노출되지 않아 텍스트로는 찾을 수 없다. [WantedAlert] 가 다이얼로그 윈도우 안에서
 * `testTagsAsResourceId` 를 켜 두므로 이 값이 그대로 resource-id 가 된다.
 *
 * 다이얼로그는 한 번에 하나만 뜨므로 모든 [WantedAlert] 가 같은 태그를 쓴다.
 */
object WantedAlertTestTags {
    /** 확인(positive) 버튼. */
    const val CONFIRM = "wanted_alert_confirm"

    /** 취소(negative) 버튼. `cancel` 을 넘긴 경우에만 존재한다. */
    const val CANCEL = "wanted_alert_cancel"
}
