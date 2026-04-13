package com.wanted.android.montage.sample.content.accordion

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertNotNull
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DSWantedAccordionDemoViewModelTest {

    @Test
    fun `(Given)  Accordion 뷰모델이 생성되면  (When)  초기 상태를 조회하면  (Then)  기본 뷰 상태가 생성된다 `() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        try {
            val viewModel = DSWantedAccordionDemoViewModel()

            assertNotNull(viewModel.viewState.value)
        } finally {
            Dispatchers.resetMain()
        }
    }
}
