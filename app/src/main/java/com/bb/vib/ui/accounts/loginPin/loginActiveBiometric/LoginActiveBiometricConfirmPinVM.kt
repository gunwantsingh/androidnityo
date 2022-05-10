package com.bb.vib.ui.accounts.loginPin.loginActiveBiometric

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bb.vib.api.WebServiceRequests
import com.bb.vib.api.model.response.RegisterPinResponseModel
import com.bb.vib.base.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginActiveBiometricConfirmPinVM(private val webServiceRequests: WebServiceRequests) : ViewModel() {

    val verifyPinResponse = MutableLiveData<Event<RegisterPinResponseModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    val progressIndicator = MutableLiveData<Boolean>()

    fun verifyPin(userId: String, pin: Int) {
        progressIndicator.value = true
        webServiceRequests.verifyPin(userId, pin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                verifyPinResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

}