# Avatar

## WantedAvatar

### 개요
WantedAvatar 컴포넌트는 사람, 회사, 학력 등 다양한 유형의 프로필 이미지를 표시하기 위해 사용됩니다.
이미지 또는 아이콘 형태로 표시할 수 있으며, 다양한 크기와 스타일 조합이 가능합니다.
또한, 알림 뱃지(PushBadge)를 표시하거나 클릭 이벤트를 받을 수 있어 유저 인터랙션 요소로도 활용됩니다.

### 사용 예시
```kotlin
WantedAvatar(
    modifier = Modifier,
    model = R.drawable.ic_avatar_placeholder_person,
    placeHolder = R.drawable.ic_avatar_placeholder_person,
    size = WantedAvatarSize.XLarge,
    type = WantedAvatarType.Person,
    isDrawableRes = true,
    pushBadge = true,
    onClick = {}
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| modifier | Modifier | 아바타 컴포넌트의 크기 및 배치를 조정합니다. |
| model | Any? | 표시할 이미지 또는 리소스입니다. URL 또는 Drawable ID를 받을 수 있습니다. |
| placeHolder | Int? | 이미지를 불러오는 동안 또는 실패했을 때 표시할 플레이스홀더 리소스 ID입니다. |
| size | WantedAvatarSize | 아바타의 크기 및 모양을 정의합니다. 기본값은 Small입니다. |
| type | WantedAvatarType | 아바타의 타입(사람, 회사, 학력)을 지정합니다. |
| isIcon | Boolean | 아바타 내부에 아이콘 스타일 보더를 적용할지 여부를 설정합니다. 기본값은 false입니다. |
| isDrawableRes | Boolean | 모델이 Drawable 리소스인지 여부를 설정합니다. 기본값은 false입니다. |
| isGroup | Boolean | 그룹 아바타 스타일을 적용할지 여부를 설정합니다. 기본값은 false입니다. |
| barderColor | Color | 외곽선(Border)의 색상을 지정합니다. 기본은 배경색입니다. |
| pushBadge | Boolean | 푸시 뱃지를 표시할지 여부를 설정합니다. 기본값은 false입니다. |
| onClick | (() -> Unit)? | 클릭 이벤트 콜백입니다. 선택 사항입니다. |

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
- 기본적으로 사람(Person) 타입은 원형, 회사(Company) 및 학력(Academic) 타입은 모서리가 둥근 사각형 형태로 표시됩니다.
- `pushBadge` 옵션을 사용하면 우측 상단에 작은 알림 뱃지를 표시할 수 있습니다.
- 다양한 스타일 조합을 통해 아이콘 아바타, 그룹 아바타 등으로 확장 가능합니다.

## SeeAlso
- [WantedAvatarPerson](#)
- [WantedAvatarContent](#)
- [WantedAvatarContract.WantedAvatarType](#)
- [WantedAvatarContract.WantedAvatarSize](#)
