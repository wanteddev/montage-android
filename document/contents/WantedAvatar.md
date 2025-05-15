---
title: Avatar
description: 다양한 유형의 아바타(Avatar)를 시각적으로 표시하기 위한 컴포저블
image: /components/avatar/design/thumbnail.png
createdAt: 2025-04-29
---

## WantedAvatar

### 개요
`WantedAvatar`는 사용자, 회사, 학교 등 다양한 유형의 아바타(Avatar)를 시각적으로 표시하기 위한 Compose 컴포넌트입니다. 아바타는 이미지, 아이콘, 플레이스홀더, 그룹 스타일, 푸시 뱃지(PushBadge) 등 다양한 요소와 함께 커스터마이징이 가능합니다. 아바타는 클릭 이벤트 핸들링을 지원하며, 리소스 ID 또는 URL 이미지 모델을 사용할 수 있습니다.

### 사용 예시
```kotlin
WantedAvatar(
    modifier = Modifier,
    model = R.drawable.ic_avatar_placeholder_person,
    placeHolder = R.drawable.ic_avatar_placeholder_person,
    size = WantedAvatarSize.Medium,
    type = WantedAvatarType.Person,
    isDrawableRes = true,
    pushBadge = true,
    onClick = { /* 클릭 동작 */ }
)
```

### 파라미터
| 이름 | 타입 | 설명 |
|:---|:---|:---|
| type | WantedAvatarType | 아바타의 유형(Person, Company, Academic)을 지정합니다. |
| size | WantedAvatarSize | 아바타의 크기 및 모서리 반경을 설정합니다. 기본값은 Small입니다. |
| model | Any? | 표시할 이미지 모델로 URL 또는 Drawable ID를 전달합니다. |
| placeHolder | Int? | 이미지 로딩 실패 시 표시할 기본 이미지 리소스 ID입니다. |
| isIcon | Boolean | 내부에 Inner 보더를 추가할지 여부입니다. |
| isDrawableRes | Boolean | `model`이 Drawable 리소스 ID일 경우 true로 설정합니다. |
| isGroup | Boolean | 그룹 스타일 보더를 적용할지 여부입니다. |
| pushBadge | Boolean | 우측 상단에 PushBadge를 표시할지 여부입니다. |
| borderColor | Color | 아바타 외곽선 색상입니다. 기본값은 배경색입니다. |
| modifier | Modifier | 외형 및 배치를 제어하는 Modifier입니다. |
| onClick | (() -> Unit)? | 아바타 클릭 시 호출되는 콜백입니다. |

---
<br />
<br />

## Enum 설명

### WantedAvatarType
| 값 | 설명 |
|:---|:---|
| Person | 사람(개인) 아바타 |
| Company | 회사 아바타 |
| Academic | 학력/학교 아바타 |

### WantedAvatarSize
| 값 | 설명 |
|:---|:---|
| XSmall | 크기 24dp, 모서리 반경 6dp |
| Small | 크기 32dp, 모서리 반경 6dp |
| Medium | 크기 40dp, 모서리 반경 8dp |
| Large | 크기 48dp, 모서리 반경 10dp |
| XLarge | 크기 56dp, 모서리 반경 12dp |

---
<br />
<br />

## Note
- `pushBadge` 옵션은 푸시 알림 상태를 표시할 때 유용합니다.
- `isIcon` 및 `isGroup`은 추가 보더 스타일 적용에 사용됩니다.
- `placeHolder`가 없을 경우 회색 배경 박스로 대체됩니다.
- 내부 이미지 로딩은 Glide 기반이며, `model`이 URL 또는 리소스 ID에 따라 자동 처리됩니다.
