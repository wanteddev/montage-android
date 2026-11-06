package com.wanted.android.wanted.design.navigations.topbar

import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.navigations.topbar.WantedTopAppBarContract.Variant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * TopAppBar toolbar 영역 단위 테스트
 *
 * 검증 항목:
 * - Variant 별 toolbar 표시 여부 (Floating 만 제외, WRP-3300)
 * - Slot 프리셋 여백·간격 값 (상하 8 · 좌우 20 · 아이템 간격 8, WRP-3298)
 */
class WantedTopAppBarToolbarTest {

	@Test
	fun `(Given) Normal (When) supportsToolbar 확인 (Then) true 반환`() {
		assertTrue(Variant.Normal.supportsToolbar)
	}

	@Test
	fun `(Given) Display (When) supportsToolbar 확인 (Then) true 반환`() {
		assertTrue(Variant.Display.supportsToolbar)
	}

	@Test
	fun `(Given) Search (When) supportsToolbar 확인 (Then) true 반환`() {
		assertTrue(Variant.Search.supportsToolbar)
	}

	@Test
	fun `(Given) Floating (When) supportsToolbar 확인 (Then) false 반환`() {
		assertFalse(Variant.Floating.supportsToolbar)
	}

	@Test
	fun `(Given) Slot 프리셋 (When) 기본 여백 확인 (Then) 상하 8dp 좌우 20dp`() {
		assertEquals(8.dp, WantedTopAppBarToolbarDefaults.SlotVerticalPadding)
		assertEquals(20.dp, WantedTopAppBarToolbarDefaults.SlotHorizontalPadding)
	}

	@Test
	fun `(Given) Slot 프리셋 (When) 아이템 간격 확인 (Then) 8dp`() {
		assertEquals(8.dp, WantedTopAppBarToolbarDefaults.SlotItemSpacing)
	}
}
