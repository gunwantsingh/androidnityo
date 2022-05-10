package com.bb.vib.ui.home.others.smartOtpPin.activeSmartOtp.confirmSmartOtp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bb.vib.api.WebServiceRequests
import com.bb.vib.api.model.request.DeviceInfoRequestModel
import com.bb.vib.api.model.response.DeviceInfoResponseModel
import com.bb.vib.api.model.response.RegisterPinResponseModel
import com.bb.vib.base.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ConfirmSmartOtpVM(private val webServiceRequests: WebServiceRequests) : ViewModel() {

    val activationCodeResponse = MutableLiveData<Event<RegisterPinResponseModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    val progressIndicator = MutableLiveData<Boolean>()

    fun sendActivationCode(userId: String) {
        progressIndicator.value = true
        webServiceRequests.sendActivationCode(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                activationCodeResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

}