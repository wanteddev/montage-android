package com.wanted.android.wanted.design.actions.button.iconbutton.internal

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.ceil

/**
 * object IconButtonGeometry
 *
 * IconButton 박스(컨테이너)에서 아이콘·radius 를 자동 산출하는 유틸입니다.
 * (Confluence IconButton 스펙 §3·§4 — size = 박스 크기, 아이콘은 variant 비율로 자동)
 *
 * - 박스: `clamp(24dp, N, maxDimensionToken())` — 하한 24dp(WCAG 2.2 SC 2.5.8), 상한 dimension 최대 토큰(64dp).
 * - normal / background 아이콘 = `box × 2/3` → dimension 토큰 스냅(동률 → 작은 값).
 * - outlined / solid 아이콘 = `box × 0.47` → dimension 토큰 스냅(동률 → 작은 값).
 * - normal radius = `box × 0.3` → radius 토큰 스냅(동률 → 작은 값). 그 외 variant 는 full(CircleShape).
 */
internal object IconButtonGeometry {

    /**
     * enum class DimensionToken
     *
     * Figma `Dimension` 토큰 후보군입니다. (오름차순)
     */
    private enum class DimensionToken(val dp: Dp) {
        Dp12(12.dp), Dp14(14.dp), Dp16(16.dp), Dp18(18.dp),
        Dp20(20.dp), Dp24(24.dp), Dp28(28.dp), Dp32(32.dp),
        Dp36(36.dp), Dp40(40.dp), Dp48(48.dp), Dp56(56.dp), Dp64(64.dp)
    }

    /**
     * enum class RadiusToken
     *
     * Figma `Radius` 토큰 후보군입니다. (오름차순) [com.wanted.android.wanted.design.theme.WantedRadius] 와 값을 맞춥니다.
     */
    private enum class RadiusToken(val dp: Dp) {
        Dp0(0.dp), Dp4(4.dp), Dp8(8.dp), Dp10(10.dp),
        Dp12(12.dp), Dp14(14.dp), Dp16(16.dp), Dp20(20.dp), Dp24(24.dp),
        Dp28(28.dp), Dp32(32.dp)
    }

    /** 박스 하한 — WCAG 2.2 SC 2.5.8 Target Size Minimum (접근성 필수). */
    private val BOX_MIN = 24.dp

    /** normal / background 아이콘 비율 (`box × 2/3`). */
    private const val NORMAL_BG_ICON_RATIO = 2f / 3f

    /** outlined / solid 아이콘 비율 (`box × 0.47`). */
    private const val OUTLINED_SOLID_ICON_RATIO = 0.47f

    /** normal border-radius 비율 (`box × 0.3`). */
    private const val RADIUS_RATIO = 0.3f

    /** interactionOverflow 인터랙션 영역 비율 (`icon × 1.5`). */
    private const val OVERFLOW_AREA_RATIO = 1.5f

    /** interactionOverflow 인터랙션 영역 올림 단위 (4의 배수). */
    private const val OVERFLOW_AREA_STEP = 4f

    /**
     * fun maxDimensionToken()
     *
     * dimension 토큰 최댓값을 반환합니다. 박스 상한(현재 64dp)으로 사용되며,
     * 토큰이 추가/삭제되면 상한도 함께 따라갑니다. (스펙 §9 — 동적 도출)
     */
    fun maxDimensionToken(): Dp = DimensionToken.entries.last().dp

    /**
     * fun radiusTokens()
     *
     * radius 스냅 후보군을 오름차순으로 반환합니다.
     * [com.wanted.android.wanted.design.theme.WantedRadius] 와 같은 값이어야 하며, 두 목록의 동기화 검증에 씁니다.
     */
    fun radiusTokens(): List<Dp> = RadiusToken.entries.map { it.dp }

    /**
     * fun nearestDimensionToken(...)
     *
     * `raw` 와 가장 가까운 dimension 토큰을 반환합니다.
     * 동률이면 [tieUp] = true 일 때 큰 값을, false 일 때 작은 값을 선택합니다.
     */
    fun nearestDimensionToken(raw: Dp, tieUp: Boolean = true): Dp =
        nearestToken(raw, DimensionToken.entries.map { it.dp }, tieUp)

    /**
     * fun nearestRadiusToken(...)
     *
     * `raw` 와 가장 가까운 radius 토큰을 반환합니다.
     * 동률이면 [tieUp] = true 일 때 큰 값을, false 일 때 작은 값을 선택합니다.
     */
    fun nearestRadiusToken(raw: Dp, tieUp: Boolean = false): Dp =
        nearestToken(raw, RadiusToken.entries.map { it.dp }, tieUp)

    /**
     * fun calcNormalGeometry(...)
     *
     * normal variant 의 박스 → 아이콘·radius 를 산출합니다. (스펙 §3·§4)
     *
     * 1. `box = clamp(24, N, 64)`
     * 2. `icon = nearestDimensionToken(box × 2/3, tie → down)`
     * 3. `radius = nearestRadiusToken(box × 0.3, tie → down)`
     *
     * @param boxSize Dp: 박스(컨테이너) 크기입니다.
     */
    fun calcNormalGeometry(boxSize: Dp): NormalGeometry {
        val box = boxSize.coerceIn(BOX_MIN, maxDimensionToken())
        val icon = nearestDimensionToken(box * NORMAL_BG_ICON_RATIO, tieUp = false)
        val radius = nearestRadiusToken(box * RADIUS_RATIO, tieUp = false)
        return NormalGeometry(box = box, icon = icon, radius = radius)
    }

    /**
     * fun calcOverflowGeometry(...)
     *
     * normal variant 에서 `interactionOverflow = true` 일 때 아이콘 → 인터랙션 영역·radius 를 산출합니다.
     * 이 경로에서는 size 의 숫자가 아이콘 크기를 뜻합니다(Web 방식과 동일, WRP-3163).
     *
     * 1. `icon = iconSize` — dimension 토큰 스냅을 하지 않습니다.
     * 2. `box(인터랙션 영역) = max(24, ceil(icon × 1.5 ÷ 4) × 4)` — 4의 배수로 올려 lookup 표 없이 프리셋
     *    (16 → 24, 18 → 28, 20 → 32, 24 → 36)이 재현되고, ceil 이 단조라 아이콘이 커질 때 영역이 줄지 않습니다.
     *    하한 24 는 WCAG 2.2 SC 2.5.8 입니다.
     * 3. `radius = nearestRadiusToken(box × 0.3, tie → down)`
     *
     * @param iconSize Dp: 아이콘 크기입니다.
     */
    fun calcOverflowGeometry(iconSize: Dp): NormalGeometry {
        val area = (ceil(iconSize.value * OVERFLOW_AREA_RATIO / OVERFLOW_AREA_STEP) * OVERFLOW_AREA_STEP).dp
        val box = maxOf(BOX_MIN, area)
        val radius = nearestRadiusToken(box * RADIUS_RATIO, tieUp = false)
        return NormalGeometry(box = box, icon = iconSize, radius = radius)
    }

    /**
     * fun calcBackgroundGeometry(...)
     *
     * background variant 의 박스 → 아이콘 을 산출합니다. radius 는 full(CircleShape). (스펙 §3·§4)
     *
     * 1. `box = clamp(24, N, 64)`
     * 2. `icon = nearestDimensionToken(box × 2/3, tie → down)`
     *
     * @param boxSize Dp: 박스(컨테이너) 크기입니다.
     */
    fun calcBackgroundGeometry(boxSize: Dp): BackgroundGeometry {
        val box = boxSize.coerceIn(BOX_MIN, maxDimensionToken())
        val icon = nearestDimensionToken(box * NORMAL_BG_ICON_RATIO, tieUp = false)
        return BackgroundGeometry(box = box, icon = icon)
    }

    /**
     * fun calcOutlinedSolidIconSize(...)
     *
     * outlined/solid variant 의 박스 → 아이콘 을 산출합니다. radius 는 full(CircleShape). (스펙 §3·§4)
     *
     * 1. `box = clamp(24, N, 64)`
     * 2. `icon = nearestDimensionToken(box × 0.47, tie → down)`
     *
     * @param boxSize Dp: 박스(컨테이너) 크기입니다.
     */
    fun calcOutlinedSolidIconSize(boxSize: Dp): OutlinedSolidGeometry {
        val box = boxSize.coerceIn(BOX_MIN, maxDimensionToken())
        val icon = nearestDimensionToken(box * OUTLINED_SOLID_ICON_RATIO, tieUp = false)
        return OutlinedSolidGeometry(box = box, icon = icon)
    }

    /**
     * fun nearestToken(...)
     *
     * `tokens` 후보 중 `raw` 와 가장 가까운 값을 선택합니다.
     * - 최솟값 미만: 최솟값 토큰으로 clamp.
     * - 최댓값 초과: 토큰 대신 [evenCeil] 로 짝수 Dp 반환 (WRP-927 스펙).
     * - 범위 내: 동률이면 [tieUp] 에 따라 큰/작은 값을 선택합니다.
     */
    private fun nearestToken(raw: Dp, tokens: List<Dp>, tieUp: Boolean): Dp {
        if (raw <= tokens.first()) return tokens.first()
        if (raw > tokens.last()) return evenCeil(raw)

        var best: Dp = tokens.first()
        var bestDiff = abs(raw.value - best.value)
        tokens.drop(1).forEach { token ->
            val diff = abs(raw.value - token.value)
            val isCloser = diff < bestDiff
            val isTieAndBigger = diff == bestDiff && token.value > best.value
            if (isCloser || (tieUp && isTieAndBigger)) {
                best = token
                bestDiff = diff
            }
        }
        return best
    }

    /**
     * fun evenCeil(...)
     *
     * `raw` 를 올림(ceil)한 뒤 짝수로 정렬해 반환합니다.
     * 토큰 최댓값 초과 시 토큰 대신 이 값을 사용합니다 (WRP-927 스펙).
     */
    private fun evenCeil(raw: Dp): Dp {
        val ceilValue = ceil(raw.value).toInt()
        return if (ceilValue % 2 == 0) ceilValue.dp else (ceilValue + 1).dp
    }

    /**
     * data class NormalGeometry
     *
     * @property box Dp: 박스(hit area) 크기입니다.
     * @property icon Dp: 아이콘 크기입니다.
     * @property radius Dp: 모서리 반경입니다.
     */
    data class NormalGeometry(val box: Dp, val icon: Dp, val radius: Dp) {

        /** 아이콘 모서리와 박스 모서리 사이의 한 변 여백 `(box - icon) / 2` 입니다. PushBadge 정렬 보정값이자, `interactionOverflow = true` 일 때 박스가 레이아웃 밖으로 넘치는 상하좌우 크기입니다. (스펙 §5 Interaction Overflow) */
        val overflowInset: Dp = (box - icon) / 2
    }

    /**
     * data class BackgroundGeometry
     *
     * @property box Dp: 박스(hit area) 크기입니다.
     * @property icon Dp: 아이콘 크기입니다.
     */
    data class BackgroundGeometry(val box: Dp, val icon: Dp)

    /**
     * data class OutlinedSolidGeometry
     *
     * @property box Dp: 박스(컨테이너) 크기입니다.
     * @property icon Dp: 아이콘 크기입니다.
     */
    data class OutlinedSolidGeometry(val box: Dp, val icon: Dp)
}
