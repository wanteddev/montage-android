package com.wanted.android.montage.sample.feedback.fallback

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertNotNull
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DSWantedFallbackViewDemoViewModelTest {

    @Test
    fun `(Given)  FallbackView 뷰모델이 생성되면  (When)  초기 상태를 조회하면  (Then)  기본 뷰 상태가 생성된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedFallbackViewDemoViewModel()

            assertNotNull(viewModel.viewState.value)
        } finally {
            Dispatchers.resetMain()
        }
    }
}
