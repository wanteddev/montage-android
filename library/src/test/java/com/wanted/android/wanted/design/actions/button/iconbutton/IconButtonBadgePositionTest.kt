package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.compose.ui.Alignment
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * [IconButtonBadgePosition] 단위 테스트
 *
 * 9개 위치의 [IconButtonBadgePosition.toAlignment] · verticalInsetSign · horizontalInsetSign
 * 매핑을 전체 매트릭스로 고정한다. (사용자에게 바로 보이는 배지 위치 계약)
 */
class IconButtonBadgePositionTest {

	// region toAlignment — 9개 위치

	@Test
	fun `(Given) 9개 위치 (When) toAlignment (Then) 대응 Alignment 반환`() {
		assertEquals(Alignment.TopStart, IconButtonBadgePosition.TopLeft.toAlignment())
		assertEquals(Alignment.TopCenter, IconButtonBadgePosition.TopCenter.toAlignment())
		assertEquals(Alignment.TopEnd, IconButtonBadgePosition.TopRight.toAlignment())
		assertEquals(Alignment.CenterStart, IconButtonBadgePosition.MiddleLeft.toAlignment())
		assertEquals(Alignment.Center, IconButtonBadgePosition.MiddleCenter.toAlignment())
		assertEquals(Alignment.CenterEnd, IconButtonBadgePosition.MiddleRight.toAlignment())
		assertEquals(Alignment.BottomStart, IconButtonBadgePosition.BottomLeft.toAlignment())
		assertEquals(Alignment.BottomCenter, IconButtonBadgePosition.BottomCenter.toAlignment())
		assertEquals(Alignment.BottomEnd, IconButtonBadgePosition.BottomRight.toAlignment())
	}

	// endregion

	// region verticalInsetSign — top=+1, middle=0, bottom=-1

	@Test
	fun `(Given) 9개 위치 (When) verticalInsetSign (Then) top=1 middle=0 bottom=-1`() {
		assertEquals(1, IconButtonBadgePosition.TopLeft.verticalInsetSign)
		assertEquals(1, IconButtonBadgePosition.TopCenter.verticalInsetSign)
		assertEquals(1, IconButtonBadgePosition.TopRight.verticalInsetSign)
		assertEquals(0, IconButtonBadgePosition.MiddleLeft.verticalInsetSign)
		assertEquals(0, IconButtonBadgePosition.MiddleCenter.verticalInsetSign)
		assertEquals(0, IconButtonBadgePosition.MiddleRight.verticalInsetSign)
		assertEquals(-1, IconButtonBadgePosition.BottomLeft.verticalInsetSign)
		assertEquals(-1, IconButtonBadgePosition.BottomCenter.verticalInsetSign)
		assertEquals(-1, IconButtonBadgePosition.BottomRight.verticalInsetSign)
	}

	// endregion

	// region horizontalInsetSign — left=+1, center=0, right=-1

	@Test
	fun `(Given) 9개 위치 (When) horizontalInsetSign (Then) left=1 center=0 right=-1`() {
		assertEquals(1, IconButtonBadgePosition.TopLeft.horizontalInsetSign)
		assertEquals(1, IconButtonBadgePosition.MiddleLeft.horizontalInsetSign)
		assertEquals(1, IconButtonBadgePosition.BottomLeft.horizontalInsetSign)
		assertEquals(0, IconButtonBadgePosition.TopCenter.horizontalInsetSign)
		assertEquals(0, IconButtonBadgePosition.MiddleCenter.horizontalInsetSign)
		assertEquals(0, IconButtonBadgePosition.BottomCenter.horizontalInsetSign)
		assertEquals(-1, IconButtonBadgePosition.TopRight.horizontalInsetSign)
		assertEquals(-1, IconButtonBadgePosition.MiddleRight.horizontalInsetSign)
		assertEquals(-1, IconButtonBadgePosition.BottomRight.horizontalInsetSign)
	}

	// endregion
}
