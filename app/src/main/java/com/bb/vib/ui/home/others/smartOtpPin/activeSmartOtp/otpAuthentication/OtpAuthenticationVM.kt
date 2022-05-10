package com.bb.vib.ui.home.others.smartOtpPin.activeSmartOtp.otpAuthentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bb.vib.api.WebServiceRequests
import com.bb.vib.api.model.response.RegisterPinResponseModel
import com.bb.vib.base.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OtpAuthenticationVM(private val webServiceRequests: WebServiceRequests) : ViewModel() {

    val verifyActivationCodeResponse = MutableLiveData<Event<RegisterPinResponseModel>>()
    val activateSmartOtpResponse = MutableLiveData<Event<RegisterPinResponseModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    val progressIndicator = MutableLiveData<Boolean>()

    fun verifyActivationCode(userId: String, activationCode: String) {
        progressIndicator.value = true
        webServiceRequests.verifyActivationCode(userId, activationCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                verifyActivationCodeResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

    fun activateSmartOtp(userId: String) {
        progressIndicator.value = true
        webServiceRequests.activateSmartOtp(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                activateSmartOtpResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

}