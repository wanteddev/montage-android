/**
* 검색 입력 필드 컴포넌트입니다.
*
* String 타입의 텍스트를 받아 검색 기능을 제공하는 입력 필드를 표시합니다.
* 검색 아이콘과 삭제 버튼이 자동으로 표시되며, 포커스 상태에 따라 UI가 변경됩니다.
*
* 사용 예시 :
* ```kotlin
* var searchText by remember { mutableStateOf("") }
*
* WantedSearchField(
*     text = searchText,
*     placeholder = "검색어를 입력해주세요",
*     onValueChange = { searchText = it }
* )
* ```
*
* @param text String: 입력 필드에 표시할 텍스트입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param placeholder String: 입력 필드가 비어있을 때 표시할 힌트 텍스트입니다.
* @param enabled Boolean: 입력 필드의 활성화 여부입니다. false인 경우 사용자 입력이 불가능합니다.
* @param size Size: 입력 필드의 크기입니다. Size.Medium() 또는 Size.Small()을 사용할 수 있습니다.
* @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
* @param enabledOverflowText Boolean: 최대 글자 수를 초과하는 입력을 허용할지 여부입니다.
* @param interactionSource MutableInteractionSource: 사용자 상호작용 상태를 추적하는 소스입니다.
* @param keyboardOptions KeyboardOptions: 키보드 옵션 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
* @param focused State<Boolean>: 입력 필드의 포커스 상태입니다.
* @param textStyle TextStyle: 입력 텍스트의 스타일입니다.
* @param cursorBrush Brush: 커서의 색상을 지정하는 브러시입니다.
* @param focusRequester FocusRequester: 포커스 요청을 처리하는 객체입니다.
* @param onValueChange (String) -> Unit: 텍스트 값이 변경될 때 호출되는 콜백 함수입니다.
*/

/**
* 검색 입력 필드 컴포넌트입니다.
*
* TextFieldValue 타입의 값을 받아 검색 기능을 제공하는 입력 필드를 표시합니다.
* 커서 위치와 선택 영역을 세밀하게 제어해야 하는 경우 이 오버로드를 사용합니다.
*
* 사용 예시 :
* ```kotlin
* var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
*
* WantedSearchField(
*     value = textFieldValue,
*     placeholder = "검색어를 입력해주세요",
*     onValueChange = { textFieldValue = it }
* )
* ```
*
* @param value TextFieldValue: 입력 필드의 값, 커서 위치, 선택 영역을 포함하는 객체입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param placeholder String: 입력 필드가 비어있을 때 표시할 힌트 텍스트입니다.
* @param enabled Boolean: 입력 필드의 활성화 여부입니다. false인 경우 사용자 입력이 불가능합니다.
* @param size Size: 입력 필드의 크기입니다. Size.Medium() 또는 Size.Small()을 사용할 수 있습니다.
* @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
* @param enabledOverflowText Boolean: 최대 글자 수를 초과하는 입력을 허용할지 여부입니다.
* @param interactionSource MutableInteractionSource: 사용자 상호작용 상태를 추적하는 소스입니다.
* @param keyboardOptions KeyboardOptions: 키보드 옵션 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
* @param focused State<Boolean>: 입력 필드의 포커스 상태입니다.
* @param textStyle TextStyle: 입력 텍스트의 스타일입니다.
* @param cursorBrush Brush: 커서의 색상을 지정하는 브러시입니다.
* @param focusRequester FocusRequester: 포커스 요청을 처리하는 객체입니다.
* @param onValueChange (TextFieldValue) -> Unit: 텍스트 값이 변경될 때 호출되는 콜백 함수입니다.
*/

/**
* object WantedSearchFieldDefaults
*
* WantedSearchField 컴포넌트에서 사용하는 상수들을 정의하는 객체입니다.
*/

/**
* sealed class Size
*
* 검색 입력 필드의 크기를 정의하는 sealed class입니다.
* Small, Medium, Custom 세 가지 타입이 존재합니다.
*
* @property padding Dp: 입력 필드 내부의 패딩 값입니다.
* @property minHeight Dp: 입력 필드의 최소 높이입니다.
*/

/**
* data class Small
*
* 작은 크기의 검색 입력 필드입니다.
*
* @property padding Dp: 내부 패딩 8dp입니다.
* @property minHeight Dp: 최소 높이 40dp입니다.
*/

/**
* data class Medium
*
* 중간 크기의 검색 입력 필드입니다. (기본값)
*
* @property padding Dp: 내부 패딩 12dp입니다.
* @property minHeight Dp: 최소 높이 48dp입니다.
*/

/**
* data class Custom
*
* 사용자 정의 크기의 검색 입력 필드입니다.
*
* @property padding Dp: 내부 패딩을 직접 지정할 수 있습니다. 기본값은 12dp입니다.
* @property minHeight Dp: 최소 높이를 직접 지정할 수 있습니다. 기본값은 48dp입니다.
*/