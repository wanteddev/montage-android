/**
* WantedSearchField
*
* 검색 입력 필드 컴포넌트입니다.
*
* String 타입의 텍스트를 받아 검색 기능을 제공하는 입력 필드를 표시합니다.
* 검색 아이콘이 항상 표시되고, 입력값이 있으면 삭제 버튼이 표시됩니다.
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
* @param variant Variant: 입력 필드의 스타일입니다. Solid 또는 Outlined를 사용할 수 있습니다.
* @param size Size: 입력 필드의 크기입니다. Size.Large 또는 Size.Medium을 사용할 수 있습니다.
* @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
* @param enabledOverflowText Boolean: 최대 글자 수를 초과하는 입력을 허용할지 여부입니다.
* @param interactionSource MutableInteractionSource: 사용자 상호작용 상태를 추적하는 소스입니다.
* @param keyboardOptions KeyboardOptions: 키보드 옵션 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
* @param textStyle TextStyle?: 입력 텍스트의 스타일입니다. null이면 size별 기본 typography를 사용합니다.
* @param cursorBrush Brush?: 커서의 색상을 지정하는 브러시입니다. null이면 primary 색상을 사용합니다.
* @param focusRequester FocusRequester: 포커스 요청을 처리하는 객체입니다.
* @param onValueChange (String) -> Unit: 텍스트 값이 변경될 때 호출되는 콜백 함수입니다.
*/

/**
* WantedSearchField
*
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
* @param variant Variant: 입력 필드의 스타일입니다. Solid 또는 Outlined를 사용할 수 있습니다.
* @param size Size: 입력 필드의 크기입니다. Size.Large 또는 Size.Medium을 사용할 수 있습니다.
* @param maxWordCount Int: 입력 가능한 최대 글자 수입니다.
* @param enabledOverflowText Boolean: 최대 글자 수를 초과하는 입력을 허용할지 여부입니다.
* @param interactionSource MutableInteractionSource: 사용자 상호작용 상태를 추적하는 소스입니다.
* @param keyboardOptions KeyboardOptions: 키보드 옵션 설정입니다.
* @param keyboardActions KeyboardActions: 키보드 액션 설정입니다.
* @param textStyle TextStyle?: 입력 텍스트의 스타일입니다. null이면 size별 기본 typography를 사용합니다.
* @param cursorBrush Brush?: 커서의 색상을 지정하는 브러시입니다. null이면 primary 색상을 사용합니다.
* @param focusRequester FocusRequester: 포커스 요청을 처리하는 객체입니다.
* @param onValueChange (TextFieldValue) -> Unit: 텍스트 값이 변경될 때 호출되는 콜백 함수입니다.
*/

/** 최대 글자 수 정책을 적용한 값을 반환합니다. */

/** 값이 비어 있을 때만 placeholder 슬롯을 반환합니다. */

/** 좌측 검색 아이콘 슬롯을 반환합니다. */

/** Variant·활성 상태별 Container 보더 색상입니다. Solid는 보더를 사용하지 않습니다. */

/** Size × 상태 조합을 한 번에 보여 주는 preview 본문입니다. */

/**
* object WantedSearchFieldDefaults
*
* WantedSearchField 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
*
* 치수·타이포는 WantedTextField 와 동일한 값을 사용하며,
* Size 별 토큰은 아래 internal 확장 프로퍼티로 분리되어 있습니다.
*/

/**
* sealed class Size
*
* 검색 입력 필드의 크기를 정의하는 sealed class입니다.
* 각 크기에 따라 padding, radius, 최소 높이, 입력 typography, 아이콘 크기가 달라집니다.
* - Large: 큰 크기입니다. (최소 높이 48dp)
* - Medium: 중간 크기입니다. (최소 높이 40dp)
*/

/**
* enum class Variant
*
* 검색 입력 필드의 스타일을 정의하는 enum 클래스입니다.
* - Solid: 채워진 배경을 사용하고 보더가 없습니다.
* - Outlined: 투명한 배경 위에 1dp 보더를 사용합니다.
*/