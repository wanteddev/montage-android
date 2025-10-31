/**
* WantedSelect
*
* 단일 항목을 선택할 수 있는 Select 컴포넌트입니다.
*
* 선택 가능한 항목을 BottomSheet로 제공하며, 선택 시 콜백으로 결과를 반환합니다.
*
* 사용 예시:
* ```kotlin
* var selectedValue by remember { mutableStateOf("") }
*
* WantedSelect(
*     value = selectedValue,
*     title = "직무 선택",
*     placeholder = "직무를 선택해주세요",
*     selectValueList = listOf("백엔드", "프론트엔드", "디자이너"),
*     onSelect = { selectedValue = it }
* )
* ```
*
* @param value String: 선택된 현재 값입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param title String?: 상단에 표시할 제목입니다.
* @param description String?: 셀렉트 아래에 표시할 부가 설명입니다.
* @param placeHolder String: 선택 전 표시될 플레이스홀더입니다.
* @param confirmText String: 확인 버튼 텍스트입니다.
* @param isRequiredBadge Boolean: 제목 옆에 필수 표시 뱃지를 보여줄지 여부입니다.
* @param negative Boolean: 오류 상태 여부입니다.
* @param focused Boolean: 포커스 시 테두리 강조 여부입니다.
* @param enabled Boolean: 활성화 여부입니다.
* @param selectValueList List<String>: 선택 가능한 항목 리스트입니다.
* @param selectedValue String?: 초기 선택된 항목입니다.
* @param bottomSheetType ModalType: BottomSheet 형식입니다.
* @param selectType SelectType: 선택 UI 타입입니다.
* @param background Color: 배경 색상입니다.
* @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
* @param onSelect (String) -> Unit: 선택 완료 시 호출되는 콜백입니다.
* @param leadingIcon (@Composable () -> Unit)?: 왼쪽 아이콘 슬롯입니다.
*/

/**
* WantedSelect
*
* WantedSelectData를 사용하는 단일 항목 선택 컴포넌트입니다.
*
* 선택된 항목을 화면에 표시하고, 클릭 시 BottomSheet를 통해 항목을 선택할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* var selectedData by remember { mutableStateOf<WantedSelectData?>(null) }
*
* WantedSelect(
*     title = "직무 선택",
*     selectData = selectedData,
*     selectDataList = listOf(
*         WantedSelectData(id = "1", text = "백엔드"),
*         WantedSelectData(id = "2", text = "프론트엔드")
*     ),
*     onSelectData = { selectedData = it }
* )
* ```
*
* @param selectData WantedSelectData?: 화면에 표시할 현재 선택된 값입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param title String?: 상단에 표시할 제목입니다.
* @param description String?: 셀렉트 아래에 표시할 설명 텍스트입니다.
* @param confirmText String: 확인 버튼 텍스트입니다. 비어 있으면 즉시 선택이 적용됩니다.
* @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
* @param isRequiredBadge Boolean: 제목 우측에 필수 뱃지를 표시할지 여부입니다.
* @param negative Boolean: 오류 상태 여부입니다. true일 경우 강조 색상 및 아이콘이 표시됩니다.
* @param focused Boolean: 포커스 상태 여부입니다. true일 경우 테두리가 강조됩니다.
* @param enabled Boolean: 컴포넌트 활성화 여부입니다.
* @param selectDataList List<WantedSelectData>: 선택 가능한 항목 리스트입니다.
* @param selectedData WantedSelectData?: 초기 선택된 항목입니다.
* @param bottomSheetType ModalType: BottomSheet 형식입니다.
* @param selectType SelectType: 항목 선택 방식입니다.
* @param background Color: 셀렉트 박스의 배경 색상입니다.
* @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다.
* @param onSelectData (WantedSelectData) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
* @param leadingIcon (@Composable () -> Unit)?: 좌측에 표시할 커스텀 아이콘 슬롯입니다.
*/

/**
* WantedSelect
*
* 여러 항목을 선택할 수 있는 멀티 셀렉트 컴포넌트입니다.
*
* 선택된 항목들을 Chip 또는 텍스트 형태로 표시하며, 클릭 시 BottomSheet 형태의 다중 선택 다이얼로그를 표시합니다.
*
* 사용 예시:
* ```kotlin
* var selectedList by remember { mutableStateOf(listOf<WantedSelectData>()) }
*
* WantedSelect(
*     title = "관심 분야",
*     selectedDataList = selectedList,
*     selectDataList = listOf(
*         WantedSelectData(id = "1", text = "개발"),
*         WantedSelectData(id = "2", text = "디자인")
*     ),
*     onSelectDataList = { selectedList = it },
*     onDeleteData = { item ->
*         selectedList = selectedList.filter { it.id != item.id }
*     }
* )
* ```
*
* @param selectedDataList List<WantedSelectData>: 현재 선택된 항목 리스트입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param title String?: 상단에 표시할 제목입니다.
* @param description String?: 셀렉트 아래에 표시할 설명 텍스트입니다.
* @param confirmText String: 확인 버튼 텍스트입니다. 비어 있으면 항목 선택 시 즉시 반영됩니다.
* @param placeHolder String: 선택 전 표시될 안내 텍스트입니다.
* @param isRequiredBadge Boolean: 제목 오른쪽에 필수 입력 뱃지를 표시할지 여부입니다.
* @param negativeDataList List<WantedSelectData>: 오류 표시를 위한 항목 리스트입니다.
* @param focused Boolean: 포커스 강조 상태 여부입니다.
* @param enabled Boolean: 선택 가능 여부입니다.
* @param overflow Boolean: 선택 항목이 넘칠 경우 줄바꿈 처리할지 여부입니다.
* @param selectDataList List<WantedSelectData>: 선택 가능한 전체 항목 리스트입니다.
* @param selectType SelectType: 선택 UI 타입입니다.
* @param render MultiSelectRender: 선택 항목 표시 방식입니다.
* @param background Color: 셀렉트 박스의 배경 색상입니다.
* @param onDeleteData (WantedSelectData) -> Unit: 선택된 항목을 삭제할 때 호출되는 콜백입니다.
* @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다.
* @param onSelectDataList (List<WantedSelectData>) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
* @param leadingIcon (@Composable () -> Unit)?: 셀렉트 박스 왼쪽에 표시할 커스텀 아이콘 슬롯입니다.
*/

/**
* WantedSelectWithString
*
* 문자열 리스트 기반의 멀티 셀렉트 컴포넌트입니다.
*
* 여러 개의 문자열 값을 선택하고, 선택된 항목을 Chip 또는 텍스트 형태로 표시합니다.
*
* 사용 예시:
* ```kotlin
* var selectedList by remember { mutableStateOf(listOf<String>()) }
*
* WantedSelectWithString(
*     title = "기술 스택",
*     selectedValueList = selectedList,
*     selectValueList = listOf("Kotlin", "Java", "Swift"),
*     onSelectList = { selectedList = it },
*     onDelete = { item ->
*         selectedList = selectedList.filter { it != item }
*     }
* )
* ```
*
* @param selectedValueList List<String>: 현재 선택된 문자열 항목 리스트입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param title String?: 상단에 표시할 제목입니다.
* @param description String?: 셀렉트 아래 설명 텍스트입니다.
* @param confirmText String: 확인 버튼에 표시할 텍스트입니다. 비워두면 즉시 반영됩니다.
* @param placeHolder String: 아무 항목도 선택되지 않았을 때 표시되는 안내 텍스트입니다.
* @param isRequiredBadge Boolean: 제목 옆 필수 뱃지를 표시할지 여부입니다.
* @param negativeList List<String>: 오류로 표시할 항목 리스트입니다.
* @param focused Boolean: 포커스 상태 여부입니다.
* @param enabled Boolean: 활성화 여부입니다.
* @param overflow Boolean: Chip 렌더링 시 줄바꿈 여부입니다.
* @param selectValueList List<String>: 선택 가능한 항목 리스트입니다.
* @param selectType SelectType: 선택 방식입니다.
* @param render MultiSelectRender: 선택 항목 렌더링 형태입니다.
* @param background Color: 컴포넌트 배경 색상입니다.
* @param leadingIcon (@Composable () -> Unit)?: 왼쪽에 표시할 선택적 아이콘 슬롯입니다.
* @param onDelete (String) -> Unit: 선택된 항목에서 삭제 버튼 클릭 시 호출되는 콜백입니다.
* @param onClick () -> Unit: 셀렉트 영역 클릭 시 호출되는 콜백입니다.
* @param onSelectList (List<String>) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
*/

/**
* object WantedSelectContract
*
* 셀렉트(Select) 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
*/

/**
* enum class MultiSelectRender
*
* 멀티 셀렉트에서 선택된 항목을 화면에 표시하는 방식을 정의하는 enum 클래스입니다.
*
* 사용 가능한 렌더링 타입은 다음과 같습니다:
* - Chip: 선택된 항목을 Chip 형태로 표시
* - Text: 선택된 항목을 텍스트 형태로 나열
*/

/**
* enum class SelectType
*
* 셀렉트 다이얼로그에서 항목을 선택할 때 사용할 UI 타입을 정의하는 enum 클래스입니다.
*
* 사용 가능한 UI 타입은 다음과 같습니다:
* - CheckMark: 단일 선택 시 체크마크 방식
* - CheckBox: 멀티 선택 시 체크박스 방식
* - Radio: 단일 선택 시 라디오 버튼 방식
*/

/**
* Data class representing a select item data model used in WantedSelect and WantedMultiSelect.
*
* WantedSelect, WantedMultiSelect에서 사용되는 선택 항목 데이터 모델입니다.
*
* 선택 가능한 항목을 구성할 때 텍스트, 아이콘 리소스, URL, 부가 데이터 등을 함께 담을 수 있습니다.
*
* 사용 예시:
* ```kotlin
* val item = WantedSelectData(
*     id = "01",
*     text = "디자인",
*     iconUrl = "https://icon.url",
*     iconRes = R.drawable.ic_design,
*     tint = R.color.primary_normal,
*     any = DesignCategory.UI
* )
* ```
*
* @property id String: 항목의 고유 ID입니다.
* @property text String: 사용자에게 표시할 텍스트입니다.
* @property iconUrl String: 아이콘 이미지 URL입니다.
* @property any Any?: 부가 데이터를 담기 위한 확장 필드입니다.
* @property iconRes Int: Drawable 리소스 ID입니다.
* @property tint Int: 아이콘에 적용할 색상 리소스 ID입니다.
*/

/**
* object WantedSelectDefaults
*
* 셀렉트(Select) 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
*/

/**
* enum class MultiSelectRender
*
* 멀티 셀렉트에서 선택된 항목을 화면에 표시하는 방식을 정의하는 enum 클래스입니다.
*
* 사용 가능한 렌더링 타입은 다음과 같습니다:
* - Chip: 선택된 항목을 Chip 형태로 표시
* - Text: 선택된 항목을 텍스트 형태로 나열
*/

/**
* enum class SelectType
*
* 셀렉트 다이얼로그에서 항목을 선택할 때 사용할 UI 타입을 정의하는 enum 클래스입니다.
*
* 사용 가능한 UI 타입은 다음과 같습니다:
* - CheckMark: 단일 선택 시 체크마크 방식
* - CheckBox: 멀티 선택 시 체크박스 방식
* - Radio: 단일 선택 시 라디오 버튼 방식
*/