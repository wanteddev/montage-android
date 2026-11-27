/**
* fun Modifier.framedStyle(...)
*
* 프레임 스타일을 적용하는 Modifier 확장 함수입니다.
*
* 여러 요소를 프레임으로 감싸 그룹화하고, 그룹 단위의 선택 상태를 강조할 때 사용합니다.
* 상태에 맞는 1dp 테두리를 그리고, 선택 상태는 1dp 상태 색 border 아래에 1dp surface/neutral/primary Base border 를 깔고
* 컴포넌트 바깥 4dp Ring 으로 강조합니다. 선택(selected)과 상태(status)는 서로 독립된 축입니다.
* 배경은 그리지 않고, shadow 를 지정했을 때만 background/neutral/primary 를 깝니다.
*
* 상태별 외형:
* - Normal: 테두리 line/neutral/secondary
* - Normal + selected: 테두리 line/brand/strong + Base surface/neutral/primary + 바깥 Ring line/brand/focus
* - Negative: 테두리 line/negative/primary
* - Negative + selected: 테두리 line/negative/strong + Base surface/neutral/primary + 바깥 Ring line/negative/focus
* - disabled: 상태와 관계없이 테두리·Base·Ring 의 컬러 토큰은 그대로 두고 현재 알파값에 43% 를 곱합니다(예: line/brand/strong 43% → 약 18.5%)
*
* 사용 예시:
* ```
* Box(
*     modifier = Modifier
*         .size(100.dp)
*         .framedStyle(
*             selected = true,
*             status = WantedFramedStyleStatus.Normal,
*             enabled = true
*         )
* )
* ```
*
* @param selected Boolean: 선택 여부입니다. true일 경우 상태 색 테두리 아래 Base 를 깔고 바깥 4dp Ring 을 그립니다.
* @param status WantedFramedStyleStatus: 프레임 상태입니다. Normal, Negative 중 하나를 선택합니다.
* @param shape RoundedCornerShape: 모서리 둥글기 형태입니다. 기본값은 14dp입니다.
* @param enabled Boolean: 활성화 여부입니다. false일 경우 상태와 관계없이 테두리·Base·Ring 의 컬러 토큰은 그대로 두고 현재 알파값에 43% 를 곱합니다.
* @param shadow WantedShadowStyle?: 적용할 섀도우 스타일입니다. 기본값은 null 로 그림자와 배경을 그리지 않습니다. 지정하면 배경 background/neutral/primary 를 함께 깝니다.
* @return Modifier: 스타일이 적용된 Modifier를 반환합니다.
*/

/**
* fun Modifier.focusRing(...)
*
* 컴포넌트 외곽에 Focus Ring(외부 강조 테두리)을 그리는 Modifier 확장 함수입니다.
*
* `framedStyle`의 inset border와 관심사를 분리하여, 포커스 시 컴포넌트 경계 바깥쪽에
* stroke를 그립니다. `drawBehind`로 outset 영역에 그리므로 레이아웃에 영향을 주지 않고
* (focus 토글 시 layout shift 없음), 이후 `.clip()`에 의해 잘리지 않습니다.
*
* stroke는 외곽 경계를 기준으로 두께의 절반만큼 바깥으로 밀어 `[edge, edge + width]` 구간에
* 위치합니다. (CSS `box-shadow: 0 0 0 width` 와 동일) corner radius는 동심을 유지하기 위해
* 컴포넌트 radius + 두께 절반으로 계산합니다.
*
* @param visible Boolean: ring 노출 여부입니다. false일 경우 아무것도 그리지 않습니다.
* @param shape RoundedCornerShape: 컴포넌트의 모서리 형태입니다. ring radius 계산에 사용됩니다.
* @param color Color: ring 색상입니다. 기본값은 lineBrandFocus 토큰입니다.
* @param width Dp: ring 두께입니다. 기본값은 4dp입니다.
* @return Modifier: Focus Ring이 적용된 Modifier를 반환합니다.
*/

/**
* enum class WantedFramedStyleStatus
*
* 프레임 스타일의 상태를 정의하는 enum 클래스입니다. 선택 여부는 `framedStyle` 의 `selected` 로 따로 지정합니다.
*
* 각 상태에 따라 테두리 색상이 달라집니다(테두리 굵기는 모두 1dp):
* - Normal: 일반 상태입니다. 기본 테두리 색상이 적용됩니다.
* - Negative: 오류 또는 부정적인 상태입니다. 빨간색 계열 테두리가 적용됩니다.
*/