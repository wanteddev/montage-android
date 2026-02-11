/**
* WantedAutoCompleteTextField
*
* 자동완성 기능이 포함된 Text field 컴포넌트입니다.
*
* 입력값을 기반으로 Dropdown 자동완성 리스트를 표시합니다.
* 섹션별 아이템, 제목, 직접 입력 슬롯 등 다양한 확장이 가능합니다.
*
* 사용 예시:
* ```kotlin
* WantedAutoCompleteTextField(
*     text = input,
*     onValueChange = { input = it },
*     onExpandedChange = { expanded = it },
*     sectionItemCount = { 1 },
*     sectionItem = { section, index -> ... }
* )
* ```
*
* @param text String: 현재 입력된 텍스트입니다.
* @param sectionItemCount (Int) -> Int: 각 섹션 별 아이템 수를 반환하는 함수입니다.
* @param onExpandedChange (Boolean) -> Unit: 드롭다운 확장 상태 변경 콜백입니다.
* @param modifier Modifier: 외형 및 레이아웃 설정입니다.
* @param placeholder String: 플레이스홀더 텍스트입니다.
* @param title String: 상단 제목입니다.
* @param description String?: 하단 설명 또는 상태 메시지입니다.
* @param rightButton String?: 우측 버튼 텍스트입니다.
* @param rightButtonVariant RightVariant: 우측 버튼 스타일입니다.
* @param status Status: 입력 상태입니다.
* @param enabled Boolean: 입력 가능 여부입니다.
* @param rightButtonEnabled Boolean: 우측 버튼 활성화 여부입니다.
* @param maxLines Int: 최대 줄 수입니다.
* @param minLines Int: 최소 줄 수입니다.
* @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
* @param requiredBadge Boolean: 필수 입력 뱃지 여부입니다.
* @param expanded Boolean: 자동완성 드롭다운의 확장 상태입니다.
* @param anchorPadding Dp: 드롭다운과 필드 간 패딩입니다.
* @param dropDownMaxHeight Dp: 드롭다운 최대 높이입니다.
* @param sectionTitleHorizontalPadding Dp: 섹션 제목의 좌우 여백입니다.
* @param sectionCount Int: 자동완성 섹션 수입니다.
* @param background Color: 배경 색상입니다.
* @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
* @param interactionSource MutableInteractionSource: 포커스 등 상태 추적용입니다.
* @param keyboardOptions KeyboardOptions: 키보드 동작 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 처리입니다.
* @param sectionTitle ((Int) -> String)?: 섹션 제목 반환 함수입니다.
* @param onClickRightButton () -> Unit: 우측 버튼 클릭 시 호출됩니다.
* @param onValueChange (String) -> Unit: 값 변경 시 호출됩니다.
* @param leadingIcon (() -> Unit)?: 좌측 아이콘 슬롯입니다.
* @param trailingIcon (() -> Unit)?: 우측 아이콘 슬롯입니다.
* @param trailingContent (() -> Unit)?: 우측 콘텐츠 슬롯입니다.
* @param topDirectInput (() -> Unit)?: 드롭다운 상단 직접 입력 슬롯입니다.
* @param bottomDirectInput (() -> Unit)?: 드롭다운 하단 직접 입력 슬롯입니다.
* @param sectionItem @Composable (Int, Int) -> Unit: 각 섹션의 아이템 UI를 정의합니다.
*/

/**
* WantedAutoCompleteTextField
*
* 자동완성 기능이 포함된 Text field 컴포넌트입니다.
*
* 커서 및 선택 영역 제어가 필요한 경우 TextFieldValue를 통해 입력 상태를 관리합니다.
* 나머지 기능은 String 기반 버전과 동일합니다.
*
* 사용 예시:
* ```kotlin
* WantedAutoCompleteTextField(
*     value = input,
*     sectionItemCount = { 1 },
*     sectionItem = { section, index -> ... },
*     onExpandedChange = { expanded = it },
*     onValueChange = { input = it }
* )
* ```
* @param value TextFieldValue: 입력된 값 및 커서 상태입니다.
* @param onValueChange (TextFieldValue) -> Unit: 입력 값 변경 콜백입니다.
* @param sectionItemCount (Int) -> Int: 섹션별 아이템 수입니다.
* @param sectionItem @Composable (Int, Int) -> Unit: 섹션 아이템 UI입니다.
* @param onExpandedChange (Boolean) -> Unit: 드롭다운 확장 상태 변경 콜백입니다.
* @param modifier Modifier: 외형 설정입니다.
* @param placeholder String: 플레이스홀더입니다.
* @param title String: 상단 제목입니다.
* @param description String?: 하단 설명입니다.
* @param rightButton String?: 우측 버튼 텍스트입니다.
* @param rightButtonVariant RightVariant: 우측 버튼 스타일입니다.
* @param status Status: 입력 상태입니다.
* @param enabled Boolean: 활성화 여부입니다.
* @param rightButtonEnabled Boolean: 우측 버튼 활성화 여부입니다.
* @param maxLines Int: 최대 줄 수입니다.
* @param minLines Int: 최소 줄 수입니다.
* @param maxWordCount Int: 최대 입력 글자 수입니다.
* @param requiredBadge Boolean: 필수 입력 뱃지 여부입니다.
* @param expanded Boolean: 드롭다운 확장 상태입니다.
* @param anchorPadding Dp: 필드와 드롭다운 간 간격입니다.
* @param dropDownMaxHeight Dp: 드롭다운 최대 높이입니다.
* @param sectionTitleHorizontalPadding Dp: 섹션 제목 여백입니다.
* @param sectionCount Int: 섹션 수입니다.
* @param background Color: 배경 색입니다.
* @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
* @param interactionSource MutableInteractionSource: 포커스 상태 추적용입니다.
* @param keyboardOptions KeyboardOptions: 키보드 동작 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 처리입니다.
* @param sectionTitle ((Int) -> String)?: 섹션 제목 제공 함수입니다.
* @param onClickRightButton () -> Unit: 우측 버튼 클릭 콜백입니다.
* @param leadingIcon (() -> Unit)?: 좌측 아이콘 슬롯입니다.
* @param trailingIcon (() -> Unit)?: 우측 아이콘 슬롯입니다.
* @param trailingContent ((Dp) -> Unit)?: 우측 콘텐츠입니다.
* @param topDirectInput (() -> Unit)?: 드롭다운 상단 콘텐츠입니다.
* @param bottomDirectInput (() -> Unit)?: 드롭다운 하단 콘텐츠입니다.
*/