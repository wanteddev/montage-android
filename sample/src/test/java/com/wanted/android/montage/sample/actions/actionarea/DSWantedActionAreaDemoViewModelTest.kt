package com.wanted.android.montage.sample.actions.actionarea

import com.wanted.android.montage.sample.actions.actionarea.DSWantedActionAreaDemoScreenContract.DSWantedActionAreaDemoEvent
import com.wanted.android.montage.sample.actions.actionarea.DSWantedActionAreaDemoScreenContract.DSWantedActionAreaDemoSideEffect
import com.wanted.android.wanted.design.actions.actionarea.ActionAreaType
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
class DSWantedActionAreaDemoViewModelTest {

    @Test
    fun `(Given)  ActionArea 데모에서 옵션 변경 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  뷰 상태가 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedActionAreaDemoViewModel()

            viewModel.setEvent(DSWantedActionAreaDemoEvent.SetType(ActionAreaType.Neutral))
            advanceUntilIdle()

            assertEquals(ActionAreaType.Neutral, viewModel.viewState.value.type)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  ActionArea 데모에서 코드 보기 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 문자열이 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedActionAreaDemoViewModel()

            viewModel.setEvent(DSWantedActionAreaDemoEvent.ShowCode(true))
            advanceUntilIdle()

            val state = viewModel.viewState.value
            assertTrue(state.isShowCode)
            assertTrue(state.code.contains("WantedActionArea"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  ActionArea 데모에서 코드 복사 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 복사 사이드이펙트를 방출한다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedActionAreaDemoViewModel()
            val effectDeferred = async { viewModel.sideEffect.first() }

            viewModel.setEvent(DSWantedActionAreaDemoEvent.CopyCode)
            advanceUntilIdle()

            val effect = effectDeferred.await()
            assertTrue(effect is DSWantedActionAreaDemoSideEffect.CopyCode)
        } finally {
            Dispatchers.resetMain()
        }
    }
}
