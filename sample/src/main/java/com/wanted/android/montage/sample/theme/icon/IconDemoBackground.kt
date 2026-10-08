package com.wanted.android.montage.sample.theme.icon

/**
 * 아이콘 데모 화면에서 아이콘 미리보기 배경을 전환하기 위한 옵션.
 *
 * 흰 배경이 포함된 opaque 아이콘 등은 밝은 화면 배경에서 잘 보이지 않으므로,
 * 배경을 바꿔가며 확인할 수 있도록 제공한다.
 * 실제 Color 매핑은 Screen(Compose)에서 `DesignSystemTheme.colors`로 수행한다.
 */
enum class IconDemoBackground(val label: String) {
    Default("기본"),
    Alternative("대체"),
    White("화이트"),
    Dark("다크"),
    Black("블랙"),
}
