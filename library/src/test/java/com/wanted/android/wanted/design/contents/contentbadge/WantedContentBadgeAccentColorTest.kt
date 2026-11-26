package com.wanted.android.wanted.design.contents.contentbadge

import androidx.compose.ui.graphics.Color
import com.wanted.android.wanted.design.util.OPACITY_43
import com.wanted.android.wanted.design.util.OPACITY_8
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Accent Content Badge 기본 색 결정 단위 테스트 (WRP-4392)
 *
 * 검증 항목:
 * - 기본 글자색(cyan)이면 배경·테두리에 Figma 토큰(Surface/Line Accent Cyan)을 쓴다
 * - 글자색을 바꾼 호출부는 배경을 글자색 × 8% 로 유지하고, 테두리는 글자색 × 43% 로 계산한다
 * - WantedContentBadgeDefault 의 테두리 기본값도 글자색 × 43% 다
 */
class WantedContentBadgeAccentColorTest {

	@Test
	fun `(Given) 기본 글자색 (When) 배경 결정 (Then) surfaceAccentCyan 토큰을 쓴다`() {
		val actual = resolveAccentBackgroundColor(
			contentColor = FOREGROUND_ACCENT_CYAN,
			defaultContentColor = FOREGROUND_ACCENT_CYAN,
			defaultBackgroundColor = SURFACE_ACCENT_CYAN
		)

		assertEquals(SURFACE_ACCENT_CYAN, actual)
	}

	@Test
	fun `(Given) 다른 글자색 (When) 배경 결정 (Then) 글자색에 8퍼센트를 적용한다`() {
		val actual = resolveAccentBackgroundColor(
			contentColor = CUSTOM_CONTENT_COLOR,
			defaultContentColor = FOREGROUND_ACCENT_CYAN,
			defaultBackgroundColor = SURFACE_ACCENT_CYAN
		)

		assertEquals(CUSTOM_CONTENT_COLOR.copy(OPACITY_8), actual)
	}

	@Test
	fun `(Given) 기본 글자색 (When) 테두리 결정 (Then) lineAccentCyan 토큰을 쓴다`() {
		val actual = resolveAccentOutLineColor(
			contentColor = FOREGROUND_ACCENT_CYAN,
			defaultContentColor = FOREGROUND_ACCENT_CYAN,
			defaultOutLineColor = LINE_ACCENT_CYAN
		)

		assertEquals(LINE_ACCENT_CYAN, actual)
	}

	@Test
	fun `(Given) 다른 글자색 (When) 테두리 결정 (Then) 글자색에 43퍼센트를 적용한다`() {
		val actual = resolveAccentOutLineColor(
			contentColor = CUSTOM_CONTENT_COLOR,
			defaultContentColor = FOREGROUND_ACCENT_CYAN,
			defaultOutLineColor = LINE_ACCENT_CYAN
		)

		assertEquals(CUSTOM_CONTENT_COLOR.copy(OPACITY_43), actual)
	}

	@Test
	fun `(Given) 글자색만 지정 (When) WantedContentBadgeDefault 생성 (Then) 테두리는 글자색에 43퍼센트를 적용한다`() {
		val actual = WantedContentBadgeDefault(contentColor = CUSTOM_CONTENT_COLOR)

		assertEquals(CUSTOM_CONTENT_COLOR.copy(OPACITY_43), actual.outLineColor)
	}

	private companion object {
		// design_system_atomic_colors.xml 의 라이트 테마 값 (cyan_40 / cyan_50_opacity8 / cyan_40_opacity43)
		val FOREGROUND_ACCENT_CYAN = Color(0xFF0098B2)
		val SURFACE_ACCENT_CYAN = Color(0x1400BDDE)
		val LINE_ACCENT_CYAN = Color(0x6E0098B2)
		val CUSTOM_CONTENT_COLOR = Color(0xFF6541F2)
	}
}
