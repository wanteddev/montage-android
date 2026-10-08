package com.wanted.android.montage.sample.theme.icon

private const val HANGUL_SYLLABLE_START = 0xAC00
private const val HANGUL_SYLLABLE_END = 0xD7A3
private const val CHOSUNG_DIVISOR = 588

private val CHOSUNG_LIST = listOf(
    'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
    'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
)

private val CHOSUNG_SET = CHOSUNG_LIST.toSet()

/**
 * 한글 음절을 초성 문자열로 변환한다. 음절이 아닌 문자는 그대로 둔다.
 * 예: "하트" -> "ㅎㅌ"
 */
internal fun extractChosung(text: String): String = buildString {
    text.forEach { char ->
        val code = char.code
        if (code in HANGUL_SYLLABLE_START..HANGUL_SYLLABLE_END) {
            append(CHOSUNG_LIST[(code - HANGUL_SYLLABLE_START) / CHOSUNG_DIVISOR])
        } else {
            append(char)
        }
    }
}

/** 입력이 공백을 제외하고 모두 초성 자모로만 이루어졌는지 여부 */
internal fun isChosungQuery(query: String): Boolean {
    val nonBlank = query.filterNot { it.isWhitespace() }
    return nonBlank.isNotEmpty() && nonBlank.all { it in CHOSUNG_SET }
}

/** 검색 비교용으로 구분자(_, 공백)를 제거한다. */
private fun stripSeparators(text: String): String = buildString {
    text.forEach { char ->
        if (char != '_' && !char.isWhitespace()) append(char)
    }
}

/** 영문 부분일치 비교용 정규화: 구분자(_, 공백) 제거 + 소문자화 */
private fun normalizeForMatch(text: String): String = stripSeparators(text).lowercase()

/**
 * 아이콘 1개에 대한 검색용 사전 계산 인덱스.
 * 정규화·초성 변환은 아이콘 목록이 바뀔 때 한 번만 수행하고,
 * 질의가 바뀔 때마다 재계산하지 않도록 결과를 보관한다.
 */
internal data class IconSearchIndex(
    val normalizedResourceName: String,
    val normalizedKeywords: List<String>,
    val chosungKeywords: List<String>
)

/** 리소스명/키워드를 [IconSearchIndex]로 사전 계산한다. */
internal fun buildIconSearchIndex(
    resourceName: String,
    keywords: List<String>
): IconSearchIndex = IconSearchIndex(
    normalizedResourceName = normalizeForMatch(resourceName),
    normalizedKeywords = keywords.map(::normalizeForMatch),
    chosungKeywords = keywords.map { stripSeparators(extractChosung(it)) }
)

/**
 * 아이콘 검색 매칭.
 * 영문 리소스명/한글 키워드 부분일치와 한글 초성 검색을 지원한다.
 * 영문 비교 시 query/resourceName/keywords를 동일하게 정규화(구분자 제거)하여
 * `linked_in`/`google_play` 같은 리소스를 `linkedin`/`googleplay` 질의로도 찾는다.
 */
internal fun matchesIconQuery(
    query: String,
    index: IconSearchIndex
): Boolean {
    val trimmedQuery = query.trim()
    if (trimmedQuery.isBlank()) return true

    val normalizedQuery = normalizeForMatch(trimmedQuery)
    if (normalizedQuery.isNotEmpty()) {
        if (index.normalizedResourceName.contains(normalizedQuery)) return true
        if (index.normalizedKeywords.any { it.contains(normalizedQuery) }) return true
    }

    if (isChosungQuery(trimmedQuery)) {
        val chosungQuery = stripSeparators(trimmedQuery)
        return index.chosungKeywords.any { it.contains(chosungQuery) }
    }
    return false
}
