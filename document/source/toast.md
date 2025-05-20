/**
* SnackbarHost를 기반으로 한 Wanted 커스텀 Toast 컴포저블입니다.
*
* `SnackbarData.visuals` 타입이 `WantedToastVisuals`인 경우 variant와 아이콘을 사용한 토스트를 표시하며,
* 일반 메시지 타입일 경우 기본 메시지 출력만 합니다.
*
* 사용 예시:
* ```kotlin
* val snackbarHostState = remember { SnackbarHostState() }
* WantedToast(snackBarHostState = snackbarHostState)
* ```
*
* @param snackBarHostState SnackbarHostState: Snackbar 상태를 관리합니다.
* @param modifier Modifier: 외형을 조정합니다.
* @param windowInsets WindowInsets: 시스템 인셋 대응을 위한 설정입니다.
*/

/**
* sealed class WantedToastVariant
*
* Toast 메시지의 시각적 스타일을 정의하는 sealed 클래스입니다.
*
* 각 타입은 고유의 아이콘 리소스 및 색상 리소스를 포함하고 있으며, 사용자 피드백의 성격(정보, 긍정, 경고, 부정 등)을 나타냅니다.
*
* 제공되는 토스트 스타일은 다음과 같습니다:
* - Message: 일반 메시지 (아이콘 없음)
* - Positive: 긍정적인 메시지 (체크 아이콘, 초록색)
* - Cautionary: 주의 메시지 (느낌표 아이콘, 주황색)
* - Negative: 부정적인 메시지 (느낌표 아이콘, 빨간색)
*
* @property resourceId `Int`: 표시할 아이콘 리소스의 ID입니다.
* @property tinColor `Int`: 아이콘에 적용될 색상 리소스의 ID입니다.
*/

/**
* data object Message
*
* 일반 텍스트 메시지를 표시하는 스타일입니다. 아이콘은 표시되지 않습니다.
*/

/**
* data object Positive
*
* 긍정적인 알림을 위한 스타일입니다.
* 체크 아이콘과 초록색 색상이 사용됩니다.
*/

/**
* data object Cautionary
*
* 주의가 필요한 상황을 나타내는 스타일입니다.
* 느낌표 아이콘과 주황색 색상이 사용됩니다.
*/

/**
* data object Negative
*
* 부정적인 상황을 나타내는 스타일입니다.
* 느낌표 아이콘과 빨간색 색상이 사용됩니다.
*/

/**
* 커스텀 Toast를 위한 시각적 구성 클래스입니다.
*
* 메시지, 아이콘, variant 설정 등을 포함하며 SnackbarVisuals를 확장합니다.
*
* @param message String: 토스트에 표시할 메시지입니다.
* @param actionLabel String: 액션 버튼에 표시할 텍스트입니다.
* @param duration SnackbarDuration: 토스트 노출 시간입니다.
* @param withDismissAction Boolean: 닫기 액션 여부입니다.
* @param variant WantedToastVariant: 메시지 타입을 나타내는 스타일 variant입니다.
* @param icon @Composable (() -> Unit)?: 사용자 정의 아이콘 슬롯입니다.
*/