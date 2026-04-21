package com.wanted.android.montage.sample.feedback.loading

import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreenContract.DSWantedLoadingDemoEvent
import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreenContract.DSWantedLoadingDemoSideEffect
import com.wanted.android.montage.sample.feedback.loading.DSWantedLoadingDemoScreenContract.LoadingType
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
class DSWantedLoadingDemoViewModelTest {

    @Test
    fun `(Given)  Loading 데모에서 옵션 변경 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  뷰 상태가 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedLoadingDemoViewModel()

            viewModel.setEvent(DSWantedLoadingDemoEvent.SetLoadingType(LoadingType.Logo))
            advanceUntilIdle()

            assertEquals(LoadingType.Logo, viewModel.viewState.value.loadingType)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  Loading 데모에서 코드 보기 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 문자열이 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedLoadingDemoViewModel()

            viewModel.setEvent(DSWantedLoadingDemoEvent.ShowCode(true))
            advanceUntilIdle()

            val state = viewModel.viewState.value
            assertTrue(state.isShowCode)
            assertTrue(state.code.contains("WantedCircularLoading"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  Loading 데모에서 코드 복사 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 복사 사이드이펙트를 방출한다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedLoadingDemoViewModel()
            val effectDeferred = async { viewModel.sideEffect.first() }

            viewModel.setEvent(DSWantedLoadingDemoEvent.CopyCode)
            advanceUntilIdle()

            val effect = effectDeferred.await()
            assertTrue(effect is DSWantedLoadingDemoSideEffect.CopyCode)
        } finally {
            Dispatchers.resetMain()
        }
    }
}