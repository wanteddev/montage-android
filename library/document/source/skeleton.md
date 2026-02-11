/**
* WantedSkeletonCircle
*
* 원형 Skeleton 컴포넌트입니다.
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
* 사각형 Skeleton 컴포넌트입니다.
*
* Shimmer 애니메이션이 자동으로 적용됩니다.
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
* WantedSkeletonText
*
* 텍스트를 표현하는 Skeleton 컴포넌트입니다.
*
* 다양한 길이 비율과 정렬 방식을 지원하며, Shimmer 애니메이션이 자동으로 적용됩니다.
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
* 커스텀 비율 기반의 텍스트를 표현하는 Skeleton 컴포넌트입니다.
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
* - Ratio100: 100% 너비 비율입니다.
* - Ratio75: 75% 너비 비율입니다.
* - Ratio50: 50% 너비 비율입니다.
* - Ratio25: 25% 너비 비율입니다.
*/

/**
* enum class WantedSkeAlign
*
* 텍스트형 스켈레톤의 정렬 방식을 정의하는 enum 클래스입니다.
* - Left: 왼쪽 정렬입니다.
* - Center: 가운데 정렬입니다.
* - Right: 오른쪽 정렬입니다.
*/