package com.wanted.android.wanted.design.actions.button.iconbutton

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * [IconButtonInteractionEffect] 단위 테스트
 *
 * Figma 4.0.0 IconButton 스펙의 인터랙션 피드백 계약을 고정한다.
 * - ripple 레이어는 Highlight 에서만 표시
 * - 아이콘 투명도 낮춤(Dim)은 Dim 에서만 적용
 * - deprecated disableInteraction 매핑
 * - interactionColor 미지정 시 Dim 은 tint, 나머지는 ripple 기본색을 기준으로 사용
 * - Pressed 22% 는 원래 색 알파에 곱해 적용
 */
class IconButtonInteractionEffectTest {

	// region showsIndication — Highlight 만 ripple 레이어

	@Test
	fun `(Given) Highlight (When) showsIndication (Then) true`() {
		assertTrue(IconButtonInteractionEffect.Highlight.showsIndication)
	}

	@Test
	fun `(Given) Dim, None (When) showsIndication (Then) false`() {
		assertFalse(IconButtonInteractionEffect.Dim.showsIndication)
		assertFalse(IconButtonInteractionEffect.None.showsIndication)
	}

	// endregion

	// region dimsIcon — Dim 만 아이콘 투명도 변경

	@Test
	fun `(Given) Dim (When) dimsIcon (Then) true`() {
		assertTrue(IconButtonInteractionEffect.Dim.dimsIcon)
	}

	@Test
	fun `(Given) Highlight, None (When) dimsIcon (Then) false`() {
		assertFalse(IconButtonInteractionEffect.Highlight.dimsIcon)
		assertFalse(IconButtonInteractionEffect.None.dimsIcon)
	}

	// endregion

	// region fromDisableInteraction — deprecated 파라미터 매핑

	@Test
	fun `(Given) disableInteraction=true (When) fromDisableInteraction (Then) None`() {
		val actual = IconButtonInteractionEffect.Companion.fromDisableInteraction(true)

		assertEquals(IconButtonInteractionEffect.None, actual)
	}

	@Test
	fun `(Given) disableInteraction=false (When) fromDisableInteraction (Then) Highlight`() {
		val actual = IconButtonInteractionEffect.Companion.fromDisableInteraction(false)

		assertEquals(IconButtonInteractionEffect.Highlight, actual)
	}

	// endregion

	// region resolveInteractionColor — interactionColor 미지정 시 기준색

	@Test
	fun `(Given) Dim + interactionColor 미지정 (When) resolveInteractionColor (Then) tint 반환`() {
		val actual = IconButtonInteractionEffect.Dim.resolveInteractionColor(
			interactionColor = Color.Unspecified,
			tint = TINT,
			rippleFallback = RIPPLE_FALLBACK,
		)

		assertEquals(TINT, actual)
	}

	@Test
	fun `(Given) Highlight, None + interactionColor 미지정 (When) resolveInteractionColor (Then) ripple 기본색 반환`() {
		val normal = IconButtonInteractionEffect.Highlight.resolveInteractionColor(
			interactionColor = Color.Unspecified,
			tint = TINT,
			rippleFallback = RIPPLE_FALLBACK,
		)
		val none = IconButtonInteractionEffect.None.resolveInteractionColor(
			interactionColor = Color.Unspecified,
			tint = TINT,
			rippleFallback = RIPPLE_FALLBACK,
		)

		assertEquals(RIPPLE_FALLBACK, normal)
		assertEquals(RIPPLE_FALLBACK, none)
	}

	@Test
	fun `(Given) interactionColor 지정 (When) resolveInteractionColor (Then) 지정한 색 그대로 반환`() {
		IconButtonInteractionEffect.entries.forEach { effect ->
			val actual = effect.resolveInteractionColor(
				interactionColor = INTERACTION,
				tint = TINT,
				rippleFallback = RIPPLE_FALLBACK,
			)

			assertEquals(INTERACTION, actual)
		}
	}

	// endregion

	// region dimmedForPress — Pressed 22% 는 원래 알파에 곱셈

	@Test
	fun `(Given) 불투명 색 (When) dimmedForPress (Then) 알파 22퍼센트`() {
		val actual = Color.Black.dimmedForPress()

		assertEquals(0.22f, actual.alpha, ALPHA_TOLERANCE)
	}

	@Test
	fun `(Given) 반투명 색 (When) dimmedForPress (Then) 원래 알파에 22퍼센트를 곱한 값`() {
		val origin = Color.Black.copy(alpha = 0.28f)

		val actual = origin.dimmedForPress()

		assertEquals(origin.alpha * 0.22f, actual.alpha, ALPHA_TOLERANCE)
	}

	@Test
	fun `(Given) 반투명 색 (When) dimmedForPress (Then) RGB 채널은 유지`() {
		val origin = Color(red = 0.2f, green = 0.4f, blue = 0.6f, alpha = 0.5f)

		val actual = origin.dimmedForPress()

		assertEquals(origin.red, actual.red, ALPHA_TOLERANCE)
		assertEquals(origin.green, actual.green, ALPHA_TOLERANCE)
		assertEquals(origin.blue, actual.blue, ALPHA_TOLERANCE)
	}

	// endregion

	companion object {
		/** Color 는 채널당 8bit 로 양자화되므로 1/255 만큼의 오차를 허용한다. */
		private const val ALPHA_TOLERANCE = 0.005f

		private val TINT = Color.Red
		private val RIPPLE_FALLBACK = Color.Blue
		private val INTERACTION = Color.Green
	}
}
