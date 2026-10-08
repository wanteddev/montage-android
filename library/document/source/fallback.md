/**
* WantedFallbackView
*
* 제목, 설명, 버튼을 조합하여 비어 있는 상태를 안내하는 컴포넌트입니다.
*
* 주로 데이터가 없거나 결과가 없을 때 사용자에게 피드백을 제공하는 용도로 사용됩니다.
* 제목, 설명, 버튼을 선택적으로 구성할 수 있으며, 버튼 클릭 시 콜백을 전달할 수 있습니다.
* 버튼은 Assistive 색상만 사용하며, 좌우 여백은 호출하는 화면에서 배치합니다.
* 상하 여백은 [padding] 으로 지정합니다.
*
* 사용 예시:
* ```kotlin
* WantedFallbackView(
*     padding = WantedFallbackPadding.Normal,
*     heading = "데이터가 없습니다.",
*     description = "새로운 데이터를 추가해보세요.",
*     main = "추가하기",
*     onClickMain = { /* 버튼 클릭 처리 */ }
* )
* ```
*
* @param modifier Modifier: 외형 및 배치를 조정하는 Modifier입니다.
* @param padding WantedFallbackPadding: 콘텐츠 상하 여백을 지정합니다 (Normal, Compact).
* @param buttonVariant WantedFallbackButtonVariant: 버튼 배치 방식을 지정합니다 (Single, Horizontal, Vertical).
* @param heading String?: 상단에 강조 텍스트(제목)를 표시합니다.
* @param description String?: 제목 아래에 설명 텍스트를 표시합니다. 최대 두 줄까지만 표시됩니다.
* @param main String?: 메인 액션 버튼에 표시될 텍스트입니다. null일 경우 버튼은 렌더링되지 않습니다.
* @param alternative String?: 대체 액션 버튼에 표시될 텍스트입니다. null일 경우 버튼은 렌더링되지 않습니다.
* @param onClickMain () -> Unit: 메인 액션 버튼 클릭 시 호출되는 콜백입니다.
* @param onClickAlternative () -> Unit: 대체 액션 버튼 클릭 시 호출되는 콜백입니다.
*/

/**
* 이미지(일러스트) 슬롯이 포함된 [WantedFallbackView] 입니다.
*
* 디자인 4.0.0 에서 이미지 슬롯이 제거되어 더 이상 사용하지 않습니다.
* 기존 화면 호환을 위해서만 유지하며, 이미지 없는 오버로드로 이관합니다.
*
* @param image (@Composable () -> Unit): 텍스트 위에 표시될 이미지 컴포넌트입니다.
* @param modifier Modifier: 외형 및 배치를 조정하는 Modifier입니다.
* @param buttonVariant WantedFallbackButtonVariant: 버튼 배치 방식을 지정합니다 (Single, Horizontal, Vertical).
* @param heading String?: 상단에 강조 텍스트(제목)를 표시합니다.
* @param description String?: 제목 아래에 설명 텍스트를 표시합니다. 최대 두 줄까지만 표시됩니다.
* @param main String?: 메인 액션 버튼에 표시될 텍스트입니다. null일 경우 버튼은 렌더링되지 않습니다.
* @param alternative String?: 대체 액션 버튼에 표시될 텍스트입니다. null일 경우 버튼은 렌더링되지 않습니다.
* @param onClickMain () -> Unit: 메인 액션 버튼 클릭 시 호출되는 콜백입니다.
* @param onClickAlternative () -> Unit: 대체 액션 버튼 클릭 시 호출되는 콜백입니다.
*/

/**
* enum class WantedFallbackPadding
*
* 콘텐츠 상하 여백 옵션입니다. Figma `Fallback View` 의 `Padding` 속성과 1:1 대응합니다.
* 값은 Spacing 토큰(최대 80dp)으로 표현할 수 없어 시안 실측값을 그대로 사용합니다.
*
* @property verticalPadding Dp: 콘텐츠 위·아래에 각각 적용되는 여백입니다.
*/