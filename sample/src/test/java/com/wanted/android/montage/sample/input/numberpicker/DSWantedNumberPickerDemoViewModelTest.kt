package com.wanted.android.montage.sample.input.numberpicker

import com.wanted.android.montage.sample.input.numberpicker.DSWantedNumberPickerDemoScreenContract.DSWantedNumberPickerDemoEvent
import com.wanted.android.montage.sample.input.numberpicker.DSWantedNumberPickerDemoScreenContract.DSWantedNumberPickerDemoSideEffect
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
class DSWantedNumberPickerDemoViewModelTest {

    @Test
    fun `(Given)  NumberPicker 데모에서 step 값이 1 미만이면  (When)  step 변경 이벤트를 처리하면  (Then)  step이 1로 보정된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedNumberPickerDemoViewModel()

            viewModel.setEvent(DSWantedNumberPickerDemoEvent.SetStep(0))
            advanceUntilIdle()

            val state = viewModel.viewState.value
            assertEquals(1, state.step)
            assertEquals(state.start, state.selectedValue)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  NumberPicker 데모에서 코드 보기 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 문자열이 업데이트된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedNumberPickerDemoViewModel()

            viewModel.setEvent(DSWantedNumberPickerDemoEvent.ShowCode(true))
            advanceUntilIdle()

            val state = viewModel.viewState.value
            assertTrue(state.isShowCode)
            assertTrue(state.code.contains("WantedNumberPicker"))
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `(Given)  NumberPicker 데모에서 코드 복사 이벤트가 전달되면  (When)  이벤트를 처리하면  (Then)  코드 복사 사이드이펙트를 방출한다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedNumberPickerDemoViewModel()
            val effectDeferred = async { viewModel.sideEffect.first() }

            viewModel.setEvent(DSWantedNumberPickerDemoEvent.CopyCode)
            advanceUntilIdle()

            val effect = effectDeferred.await()
            assertTrue(effect is DSWantedNumberPickerDemoSideEffect.CopyCode)
        } finally {
            Dispatchers.resetMain()
        }
    }
}
