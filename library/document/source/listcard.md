/**
* WantedListCard
* 썸네일과 설명 영역을 좌우로 배치한 가로형 카드 컴포넌트입니다.
*
* 로딩 상태(isLoading)에 따라 Skeleton 또는 실제 콘텐츠를 렌더링합니다.
* 상단/하단 커스텀 콘텐츠와 좌우 콘텐츠 Slot을 제공합니다.
*
* 사용 예시:
* ```kotlin
* WantedListCard(
*     modifier = Modifier.fillMaxWidth(),
*     title = "제목",
*     caption = "캡션",
*     extraCaption = "추가 설명",
*     topContent = { WantedContentBadge(text = "상단") },
*     bottomContent = { WantedContentBadge(text = "하단") },
*     leadingContent = { WantedCheckBox(...) },
*     trailingContent = { Icon(...) },
*     onClick = { /* 클릭 처리 */ }
* )
* ```
*
* @param modifier Modifier: 컴포넌트의 레이아웃과 스타일을 조정합니다.
* @param title String: 카드의 주요 제목입니다.
* @param caption String: 제목 아래에 표시될 보조 설명입니다.
* @param extraCaption String: 추가적인 설명 텍스트입니다.
* @param isLoading Boolean: true일 경우 스켈레톤 UI로 렌더링됩니다.
* @param cardDefault WantedCardDefault: 스켈레톤 모드 시 항목별 표시 여부를 지정하는 설정 객체입니다.
* @param thumbnail (@Composable () -> Unit)?: 썸네일 이미지 영역입니다. null이면 기본 배경으로 표시됩니다.
* @param topContent (@Composable () -> Unit)?: 설명 위에 표시될 추가 콘텐츠입니다.
* @param bottomContent (@Composable () -> Unit)?: 설명 아래에 표시될 추가 콘텐츠입니다.
* @param leadingContent (@Composable () -> Unit)?: 썸네일 왼쪽에 표시될 콘텐츠입니다 (예: 체크박스).
* @param trailingContent (@Composable () -> Unit)?: 설명 오른쪽에 표시될 콘텐츠입니다 (예: 아이콘 버튼).
* @param onClick () -> Unit: 카드 클릭 시 호출되는 콜백 함수입니다.
*/