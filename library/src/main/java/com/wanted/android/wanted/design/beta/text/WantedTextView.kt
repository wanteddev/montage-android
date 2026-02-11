package com.wanted.android.wanted.design.beta.text

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.text.TextStyle
import androidx.core.content.ContextCompat
import com.wanted.android.designsystem.R
import com.wanted.android.wanted.design.theme.DesignSystemTheme
import com.wanted.android.wanted.design.util.WantedTextStyle
import com.wanted.android.wanted.design.util.getTextStyle

class WantedTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    private var text = ""
    private var textStyle = WantedTextStyle.BODY1_REGULAR
    private var textColor = 0

    init {
        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.WantedTextView).run {
                text = getString(R.styleable.WantedTextView_text) ?: ""
                textStyle =
                    WantedTextStyle.entries[getInteger(R.styleable.WantedTextView_textStyle, 5)]
                textColor = getColor(
                    R.styleable.WantedTextView_textColor,
                    ContextCompat.getColor(context, R.color.label_normal)
                )

                recycle()
            }
        }
    }

    @Composable
    override fun Content() {
        TextView(
            text = text,
            textStyle = textStyle,
            textColor = Color(textColor),
        )
    }
}

@Composable
fun TextView(
    text: String = "",
    textStyle: WantedTextStyle = WantedTextStyle.BODY1_REGULAR,
    textColor: Color = DesignSystemTheme.colors.labelNormal,
    modifier: Modifier = Modifier.wrapContentSize(),
    onClick: (() -> Unit)? = null
) {
    val style = getTextStyle(textStyle = textStyle)

    Text(
        modifier = modifier.clickable { onClick?.invoke() },
        text = text,
        style = TextStyle(
            fontFamily = style.fontFamily,
            fontWeight = style.fontWeight,
            fontSize = style.fontSize,
            letterSpacing = style.letterSpacing,
            lineHeight = style.lineHeight,
            color = textColor
        )
    )
}
