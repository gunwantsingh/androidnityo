package com.bb.vib.ui.accounts.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bb.vib.api.WebServiceRequests
import com.bb.vib.api.model.request.LoginRequestModel
import com.bb.vib.api.model.response.LoginResponseModel
import com.bb.vib.base.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginVM(private val webServiceRequests: WebServiceRequests) : ViewModel() {

    val loginResponse = MutableLiveData<Event<LoginResponseModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    val progressIndicator = MutableLiveData<Boolean>()

    fun login(request: LoginRequestModel) {
        progressIndicator.value = true
        webServiceRequests.login(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                loginResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

}