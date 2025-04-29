# AvatarGroup

## WantedAvatarGroup

### 개요
여러 개의 아바타를 그룹 형태로 보여주는 컴포넌트입니다.

모델 리스트를 받아 좌우로 겹쳐진 형태로 아바타들을 표시하며, 필요 시 추가 텍스트나 컴포넌트를 우측에 표시할 수 있습니다. 
아바타는 Drawable 리소스 또는 URL 기반의 이미지를 지원합니다.

### 사용 예시
```kotlin
WantedAvatarGroup(
    modifier = Modifier,
    modelList = listOf(
        R.drawable.ic_avatar_placeholder_person,
        R.drawable.ic_avatar_placeholder_person,
        R.drawable.ic_avatar_placeholder_person
    ),
    placeHolder = R.drawable.ic_avatar_placeholder_person,
    size = WantedAvatarSize.XLarge,
    type = WantedAvatarType.Person,
    isDrawableRes = true,
    isIcon = false
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 외부에서 전달받는 Modifier로 레이아웃 커스터마이징에 사용됩니다. |
| modelList | List<Any> | 표시할 아바타 모델 리스트입니다. (URL 또는 Drawable ID) |
| placeHolder | Int? | 이미지 로딩 실패 시 표시할 Drawable 리소스 ID입니다. |
| size | WantedAvatarSize | 아바타 크기 및 모서리 반경을 정의합니다. |
| type | WantedAvatarType | 아바타의 유형(Person, Company, Academic)을 지정합니다. |
| isIcon | Boolean | 아바타를 아이콘 스타일로 표시할지 여부를 설정합니다. |
| isDrawableRes | Boolean | 모델이 Drawable 리소스인지 여부를 나타냅니다. |
| trailingContent | (@Composable (Dp) -> Unit)? | 아바타 그룹 우측에 추가적으로 표시할 컴포저블 콘텐츠입니다. |

---

## Enum 설명

### WantedAvatarType
| 값 | 설명 |
|:---|:---|
| Person | 개인(사람) 아바타 타입입니다. |
| Company | 회사 아바타 타입입니다. |
| Academic | 학력/학교 아바타 타입입니다. |

### WantedAvatarSize
| 값 | 설명 |
|:---|:---|
| XSmall | 24dp 크기, 6dp 반경을 가진 매우 작은 아바타입니다. |
| Small | 32dp 크기, 6dp 반경을 가진 작은 아바타입니다. |
| Medium | 40dp 크기, 8dp 반경을 가진 중간 크기 아바타입니다. |
| Large | 48dp 크기, 10dp 반경을 가진 큰 아바타입니다. |
| XLarge | 56dp 크기, 12dp 반경을 가진 매우 큰 아바타입니다. |

---

## Note
- 아바타를 겹쳐서 표현할 때 zIndex를 이용해 뒤쪽 아바타가 가려지지 않도록 처리합니다.
- trailingContent는 보통 "외 1명"과 같은 텍스트 표현에 활용됩니다.

## SeeAlso
- [WantedAvatar](./WantedAvatar)
- [WantedAvatarContract](./WantedAvatarContract)