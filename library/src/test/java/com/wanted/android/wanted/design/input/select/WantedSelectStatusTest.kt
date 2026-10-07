package com.wanted.android.wanted.design.input.select

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * [WantedSelectDefaults.Status] 단위 테스트
 *
 * 검증 항목:
 * - Figma `Select` 의 `status` 속성(Normal, Negative)과 1:1 대응하는 옵션
 * - Deprecated 오버로드의 Boolean negative 가 Status 로 변환되는 규칙 (WRP-2847)
 */
class WantedSelectStatusTest {

	@Test
	fun `(Given) WantedSelectDefaults Status (When) entries 확인 (Then) Normal Negative 순서로 2개`() {
		assertEquals(
			listOf(WantedSelectDefaults.Status.Normal, WantedSelectDefaults.Status.Negative),
			WantedSelectDefaults.Status.entries
		)
	}

	@Test
	fun `(Given) negative true (When) toSelectStatus 호출 (Then) Negative 반환`() {
		assertEquals(WantedSelectDefaults.Status.Negative, true.toSelectStatus())
	}

	@Test
	fun `(Given) negative false (When) toSelectStatus 호출 (Then) Normal 반환`() {
		assertEquals(WantedSelectDefaults.Status.Normal, false.toSelectStatus())
	}
}
