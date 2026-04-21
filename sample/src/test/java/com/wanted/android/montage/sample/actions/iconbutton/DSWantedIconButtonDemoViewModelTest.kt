package com.wanted.android.montage.sample.actions.iconbutton

import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoEvent
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.DSWantedIconButtonDemoSideEffect
import com.wanted.android.montage.sample.actions.iconbutton.DSWantedIconButtonDemoScreenContract.IconButtonVariant
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
class DSWantedIconButtonDemoViewModelTest {

    @Test
    fun `(Given)  IconButton 데모에서 옵션 변경 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  뷰 상태가 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedIconButtonDemoViewModel()

            viewModel.setEvent(DSWantedIconButtonDemoEvent.SetVariant(IconButtonVariant.Solid))
            advanceUntilIdle()

            assertEquals(IconButtonVariant.Solid, viewModel.viewState.value.variant)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  IconButton 데모에서 코드 보기 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 문자열이 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedIconButtonDemoViewModel()

            viewModel.setEvent(DSWantedIconButtonDemoEvent.ShowCode(true))
            advanceUntilIdle()

            val state = viewModel.viewState.value
            assertTrue(state.isShowCode)
            assertTrue(state.code.contains("WantedIconButton"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  IconButton 데모에서 코드 복사 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 복사 사이드이펙트를 방출한다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedIconButtonDemoViewModel()
            val effectDeferred = async { viewModel.sideEffect.first() }

            viewModel.setEvent(DSWantedIconButtonDemoEvent.CopyCode)
            advanceUntilIdle()

            val effect = effectDeferred.await()
            assertTrue(effect is DSWantedIconButtonDemoSideEffect.CopyCode)
        } finally {
            Dispatchers.resetMain()
        }
    }
}