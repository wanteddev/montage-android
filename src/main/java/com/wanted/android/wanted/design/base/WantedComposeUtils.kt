package com.wanted.android.wanted.design.base

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.util.OPACITY_5

/**
 * 문자열 내에서 특정 부분 텍스트를 강조하여 AnnotatedString을 생성합니다.
 *
 * 전체 문자열에서 subText를 찾아 지정된 textStyle을 적용합니다.
 * 동일한 subText가 여러 번 나타나면 모두 강조됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * "안녕하세요 홍길동님".accent(
 *     subText = "홍길동",
 *     textStyle = TextStyle(color = Color.Blue)
 * )
 * ```
 *
 * @param subText String: 강조할 부분 텍스트입니다.
 * @param textStyle TextStyle: 강조 부분에 적용할 텍스트 스타일입니다.
 * @return AnnotatedString: 강조 스타일이 적용된 AnnotatedString을 반환합니다.
 */
@Composable
fun String.accent(
    subText: String,
    textStyle: TextStyle
): AnnotatedString {
    val spanStyle = SpanStyle(
        fontFamily = textStyle.fontFamily,
        fontSize = textStyle.fontSize,
        fontWeight = textStyle.fontWeight,
        letterSpacing = textStyle.letterSpacing,
        color = textStyle.color
    )

    return buildAnnotatedString {
        var startIndex = 0
        while (startIndex < this@accent.length) {
            val index = this@accent.indexOf(subText, startIndex)
            if (index == -1) {
                append(this@accent.substring(startIndex))
                break
            } else {
                append(this@accent.substring(startIndex, index))
                withStyle(style = spanStyle) {
                    append(subText)
                }
                startIndex = index + subText.length
            }
        }
    }
}

/**
 * 문자열 내에서 여러 부분 텍스트를 강조하여 AnnotatedString을 생성합니다.
 *
 * 전체 문자열에서 여러 개의 subText를 찾아 지정된 textStyle을 적용합니다.
 * 각 subText가 여러 번 나타나면 모두 강조됩니다.
 *
 * 사용 예시:
 * ```kotlin
 * "안녕하세요 홍길동님, 반갑습니다 홍길동님".accent(
 *     "홍길동", "반갑습니다",
 *     textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold)
 * )
 * ```
 *
 * @param subText vararg String: 강조할 여러 부분 텍스트들입니다.
 * @param textStyle TextStyle: 강조 부분에 적용할 텍스트 스타일입니다.
 * @return AnnotatedString: 강조 스타일이 적용된 AnnotatedString을 반환합니다.
 */
@Composable
fun String.accent(
    vararg subText: String,
    textStyle: TextStyle
): AnnotatedString {
    val spanStyles = mutableListOf<AnnotatedString.Range<SpanStyle>>()
    subText.forEach {
        var start = this.indexOf(it, 0)
        while (start != -1) {
            spanStyles.add(
                AnnotatedString.Range(
                    SpanStyle(
                        fontFamily = textStyle.fontFamily,
                        fontSize = textStyle.fontSize,
                        fontWeight = textStyle.fontWeight,
                        letterSpacing = textStyle.letterSpacing,
                        color = textStyle.color
                    ),
                    start = start,
                    end = start + it.length
                )
            )
            start = this.indexOf(it, start + it.length)
        }
    }

    return AnnotatedString(text = this, spanStyles = spanStyles)
}

/**
 * 컴포넌트에 테두리 스타일을 적용하는 Modifier 확장 함수입니다.
 *
 * 원형 또는 둥근 모서리 형태에 대해 테두리 없음, 외곽선, 내부선 타입을 지원합니다.
 * 테두리 타입에 따라 적절한 Modifier 체인을 반환합니다.
 *
 * 사용 예시:
 * ```kotlin
 * Modifier.getBorderModifier(
 *     size = 40.dp,
 *     isCircleShape = true,
 *     borderType = BorderType.OutLine,
 *     borderWidth = 2.dp,
 *     borderColor = Color.Gray
 * )
 * ```
 *
 * @param size Dp: 컴포넌트의 크기입니다.
 * @param isCircleShape Boolean: 원형 여부입니다. true면 원형, false면 둥근 모서리입니다.
 * @param borderType BorderType: 테두리 타입입니다 (None, OutLine, InnerLine).
 * @param cornerRadius Dp: 모서리 반경입니다. 기본값은 0.dp입니다.
 * @param borderWidth Dp: 테두리 두께입니다. 기본값은 1.dp입니다.
 * @param borderColor Color: 테두리 색상입니다.
 * @param backgroundColor Color: 배경 색상입니다.
 * @return Modifier: 테두리 스타일이 적용된 Modifier를 반환합니다.
 */
@Composable
fun Modifier.getBorderModifier(
    size: Dp,
    isCircleShape: Boolean,
    borderType: BorderType,
    cornerRadius: Dp = 0.dp,
    borderWidth: Dp = 1.dp,
    borderColor: Color = colorResource(id = R.color.label_normal).copy(OPACITY_5),
    backgroundColor: Color = colorResource(id = R.color.static_white)
) = this.then(
    when (borderType) {
        BorderType.None -> {
            if (isCircleShape) {
                Modifier
                    .clip(CircleShape)
                    .background(color = backgroundColor)
            } else {
                Modifier
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(color = backgroundColor)
            }
        }

        BorderType.OutLine -> {
            val localDensity = LocalDensity.current
            Modifier
                .drawBehind {
                    if (isCircleShape) {
                        drawCircle(
                            color = borderColor,
                            radius = with(localDensity) { (size + borderWidth * 2).toPx() } / 2,
                            center = center,
                            style = Fill
                        )
                    } else {
                        drawRoundRect(
                            color = borderColor,
                            topLeft = Offset(
                                -with(localDensity) { borderWidth.toPx() },
                                -with(localDensity) { borderWidth.toPx() }),
                            size = Size(
                                with(localDensity) { (size + borderWidth * 2).toPx() },
                                with(localDensity) { (size + borderWidth * 2).toPx() }),
                            cornerRadius = CornerRadius(with(localDensity) { (cornerRadius + borderWidth).toPx() }),
                            style = Fill
                        )
                    }
                }
                .background(
                    color = backgroundColor,
                    shape = if (isCircleShape) {
                        CircleShape
                    } else {
                        RoundedCornerShape(cornerRadius)
                    }
                )
        }

        BorderType.InnerLine -> {
            if (isCircleShape) {
                Modifier
                    .clip(CircleShape)
                    .background(color = backgroundColor)
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = CircleShape
                    )
            } else {
                Modifier
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(color = backgroundColor)
                    .border(
                        width = borderWidth,
                        color = borderColor,
                        shape = RoundedCornerShape(cornerRadius)
                    )
            }
        }
    }
)

/**
 * enum class BorderType
 *
 * 컴포넌트의 테두리 스타일 타입을 정의하는 enum 클래스입니다.
 *
 * 다음과 같은 세 가지 타입을 제공합니다:
 * - None: 테두리가 없는 형태입니다.
 * - InnerLine: 내부에 그려지는 테두리입니다.
 * - OutLine: 외부로 확장되는 테두리입니다.
 */
enum class BorderType {
    None,
    InnerLine,
    OutLine
}
