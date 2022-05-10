package com.bb.vib.ui.accounts.createPin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bb.vib.api.WebServiceRequests
import com.bb.vib.api.model.request.DeviceInfoRequestModel
import com.bb.vib.api.model.request.RegisterPinRequestModel
import com.bb.vib.api.model.response.DeviceInfoResponseModel
import com.bb.vib.api.model.response.RegisterPinResponseModel
import com.bb.vib.base.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CreatePinVM(private val webServiceRequests: WebServiceRequests) : ViewModel() {

    val registerPinResponse = MutableLiveData<Event<RegisterPinResponseModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    val progressIndicator = MutableLiveData<Boolean>()

    fun registerPin(request: RegisterPinRequestModel) {
        progressIndicator.value = true
        webServiceRequests.registerPin(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                registerPinResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

}