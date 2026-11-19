package com.wanted.android.wanted.design.input.formcontrol

import androidx.compose.ui.unit.Constraints
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Leading 배치 라벨 열 폭 규칙 단위 테스트 (WRP-4445)
 *
 * 검증 항목:
 * - 단독 사용은 부모 폭 50% 상한을 유지한다
 * - 고정 폭(개별·그룹 labelWidth)은 그 폭으로 고정하고 부모 폭을 넘지 않는다
 * - 그룹 공유 폭은 최소 폭으로만 걸고 상한은 부모 폭이다
 * - 그룹 registry 는 등록된 자연 폭의 최댓값을 돌려준다
 */
class WantedFormControlLabelWidthTest {

	@Test
	fun `(Given) 단독 사용 (When) 부모 폭 400 (Then) 라벨 폭 범위는 0 ~ 200`() {
		val bounds = leadingLabelWidthBounds(LeadingLabelWidth.Standalone, parentMaxWidth = 400)

		assertEquals(LeadingLabelWidthBounds(minWidth = 0, maxWidth = 200), bounds)
	}

	@Test
	fun `(Given) 단독 사용 (When) 부모 폭 unbounded (Then) 상한 없음`() {
		val bounds = leadingLabelWidthBounds(LeadingLabelWidth.Standalone, parentMaxWidth = Constraints.Infinity)

		assertEquals(LeadingLabelWidthBounds(minWidth = 0, maxWidth = Constraints.Infinity), bounds)
	}

	@Test
	fun `(Given) 고정 폭 120 (When) 부모 폭 400 (Then) 120 으로 고정해 절반 상한 미적용`() {
		val bounds = leadingLabelWidthBounds(LeadingLabelWidth.Fixed(widthPx = 120), parentMaxWidth = 400)

		assertEquals(LeadingLabelWidthBounds(minWidth = 120, maxWidth = 120), bounds)
	}

	@Test
	fun `(Given) 고정 폭 300 (When) 부모 폭 200 (Then) 부모 폭으로 줄인다`() {
		val bounds = leadingLabelWidthBounds(LeadingLabelWidth.Fixed(widthPx = 300), parentMaxWidth = 200)

		assertEquals(LeadingLabelWidthBounds(minWidth = 200, maxWidth = 200), bounds)
	}

	@Test
	fun `(Given) 고정 폭 120 (When) 부모 폭 unbounded (Then) 120 으로 고정`() {
		val bounds = leadingLabelWidthBounds(
			LeadingLabelWidth.Fixed(widthPx = 120),
			parentMaxWidth = Constraints.Infinity,
		)

		assertEquals(LeadingLabelWidthBounds(minWidth = 120, maxWidth = 120), bounds)
	}

	@Test
	fun `(Given) 그룹 공유 폭 250 (When) 부모 폭 400 (Then) 최소 250 상한 400`() {
		val bounds = leadingLabelWidthBounds(LeadingLabelWidth.Shared(widthPx = 250), parentMaxWidth = 400)

		assertEquals(LeadingLabelWidthBounds(minWidth = 250, maxWidth = 400), bounds)
	}

	@Test
	fun `(Given) 그룹 공유 폭 500 (When) 부모 폭 400 (Then) 최소 폭을 부모 폭으로 줄인다`() {
		val bounds = leadingLabelWidthBounds(LeadingLabelWidth.Shared(widthPx = 500), parentMaxWidth = 400)

		assertEquals(LeadingLabelWidthBounds(minWidth = 400, maxWidth = 400), bounds)
	}

	@Test
	fun `(Given) 라벨 3개 등록 (When) maxWidthPx 호출 (Then) 가장 긴 폭 반환`() {
		val registry = WantedFormControlGroupLabelWidthRegistry()

		registry.register(key = "a", widthPx = 40)
		registry.register(key = "b", widthPx = 96)
		registry.register(key = "c", widthPx = 72)

		assertEquals(96, registry.maxWidthPx())
	}

	@Test
	fun `(Given) 가장 긴 라벨 등록 해제 (When) maxWidthPx 호출 (Then) 남은 것 중 최댓값 반환`() {
		val registry = WantedFormControlGroupLabelWidthRegistry()
		registry.register(key = "a", widthPx = 40)
		registry.register(key = "b", widthPx = 96)

		registry.unregister(key = "b")

		assertEquals(40, registry.maxWidthPx())
	}

	@Test
	fun `(Given) 같은 key 로 다시 등록 (When) maxWidthPx 호출 (Then) 새 폭으로 바뀐다`() {
		val registry = WantedFormControlGroupLabelWidthRegistry()
		registry.register(key = "a", widthPx = 96)

		registry.register(key = "a", widthPx = 30)

		assertEquals(30, registry.maxWidthPx())
	}

	@Test
	fun `(Given) 등록 없음 (When) maxWidthPx 호출 (Then) 0 반환`() {
		assertEquals(0, WantedFormControlGroupLabelWidthRegistry().maxWidthPx())
	}
}
