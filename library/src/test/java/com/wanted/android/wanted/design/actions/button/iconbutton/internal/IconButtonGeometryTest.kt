package com.wanted.android.wanted.design.actions.button.iconbutton.internal

import androidx.compose.ui.unit.dp
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonBackgroundSize
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonNormalSize
import com.wanted.android.wanted.design.actions.button.iconbutton.WantedIconButtonSize
import com.wanted.android.wanted.design.actions.button.iconbutton.overflowIconSize
import com.wanted.android.wanted.design.theme.WantedRadius
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * [IconButtonGeometry] 단위 테스트
 *
 * 검증 항목:
 * - 토큰 범위 내 nearestDimensionToken / nearestRadiusToken 정상 동작 (회귀)
 * - 토큰 최댓값 초과 시 evenCeil 보정값 반환 (WRP-927 스펙)
 * - 박스 기준 calcNormalGeometry / calcBackgroundGeometry / calcOutlinedSolidIconSize 산출 (스펙 §3·§4)
 * - 박스 clamp(24, N, 64) 및 size sealed class preset/Custom
 * - NormalGeometry.overflowInset — interactionOverflow 넘침 크기 (WRP-3052)
 * - calcOverflowGeometry / overflowIconSize — interactionOverflow 시 size = 아이콘 크기 규칙 (WRP-3163)
 */
class IconButtonGeometryTest {

	// region nearestDimensionToken — 토큰 범위 내 (회귀)

	@Test
	fun `(Given) 16dp (When) nearestDimensionToken (Then) 16dp 반환`() {
		val result = IconButtonGeometry.nearestDimensionToken(16.dp)
		assertEquals(16.dp, result)
	}

	@Test
	fun `(Given) 64dp 최댓값과 동일 (When) nearestDimensionToken (Then) 64dp 반환`() {
		val result = IconButtonGeometry.nearestDimensionToken(64.dp)
		assertEquals(64.dp, result)
	}

	@Test
	fun `(Given) raw=21dp 토큰 사이 (When) nearestDimensionToken tieUp=true 호출 (Then) 가장 가까운 20dp 반환`() {
		// 21dp → 20dp 거리1, 24dp 거리3 → 20dp
		val result = IconButtonGeometry.nearestDimensionToken(21.dp, tieUp = true)
		assertEquals(20.dp, result)
	}

	@Test
	fun `(Given) raw=22dp 동률 (When) nearestDimensionToken tieUp=true 호출 (Then) 큰 값 24dp 반환`() {
		// 22dp → 20dp 거리2, 24dp 거리2 → 동률, tieUp=true이므로 24dp
		val result = IconButtonGeometry.nearestDimensionToken(22.dp, tieUp = true)
		assertEquals(24.dp, result)
	}

	@Test
	fun `(Given) raw=26dp 동률 (When) nearestDimensionToken tieUp=false 호출 (Then) 작은 값 24dp 반환`() {
		// 26dp → 24dp 거리2, 28dp 거리2 → 동률, tieUp=false이므로 24dp
		val result = IconButtonGeometry.nearestDimensionToken(26.dp, tieUp = false)
		assertEquals(24.dp, result)
	}

	@Test
	fun `(Given) raw=1dp 토큰 하한 미만 (When) nearestDimensionToken 호출 (Then) 최솟값 12dp 반환`() {
		val result = IconButtonGeometry.nearestDimensionToken(1.dp, tieUp = true)
		assertEquals(12.dp, result)
	}

	// endregion

	// region nearestDimensionToken — 토큰 최댓값(64dp) 초과 → evenCeil 보정 (WRP-927)

	@Test
	fun `(Given) 65dp 홀수 올림 (When) nearestDimensionToken (Then) 66dp 반환`() {
		// ceil(65) = 65(홀수) → 66
		val result = IconButtonGeometry.nearestDimensionToken(65.dp)
		assertEquals(66.dp, result)
	}

	@Test
	fun `(Given) 70dp 짝수 그대로 (When) nearestDimensionToken (Then) 70dp 반환`() {
		// ceil(70) = 70(짝수) → 70
		val result = IconButtonGeometry.nearestDimensionToken(70.dp)
		assertEquals(70.dp, result)
	}

	@Test
	fun `(Given) 100dp 짝수 (When) nearestDimensionToken (Then) 100dp 반환`() {
		// ceil(100) = 100(짝수) → 100
		val result = IconButtonGeometry.nearestDimensionToken(100.dp)
		assertEquals(100.dp, result)
	}

	// endregion

	// region nearestRadiusToken — 토큰 범위 내 (회귀)

	@Test
	fun `(Given) 8dp (When) nearestRadiusToken (Then) 8dp 반환`() {
		val result = IconButtonGeometry.nearestRadiusToken(8.dp)
		assertEquals(8.dp, result)
	}

	@Test
	fun `(Given) raw=6dp 동률 (When) nearestRadiusToken tieUp=false 호출 (Then) 작은 값 4dp 반환`() {
		// 6dp → 4dp 거리2, 8dp 거리2 → tieUp=false이므로 4dp
		val result = IconButtonGeometry.nearestRadiusToken(6.dp, tieUp = false)
		assertEquals(4.dp, result)
	}

	@Test
	fun `(Given) raw=6dp 동률 (When) nearestRadiusToken tieUp=true 호출 (Then) 큰 값 8dp 반환`() {
		// 6dp → 4dp 거리2, 8dp 거리2 → tieUp=true이므로 8dp
		val result = IconButtonGeometry.nearestRadiusToken(6.dp, tieUp = true)
		assertEquals(8.dp, result)
	}

	// endregion

	// region nearestRadiusToken — 28 · 32 토큰 추가 (WRP-3160)

	@Test
	fun `(Given) 25dp (When) nearestRadiusToken (Then) 가장 가까운 24dp 반환`() {
		// 24dp 거리1, 28dp 거리3 → 24dp (28 추가 전에는 최댓값 초과로 26dp 였다)
		val result = IconButtonGeometry.nearestRadiusToken(25.dp)
		assertEquals(24.dp, result)
	}

	@Test
	fun `(Given) raw=26dp 동률 (When) nearestRadiusToken tieUp=false (Then) 작은 값 24dp 반환`() {
		val result = IconButtonGeometry.nearestRadiusToken(26.dp, tieUp = false)
		assertEquals(24.dp, result)
	}

	@Test
	fun `(Given) raw=30dp 동률 (When) nearestRadiusToken tieUp=false (Then) 작은 값 28dp 반환`() {
		val result = IconButtonGeometry.nearestRadiusToken(30.dp, tieUp = false)
		assertEquals(28.dp, result)
	}

	@Test
	fun `(Given) 32dp 최댓값과 동일 (When) nearestRadiusToken (Then) 32dp 반환`() {
		val result = IconButtonGeometry.nearestRadiusToken(32.dp)
		assertEquals(32.dp, result)
	}

	@Test
	fun `(Given) WantedRadius 의 Dp 토큰 (When) radiusTokens 비교 (Then) 같은 목록`() {
		// WantedRadius 와 RadiusToken 이 따로 관리돼 한쪽만 고치면 스냅이 새 토큰을 못 본다 — 목록 동기화 계약.
		// 스냅 결과로는 검증할 수 없다: 최댓값 토큰이 빠져도 초과 구간의 evenCeil 이 같은 값을 돌려준다.
		val radius = WantedRadius()
		val tokens = WantedRadius::class.java.declaredMethods
			.filter { it.name.startsWith("getRadius") && it.returnType == Float::class.javaPrimitiveType }
			.map { (it.invoke(radius) as Float).dp }
			.sortedBy { it.value }

		assertEquals(tokens, IconButtonGeometry.radiusTokens())
	}

	// endregion

	// region nearestRadiusToken — 토큰 최댓값(32dp) 초과 → evenCeil 보정 (WRP-927)

	@Test
	fun `(Given) 33dp 홀수 올림 (When) nearestRadiusToken (Then) 34dp 반환`() {
		// ceil(33) = 33(홀수) → 34
		val result = IconButtonGeometry.nearestRadiusToken(33.dp)
		assertEquals(34.dp, result)
	}

	// endregion

	// region NormalGeometry.overflowInset — interactionOverflow 넘침 크기 (WRP-3052)

	@Test
	fun `(Given) Xlarge box=36dp (When) overflowInset (Then) 6dp 반환`() {
		// (36 - 24) / 2 = 6
		val result = IconButtonGeometry.calcNormalGeometry(WantedIconButtonNormalSize.Xlarge.boxSize)
		assertEquals(6.dp, result.overflowInset)
	}

	@Test
	fun `(Given) Large box=32dp (When) overflowInset (Then) 6dp 반환`() {
		// (32 - 20) / 2 = 6
		val result = IconButtonGeometry.calcNormalGeometry(WantedIconButtonNormalSize.Large.boxSize)
		assertEquals(6.dp, result.overflowInset)
	}

	@Test
	fun `(Given) Medium box=28dp (When) overflowInset (Then) 5dp 반환`() {
		// (28 - 18) / 2 = 5
		val result = IconButtonGeometry.calcNormalGeometry(WantedIconButtonNormalSize.Medium.boxSize)
		assertEquals(5.dp, result.overflowInset)
	}

	@Test
	fun `(Given) Small box=24dp (When) overflowInset (Then) 4dp 반환`() {
		// (24 - 16) / 2 = 4
		val result = IconButtonGeometry.calcNormalGeometry(WantedIconButtonNormalSize.Small.boxSize)
		assertEquals(4.dp, result.overflowInset)
	}

	@Test
	fun `(Given) Custom box=40dp (When) overflowInset (Then) 6dp 반환`() {
		// (40 - 28) / 2 = 6
		val result = IconButtonGeometry.calcNormalGeometry(40.dp)
		assertEquals(6.dp, result.overflowInset)
	}

	@Test
	fun `(Given) box=24dp 하한 (When) overflowInset 더한 레이아웃 (Then) 아이콘 + 넘침 = 박스`() {
		val result = IconButtonGeometry.calcNormalGeometry(24.dp)
		assertEquals(result.box, result.icon + result.overflowInset * 2)
	}

	// endregion

	// region calcNormalGeometry — box → icon(box×2/3, tie↓) · radius(box×0.3, tie↓)

	@Test
	fun `(Given) box=24dp Small preset (When) calcNormalGeometry (Then) box=24 icon=16 radius=8`() {
		// icon = 24*2/3 = 16, radius = 24*0.3 = 7.2 → 8
		val result = IconButtonGeometry.calcNormalGeometry(24.dp)
		assertEquals(24.dp, result.box)
		assertEquals(16.dp, result.icon)
		assertEquals(8.dp, result.radius)
	}

	@Test
	fun `(Given) box=28dp Medium preset (When) calcNormalGeometry (Then) box=28 icon=18 radius=8`() {
		// icon = 28*2/3 = 18.67 → 18, radius = 28*0.3 = 8.4 → 8
		val result = IconButtonGeometry.calcNormalGeometry(28.dp)
		assertEquals(28.dp, result.box)
		assertEquals(18.dp, result.icon)
		assertEquals(8.dp, result.radius)
	}

	@Test
	fun `(Given) box=32dp Large preset (When) calcNormalGeometry (Then) box=32 icon=20 radius=10`() {
		// icon = 32*2/3 = 21.33 → 20, radius = 32*0.3 = 9.6 → 10
		val result = IconButtonGeometry.calcNormalGeometry(32.dp)
		assertEquals(32.dp, result.box)
		assertEquals(20.dp, result.icon)
		assertEquals(10.dp, result.radius)
	}

	@Test
	fun `(Given) box=36dp Xlarge preset (When) calcNormalGeometry (Then) box=36 icon=24 radius=10`() {
		// icon = 36*2/3 = 24, radius = 36*0.3 = 10.8 → 10
		val result = IconButtonGeometry.calcNormalGeometry(36.dp)
		assertEquals(36.dp, result.box)
		assertEquals(24.dp, result.icon)
		assertEquals(10.dp, result.radius)
	}

	@Test
	fun `(Given) box=40dp custom (When) calcNormalGeometry (Then) box=40 icon=28 radius=12`() {
		// icon = 40*2/3 = 26.67 → 28, radius = 40*0.3 = 12 → 12
		val result = IconButtonGeometry.calcNormalGeometry(40.dp)
		assertEquals(40.dp, result.box)
		assertEquals(28.dp, result.icon)
		assertEquals(12.dp, result.radius)
	}

	@Test
	fun `(Given) box=64dp 상한값 (When) calcNormalGeometry (Then) box=64 icon=40 radius=20`() {
		// icon = 64*2/3 = 42.67 → 40, radius = 64*0.3 = 19.2 → 20
		val result = IconButtonGeometry.calcNormalGeometry(64.dp)
		assertEquals(64.dp, result.box)
		assertEquals(40.dp, result.icon)
		assertEquals(20.dp, result.radius)
	}

	@Test
	fun `(Given) box=10dp 하한 미만 (When) calcNormalGeometry (Then) box=24 WCAG clamp`() {
		val result = IconButtonGeometry.calcNormalGeometry(10.dp)
		assertEquals(24.dp, result.box)
		assertEquals(16.dp, result.icon)
	}

	@Test
	fun `(Given) box=100dp 상한 초과 (When) calcNormalGeometry (Then) box=64 clamp`() {
		val result = IconButtonGeometry.calcNormalGeometry(100.dp)
		assertEquals(64.dp, result.box)
		assertEquals(40.dp, result.icon)
	}

	// endregion

	// region calcBackgroundGeometry — box → icon(box×2/3, tie↓), radius=full

	@Test
	fun `(Given) box=32dp Default preset (When) calcBackgroundGeometry (Then) box=32 icon=20`() {
		// icon = 32*2/3 = 21.33 → 20
		val result = IconButtonGeometry.calcBackgroundGeometry(32.dp)
		assertEquals(32.dp, result.box)
		assertEquals(20.dp, result.icon)
	}

	@Test
	fun `(Given) box=24dp (When) calcBackgroundGeometry (Then) box=24 icon=16`() {
		val result = IconButtonGeometry.calcBackgroundGeometry(24.dp)
		assertEquals(24.dp, result.box)
		assertEquals(16.dp, result.icon)
	}

	@Test
	fun `(Given) box=10dp 하한 미만 (When) calcBackgroundGeometry (Then) box=24 clamp`() {
		val result = IconButtonGeometry.calcBackgroundGeometry(10.dp)
		assertEquals(24.dp, result.box)
		assertEquals(16.dp, result.icon)
	}

	@Test
	fun `(Given) box=100dp 상한 초과 (When) calcBackgroundGeometry (Then) box=64 icon=40 clamp`() {
		val result = IconButtonGeometry.calcBackgroundGeometry(100.dp)
		assertEquals(64.dp, result.box)
		assertEquals(40.dp, result.icon)
	}

	// endregion

	// region calcOutlinedSolidIconSize — box → icon(box×0.47, tie↓), clamp(24,N,64)

	@Test
	fun `(Given) box=24dp 하한값 (When) calcOutlinedSolidIconSize (Then) box=24 icon=12`() {
		// raw = 24*0.47 = 11.28 → 12
		val result = IconButtonGeometry.calcOutlinedSolidIconSize(24.dp)
		assertEquals(24.dp, result.box)
		assertEquals(12.dp, result.icon)
	}

	@Test
	fun `(Given) box=32dp Small preset (When) calcOutlinedSolidIconSize (Then) box=32 icon=16`() {
		// raw = 32*0.47 = 15.04 → 16 (14 거리1.04, 16 거리0.96)
		val result = IconButtonGeometry.calcOutlinedSolidIconSize(32.dp)
		assertEquals(32.dp, result.box)
		assertEquals(16.dp, result.icon)
	}

	@Test
	fun `(Given) box=40dp Medium preset (When) calcOutlinedSolidIconSize (Then) box=40 icon=18`() {
		// raw = 40*0.47 = 18.8 → 18 (거리0.8 vs 20dp 거리1.2)
		val result = IconButtonGeometry.calcOutlinedSolidIconSize(40.dp)
		assertEquals(40.dp, result.box)
		assertEquals(18.dp, result.icon)
	}

	@Test
	fun `(Given) box=48dp (When) calcOutlinedSolidIconSize (Then) box=48 icon=24`() {
		// raw = 48*0.47 = 22.56 → 24 (20 거리2.56, 24 거리1.44)
		val result = IconButtonGeometry.calcOutlinedSolidIconSize(48.dp)
		assertEquals(48.dp, result.box)
		assertEquals(24.dp, result.icon)
	}

	@Test
	fun `(Given) box=64dp 상한값 (When) calcOutlinedSolidIconSize (Then) box=64 icon=32`() {
		// raw = 64*0.47 = 30.08 → 32 (28 거리2.08, 32 거리1.92)
		val result = IconButtonGeometry.calcOutlinedSolidIconSize(64.dp)
		assertEquals(64.dp, result.box)
		assertEquals(32.dp, result.icon)
	}

	@Test
	fun `(Given) box=10dp 하한 미만 (When) calcOutlinedSolidIconSize (Then) box=24 clamp`() {
		val result = IconButtonGeometry.calcOutlinedSolidIconSize(10.dp)
		assertEquals(24.dp, result.box)
	}

	@Test
	fun `(Given) box=100dp 상한 초과 (When) calcOutlinedSolidIconSize (Then) box=64 clamp`() {
		val result = IconButtonGeometry.calcOutlinedSolidIconSize(100.dp)
		assertEquals(64.dp, result.box)
	}

	// endregion

	// region maxDimensionToken

	@Test
	fun `(Given) dimension 토큰 (When) maxDimensionToken (Then) 64dp 반환`() {
		assertEquals(64.dp, IconButtonGeometry.maxDimensionToken())
	}

	// endregion

	// region size sealed class — preset boxSize 및 Custom

	@Test
	fun `(Given) WantedIconButtonNormalSize presets (When) boxSize 확인 (Then) 36_32_28_24`() {
		assertEquals(36.dp, WantedIconButtonNormalSize.Xlarge.boxSize)
		assertEquals(32.dp, WantedIconButtonNormalSize.Large.boxSize)
		assertEquals(28.dp, WantedIconButtonNormalSize.Medium.boxSize)
		assertEquals(24.dp, WantedIconButtonNormalSize.Small.boxSize)
	}

	@Test
	fun `(Given) WantedIconButtonNormalSize_Custom (When) copy boxSize (Then) 변경된 boxSize 반환`() {
		val custom = WantedIconButtonNormalSize.Custom(50.dp)
		assertEquals(50.dp, custom.boxSize)
		assertEquals(60.dp, custom.copy(boxSize = 60.dp).boxSize)
	}

	@Test
	fun `(Given) WantedIconButtonSize presets (When) boxSize 확인 (Then) Medium=40 Small=32`() {
		assertEquals(40.dp, WantedIconButtonSize.Medium.boxSize)
		assertEquals(32.dp, WantedIconButtonSize.Small.boxSize)
	}

	@Test
	fun `(Given) WantedIconButtonSize_Custom (When) copy boxSize (Then) 변경된 boxSize 반환`() {
		val custom = WantedIconButtonSize.Custom(48.dp)
		assertEquals(48.dp, custom.boxSize)
		assertEquals(56.dp, custom.copy(boxSize = 56.dp).boxSize)
	}

	@Test
	fun `(Given) WantedIconButtonBackgroundSize_Default (When) boxSize 확인 (Then) 32dp`() {
		assertEquals(32.dp, WantedIconButtonBackgroundSize.Default.boxSize)
	}

	@Test
	fun `(Given) WantedIconButtonBackgroundSize_Custom (When) copy boxSize (Then) 변경된 boxSize 반환`() {
		val custom = WantedIconButtonBackgroundSize.Custom(48.dp)
		assertEquals(48.dp, custom.boxSize)
		assertEquals(40.dp, custom.copy(boxSize = 40.dp).boxSize)
	}

	// endregion

	// region calcOverflowGeometry — interactionOverflow 시 아이콘 → 인터랙션 영역 (WRP-3163)

	@Test
	fun `(Given) 아이콘 16dp (When) calcOverflowGeometry (Then) 영역 24dp radius 8dp`() {
		val result = IconButtonGeometry.calcOverflowGeometry(16.dp)
		assertEquals(IconButtonGeometry.NormalGeometry(box = 24.dp, icon = 16.dp, radius = 8.dp), result)
	}

	@Test
	fun `(Given) 아이콘 18dp (When) calcOverflowGeometry (Then) 영역 28dp radius 8dp`() {
		val result = IconButtonGeometry.calcOverflowGeometry(18.dp)
		assertEquals(IconButtonGeometry.NormalGeometry(box = 28.dp, icon = 18.dp, radius = 8.dp), result)
	}

	@Test
	fun `(Given) 아이콘 20dp (When) calcOverflowGeometry (Then) 영역 32dp radius 10dp`() {
		val result = IconButtonGeometry.calcOverflowGeometry(20.dp)
		assertEquals(IconButtonGeometry.NormalGeometry(box = 32.dp, icon = 20.dp, radius = 10.dp), result)
	}

	@Test
	fun `(Given) 아이콘 24dp (When) calcOverflowGeometry (Then) 영역 36dp radius 10dp`() {
		val result = IconButtonGeometry.calcOverflowGeometry(24.dp)
		assertEquals(IconButtonGeometry.NormalGeometry(box = 36.dp, icon = 24.dp, radius = 10.dp), result)
	}

	@Test
	fun `(Given) 아이콘 12dp 영역 계산값 20dp (When) calcOverflowGeometry (Then) 하한 24dp 로 올림`() {
		// ceil(12 × 1.5 ÷ 4) × 4 = 20 → max(24, 20) = 24
		val result = IconButtonGeometry.calcOverflowGeometry(12.dp)
		assertEquals(24.dp, result.box)
	}

	@Test
	fun `(Given) 아이콘 17dp 토큰 사이 값 (When) calcOverflowGeometry (Then) 아이콘은 스냅하지 않고 영역은 4의 배수 28dp`() {
		// ceil(17 × 1.5 ÷ 4) = ceil(6.375) = 7 → 28
		val result = IconButtonGeometry.calcOverflowGeometry(17.dp)
		assertEquals(17.dp, result.icon)
		assertEquals(28.dp, result.box)
	}

	@Test
	fun `(Given) 아이콘 32dp (When) calcOverflowGeometry (Then) 상한 없이 영역 48dp`() {
		val result = IconButtonGeometry.calcOverflowGeometry(32.dp)
		assertEquals(48.dp, result.box)
	}

	@Test
	fun `(Given) 아이콘 8~64dp 증가 (When) calcOverflowGeometry (Then) 영역이 줄어들지 않음`() {
		val boxes = (8..64).map { IconButtonGeometry.calcOverflowGeometry(it.dp).box }
		assertTrue(boxes.zipWithNext().all { (prev, next) -> next >= prev })
	}

	// endregion

	// region overflowIconSize — interactionOverflow 시 size 가 뜻하는 아이콘 크기 (WRP-3163)

	@Test
	fun `(Given) preset 4종 (When) overflowIconSize (Then) 24 20 18 16dp 반환`() {
		val result = WantedIconButtonNormalSize.presets.map { it.overflowIconSize }
		assertEquals(listOf(24.dp, 20.dp, 18.dp, 16.dp), result)
	}

	@Test
	fun `(Given) Custom 20dp (When) overflowIconSize (Then) 숫자 그대로 20dp 반환`() {
		assertEquals(20.dp, WantedIconButtonNormalSize.Custom(20.dp).overflowIconSize)
	}

	@Test
	fun `(Given) preset 4종 (When) overflowIconSize 로 calcOverflowGeometry (Then) 기존 박스 36 32 28 24dp 와 같음`() {
		val result = WantedIconButtonNormalSize.presets.map {
			IconButtonGeometry.calcOverflowGeometry(it.overflowIconSize).box
		}
		assertEquals(listOf(36.dp, 32.dp, 28.dp, 24.dp), result)
	}

	// endregion
}
