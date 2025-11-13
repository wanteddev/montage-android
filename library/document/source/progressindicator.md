/**
* WantedLinearProgressIndicator
*
* 진행 상태를 나타내는 선형 프로그레스 바 컴포넌트입니다.
*
* 0.0 ~ 1.0 사이의 값으로 진행률을 표시하며, 높이는 2dp로 고정됩니다.
*
* 사용 예시:
* ```kotlin
* var progress by remember { mutableFloatStateOf(0.3f) }
*
* WantedLinearProgressIndicator(
*     currentProgress = progress
* )
* ```
*
* @param currentProgress Float: 진행률입니다. 0.0은 0%, 1.0은 100%를 의미합니다.
* @param modifier Modifier: 컴포넌트에 적용할 Modifier입니다.
*/