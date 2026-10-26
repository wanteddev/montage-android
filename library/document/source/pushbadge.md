/**
* object PushBadgeTypes
* Push badge Type 클래스입니다.
*
* 이 클래스는 Push badge의 표시 유형, 크기, 위치를 정의하는 열거형 클래스들을 포함합니다.
*/

    /**
* enum class PushBadgeVariant
*
* Push 배지의 표시 유형을 정의합니다.
*
* - Dot: 작은 점 형태의 배지를 표시합니다.
* - Text: 임의의 문자열(숫자·"N" 등)을 그대로 표시합니다.
* - MaxCount: 개수를 표시하되 maxCount 초과 시 "maxCount+"(예: 99+)로 표기합니다.
*/

    /**
* enum class PushBadgeSize
*
* Push 배지의 크기를 정의합니다.
*
* - XSmall: 가장 작은 배지입니다(점 4dp, 텍스트 caption2Bold).
* - Small: 중간 크기 배지입니다(점 6dp, 텍스트는 XSmall 과 같은 caption2Bold 이고 여백만 큽니다).
* - Medium: 가장 큰 배지입니다(점 8dp, 텍스트 label1Bold).
*/

    /**
* enum class PushBadgePosition
*
* Push 배지가 놓인 자리에서 어느 방향으로 걸쳐 나갈지 정의하는 열거형 클래스입니다.
*
* 대상의 해당 모서리로 옮기지는 않습니다. 대상 모서리에 붙이려면 배지 modifier 에 같은 방향의 정렬(예: Modifier.align(Alignment.TopEnd))을 함께 지정합니다.
*
* - TopStart: 배지를 자기 크기의 절반만큼 위·시작 방향으로 걸쳐 나가게 합니다.
* - TopCenter: 배지를 자기 크기의 절반만큼 위 방향으로 걸쳐 나가게 합니다.
* - TopEnd: 배지를 자기 크기의 절반만큼 위·끝 방향으로 걸쳐 나가게 합니다.
* - MiddleStart: 배지를 자기 크기의 절반만큼 시작 방향으로 걸쳐 나가게 합니다.
* - MiddleCenter: 배지를 놓인 자리 그대로 둡니다(걸쳐 나가지 않음).
* - MiddleEnd: 배지를 자기 크기의 절반만큼 끝 방향으로 걸쳐 나가게 합니다.
* - BottomStart: 배지를 자기 크기의 절반만큼 아래·시작 방향으로 걸쳐 나가게 합니다.
* - BottomCenter: 배지를 자기 크기의 절반만큼 아래 방향으로 걸쳐 나가게 합니다.
* - BottomEnd: 배지를 자기 크기의 절반만큼 아래·끝 방향으로 걸쳐 나가게 합니다.
*/

/**
* WantedPushBadge
*
* 아이콘이나 UI 요소에 붙여 표시되는 Push badge 컴포넌트입니다.
*
* Dot, Text, MaxCount 타입 중 하나를 선택할 수 있으며, 위치·사이즈·색상·타이포그래피를 설정할 수 있습니다.
* 아바타 등 겹치는 배경에서 배지를 분리하는 outlineBorder(외곽 보더)와, 부착 위치를 미세 조정하는 inset 을 지원합니다.
*
* 다이나믹 타입(시스템 폰트 확대)은 fontScale 1.3 까지만 반영합니다.
* 배지 높이는 고정값이 아니라 `라인 높이 + 세로 패딩` 으로 결정되므로 확대 배율에 따라 함께 커집니다.
*
* 사용 예시:
* ```kotlin
* // 임의 문자열
* WantedPushBadge(variant = PushBadgeVariant.Text, text = "N")
*
* // 개수(99 초과 시 "99+")
* WantedPushBadge(variant = PushBadgeVariant.MaxCount, text = "128") // -> "99+"
*
* // 아바타 위에 외곽 보더와 함께
* WantedPushBadge(size = PushBadgeSize.Medium, outlineBorder = true)
* ```
*
* @param modifier Modifier: 배지의 배치·정렬 등에 사용되는 Modifier입니다.
* @param variant PushBadgeVariant: 표시할 배지 타입입니다. Dot, Text, MaxCount 중 선택합니다.
* @param size PushBadgeSize: 배지의 크기입니다. XSmall, Small, Medium 중 선택합니다.
* @param position PushBadgePosition: 배지가 놓인 자리에서 자기 크기의 절반만큼 어느 방향으로 걸쳐 나갈지 정합니다(TopEnd 등 9가지). 대상의 해당 모서리로 옮기지는 않으므로, 대상 위 모서리에 붙이려면 modifier 에 같은 방향의 정렬(예: Modifier.align(Alignment.TopEnd))을 함께 지정합니다. 기본값은 TopEnd입니다.
* @param text String: Text·MaxCount 타입일 때 표시할 문자열입니다. MaxCount 는 이 값을 정수로 해석하며, 정수가 아니면 0 으로 표시합니다.
* @param maxCount Int: `MaxCount` 타입의 상한값입니다. `text` 를 숫자로 해석한 값이 이 값을 넘으면 "maxCount+"로 표기합니다. 기본값은 99입니다.
* @param outlineBorder Boolean: 겹치는 배경에서 배지를 분리하는 외곽 보더 표시 여부입니다. 기본값은 false입니다.
* @param outlineBorderColor Color: 외곽 보더 색상입니다. 기본값은 backgroundNeutralPrimary입니다.
* @param background Color: 배지의 배경 색상입니다. 기본값은 surfaceBrandPrimary입니다.
* @param contentColor Color: 텍스트 색상입니다. 기본값은 static_white입니다.
* @param textStyle TextStyle?: 텍스트 타이포그래피 오버라이드입니다. null이면 사이즈별 기본값을 사용합니다.
* @param inset DpOffset: 부착 위치 미세 조정값입니다. 값이 커질수록 대상 안쪽으로 이동하며, 기본값은 (0, 0)입니다. position 이 Center·Middle 인 축(예: TopCenter 의 x)에는 적용되지 않습니다.
*/

/** fontScale 만 [MAX_FONT_SCALE] 로 제한한 Density 를 반환합니다. dp 배율은 그대로 유지합니다. */

/** 핸드오프가 정의한 Android 다이나믹 타입 확대 상한입니다. (시스템 '가장 크게') */

/** 1:1 정사각 비율을 강제하는 글자 수입니다. */