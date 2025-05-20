/**
* 재생(Play) 아이콘을 원형 배지로 감싸서 표시하는 컴포저블입니다.
*
* 회색 계열 배경과 흰색 플레이 아이콘으로 구성되며, 크기 및 대체 색상 사용 여부를 설정할 수 있습니다.
* `Size`에 따라 배지와 아이콘의 크기가 달라집니다.
*
* 사용 예시 :
* ```kotlin
* WantedPlayBadge(
*     size = Size.Medium,
*     isAlternative = true,
*     modifier = Modifier
* )
* ```
*
* @param size Size: 배지의 크기 옵션입니다. Small, Medium, Large 세 가지 크기를 제공합니다.
* @param isAlternative Boolean: true일 경우 대체 색상(더 어두운 회색 계열 배경)을 사용합니다.
* @param modifier Modifier: 배지의 외형, 정렬, 패딩 등을 제어하는 Modifier입니다.
*/

/**
* object WantedPlayBadgeContract
*
* 재생 뱃지(PlayBadge) 컴포넌트에서 사용되는 설정 값을 정의하는 객체입니다.
*
* 주로 뱃지의 크기와 관련된 정보를 제공합니다.
*/

/**
* enum class Size
*
* PlayBadge의 크기를 정의하는 enum 클래스입니다.
*
* 각 값은 뱃지 컨테이너 크기와 아이콘 크기를 함께 정의합니다.
* 제공되는 옵션은 다음과 같습니다:
* - Small: 컨테이너 36dp, 아이콘 24dp
* - Medium: 컨테이너 60dp, 아이콘 40dp
* - Large: 컨테이너 80dp, 아이콘 56dp
*
* @property container `Dp`: PlayBadge 외곽 컨테이너 크기입니다.
* @property icon `Dp`: Play 아이콘의 크기입니다.
*/