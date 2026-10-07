package com.wanted.android.wanted.design.navigations.topbar.dialogtopbar


/**
 * object WantedDialogTopAppBarContract
 *
 * DialogTopAppBar 컴포넌트에서 사용하는 설정 값을 정의하는 객체입니다.
 */
object WantedDialogTopAppBarContract {


    /**
     * sealed class Variant
     *
     * TopDialogAppBar 의 형태를 정의하는 sealed 클래스입니다.
     * Normal, Emphasized, Floating, Search 네 가지를 제공합니다.
     */
    sealed class Variant {

        /**
         * data object Normal
         *
         * Title 이 Center 에 위치합니다. Full 에서만 사용합니다.
         */
        data object Normal : Variant()

        /**
         * data object Emphasized
         *
         * Title 이 왼쪽에 위치합니다. Popup · Bottom Sheet 의 기본값입니다.
         */
        data object Emphasized : Variant()

        /**
         * data class Floating
         *
         * 플로팅 형태입니다. Title 없이 Leading · Trailing 버튼만 콘텐츠 위에 떠 있습니다.
         *
         * @property iconBackground Boolean: Leading · Trailing 아이콘 버튼에 반투명 원형 배경을 넣을지 여부입니다. 상단에 이미지가 깔릴 때 사용하며 기본값은 false 입니다. Text 버튼에는 적용되지 않습니다.
         */
        data class Floating(val iconBackground: Boolean = false) : Variant()

        /**
         * data object Search
         *
         * 제목 대신 검색 필드(폭 가변)를 두는 형태입니다. Popup · Bottom Sheet · Full 어디서나 사용할 수 있습니다.
         * 여백은 다른 variant 와 같은 navigationPadding 을 쓰고, 바 높이는 검색 필드(40) + 상하 여백으로 정해집니다.
         * (Popup · Bottom Sheet 88, Full 80)
         */
        data object Search : Variant()

        companion object {
            // 선택 UI(데모 등) 에서 쓰는 preset 목록입니다.
            val presets: List<Variant> = listOf(Normal, Emphasized, Floating(), Search)
        }
    }
}

// Floating 이 아닌 variant 에는 아이콘 배경이 없다. Floating 여부 판정과 값 꺼내기를 한 곳으로 모은다.
internal val WantedDialogTopAppBarContract.Variant.useIconBackground: Boolean
    get() = (this as? WantedDialogTopAppBarContract.Variant.Floating)?.iconBackground == true
