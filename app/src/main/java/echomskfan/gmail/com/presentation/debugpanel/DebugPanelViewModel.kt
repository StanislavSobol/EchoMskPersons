package echomskfan.gmail.com.presentation.debugpanel

import android.util.Log
import echomskfan.gmail.com.domain.interactor.debugpanel.IDebugPanelInteractor
import echomskfan.gmail.com.presentation.BaseViewModel

class DebugPanelViewModel(private val interactor: IDebugPanelInteractor) : BaseViewModel() {
    fun deleteLastNevzorovButtonClicked() {
        Log.d("SSS", "DebugPanelViewModel :: deleteLastNevzorovButtonClicked")
    }
}