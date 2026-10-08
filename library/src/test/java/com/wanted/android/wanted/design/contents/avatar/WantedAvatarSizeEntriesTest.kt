package com.wanted.android.wanted.design.contents.avatar

import com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults.WantedAvatarSize
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

/**
 * [WantedAvatarSize.Companion.entries] 단위 테스트
 *
 * 검증 항목:
 * - 고정 사이즈 5종을 선언 순서대로 담는다
 * - 하위 data object 를 먼저 건드린 뒤 읽어도 `null` 원소가 섞이지 않는다 (WRP-2853)
 *
 * 두 번째 항목은 클래스 초기화 순서에 달려 있어 같은 JVM 에서 한 번 초기화되면 재현되지 않는다.
 * 따라서 대상 패키지만 직접 로드하는 child-first 클래스로더로 정적 상태를 격리해 검증한다.
 */
class WantedAvatarSizeEntriesTest {

	@Test
	fun `(Given) WantedAvatarSize (When) entries 확인 (Then) 고정 사이즈 5종을 선언 순서로 반환`() {
		assertEquals(
			listOf(
				WantedAvatarSize.XSmall,
				WantedAvatarSize.Small,
				WantedAvatarSize.Medium,
				WantedAvatarSize.Large,
				WantedAvatarSize.XLarge
			),
			WantedAvatarSize.entries
		)
	}

	@Test
	fun `(Given) 하위 data object 를 먼저 초기화 (When) entries 확인 (Then) null 원소가 없다`() {
		val loader = IsolatedAvatarClassLoader(javaClass.classLoader!!)

		// 하위 data object 초기화를 먼저 트리거한다 — 과거 이 경로에서 entries 에 null 이 담겼다
		Class.forName("$SIZE_CLASS_NAME\$XSmall", true, loader)

		val companion = Class.forName("$SIZE_CLASS_NAME\$Companion", true, loader)
		val sizeClass = Class.forName(SIZE_CLASS_NAME, true, loader)
		val companionInstance = sizeClass.getDeclaredField("Companion")
			.apply { isAccessible = true }
			.get(null)
		val entries = companion.getDeclaredMethod("getEntries")
			.apply { isAccessible = true }
			.invoke(companionInstance) as List<*>

		assertEquals(5, entries.size)
		assertFalse(entries.contains(null))
	}

	/**
	 * 대상 패키지의 클래스만 직접 정의하고 나머지는 부모에 위임하는 클래스로더입니다.
	 * 부모에 위임하면 이미 초기화된 정적 상태를 재사용해 초기화 순서를 재현할 수 없습니다.
	 */
	private class IsolatedAvatarClassLoader(
		private val source: ClassLoader
	) : ClassLoader(source) {

		override fun loadClass(name: String, resolve: Boolean): Class<*> =
			findLoadedClass(name)
				?: name.takeIf { it.startsWith(ISOLATED_PACKAGE_PREFIX) }
					?.let { source.getResourceAsStream("${it.replace('.', '/')}.class") }
					?.use { it.readBytes() }
					?.let { defineClass(name, it, 0, it.size) }
					?.also { if (resolve) resolveClass(it) }
				?: super.loadClass(name, resolve)
	}

	companion object {
		private const val SIZE_CLASS_NAME =
			"com.wanted.android.wanted.design.contents.avatar.WantedAvatarDefaults\$WantedAvatarSize"
		private const val ISOLATED_PACKAGE_PREFIX =
			"com.wanted.android.wanted.design.contents.avatar."
	}
}
