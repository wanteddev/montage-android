package com.wanted.android.montage.sample.navigations.pagecounter

import com.wanted.android.montage.sample.navigations.pagecounter.DSWantedPageCounterDemoScreenContract.DSWantedPageCounterDemoEvent
import com.wanted.android.montage.sample.navigations.pagecounter.DSWantedPageCounterDemoScreenContract.DSWantedPageCounterDemoSideEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DSWantedPageCounterDemoViewModelTest {

    @Test
    fun `(Given)  PageCounter 데모에서 옵션 변경 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  뷰 상태가 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedPageCounterDemoViewModel()

            viewModel.setEvent(DSWantedPageCounterDemoEvent.SetTotalCount(8))
            advanceUntilIdle()

            assertTrue(viewModel.viewState.value.totalCount == 8)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  PageCounter 데모에서 코드 보기 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 문자열이 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedPageCounterDemoViewModel()

            viewModel.setEvent(DSWantedPageCounterDemoEvent.ShowCode(true))
            advanceUntilIdle()

            val state = viewModel.viewState.value
            assertTrue(state.isShowCode)
            assertTrue(state.code.contains("WantedPageCounter"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  PageCounter 데모에서 코드 복사 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 복사 사이드이펙트를 방출한다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedPageCounterDemoViewModel()
            val effectDeferred = async { viewModel.sideEffect.first() }

            viewModel.setEvent(DSWantedPageCounterDemoEvent.CopyCode)
            advanceUntilIdle()

            val effect = effectDeferred.await()
            assertTrue(effect is DSWantedPageCounterDemoSideEffect.CopyCode)
        } finally {
            Dispatchers.resetMain()
        }
    }
}