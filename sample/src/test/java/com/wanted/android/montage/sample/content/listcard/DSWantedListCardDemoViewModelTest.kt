package com.wanted.android.montage.sample.content.listcard

import com.wanted.android.montage.sample.content.listcard.DSWantedListCardDemoScreenContract.DSWantedListCardDemoEvent
import com.wanted.android.montage.sample.content.listcard.DSWantedListCardDemoScreenContract.DSWantedListCardDemoSideEffect
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
class DSWantedListCardDemoViewModelTest {

    @Test
    fun `(Given)  ListCard 데모에서 토글 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  뷰 상태가 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedListCardDemoViewModel()

            viewModel.setEvent(DSWantedListCardDemoEvent.SetLoading(true))
            advanceUntilIdle()

            assertTrue(viewModel.viewState.value.isLoading)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  ListCard 데모에서 코드 보기 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 문자열이 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedListCardDemoViewModel()

            viewModel.setEvent(DSWantedListCardDemoEvent.ShowCode(true))
            advanceUntilIdle()

            val state = viewModel.viewState.value
            assertTrue(state.isShowCode)
            assertTrue(state.code.contains("WantedListCard"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  ListCard 데모에서 코드 복사 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 복사 사이드이펙트를 방출한다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedListCardDemoViewModel()
            val effectDeferred = async { viewModel.sideEffect.first() }

            viewModel.setEvent(DSWantedListCardDemoEvent.CopyCode)
            advanceUntilIdle()

            val effect = effectDeferred.await()
            assertTrue(effect is DSWantedListCardDemoSideEffect.CopyCode)
        } finally {
            Dispatchers.resetMain()
        }
    }
}