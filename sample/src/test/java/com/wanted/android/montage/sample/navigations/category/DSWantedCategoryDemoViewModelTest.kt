package com.wanted.android.montage.sample.navigations.category

import com.wanted.android.montage.sample.navigations.category.DSWantedCategoryDemoScreenContract.DSWantedCategoryDemoEvent
import com.wanted.android.montage.sample.navigations.category.DSWantedCategoryDemoScreenContract.DSWantedCategoryDemoSideEffect
import com.wanted.android.wanted.design.navigations.category.WantedCategoryDefaults.Size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DSWantedCategoryDemoViewModelTest {

    @Test
    fun `(Given)  Category 데모에서 아이템 선택 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  선택 목록이 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedCategoryDemoViewModel()

            viewModel.setEvent(DSWantedCategoryDemoEvent.ToggleItem("개발"))
            advanceUntilIdle()

            assertTrue(viewModel.viewState.value.selectedItems.contains("개발"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  Category 데모에서 코드 보기 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 문자열이 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedCategoryDemoViewModel()

            viewModel.setEvent(DSWantedCategoryDemoEvent.ShowCode(true))
            advanceUntilIdle()

            val state = viewModel.viewState.value
            assertTrue(state.isShowCode)
            assertTrue(state.code.contains("WantedCategory"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  Category 데모에서 코드 복사 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 복사 사이드이펙트를 방출한다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedCategoryDemoViewModel()
            val effectDeferred = async { viewModel.sideEffect.first() }

            viewModel.setEvent(DSWantedCategoryDemoEvent.CopyCode)
            advanceUntilIdle()

            val effect = effectDeferred.await()
            assertTrue(effect is DSWantedCategoryDemoSideEffect.CopyCode)
        } finally {
            Dispatchers.resetMain()
        }
    }
}
