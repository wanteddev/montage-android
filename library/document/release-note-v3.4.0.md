## Release Note - v3.4.0

### 개요
`v3.4.0`은 디자인 시스템 동기화 중심의 업데이트로, 주요 UI 컴포넌트의 사용성/안정성 개선과 아이콘 리소스 보강이 포함됩니다.

### 비교 범위
- 기준: `v3.3.0` → `v3.4.0`
- 변경 통계: **13 files changed, 144 insertions(+), 79 deletions(-)**

### 주요 변경사항

- **TopAppBar 개선**
  - `WantedTopAppBar`에 `titleAlignCenter` 옵션 추가
  - 문자열 기반 오버로드에서 `title` 파라미터를 필수화하여 사용 의도를 명확히 함

- **AutoComplete 동작 개선**
  - 드롭다운 구현을 `DropdownMenu` 기반으로 조정
  - `anchorPadding`을 통한 드롭다운 위치 보정 지원
  - `PopupProperties(focusable = false)` 적용으로 입력/포커스 관련 UX 안정화

- **Tooltip 개선**
  - anchor 좌표 추적 로직 추가
  - caret(화살표) 그리기 로직 분리(`drawCaret`)로 렌더링 구조 개선

- **Avatar 이미지 처리 개선**
  - Glide placeholder/failure 처리 방식 정리 (`placeholder(...)`)
  - 알파 처리 위치 조정으로 렌더링 일관성 개선

- **아이콘 리소스 업데이트**
  - 신규 아이콘 추가
    - `icon_normal_crop`
    - `icon_normal_flip`
    - `icon_normal_rotate`
  - `icon_normal_calendar` 벡터 경로 업데이트
  - `ic_normal_telescope_svg` → `icon_normal_telescope` 리소스명 정리

### 영향도
- **기능 추가**: TopAppBar 타이틀 중앙 정렬 옵션
- **동작 개선**: AutoComplete, Tooltip, Avatar의 표시/상호작용 안정성
- **리소스 확장**: 편집 관련 아이콘(crop/flip/rotate) 신규 제공
- **호환성 참고**: `WantedTopAppBar` 문자열 오버로드 사용 시 `title` 필수 인자 반영 필요

### 기타
- `library/publish.properties` 버전이 `v3.4.0`으로 업데이트되었습니다.
