
# WantedButton.md

## 📌 WantedButton

```kotlin
/**
 * 다양한 스타일과 상태를 지원하는 범용 버튼 컴포저블입니다.
 *
 * `WantedButton`은 Solid, Outlined, Text 등의 다양한 버튼 스타일을 지원하며,
 * 버튼의 크기, 색상, 아이콘 등을 세부적으로 커스터마이징할 수 있습니다.
 * 주로 사용자 인터랙션을 유도하거나 특정 동작을 트리거할 때 사용합니다.
 *
 * 사용 예시:
 * ```
 * @Composable
 * fun ExampleUsage() {
 *     WantedButton(
 *         text = "확인",
 *         onClick = { /* 클릭 시 동작 */ }
 *     )
 * }
 * ```
 *
 * @param text 버튼에 표시될 텍스트입니다. 사용자가 버튼의 목적을 쉽게 이해할 수 있도록 명확하게 작성해야 합니다.
 * @param onClick 버튼이 클릭될 때 호출되는 콜백 함수입니다.
 * @param modifier 외부에서 버튼의 레이아웃이나 스타일을 조정할 때 사용하는 Modifier입니다.
 * @param enabled 버튼의 활성화 여부를 설정합니다. `false`로 설정하면 버튼이 비활성화되며 클릭할 수 없습니다.
 * @param style 버튼의 스타일을 지정합니다. Solid, Outlined, Text 등의 옵션이 있습니다.
 * @param size 버튼의 크기를 지정합니다. Small, Medium, Large 등 다양한 사이즈를 지원합니다.
 * @param leftIcon 버튼 텍스트 왼쪽에 표시될 아이콘입니다. 선택적으로 사용할 수 있습니다.
 * @param rightIcon 버튼 텍스트 오른쪽에 표시될 아이콘입니다. 선택적으로 사용할 수 있습니다.
 *
 * Note: 이 컴포저블은 Wanted Design System의 일관된 스타일 가이드를 따릅니다.
 * Important: 버튼에 너무 많은 텍스트나 아이콘을 추가하면 가독성이 떨어질 수 있습니다. 필요한 요소만 추가하는 것을 권장합니다.
 * SeeAlso: [WantedSolidButton], [WantedOutlinedButton], [WantedTextButton]
 */
```

## 📌 ButtonStyle

```kotlin
/**
 * 버튼의 외형 스타일을 정의하는 열거형(enum)입니다.
 *
 * Solid, Outlined, Text 등의 다양한 스타일을 통해 버튼의 용도와 우선순위를 구분할 수 있습니다.
 *
 * 사용 예시:
 * ```
 * WantedButton(
 *     text = "확인",
 *     style = ButtonStyle.Solid,
 *     onClick = { /* 클릭 동작 */ }
 * )
 * ```
 *
 * SeeAlso: [WantedButton]
 */
enum class ButtonStyle {
    Solid,
    Outlined,
    Text
}
```

## 📌 ButtonSize

```kotlin
/**
 * 버튼의 크기를 정의하는 열거형(enum)입니다.
 *
 * 버튼이 표시되는 공간이나 사용 목적에 따라 Small, Medium, Large 크기를 선택할 수 있습니다.
 *
 * 사용 예시:
 * ```
 * WantedButton(
 *     text = "확인",
 *     size = ButtonSize.Large,
 *     onClick = { /* 클릭 동작 */ }
 * )
 * ```
 *
 * SeeAlso: [WantedButton]
 */
enum class ButtonSize {
    Small,
    Medium,
    Large
}
```

## 📌 ButtonColor

```kotlin
/**
 * 버튼의 색상 테마를 정의하는 열거형(enum)입니다.
 *
 * 버튼의 용도와 중요도에 따라 다양한 색상을 선택할 수 있으며, 브랜드 컬러와 일관성을 유지합니다.
 *
 * 사용 예시:
 * ```
 * WantedButton(
 *     text = "제출",
 *     onClick = { /* 클릭 동작 */ },
 *     color = ButtonColor.Primary
 * )
 * ```
 *
 * SeeAlso: [WantedButton]
 */
enum class ButtonColor {
    Primary,
    Secondary,
    Danger
}
```

## 📌 ButtonState

```kotlin
/**
 * 버튼의 시각적/논리적 상태를 정의하는 열거형(enum)입니다.
 *
 * 사용자의 상호작용 및 비즈니스 로직에 따라 버튼의 상태를 변경할 수 있습니다.
 *
 * 사용 예시:
 * ```
 * WantedButton(
 *     text = "로딩 중",
 *     onClick = { /* 클릭 동작 */ },
 *     state = ButtonState.Loading
 * )
 * ```
 *
 * SeeAlso: [WantedButton]
 */
enum class ButtonState {
    Enabled,
    Disabled,
    Loading
}
```
