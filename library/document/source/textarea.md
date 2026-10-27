/**
* WantedTextArea
*
* (Deprecated) 라벨·설명·필수 뱃지를 함께 렌더링하는 WantedTextArea 입니다.
*
* WantedFormControl(input = { WantedTextArea(value = ..., onValueChange = ...) }) 형태로 조합해 사용합니다.
* 본체 WantedTextArea 는 글자 수를 그리지 않으므로, 글자 수 표시가 필요하면 WantedFormControl 의 accessory 에 WantedTextAreaCharacterCount 를 넣습니다.
*
* @param text String: 입력된 텍스트 값입니다.
* @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
* @param placeholder String: 힌트로 보여질 텍스트입니다.
* @param title String: 상단 제목 텍스트입니다.
* @param description String?: 하단 메시지 또는 설명입니다.
* @param leadingContent (@Composable () -> Unit)?: 하단 영역 왼쪽 슬롯 콘텐츠입니다.
* @param trailingContent (@Composable () -> Unit)?: 하단 영역 오른쪽 슬롯 콘텐츠입니다.
* @param size WantedTextAreaDefaults.Size: TextArea 크기입니다. (Large, Medium)
* @param resize WantedTextAreaDefaults.Resize: 높이 확장 동작입니다. (Normal, Limit, Fixed)
* @param enabled Boolean: 입력 활성화 여부입니다.
* @param status WantedTextAreaDefaults.Status: 입력 상태입니다. (Normal, Negative)
* @param maxLines Int: 최대 줄 수입니다. Resize.Limit 일 때 스크롤 기준이 됩니다.
* @param minLines Int: 최소 줄 수입니다.
* @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
* @param enabledOverflowText Boolean: 글자 수 초과 입력 허용 여부입니다.
* @param isGraphemeClusterCount Boolean: grapheme cluster 기준으로 글자 수를 셉니다.
* @param requiredBadge Boolean: 필수 입력 뱃지 표시 여부입니다.
* @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
* @param focusRequester FocusRequester: 입력 필드 포커스 요청용입니다.
* @param keyboardOptions KeyboardOptions: 키보드 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
* @param background Color: 배경 색상입니다.
* @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
* @param onValueChange (String) -> Unit: 값 변경 콜백입니다.
*/

/**
* WantedTextArea
*
* (Deprecated) 라벨·설명·필수 뱃지를 함께 렌더링하는 WantedTextArea 입니다.
*
* WantedFormControl(input = { WantedTextArea(value = ..., onValueChange = ...) }) 형태로 조합해 사용합니다.
* 본체 WantedTextArea 는 글자 수를 그리지 않으므로, 글자 수 표시가 필요하면 WantedFormControl 의 accessory 에 WantedTextAreaCharacterCount 를 넣습니다.
*
* @param value TextFieldValue: 입력 값 및 커서, 선택 정보 등을 포함합니다.
* @param onValueChange (TextFieldValue) -> Unit: 값 변경 콜백입니다.
* @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
* @param placeholder String: 힌트 텍스트입니다.
* @param title String: 상단 제목입니다.
* @param description String?: 하단 설명 또는 상태 메시지입니다.
* @param size WantedTextAreaDefaults.Size: TextArea 크기입니다.
* @param resize WantedTextAreaDefaults.Resize: 높이 확장 동작입니다.
* @param enabled Boolean: 입력 활성화 여부입니다.
* @param status WantedTextAreaDefaults.Status: 입력 상태입니다. (Normal, Negative)
* @param maxLines Int: 최대 줄 수입니다.
* @param minLines Int: 최소 줄 수입니다.
* @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
* @param enabledOverflowText Boolean: 글자 수 초과 허용 여부입니다.
* @param requiredBadge Boolean: 필수 입력 여부입니다.
* @param isGraphemeClusterCount Boolean: grapheme cluster 기준 글자 수 사용 여부입니다.
* @param leadingContent (@Composable () -> Unit)?: 하단 영역 왼쪽 슬롯 콘텐츠입니다.
* @param trailingContent (@Composable () -> Unit)?: 하단 영역 오른쪽 슬롯 콘텐츠입니다.
* @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
* @param focusRequester FocusRequester: 입력 필드 포커스 요청용입니다.
* @param keyboardOptions KeyboardOptions: 키보드 동작 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 처리입니다.
* @param background Color: 배경 색상입니다.
* @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
*/

/**
* WantedTextArea
*
* (Deprecated) 라벨·설명·필수 뱃지를 함께 렌더링하는 WantedTextArea 입니다.
*
* WantedFormControl(input = { WantedTextArea(value = ..., onValueChange = ...) }) 형태로 조합해 사용합니다.
* 본체 WantedTextArea 는 글자 수를 그리지 않으므로, 글자 수 표시가 필요하면 WantedFormControl 의 accessory 에 WantedTextAreaCharacterCount 를 넣습니다.
*
* @param text String: 입력된 텍스트 값입니다.
* @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
* @param placeholder String: 힌트로 보여질 텍스트입니다.
* @param title String: 상단 제목 텍스트입니다.
* @param description String?: 하단 메시지 또는 설명입니다.
* @param button String?: 하단 버튼 텍스트입니다.
* @param leadingContent (@Composable () -> Unit)?: 하단 영역 왼쪽 슬롯 콘텐츠입니다.
* @param size WantedTextAreaDefaults.Size: TextArea 크기입니다. (Large, Medium)
* @param resize WantedTextAreaDefaults.Resize: 높이 확장 동작입니다. (Normal, Limit, Fixed)
* @param enabled Boolean: 입력 활성화 여부입니다.
* @param status WantedTextAreaDefaults.Status: 입력 상태입니다. (Normal, Negative)
* @param maxLines Int: 최대 줄 수입니다. Resize.Limit 일 때 스크롤 기준이 됩니다.
* @param minLines Int: 최소 줄 수입니다.
* @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
* @param enabledOverflowText Boolean: 글자 수 초과 입력 허용 여부입니다.
* @param isGraphemeClusterCount Boolean: grapheme cluster 기준으로 글자 수를 셉니다.
* @param requiredBadge Boolean: 필수 입력 뱃지 표시 여부입니다.
* @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
* @param focusRequester FocusRequester: 입력 필드 포커스 요청용입니다.
* @param keyboardOptions KeyboardOptions: 키보드 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
* @param background Color: 배경 색상입니다.
* @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
* @param onClickButton () -> Unit: 버튼 클릭 콜백입니다.
* @param onValueChange (String) -> Unit: 값 변경 콜백입니다.
*/

/**
* WantedTextArea
*
* (Deprecated) 라벨·설명·필수 뱃지를 함께 렌더링하는 WantedTextArea 입니다.
*
* WantedFormControl(input = { WantedTextArea(value = ..., onValueChange = ...) }) 형태로 조합해 사용합니다.
* 본체 WantedTextArea 는 글자 수를 그리지 않으므로, 글자 수 표시가 필요하면 WantedFormControl 의 accessory 에 WantedTextAreaCharacterCount 를 넣습니다.
*
* @param value TextFieldValue: 입력 값 및 커서, 선택 정보 등을 포함합니다.
* @param onValueChange (TextFieldValue) -> Unit: 값 변경 콜백입니다.
* @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
* @param placeholder String: 힌트 텍스트입니다.
* @param title String: 상단 제목입니다.
* @param description String?: 하단 설명 또는 상태 메시지입니다.
* @param button String?: 하단 버튼 텍스트입니다.
* @param size WantedTextAreaDefaults.Size: TextArea 크기입니다.
* @param resize WantedTextAreaDefaults.Resize: 높이 확장 동작입니다.
* @param enabled Boolean: 입력 활성화 여부입니다.
* @param status WantedTextAreaDefaults.Status: 입력 상태입니다. (Normal, Negative)
* @param maxLines Int: 최대 줄 수입니다.
* @param minLines Int: 최소 줄 수입니다.
* @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
* @param enabledOverflowText Boolean: 글자 수 초과 허용 여부입니다.
* @param requiredBadge Boolean: 필수 입력 여부입니다.
* @param isGraphemeClusterCount Boolean: grapheme cluster 기준 글자 수 사용 여부입니다.
* @param leadingContent (@Composable () -> Unit)?: 하단 영역 왼쪽 슬롯 콘텐츠입니다.
* @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
* @param focusRequester FocusRequester: 입력 필드 포커스 요청용입니다.
* @param keyboardOptions KeyboardOptions: 키보드 동작 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 처리입니다.
* @param background Color: 배경 색상입니다.
* @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
* @param onClickButton () -> Unit: 버튼 클릭 콜백입니다.
*/

/**
* WantedTextArea
*
* 여러 줄의 텍스트 입력이 필요한 경우 사용하는 입력 컴포넌트입니다.
*
* 라벨/설명/필수 뱃지 등 폼 부가 요소는 포함하지 않습니다.
* 해당 요소가 필요하면 WantedFormControl 의 input 슬롯에 조합해 사용합니다.
*
* 사용 예시:
* ```kotlin
* val state = remember { mutableStateOf(TextFieldValue("입력값")) }
* WantedFormControl(
*     label = "설명",
*     input = {
*         WantedTextArea(
*             value = state.value,
*             placeholder = "입력해주세요",
*             onValueChange = { state.value = it }
*         )
*     }
* )
* ```
*
* @param value TextFieldValue: 입력 값 및 커서, 선택 정보 등을 포함합니다.
* @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
* @param placeholder String: 힌트로 보여질 텍스트입니다.
* @param status WantedTextAreaDefaults.Status: 입력 상태입니다. (Normal, Negative)
* @param enabled Boolean: 입력 활성화 여부입니다.
* @param focusRequester FocusRequester: 입력 필드 포커스 요청용입니다.
* @param size WantedTextAreaDefaults.Size: TextArea 크기입니다. (Large, Medium)
* @param resize WantedTextAreaDefaults.Resize: 높이 확장 동작입니다. (Normal, Limit, Fixed)
* @param maxLines Int: 최대 줄 수입니다. Resize.Limit 일 때만 적용되며, Normal 은 제한 없이 늘어나고 Fixed 는 minLines 로 고정됩니다.
* @param minLines Int: 최소 줄 수입니다. Resize.Fixed 에서는 이 값이 최대 줄 수도 됩니다.
* @param maxWordCount Int: 입력 가능한 최대 글자 수입니다. 이 오버로드는 글자 수를 표시하지 않으므로, 표시가 필요하면 WantedFormControl 의 accessory 에 WantedTextAreaCharacterCount 를 넣습니다.
* @param enabledOverflowText Boolean: 글자 수 초과 입력 허용 여부입니다.
* @param isGraphemeClusterCount Boolean: grapheme cluster 기준으로 글자 수를 셉니다.
* @param visualTransformation VisualTransformation: 텍스트 표시 방식을 변환합니다 (예: 비밀번호 마스킹).
* @param interactionSource MutableInteractionSource: 포커스 등 인터랙션 추적용입니다.
* @param keyboardOptions KeyboardOptions: 키보드 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
* @param background Color: 배경 색상입니다.
* @param button String?: 하단 버튼 텍스트입니다. 지정하면 trailingContent 대신 버튼이 노출되며, enabled=false 이거나 status=Negative 이면 버튼이 비활성화됩니다.
* @param leadingContent (@Composable () -> Unit)?: 하단 영역 왼쪽 슬롯 콘텐츠입니다.
* @param trailingContent (@Composable () -> Unit)?: 하단 영역 오른쪽 슬롯 콘텐츠입니다. button 이 지정되면 표시되지 않습니다.
* @param onClickButton () -> Unit: 버튼 클릭 콜백입니다.
* @param onValueChange (TextFieldValue) -> Unit: 값 변경 콜백입니다.
*/

/** 상태 색 border 아래에 깔리는 underlay border 색상입니다. 상태 색 위를 덮지 않습니다. */

/** Container border 색상입니다. Negative + Focused 는 opacity 를 52% 로 올려 강조합니다. */

/**
* object WantedTextAreaDefaults
*
* TextArea 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
*
* Size와 Resize 열거형을 포함하며, 각 크기 및 리사이즈 동작에 따른 토큰을 제공합니다.
*/

    /** 기본 최소 행 수입니다. 텍스트 2줄이 보이는 높이가 기본 높이가 됩니다. */

    /** 기본 최대 행 수입니다. Resize.Limit 에서 이 행 수를 최대 높이로 삼습니다. */

    /**
* enum class Status
*
* TextArea의 상태를 정의하는 enum 클래스입니다. (WantedTextField 의 Status 와 동일한 축)
* - Normal: 일반 상태입니다.
* - Negative: 부정(에러) 상태입니다.
*/

    /**
* enum class Size
*
* TextArea의 크기를 정의하는 enum 클래스입니다.
* - Large: 큰 크기입니다. (콘텐츠 최소 높이 48dp, body2ReadingRegular)
* - Medium: 중간 크기입니다. (콘텐츠 최소 높이 44dp, label1ReadingRegular)
*/

    /**
* enum class Resize
*
* TextArea의 높이 확장 동작을 정의하는 enum 클래스입니다.
* - Normal: 사용자 입력에 따라 자유롭게 높이 확장합니다. (최소 높이만 보장)
* - Limit: maxLines 설정값 이상으로 확장하지 않습니다. (스크롤 전환)
* - Fixed: 높이가 고정됩니다. (최소 높이 = 최대 높이, 스크롤 전환)
*/

/**
* fun WantedTextAreaCharacterCount(...)
*
* 입력한 글자 수를 `현재/최대` 형태로 표시하는 글자 수 표시 컴포넌트입니다.
*
* 본체 WantedTextArea 는 글자 수를 그리지 않으므로, WantedFormControl 의 accessory 슬롯에 이 컴포넌트를 넣어 표시합니다(deprecated WantedTextArea 오버로드만 내부에서 직접 그립니다). 다른 입력 컴포넌트에도 붙일 수 있습니다. 글자 수를 세거나 입력을 막지는 않으므로 current 와 error 는 호출부가 계산해서 넘깁니다.
*
* 사용 예시:
* ```kotlin
* val state = remember { mutableStateOf(TextFieldValue("")) }
*
* WantedFormControl(
*     label = "자기소개",
*     accessory = {
*         WantedTextAreaCharacterCount(
*             current = state.value.text.length,
*             maxWordCount = 200,
*             error = state.value.text.length > 200
*         )
*     },
*     input = {
*         WantedTextArea(
*             value = state.value,
*             maxWordCount = 200,
*             onValueChange = { state.value = it }
*         )
*     }
* )
* ```
*
* @param current Int: 현재 글자 수입니다.
* @param maxWordCount Int: 최대 글자 수입니다. `/최대` 로 표시됩니다.
* @param modifier Modifier: 표시 영역에 적용할 Modifier입니다. 좌우 6dp 여백이 항상 더해집니다.
* @param error Boolean: true 이면 현재 글자 수를 부정(에러) 색으로 표시합니다. 최대 글자 수 쪽 색은 바뀌지 않습니다.
* @param enable Boolean: false 이면 두 숫자를 모두 비활성 색으로 표시하며, error 보다 우선합니다.
*/