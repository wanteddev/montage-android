/**
* 자동완성 기능이 포함된 TextField 컴포저블입니다. (String 기반)
*
* 입력값을 기반으로 드롭다운 자동완성 리스트를 표시합니다.
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
* @param expended Boolean: 자동완성 드롭다운의 확장 상태입니다.
* @param anchorPadding Dp: 드롭다운과 필드 간 패딩입니다.
* @param dropDownMaxHeight Dp: 드롭다운 최대 높이입니다.
* @param sectionTitleHorizontalPadding Dp: 섹션 제목의 좌우 여백입니다.
* @param sectionCount Int: 자동완성 섹션 수입니다.
* @param background Color: 배경 색상입니다.
* @param interactionSource MutableInteractionSource: 포커스 등 상태 추적용입니다.
* @param focused State<Boolean>: 포커스 상태입니다.
* @param keyboardOptions KeyboardOptions: 키보드 동작 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 처리입니다.
* @param sectionTitle ((Int) -> String)?: 섹션 제목 반환 함수입니다.
* @param onClickRightButton () -> Unit: 우측 버튼 클릭 시 호출됩니다.
* @param onValueChange (String) -> Unit: 값 변경 시 호출됩니다.
* @param leadingIcon (() -> Unit)?: 좌측 아이콘 슬롯입니다.
* @param trailingIcon (() -> Unit)?: 우측 아이콘 슬롯입니다.
* @param rightContent ((Dp) -> Unit)?: 우측 콘텐츠 슬롯입니다.
* @param topDirectInput (() -> Unit)?: 드롭다운 상단 직접 입력 슬롯입니다.
* @param bottomDirectInput (() -> Unit)?: 드롭다운 하단 직접 입력 슬롯입니다.
* @param sectionItem @Composable (Int, Int) -> Unit: 각 섹션의 아이템 UI를 정의합니다.
*/

/**
* 자동완성 기능이 포함된 TextField 컴포저블입니다. (TextFieldValue 기반)
*
* 커서 및 선택 영역 제어가 필요한 경우 `TextFieldValue`를 통해 입력 상태를 관리합니다.
* 나머지 기능은 `String` 기반 버전과 동일합니다.
*
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
* @param expended Boolean: 드롭다운 확장 상태입니다.
* @param anchorPadding Dp: 필드와 드롭다운 간 간격입니다.
* @param dropDownMaxHeight Dp: 드롭다운 최대 높이입니다.
* @param sectionTitleHorizontalPadding Dp: 섹션 제목 여백입니다.
* @param sectionCount Int: 섹션 수입니다.
* @param background Color: 배경 색입니다.
* @param interactionSource MutableInteractionSource: 포커스 상태 추적용입니다.
* @param focused State<Boolean>: 포커스 여부입니다.
* @param keyboardOptions KeyboardOptions: 키보드 동작 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 처리입니다.
* @param sectionTitle ((Int) -> String)?: 섹션 제목 제공 함수입니다.
* @param onClickRightButton () -> Unit: 우측 버튼 클릭 콜백입니다.
* @param leadingIcon (() -> Unit)?: 좌측 아이콘 슬롯입니다.
* @param trailingIcon (() -> Unit)?: 우측 아이콘 슬롯입니다.
* @param rightContent ((Dp) -> Unit)?: 우측 콘텐츠입니다.
* @param topDirectInput (() -> Unit)?: 드롭다운 상단 콘텐츠입니다.
* @param bottomDirectInput (() -> Unit)?: 드롭다운 하단 콘텐츠입니다.
*/

/**
* 텍스트 영역 입력 필드 컴포저블입니다. (String 기반)
*
* 여러 줄의 텍스트 입력이 필요한 경우 사용되며, 버튼, 아이콘, 타이틀, 설명 등을 유연하게 조합할 수 있습니다.
* 내부적으로 `TextFieldValue`를 상태로 관리하며 `onValueChange`를 통해 외부에 값을 전달합니다.
*
* 사용 예시:
* ```kotlin
* WantedTextArea(
*     text = "내용",
*     title = "설명",
*     placeholder = "입력해주세요",
*     rightButton = "완료",
*     onValueChange = { newText -> ... }
* )
* ```
*
* @param text String: 입력된 텍스트 값입니다.
* @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
* @param placeholder String: 힌트로 보여질 텍스트입니다.
* @param title String: 상단 제목 텍스트입니다.
* @param description String?: 하단 메시지 또는 설명입니다.
* @param rightButton String?: 우측 버튼 텍스트입니다.
* @param leadingContent (() -> Unit)?: 왼쪽 슬롯 콘텐츠입니다.
* @param trailingContent (() -> Unit)?: 오른쪽 슬롯 콘텐츠입니다.
* @param enabled Boolean: 입력 활성화 여부입니다.
* @param negative Boolean: 에러 상태 여부입니다.
* @param maxLines Int: 최대 줄 수입니다.
* @param minLines Int: 최소 줄 수입니다.
* @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
* @param enabledOverflowText Boolean: 글자 수 초과 입력 허용 여부입니다.
* @param isGraphemeClusterCount Boolean: 문자 수 대신 grapheme cluster 기준으로 글자 수를 셉니다.
* @param requiredBadge Boolean: 필수 입력 뱃지 표시 여부입니다.
* @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
* @param focused State<Boolean>: 포커스 상태입니다.
* @param keyboardOptions KeyboardOptions: 키보드 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
* @param background Color: 배경 색상입니다.
* @param onClickRightButton () -> Unit: 우측 버튼 클릭 콜백입니다.
* @param onValueChange (String) -> Unit: 값 변경 콜백입니다.
*/

/**
* 텍스트 영역 입력 필드 컴포저블입니다. (TextFieldValue 기반)
*
* 커서, 선택 영역 등 복잡한 상태를 다룰 수 있는 `TextFieldValue`를 사용합니다.
*
* 사용 예시:
* ```kotlin
* val state = remember { mutableStateOf(TextFieldValue("입력값")) }
* WantedTextArea(value = state.value, onValueChange = { state.value = it })
* ```
*
* @param value TextFieldValue: 입력 값 및 커서, 선택 정보 등을 포함합니다.
* @param onValueChange (TextFieldValue) -> Unit: 값 변경 콜백입니다.
* @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
* @param placeholder String: 힌트 텍스트입니다.
* @param title String: 상단 제목입니다.
* @param description String?: 하단 설명 또는 상태 메시지입니다.
* @param rightButton String?: 우측 버튼 텍스트입니다.
* @param enabled Boolean: 입력 활성화 여부입니다.
* @param negative Boolean: 에러 상태 여부입니다.
* @param maxLines Int: 최대 줄 수입니다.
* @param minLines Int: 최소 줄 수입니다.
* @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
* @param enabledOverflowText Boolean: 글자 수 초과 허용 여부입니다.
* @param requiredBadge Boolean: 필수 입력 여부입니다.
* @param isGraphemeClusterCount Boolean: grapheme cluster 기준 글자 수 사용 여부입니다.
* @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
* @param focused State<Boolean>: 포커스 상태입니다.
* @param keyboardOptions KeyboardOptions: 키보드 동작 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
* @param background Color: 배경 색상입니다.
*/

/**
* 텍스트 입력을 위한 커스텀 TextField 컴포저블입니다. (String 기반)
*
* `TextFieldValue`가 아닌 문자열 기반으로 값을 주고받는 방식입니다.
* 에러, 완료 상태, 우측 버튼, 아이콘, 설명, 포커스 등을 커스터마이징할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedTextField(
*     text = "입력값",
*     placeholder = "텍스트를 입력하세요",
*     onValueChange = { newValue -> ... }
* )
* ```
*
* @param text String: 현재 입력된 텍스트입니다.
* @param modifier Modifier: 레이아웃 및 스타일을 설정합니다.
* @param placeholder String: 텍스트 필드에 힌트로 표시될 문자열입니다.
* @param title String: 상단 제목 텍스트입니다.
* @param description String?: 하단에 표시할 설명 또는 상태 메시지입니다.
* @param rightButton String?: 우측 버튼에 표시될 텍스트입니다.
* @param rightButtonVariant RightVariant: 우측 버튼 스타일을 지정합니다.
* @param status Status: 텍스트 필드의 상태 (Normal, Positive, Negative)입니다.
* @param enabled Boolean: 입력 가능 여부입니다.
* @param rightButtonEnabled Boolean: 우측 버튼 활성화 여부입니다.
* @param maxLines Int: 텍스트 필드의 최대 줄 수입니다.
* @param minLines Int: 텍스트 필드의 최소 줄 수입니다.
* @param maxWordCount Int: 허용되는 최대 글자 수입니다.
* @param enabledOverflowText Boolean: 글자 수 초과 허용 여부입니다.
* @param requiredBadge Boolean: 제목 옆에 필수 뱃지 표시 여부입니다.
* @param interactionSource MutableInteractionSource: 포커스 및 인터랙션 상태를 추적합니다.
* @param focused State<Boolean>: 포커스 여부를 외부에서 제어합니다.
* @param keyboardOptions KeyboardOptions: 키보드 동작 옵션입니다.
* @param keyboardActions KeyboardActions: 키보드 액션에 대한 핸들링입니다.
* @param background Color: 텍스트 필드 배경 색상입니다.
* @param leadingIcon (() -> Unit)?: 좌측 아이콘 슬롯입니다.
* @param trailingIcon (() -> Unit)?: 우측 아이콘 슬롯입니다.
* @param rightContent ((Dp) -> Unit)?: 우측 버튼 외 영역에 들어갈 커스텀 콘텐츠입니다.
* @param onClickRightButton () -> Unit: 우측 버튼 클릭 시 콜백입니다.
* @param onValueChange (String) -> Unit: 텍스트 변경 시 콜백입니다.
*/

/**
* 텍스트 입력을 위한 커스텀 TextField 컴포저블입니다. (TextFieldValue 기반)
*
* 커서 위치 및 선택 영역 처리를 위해 `TextFieldValue` 객체를 사용합니다.
*
* 사용 예시:
* ```kotlin
* val state = remember { mutableStateOf(TextFieldValue("")) }
* WantedTextField(value = state.value, onValueChange = { state.value = it })
* ```
*
* @param value TextFieldValue: 입력된 텍스트 및 커서 상태를 포함한 값입니다.
* @param onValueChange (TextFieldValue) -> Unit: 값 변경 시 호출되는 콜백입니다.
* @param modifier Modifier: 외형 및 레이아웃 설정입니다.
* @param enabled Boolean: 입력 가능 여부입니다.
* @param title String: 상단 제목 텍스트입니다.
* @param requiredBadge Boolean: 필수 뱃지 표시 여부입니다.
* @param placeholder String: 플레이스홀더 문자열입니다.
* @param description String?: 하단 상태 또는 설명 메시지입니다.
* @param leadingIcon (() -> Unit)?: 좌측 아이콘 슬롯입니다.
* @param trailingIcon (() -> Unit)?: 우측 아이콘 슬롯입니다.
* @param rightContent ((Dp) -> Unit)?: 우측 영역 콘텐츠 슬롯입니다.
* @param rightButton String?: 우측 버튼 텍스트입니다.
* @param onClickRightButton () -> Unit: 우측 버튼 클릭 콜백입니다.
* @param rightButtonEnabled Boolean: 우측 버튼 활성화 여부입니다.
* @param rightButtonVariant RightVariant: 우측 버튼 스타일입니다.
* @param status Status: 입력 상태입니다.
* @param maxWordCount Int: 최대 글자 수입니다.
* @param enabledOverflowText Boolean: 글자 수 초과 허용 여부입니다.
* @param minLines Int: 최소 줄 수입니다.
* @param maxLines Int: 최대 줄 수입니다.
* @param interactionSource MutableInteractionSource: 포커스 추적용입니다.
* @param focused State<Boolean>: 포커스 상태 제어용입니다.
* @param keyboardOptions KeyboardOptions: 키보드 동작 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 처리입니다.
* @param background Color: 배경 색상입니다.
*/

/**
* object WantedSelectContract
*
* 셀렉트(Select) 컴포넌트에서 사용되는 UI 설정 값을 정의하는 객체입니다.
*
* 선택 렌더링 방식과 선택 UI 스타일을 제어할 수 있는 enum 클래스를 포함합니다.
*/

/**
* enum class Status
* TextField의 시각적 상태를 정의합니다.
*
* 각 상태는 UI 내 색상, 아이콘, 설명 등의 구성에 영향을 미칩니다.
*
* - `Normal`: 일반 상태로 아무런 강조 없이 기본 스타일로 표시됩니다.
* - `Positive`: 완료 상태로, 성공 또는 완료된 입력을 나타냅니다.
* - `Negative`: 에러 상태로, 입력 값이 유효하지 않거나 경고가 필요할 때 사용됩니다.
*/

/**
* enum class RightVariant
*
* TextField 우측 버튼의 시각적 스타일을 정의합니다.
*
* - `Normal`: 강조된 기본 스타일로, 일반적인 주요 액션에 사용됩니다.
* - `Assistive`: 보조적 역할을 하는 버튼 스타일로, 눈에 띄지 않게 표현됩니다.
*/