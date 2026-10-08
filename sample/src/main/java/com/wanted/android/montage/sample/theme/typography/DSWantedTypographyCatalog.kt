package com.wanted.android.montage.sample.theme.typography

import androidx.compose.ui.text.TextStyle
import com.wanted.android.wanted.design.theme.WantedTypography

/**
 * 타이포그래피 데모에서 쓰는 토큰 굵기 구분입니다. [All] 은 필터에서 전체를 뜻합니다.
 */
internal enum class TypographyWeight(val label: String) {
    All("전체"),
    Regular("Regular"),
    Medium("Medium"),
    Bold("Bold");

    companion object {
        // 토큰 이름의 접미사(…Regular / …Medium / …Bold)로 굵기를 판정한다.
        fun fromTokenName(name: String): TypographyWeight = when {
            name.endsWith(Regular.name) -> Regular
            name.endsWith(Medium.name) -> Medium
            name.endsWith(Bold.name) -> Bold
            else -> All
        }
    }
}

/**
 * 타이포그래피 데모에 표시할 샘플 문장입니다.
 */
internal enum class TypographySample(val label: String, val text: String) {
    Korean("한글", "다람쥐 헌 쳇바퀴에 타고파"),
    English("영문", "The quick brown fox jumps over the lazy dog"),
    Number("숫자", "0123456789 ₩12,345,000 (+82) 3.14%"),
    MultiLine("여러 줄", "원티드에서 일하는 사람들의 모든 가능성을 연결합니다. 두 줄 이상으로 넘어갈 때 행간을 확인하세요.")
}

/**
 * 타이포그래피 토큰 하나입니다.
 *
 * @property name String: [WantedTypography] 의 프로퍼티 이름입니다. 코드 복사에 그대로 씁니다.
 * @property style (WantedTypography) -> TextStyle: 테마의 typography 에서 이 토큰을 꺼내는 함수입니다.
 */
internal data class DSWantedTypographyItem(
    val name: String,
    val style: (WantedTypography) -> TextStyle
) {
    val weight: TypographyWeight = TypographyWeight.fromTokenName(name)
}

/**
 * [WantedTypography] 의 전체 토큰 목록입니다. 타입 스케일 순서(큰 → 작은)로 둡니다.
 *
 * 토큰을 추가·삭제하면 이 목록도 함께 바꿔야 한다. 누락은 DSWantedTypographyCatalogTest 가 잡는다.
 */
internal object DSWantedTypographyCatalog {
    val items: List<DSWantedTypographyItem> = listOf(
        DSWantedTypographyItem("display1Regular") { it.display1Regular },
        DSWantedTypographyItem("display1Medium") { it.display1Medium },
        DSWantedTypographyItem("display1Bold") { it.display1Bold },
        DSWantedTypographyItem("display2Regular") { it.display2Regular },
        DSWantedTypographyItem("display2Medium") { it.display2Medium },
        DSWantedTypographyItem("display2Bold") { it.display2Bold },
        DSWantedTypographyItem("display3Regular") { it.display3Regular },
        DSWantedTypographyItem("display3Medium") { it.display3Medium },
        DSWantedTypographyItem("display3Bold") { it.display3Bold },
        DSWantedTypographyItem("title1Regular") { it.title1Regular },
        DSWantedTypographyItem("title1Medium") { it.title1Medium },
        DSWantedTypographyItem("title1Bold") { it.title1Bold },
        DSWantedTypographyItem("title2Regular") { it.title2Regular },
        DSWantedTypographyItem("title2Medium") { it.title2Medium },
        DSWantedTypographyItem("title2Bold") { it.title2Bold },
        DSWantedTypographyItem("title3Regular") { it.title3Regular },
        DSWantedTypographyItem("title3Medium") { it.title3Medium },
        DSWantedTypographyItem("title3Bold") { it.title3Bold },
        DSWantedTypographyItem("heading1Regular") { it.heading1Regular },
        DSWantedTypographyItem("heading1Medium") { it.heading1Medium },
        DSWantedTypographyItem("heading1Bold") { it.heading1Bold },
        DSWantedTypographyItem("heading2Regular") { it.heading2Regular },
        DSWantedTypographyItem("heading2Medium") { it.heading2Medium },
        DSWantedTypographyItem("heading2Bold") { it.heading2Bold },
        DSWantedTypographyItem("headline1Regular") { it.headline1Regular },
        DSWantedTypographyItem("headline1Medium") { it.headline1Medium },
        DSWantedTypographyItem("headline1Bold") { it.headline1Bold },
        DSWantedTypographyItem("headline2Regular") { it.headline2Regular },
        DSWantedTypographyItem("headline2Medium") { it.headline2Medium },
        DSWantedTypographyItem("headline2Bold") { it.headline2Bold },
        DSWantedTypographyItem("body1Regular") { it.body1Regular },
        DSWantedTypographyItem("body1Medium") { it.body1Medium },
        DSWantedTypographyItem("body1Bold") { it.body1Bold },
        DSWantedTypographyItem("body1ReadingRegular") { it.body1ReadingRegular },
        DSWantedTypographyItem("body1ReadingMedium") { it.body1ReadingMedium },
        DSWantedTypographyItem("body1ReadingBold") { it.body1ReadingBold },
        DSWantedTypographyItem("body2Regular") { it.body2Regular },
        DSWantedTypographyItem("body2Medium") { it.body2Medium },
        DSWantedTypographyItem("body2Bold") { it.body2Bold },
        DSWantedTypographyItem("body2ReadingRegular") { it.body2ReadingRegular },
        DSWantedTypographyItem("body2ReadingMedium") { it.body2ReadingMedium },
        DSWantedTypographyItem("body2ReadingBold") { it.body2ReadingBold },
        DSWantedTypographyItem("label1Regular") { it.label1Regular },
        DSWantedTypographyItem("label1Medium") { it.label1Medium },
        DSWantedTypographyItem("label1Bold") { it.label1Bold },
        DSWantedTypographyItem("label1ReadingRegular") { it.label1ReadingRegular },
        DSWantedTypographyItem("label1ReadingMedium") { it.label1ReadingMedium },
        DSWantedTypographyItem("label1ReadingBold") { it.label1ReadingBold },
        DSWantedTypographyItem("label2Regular") { it.label2Regular },
        DSWantedTypographyItem("label2Medium") { it.label2Medium },
        DSWantedTypographyItem("label2Bold") { it.label2Bold },
        DSWantedTypographyItem("caption1Regular") { it.caption1Regular },
        DSWantedTypographyItem("caption1Medium") { it.caption1Medium },
        DSWantedTypographyItem("caption1Bold") { it.caption1Bold },
        DSWantedTypographyItem("caption2Regular") { it.caption2Regular },
        DSWantedTypographyItem("caption2Medium") { it.caption2Medium },
        DSWantedTypographyItem("caption2Bold") { it.caption2Bold }
    )
}
