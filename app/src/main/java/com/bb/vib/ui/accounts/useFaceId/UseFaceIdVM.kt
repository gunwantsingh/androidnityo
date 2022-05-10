package com.bb.vib.ui.accounts.useFaceId

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bb.vib.api.WebServiceRequests
import com.bb.vib.api.model.response.LoginResponseModel
import com.bb.vib.base.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UseFaceIdVM(private val webServiceRequests: WebServiceRequests) : ViewModel() {

    val enableDisableBiometricResponse = MutableLiveData<Event<LoginResponseModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    val progressIndicator = MutableLiveData<Boolean>()

    fun enableDisableBiometric(enableBiometrics: String) {
        progressIndicator.value = true
        webServiceRequests.enableDisableBiometric(enableBiometrics)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                enableDisableBiometricResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

}