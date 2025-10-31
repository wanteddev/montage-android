/**
* shimmer
*
* 깜박이는 shimmer 애니메이션을 적용하는 Modifier 확장 함수입니다.
*
* 투명도를 주기적으로 변경하여 깜박이는 효과를 제공합니다.
* 주로 스켈레톤 UI에 사용됩니다.
*
* 사용 예시:
* ```kotlin
* Box(
*     modifier = Modifier
*         .size(200.dp)
*         .shimmer()
* )
* ```
*
* @param colorRes Int: 적용할 색상 리소스 ID입니다.
* @return Modifier: shimmer 애니메이션이 적용된 Modifier입니다.
*/

/**
* shimmerLinear
*
* Linear 방식의 shimmer 애니메이션을 적용하는 Modifier 확장 함수입니다.
*
* 좌상단에서 우하단 방향으로 흐르는 그라디언트 효과를 주기적으로 반복합니다.
* 주로 스켈레톤 UI에 사용됩니다.
*
* 사용 예시:
* ```kotlin
* Box(
*     modifier = Modifier
*         .size(200.dp)
*         .shimmerLinear(
*             colorRes = R.color.fill_alternative,
*             alpha = 0.16f
*         )
* )
* ```
*
* @param colorRes Int: 그라디언트 색상 리소스 ID입니다.
* @param alpha Float: 투명도 값입니다. 0.0 ~ 1.0 사이의 값을 사용합니다.
* @return Modifier: shimmer 애니메이션이 적용된 Modifier입니다.
*/

/**
* WantedSkeletonCircle
*
* 원형 스켈레톤 UI 컴포넌트입니다.
*
* 내부적으로 WantedSkeletonRectangle을 CircleShape로 설정하여 사용합니다.
*
* 사용 예시:
* ```kotlin
* WantedSkeletonCircle(
*     modifier = Modifier.size(100.dp)
* )
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param color Color: 배경 색상입니다.
*/

/**
* WantedSkeletonRectangle
*
* 사각형 스켈레톤 UI 컴포넌트입니다.
*
* 로딩 상태를 표현할 때 사용되며, shimmer 애니메이션이 자동으로 적용됩니다.
*
* 사용 예시:
* ```kotlin
* WantedSkeletonRectangle(
*     modifier = Modifier.size(200.dp, 50.dp),
*     shape = RoundedCornerShape(8.dp)
* )
* ```
*
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param shape RoundedCornerShape: 사각형의 모서리 곡률입니다.
* @param color Color: 스켈레톤의 배경 색상입니다.
*/

/**
* 피그마 : https://www.figma.com/design/7RHtWV3Pw6I98UEDjbx5V1/0-Component?node-id=23703-75675&m=dev
*/

/**
* WantedSkeletonText
*
* 텍스트형 스켈레톤 UI 컴포넌트입니다.
*
* 다양한 길이 비율과 정렬 방식을 지원하며, shimmer 애니메이션이 자동으로 적용됩니다.
*
* 사용 예시:
* ```kotlin
* WantedSkeletonText(
*     length = WantedSkeletonLength.Ratio75,
*     align = WantedSkeAlign.Left
* )
* ```
*
* @param length WantedSkeletonLength: 텍스트 길이 비율입니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param align WantedSkeAlign: 정렬 방식입니다.
* @param shape RoundedCornerShape: 모서리 곡률입니다.
*/

/**
* WantedSkeletonText
*
* 커스텀 비율 기반의 텍스트형 스켈레톤 UI 컴포넌트입니다.
*
* widthRadio 파라미터를 통해 너비 비율을 직접 지정할 수 있습니다.
*
* @param widthRadio Float: 텍스트 박스의 너비 비율입니다. 0.0 ~ 1.0 사이의 값을 사용합니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
* @param align WantedSkeAlign: 정렬 방식입니다.
* @param shape RoundedCornerShape: 스켈레톤 박스의 모서리 곡률입니다.
* @param color Color: 스켈레톤 박스의 배경 색상입니다.
*/

/**
* enum class WantedSkeletonLength
*
* 텍스트형 스켈레톤의 너비 비율을 정의하는 enum 클래스입니다.
* Ratio100, Ratio75, Ratio50, Ratio25 네 가지 비율이 존재합니다.
*/

/**
* enum class WantedSkeAlign
*
* 텍스트형 스켈레톤의 정렬 방식을 정의하는 enum 클래스입니다.
* Left, Center, Right 세 가지 정렬이 존재합니다.
*/