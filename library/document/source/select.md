/**
* WantedSelect
*
* 셀렉트 박스 본체만 렌더링하는 단일 선택 컴포넌트입니다.
*
* 라벨·필수 표시(*)·하단 메시지는 포함하지 않습니다. 해당 요소가 필요하면
* WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
* 선택 가능한 항목은 Bottom sheet 로 제공하며, 선택 결과를 콜백으로 반환합니다.
*
* 사용 예시:
* ```kotlin
* var selectedData by remember { mutableStateOf<WantedSelectData?>(null) }
* val selectDataList = listOf(
*     WantedSelectData(id = "1", text = "백엔드"),
*     WantedSelectData(id = "2", text = "프론트엔드")
* )
*
* WantedFormControl(
*     label = "직무",
*     required = true,
*     description = "직무를 선택해 주세요."
* ) {
*     WantedSelect(
*         selectData = selectedData,
*         placeHolder = "선택해 주세요.",
*         enabled = true,
*         selectDataList = selectDataList,
*         onSelectData = { selectedData = it }
*     )
* }
* ```
*
* @param selectData WantedSelectData?: 화면에 표시할 현재 선택된 값입니다. null 이거나 text 가 비어 있으면 placeHolder 를 표시합니다.
* @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
* @param enabled Boolean: 선택 가능 여부입니다. false 면 클릭이 막히고 disable 스타일이 적용됩니다.
* @param selectDataList List<WantedSelectData>: Bottom sheet 에 표시할 선택 가능한 항목 리스트입니다. 비어 있으면 Bottom sheet 를 열지 않습니다.
* @param modifier Modifier: 레이아웃 및 스타일을 설정합니다.
* @param status WantedSelectDefaults.Status: 셀렉트 박스의 상태입니다. (Normal, Negative) Negative 면 border 가 red 로 바뀌고 focus 시 red ring 을 표시합니다.
* @param focused Boolean: 포커스 상태 여부입니다. true 면 primary ring 과 border 를 표시하고 chevron 이 위를 향합니다.
* @param confirmText String: Bottom sheet 확인 버튼 텍스트입니다. 비어 있으면 항목 선택 시 즉시 반영됩니다.
* @param selectedData WantedSelectData?: Bottom sheet 진입 시 체크 표시할 항목입니다.
* @param bottomSheetType WantedModalContract.ModalType: Bottom sheet 형식입니다. (Flexible, Fixed 등)
* @param selectType WantedSelectDefaults.SelectType: Bottom sheet 항목의 선택 UI 타입입니다. (CheckMark, CheckBox, Radio)
* @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
* @param background Color: 셀렉트 박스의 배경 색상입니다. enabled 가 false 면 무시됩니다.
* @param leadingIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다. Icon / Icon button / custom 을 넣을 수 있습니다.
* @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다. Bottom sheet 노출과 별개로 호출됩니다.
* @param onSelectData (WantedSelectData) -> Unit: 항목 선택이 확정됐을 때 호출되는 콜백입니다.
*/

/**
* WantedSelect
*
* 셀렉트 박스 본체만 렌더링하는 다중 선택 컴포넌트입니다.
*
* 라벨·필수 표시(*)·하단 메시지는 포함하지 않습니다. 해당 요소가 필요하면
* WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
* 선택된 항목은 render 값에 따라 Chip 또는 텍스트로 표시합니다.
*
* 사용 예시:
* ```kotlin
* var selectedDataList by remember { mutableStateOf(listOf<WantedSelectData>()) }
* val selectDataList = listOf(
*     WantedSelectData(id = "1", text = "개발"),
*     WantedSelectData(id = "2", text = "디자인")
* )
*
* WantedFormControl(
*     label = "관심 분야",
*     required = true
* ) {
*     WantedSelect(
*         selectedDataList = selectedDataList,
*         placeHolder = "선택해 주세요.",
*         enabled = true,
*         selectDataList = selectDataList,
*         render = WantedSelectDefaults.MultiSelectRender.Chip,
*         onDeleteData = { item -> selectedDataList = selectedDataList - item },
*         onSelectDataList = { selectedDataList = it }
*     )
* }
* ```
*
* @param selectedDataList List<WantedSelectData>: 현재 선택된 항목 리스트입니다. 비어 있으면 placeHolder 를 표시합니다.
* @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
* @param enabled Boolean: 선택 가능 여부입니다. false 면 클릭이 막히고 disable 스타일이 적용됩니다.
* @param selectDataList List<WantedSelectData>: Bottom sheet 에 표시할 선택 가능한 항목 리스트입니다. 비어 있으면 Bottom sheet 를 열지 않습니다.
* @param modifier Modifier: 레이아웃 및 스타일을 설정합니다.
* @param status WantedSelectDefaults.Status: 셀렉트 박스의 상태입니다. (Normal, Negative) Negative 면 border 가 red 로 바뀌고 focus 시 red ring 을 표시합니다.
* @param errorDataList List<WantedSelectData>: 오류로 표시할 항목 리스트입니다. 포함된 Chip 이 negative 스타일로 표시됩니다. 컨테이너 상태는 status 가 결정합니다.
* @param focused Boolean: 포커스 상태 여부입니다. true 면 primary ring 과 border 를 표시하고 chevron 이 위를 향합니다.
* @param confirmText String: Bottom sheet 확인 버튼 텍스트입니다. 비어 있으면 항목을 토글할 때마다 즉시 반영되고 Bottom sheet 가 열린 상태로 유지됩니다.
* @param overflow Boolean: 선택 항목이 가로로 넘칠 때 줄바꿈할지 여부입니다. false 면 가로 스크롤로 표시합니다.
* @param selectType WantedSelectDefaults.SelectType: Bottom sheet 항목의 선택 UI 타입입니다. (CheckMark, CheckBox, Radio)
* @param render WantedSelectDefaults.MultiSelectRender: 선택 항목 표시 방식입니다. (Chip, Text)
* @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
* @param background Color: 셀렉트 박스의 배경 색상입니다. Chip 가로 스크롤 시 gradient 색상으로도 사용됩니다.
* @param leadingIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다. Icon / Icon button / custom 을 넣을 수 있습니다.
* @param onDeleteData (WantedSelectData) -> Unit: Chip 의 삭제 버튼을 눌렀을 때 호출되는 콜백입니다.
* @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다. Bottom sheet 노출과 별개로 호출됩니다.
* @param onSelectDataList (List<WantedSelectData>) -> Unit: 항목 선택이 반영됐을 때 호출되는 콜백입니다.
*/

/**
* WantedSelect
*
* 셀렉트 박스 본체만 렌더링하는 문자열 기반 단일 선택 컴포넌트입니다.
*
* 항목이 텍스트뿐이라 WantedSelectData 가 필요 없을 때 사용하는 편의 오버로드입니다.
* 내부적으로 문자열을 `WantedSelectData(text = it)` 로 변환해 WantedSelect(selectData = ...) 에 위임합니다.
* 라벨·필수 표시(*)·하단 메시지가 필요하면 WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
*
* 사용 예시:
* ```kotlin
* var selectedValue by remember { mutableStateOf("") }
*
* WantedFormControl(label = "직무", required = true) {
*     WantedSelect(
*         value = selectedValue,
*         placeHolder = "선택해 주세요.",
*         enabled = true,
*         selectValueList = listOf("백엔드", "프론트엔드", "디자이너"),
*         onSelect = { selectedValue = it }
*     )
* }
* ```
*
* @param value String: 화면에 표시할 현재 선택된 값입니다. 비어 있으면 placeHolder 를 표시합니다.
* @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
* @param enabled Boolean: 선택 가능 여부입니다. false 면 클릭이 막히고 disable 스타일이 적용됩니다.
* @param selectValueList List<String>: Bottom sheet 에 표시할 선택 가능한 항목 리스트입니다. 비어 있으면 Bottom sheet 를 열지 않습니다.
* @param modifier Modifier: 레이아웃 및 스타일을 설정합니다.
* @param status WantedSelectDefaults.Status: 셀렉트 박스의 상태입니다. (Normal, Negative) Negative 면 border 가 red 로 바뀌고 focus 시 red ring 을 표시합니다.
* @param focused Boolean: 포커스 상태 여부입니다. true 면 primary ring 과 border 를 표시하고 chevron 이 위를 향합니다.
* @param confirmText String: Bottom sheet 확인 버튼 텍스트입니다. 비어 있으면 항목 선택 시 즉시 반영됩니다.
* @param selectedValue String?: Bottom sheet 진입 시 체크 표시할 항목입니다.
* @param bottomSheetType WantedModalContract.ModalType: Bottom sheet 형식입니다. (Flexible, Fixed 등)
* @param selectType WantedSelectDefaults.SelectType: Bottom sheet 항목의 선택 UI 타입입니다. (CheckMark, CheckBox, Radio)
* @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
* @param background Color: 셀렉트 박스의 배경 색상입니다. enabled 가 false 면 무시됩니다.
* @param leadingIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다. Icon / Icon button / custom 을 넣을 수 있습니다.
* @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다. Bottom sheet 노출과 별개로 호출됩니다.
* @param onSelect (String) -> Unit: 항목 선택이 확정됐을 때 호출되는 콜백입니다.
*/

/**
* WantedSelect
*
* 셀렉트 박스 본체만 렌더링하는 문자열 기반 다중 선택 컴포넌트입니다.
*
* 항목이 텍스트뿐이라 WantedSelectData 가 필요 없을 때 사용하는 편의 오버로드입니다.
* 내부적으로 문자열을 `WantedSelectData(text = it)` 로 변환해 WantedSelect(selectedDataList = ...) 에 위임합니다.
* 라벨·필수 표시(*)·하단 메시지가 필요하면 WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
*
* `List<WantedSelectData>` 오버로드와 JVM 시그니처가 겹치므로 @JvmName 으로 이름을 구분합니다.
*
* 사용 예시:
* ```kotlin
* var selectedValueList by remember { mutableStateOf(listOf<String>()) }
*
* WantedFormControl(label = "기술 스택") {
*     WantedSelect(
*         valueList = selectedValueList,
*         placeHolder = "선택해 주세요.",
*         enabled = true,
*         selectValueList = listOf("Kotlin", "Java", "Swift"),
*         render = WantedSelectDefaults.MultiSelectRender.Chip,
*         onDelete = { deleted -> selectedValueList = selectedValueList - deleted },
*         onSelectList = { selectedValueList = it }
*     )
* }
* ```
*
* @param valueList List<String>: 현재 선택된 항목 리스트입니다. 비어 있으면 placeHolder 를 표시합니다.
* @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
* @param enabled Boolean: 선택 가능 여부입니다. false 면 클릭이 막히고 disable 스타일이 적용됩니다.
* @param selectValueList List<String>: Bottom sheet 에 표시할 선택 가능한 항목 리스트입니다. 비어 있으면 Bottom sheet 를 열지 않습니다.
* @param modifier Modifier: 레이아웃 및 스타일을 설정합니다.
* @param status WantedSelectDefaults.Status: 셀렉트 박스의 상태입니다. (Normal, Negative) Negative 면 border 가 red 로 바뀌고 focus 시 red ring 을 표시합니다.
* @param errorList List<String>: 오류로 표시할 항목 리스트입니다. 포함된 Chip 이 negative 스타일로 표시됩니다. 컨테이너 상태는 status 가 결정합니다.
* @param focused Boolean: 포커스 상태 여부입니다. true 면 primary ring 과 border 를 표시하고 chevron 이 위를 향합니다.
* @param confirmText String: Bottom sheet 확인 버튼 텍스트입니다. 비어 있으면 항목을 토글할 때마다 즉시 반영되고 Bottom sheet 가 열린 상태로 유지됩니다.
* @param overflow Boolean: 선택 항목이 가로로 넘칠 때 줄바꿈할지 여부입니다. false 면 가로 스크롤로 표시합니다.
* @param selectType WantedSelectDefaults.SelectType: Bottom sheet 항목의 선택 UI 타입입니다. (CheckMark, CheckBox, Radio)
* @param render WantedSelectDefaults.MultiSelectRender: 선택 항목 표시 방식입니다. (Chip, Text)
* @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
* @param background Color: 셀렉트 박스의 배경 색상입니다. Chip 가로 스크롤 시 gradient 색상으로도 사용됩니다.
* @param leadingIcon (@Composable () -> Unit)?: 좌측 아이콘 슬롯입니다. Icon / Icon button / custom 을 넣을 수 있습니다.
* @param onDelete (String) -> Unit: Chip 의 삭제 버튼을 눌렀을 때 호출되는 콜백입니다.
* @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다. Bottom sheet 노출과 별개로 호출됩니다.
* @param onSelectList (List<String>) -> Unit: 항목 선택이 반영됐을 때 호출되는 콜백입니다.
*/

/**
* WantedSelect
*
* (Deprecated) 제목·메시지 레이아웃을 함께 렌더링하는 문자열 기반 단일 선택 컴포넌트입니다.
*
* title·isRequiredBadge·description 은 WantedFormControl 의 label·required·description 으로
* 대체되었습니다. 셀렉트 박스 본체만 담당하는 WantedSelect(selectData = ...) 를
* WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
*
* 사용 예시:
* ```kotlin
* // 대체 방식
* WantedFormControl(
*     label = "직무",
*     required = true,
*     description = "메시지에 마침표를 찍어요.",
*     status = WantedFormControlDefaults.Status.Negative
* ) {
*     WantedSelect(
*         selectData = WantedSelectData(text = selectedValue),
*         placeHolder = "선택해 주세요.",
*         status = WantedSelectDefaults.Status.Negative,
*         enabled = true,
*         selectDataList = selectValueList.map { WantedSelectData(text = it) },
*         onSelectData = { selectedValue = it.text }
*     )
* }
* ```
*
* @param value String: 선택된 현재 값입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param title String?: 상단에 표시할 제목입니다.
* @param description String?: 셀렉트 아래에 표시할 부가 설명입니다.
* @param placeHolder String: 선택 전 표시될 플레이스홀더입니다.
* @param confirmText String: 확인 버튼 텍스트입니다. 비어 있으면 즉시 선택이 적용됩니다.
* @param isRequiredBadge Boolean: 제목 옆에 필수 표시 뱃지를 보여줄지 여부입니다.
* @param negative Boolean: 오류 상태 여부입니다.
* @param focused Boolean: 포커스 시 테두리 강조 여부입니다.
* @param enabled Boolean: 활성화 여부입니다.
* @param selectValueList List<String>: 선택 가능한 항목 리스트입니다.
* @param selectedValue String?: 초기 선택된 항목입니다.
* @param bottomSheetType WantedModalContract.ModalType: BottomSheet 형식입니다.
* @param selectType WantedSelectDefaults.SelectType: 선택 UI 타입입니다.
* @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
* @param background Color: 배경 색상입니다.
* @param onClick () -> Unit: 클릭 시 호출되는 콜백입니다.
* @param onSelect (String) -> Unit: 선택 완료 시 호출되는 콜백입니다.
* @param leadingIcon (@Composable () -> Unit)?: 왼쪽 아이콘 슬롯입니다.
*/

/**
* WantedSelect
*
* (Deprecated) 제목·메시지 레이아웃을 함께 렌더링하는 WantedSelectData 기반 단일 선택 컴포넌트입니다.
*
* title·isRequiredBadge·description 은 WantedFormControl 의 label·required·description 으로
* 대체되었습니다. 셀렉트 박스 본체만 담당하는 WantedSelect(selectData = ...) 를
* WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
*
* 사용 예시:
* ```kotlin
* // 대체 방식
* WantedFormControl(label = "직무", required = true) {
*     WantedSelect(
*         selectData = selectedData,
*         placeHolder = "선택해 주세요.",
*         enabled = true,
*         selectDataList = selectDataList,
*         onSelectData = { selectedData = it }
*     )
* }
* ```
*
* @param selectData WantedSelectData?: 화면에 표시할 현재 선택된 값입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param title String?: 상단에 표시할 제목입니다.
* @param description String?: 셀렉트 아래에 표시할 설명 텍스트입니다.
* @param confirmText String: 확인 버튼 텍스트입니다. 비어 있으면 즉시 선택이 적용됩니다.
* @param placeHolder String: 선택 전 표시되는 플레이스홀더 텍스트입니다.
* @param isRequiredBadge Boolean: 제목 우측에 필수 뱃지를 표시할지 여부입니다.
* @param negative Boolean: 오류 상태 여부입니다.
* @param focused Boolean: 포커스 상태 여부입니다.
* @param enabled Boolean: 컴포넌트 활성화 여부입니다.
* @param selectDataList List<WantedSelectData>: 선택 가능한 항목 리스트입니다.
* @param selectedData WantedSelectData?: 초기 선택된 항목입니다.
* @param bottomSheetType WantedModalContract.ModalType: BottomSheet 형식입니다.
* @param selectType WantedSelectDefaults.SelectType: 항목 선택 방식입니다.
* @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
* @param background Color: 셀렉트 박스의 배경 색상입니다.
* @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다.
* @param onSelectData (WantedSelectData) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
* @param leadingIcon (@Composable () -> Unit)?: 좌측에 표시할 커스텀 아이콘 슬롯입니다.
*/

/**
* WantedSelect
*
* (Deprecated) 제목·메시지 레이아웃을 함께 렌더링하는 WantedSelectData 기반 다중 선택 컴포넌트입니다.
*
* title·isRequiredBadge·description 은 WantedFormControl 의 label·required·description 으로
* 대체되었습니다. 셀렉트 박스 본체만 담당하는 WantedSelect(selectedDataList = ...) 를
* WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
* negativeDataList 는 errorDataList 로 이름이 바뀌었습니다.
*
* 사용 예시:
* ```kotlin
* // 대체 방식
* WantedFormControl(label = "관심 분야", required = true) {
*     WantedSelect(
*         selectedDataList = selectedDataList,
*         placeHolder = "선택해 주세요.",
*         enabled = true,
*         selectDataList = selectDataList,
*         onDeleteData = { item -> selectedDataList = selectedDataList - item },
*         onSelectDataList = { selectedDataList = it }
*     )
* }
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
* @param selectType WantedSelectDefaults.SelectType: 선택 UI 타입입니다.
* @param render WantedSelectDefaults.MultiSelectRender: 선택 항목 표시 방식입니다.
* @param size WantedSelectDefaults.Size: 셀렉트 박스의 크기입니다. (Large, Medium)
* @param background Color: 셀렉트 박스의 배경 색상입니다.
* @param onDeleteData (WantedSelectData) -> Unit: 선택된 항목을 삭제할 때 호출되는 콜백입니다.
* @param onClick () -> Unit: 셀렉트 박스 클릭 시 호출되는 콜백입니다.
* @param onSelectDataList (List<WantedSelectData>) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
* @param leadingIcon (@Composable () -> Unit)?: 셀렉트 박스 왼쪽에 표시할 커스텀 아이콘 슬롯입니다.
*/

/**
* WantedSelectWithString
*
* (Deprecated) 제목·메시지 레이아웃을 함께 렌더링하는 문자열 리스트 기반 다중 선택 컴포넌트입니다.
*
* title·isRequiredBadge·description 은 WantedFormControl 의 label·required·description 으로
* 대체되었습니다. 셀렉트 박스 본체만 담당하는 WantedSelect(selectedDataList = ...) 를
* WantedFormControl 의 input 슬롯에 조합해서 사용합니다.
* 문자열은 `WantedSelectData(text = it)` 로 변환해서 전달합니다.
*
* 사용 예시:
* ```kotlin
* // 대체 방식
* WantedFormControl(label = "기술 스택") {
*     WantedSelect(
*         selectedDataList = selectedValueList.map { WantedSelectData(text = it) },
*         placeHolder = "선택해 주세요.",
*         enabled = true,
*         selectDataList = selectValueList.map { WantedSelectData(text = it) },
*         onDeleteData = { item -> selectedValueList = selectedValueList - item.text },
*         onSelectDataList = { itemList -> selectedValueList = itemList.map { it.text } }
*     )
* }
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
* @param selectType WantedSelectDefaults.SelectType: 선택 방식입니다.
* @param render WantedSelectDefaults.MultiSelectRender: 선택 항목 렌더링 형태입니다.
* @param size WantedSelectDefaults.Size: 컴포넌트 크기입니다. (Large, Medium)
* @param background Color: 컴포넌트 배경 색상입니다.
* @param leadingIcon (@Composable () -> Unit)?: 왼쪽에 표시할 선택적 아이콘 슬롯입니다.
* @param onDelete (String) -> Unit: 선택된 항목에서 삭제 버튼 클릭 시 호출되는 콜백입니다.
* @param onClick () -> Unit: 셀렉트 영역 클릭 시 호출되는 콜백입니다.
* @param onSelectList (List<String>) -> Unit: 항목 선택 완료 후 호출되는 콜백입니다.
*/

/** #3 상태 색 border 뒤에 깔리는 underlay border 색상입니다. */

/** #3 Container border 색상입니다. Negative + Focused 는 opacity 를 52% 로 올려 강조합니다. */

/**
* data class WantedSelectData
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
*     tint = R.color.foreground_brand_primary,
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
* Select 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
*/

/**
* enum class MultiSelectRender
*
* Multi Select 에서 선택된 항목을 화면에 표시하는 방식을 정의하는 enum 클래스입니다.
*
* 사용 가능한 렌더링 타입은 다음과 같습니다:
* - Chip: 선택된 항목을 Chip 형태로 표시
* - Text: 선택된 항목을 텍스트 형태로 나열
*/

/**
* enum class SelectType
*
* Select Dialog 에서 항목을 선택할 때 사용할 UI 타입을 정의하는 enum 클래스입니다.
*
* 사용 가능한 UI 타입은 다음과 같습니다:
* - CheckMark: 단일 선택 시 체크마크 방식
* - CheckBox: 멀티 선택 시 체크박스 방식
* - Radio: 단일 선택 시 라디오 버튼 방식
*/

/**
* enum class Status
*
* Select의 상태를 정의하는 enum 클래스입니다. (WantedTextArea 의 Status 와 동일한 축)
* - Normal: 일반 상태입니다.
* - Negative: 부정(에러) 상태입니다.
*/

/**
* enum class Size
*
* Select의 크기를 정의하는 enum 클래스입니다.
* 각 크기에 따라 padding, radius, 최소 높이, 입력 typography, leading content 크기,
* Chip 간격이 달라집니다.
* - Large: 큰 크기입니다. (최소 높이 48dp)
* - Medium: 중간 크기입니다. (최소 높이 40dp)
*/