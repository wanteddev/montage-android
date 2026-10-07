package com.wanted.android.wanted.design.feedback.fallback

import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * [WantedFallbackPadding] 단위 테스트
 *
 * 검증 항목:
 * - Figma `Fallback View` 4.0.0 의 `Padding` 속성별 상하 여백 값 (WRP-2268)
 * - Figma variant 와 1:1 대응하는 옵션 개수
 */
class WantedFallbackPaddingTest {

	@Test
	fun `(Given) Normal (When) verticalPadding 확인 (Then) 160dp 반환`() {
		assertEquals(160.dp, WantedFallbackPadding.Normal.verticalPadding)
	}

	@Test
	fun `(Given) Compact (When) verticalPadding 확인 (Then) 80dp 반환`() {
		assertEquals(80.dp, WantedFallbackPadding.Compact.verticalPadding)
	}

	@Test
	fun `(Given) WantedFallbackPadding (When) entries 확인 (Then) Normal Compact 순서로 2개`() {
		assertEquals(
			listOf(WantedFallbackPadding.Normal, WantedFallbackPadding.Compact),
			WantedFallbackPadding.entries
		)
	}
}
