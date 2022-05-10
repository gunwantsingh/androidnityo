package com.bb.vib.ui.accounts.termsConditions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bb.vib.api.WebServiceRequests
import com.bb.vib.api.model.response.DeviceInfoResponseModel
import com.bb.vib.api.model.response.RegisterPinResponseModel
import com.bb.vib.base.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TermsConditionsVM(private val webServiceRequests: WebServiceRequests) : ViewModel() {

    val deviceLanguageResponse = MutableLiveData<Event<DeviceInfoResponseModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    val progressIndicator = MutableLiveData<Boolean>()

    fun changeDeviceLanguage(language: String) {
        progressIndicator.value = true
        webServiceRequests.changeDeviceLanguage(language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                deviceLanguageResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

}