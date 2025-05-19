/**
* 사각형 형태의 스켈레톤 UI를 구성하는 컴포저블입니다.
*
* 기본적으로 shimmer 애니메이션이 적용되며, 배경 색상과 shape 커스터마이징이 가능합니다.
*
* 사용 예시:
* ```kotlin
* WantedSkeletonRectangle(modifier = Modifier.size(200.dp))
* ```
*
* @param modifier Modifier: 외형 및 배치를 조정합니다.
* @param shape RoundedCornerShape: 사각형의 모서리 형태를 지정합니다.
* @param color Color: 배경 색상입니다.
*/

/**
* Linear 방식의 shimmer 애니메이션을 적용하는 Modifier 확장 함수입니다.
*
* 주기적으로 좌상단에서 우하단 방향으로 흐르는 그라디언트 효과를 제공합니다.
* 일반적으로 스켈레톤 UI 등에 사용되며, 색상 및 투명도를 설정할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* Modifier.shimmerLinear(
*     colorRes = R.color.fill_alternative,
*     alpha = 0.16f
* )
* ```
*
* @param colorRes Int: 그라디언트 색상의 리소스 ID입니다. 기본값은 `R.color.fill_alternative`입니다.
* @param alpha Float: 투명도 값으로, 0.0 ~ 1.0 사이입니다. 기본값은 `OPACITY_16`입니다.
* @return Modifier: shimmer 애니메이션이 적용된 Modifier를 반환합니다.
*/

/**
* 원형 스켈레톤 UI를 구성하는 컴포저블입니다.
*
* 내부적으로 `WantedSkeletonRectangle`을 `CircleShape`로 설정하여 사용합니다.
*
* 사용 예시:
* ```kotlin
* WantedSkeletonCircle(modifier = Modifier.size(100.dp))
* ```
*
* @param modifier Modifier: 외형 및 배치를 조정합니다.
* @param color Color: 배경 색상입니다.
*/

/**
* 사각형 형태의 스켈레톤 UI 컴포저블입니다.
*
* 로딩 상태를 표현할 때 사용되며, 모서리 곡률과 색상을 지정할 수 있고,
* shimmer 애니메이션이 기본적으로 적용됩니다.
*
* 사용 예시:
* ```kotlin
* WantedSkeletonRectangle(
*     modifier = Modifier.size(200.dp)
* )
* ```
*
* @param modifier Modifier: 레이아웃과 크기를 조절하는 Modifier입니다.
* @param shape RoundedCornerShape: 사각형의 모서리 곡률입니다. 기본값은 3.dp입니다.
* @param color Color: 스켈레톤의 배경 색상입니다. 기본값은 `R.color.fill_normal`입니다.
*/

/**
* 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=23703-75675&m=dev
*/

/**
* 다양한 길이와 정렬을 지원하는 텍스트형 스켈레톤 UI입니다.
*
* enum을 이용해 텍스트 폭 및 정렬 방식을 지정할 수 있습니다.
*
* 사용 예시:
* ```kotlin
* WantedSkeletonText(length = WantedSkeletonLength.Ratio75, align = WantedSkeAlign.Left)
* ```
*
* @param length WantedSkeletonLength: 텍스트 길이 비율입니다.
* @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
* @param align WantedSkeAlign: 정렬 방식입니다.
* @param shape RoundedCornerShape: 모서리 곡률입니다.
*/

/**
* 커스텀 비율 기반으로 텍스트 스켈레톤을 생성하는 오버로드 함수입니다.
*
* 내부 레이아웃은 ConstraintLayout과 Row를 활용해 정렬을 제어하며,
* shimmer 애니메이션이 기본 적용됩니다.
*
* @param widthRadio Float: 텍스트 박스의 너비 비율입니다. (0f ~ 1f)
* @param modifier Modifier: 외형 및 레이아웃 조정용입니다.
* @param align WantedSkeAlign: 정렬 방식입니다.
* @param shape RoundedCornerShape: 스켈레톤 박스의 모서리 곡률입니다.
* @param color Color: 스켈레톤 박스의 배경 색상입니다.
*/

/**
* 텍스트형 스켈레톤의 너비 비율을 정의하는 enum 클래스입니다.
*
* - `Ratio100`: 100% 너비
* - `Ratio75`: 75% 너비
* - `Ratio50`: 50% 너비
* - `Ratio25`: 25% 너비
*/

/**
* 텍스트형 스켈레톤의 정렬 방식을 지정하는 enum 클래스입니다.
*
* - `Left`: 좌측 정렬
* - `Center`: 중앙 정렬
* - `Right`: 우측 정렬
*/