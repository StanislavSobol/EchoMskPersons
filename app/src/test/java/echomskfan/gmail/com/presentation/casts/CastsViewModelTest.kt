package echomskfan.gmail.com.presentation.casts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import echomskfan.gmail.com.BuildConfig
import echomskfan.gmail.com.domain.interactor.casts.CastsCoInteractor
import echomskfan.gmail.com.domain.interactor.casts.CastsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


//https://medium.com/swlh/unit-testing-with-kotlin-coroutines-the-android-way-19289838d257
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CastsViewModelTest {

    private lateinit var viewModel: CastsViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var interactor: CastsInteractor

    @Mock
    private lateinit var coInteractor: CastsCoInteractor

    @Before
    fun setup() {
        viewModel = CastsViewModel(interactor, coInteractor, PERSON_ID)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun loadData() {
        viewModel.loadData()

        if (BuildConfig.COROUTINES) {
            runBlockingTest {
                verify(coInteractor).transferCastsFromWebToDb(PERSON_ID, 1)
            }
        } else {

        }
    }


    private companion object {
        const val PERSON_ID = 777
    }


}