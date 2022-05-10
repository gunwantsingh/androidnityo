package com.bb.vib.ui.home.others.otherForgotPin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bb.vib.api.WebServiceRequests
import com.bb.vib.api.model.response.LoginPinResponseModel
import com.bb.vib.api.model.response.RegisterPinResponseModel
import com.bb.vib.base.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OtherForgotPinVM(private val webServiceRequests: WebServiceRequests) : ViewModel() {

    val verifyUsernameResponse = MutableLiveData<Event<RegisterPinResponseModel>>()
    val verifyPasswordResponse = MutableLiveData<Event<RegisterPinResponseModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    val progressIndicator = MutableLiveData<Boolean>()

    fun verifyUsername(userId: String, username: String) {
        progressIndicator.value = true
        webServiceRequests.verifyUsername(userId, username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                verifyUsernameResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

    fun verifyPassword(userId: String, password: String) {
        progressIndicator.value = true
        webServiceRequests.verifyPassword(userId, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                verifyPasswordResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

}