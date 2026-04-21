package com.wanted.android.montage.sample.navigations.progresstracker

import com.wanted.android.montage.sample.navigations.progresstracker.DSWantedProgressTrackerDemoScreenContract.DSWantedProgressTrackerDemoEvent
import com.wanted.android.montage.sample.navigations.progresstracker.DSWantedProgressTrackerDemoScreenContract.DSWantedProgressTrackerDemoSideEffect
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
class DSWantedProgressTrackerDemoViewModelTest {

    @Test
    fun `(Given)  ProgressTracker 데모에서 옵션 변경 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  뷰 상태가 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedProgressTrackerDemoViewModel()

            viewModel.setEvent(DSWantedProgressTrackerDemoEvent.SetStepCount(6))
            advanceUntilIdle()

            assertTrue(viewModel.viewState.value.stepCount == 6)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  ProgressTracker 데모에서 코드 보기 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 문자열이 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedProgressTrackerDemoViewModel()

            viewModel.setEvent(DSWantedProgressTrackerDemoEvent.ShowCode(true))
            advanceUntilIdle()

            val state = viewModel.viewState.value
            assertTrue(state.isShowCode)
            assertTrue(state.code.contains("WantedProgressTracker"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  ProgressTracker 데모에서 코드 복사 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 복사 사이드이펙트를 방출한다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedProgressTrackerDemoViewModel()
            val effectDeferred = async { viewModel.sideEffect.first() }

            viewModel.setEvent(DSWantedProgressTrackerDemoEvent.CopyCode)
            advanceUntilIdle()

            val effect = effectDeferred.await()
            assertTrue(effect is DSWantedProgressTrackerDemoSideEffect.CopyCode)
        } finally {
            Dispatchers.resetMain()
        }
    }
}