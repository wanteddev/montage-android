package com.wanted.android.montage.sample.graph

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
sealed interface MontageDesignDemoNavContract {

    @Keep
    @Serializable
    data object DemoList : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ButtonDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object FallbackViewDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ToastDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object DropShadowDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object CardDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object InputDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object FramedStyleDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object TextFieldDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object TextAreaDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object TooltipDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object PopoverDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object TopAppBarDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object DialogTopAppBarDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object SearchFieldDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object SearchTopAppBarDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object AvatarDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object AccordionDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ListCellDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object AlertDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object ContentBadgeDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object TimePickerWheelDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object PushBadgeDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object SnackBarDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object PlayBadgeDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object SectionHeaderDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object DatePickerDemo : MontageDesignDemoNavContract

    @Keep
    @Serializable
    data object DatePickerWheelDemo : MontageDesignDemoNavContract

    companion object {
        fun fromClassName(className: String): MontageDesignDemoNavContract? {
            return MontageDesignDemoNavContract::class.sealedSubclasses
                .mapNotNull { it.objectInstance }
                .find { it::class.simpleName == className }
        }

        fun getAllDataObjects(): List<MontageDesignDemoNavContract> {
            return MontageDesignDemoNavContract::class.sealedSubclasses
                .mapNotNull { it.objectInstance }
        }
    }
}
