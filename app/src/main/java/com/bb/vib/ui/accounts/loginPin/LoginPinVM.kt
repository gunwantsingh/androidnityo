package com.bb.vib.ui.accounts.loginPin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bb.vib.api.WebServiceRequests
import com.bb.vib.api.model.response.LoginPinResponseModel
import com.bb.vib.api.model.response.RegisterPinResponseModel
import com.bb.vib.base.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginPinVM(private val webServiceRequests: WebServiceRequests) : ViewModel() {

    val loginPinResponse = MutableLiveData<Event<LoginPinResponseModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    val progressIndicator = MutableLiveData<Boolean>()

    fun loginWithPin(userId: String, pin: Int) {
        progressIndicator.value = true
        webServiceRequests.loginWithPin(userId, pin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                loginPinResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

    fun loginWithBiometric(userId: String, biometricToken: String) {
        progressIndicator.value = true
        webServiceRequests.loginWithBiometric(userId, biometricToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                loginPinResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

}