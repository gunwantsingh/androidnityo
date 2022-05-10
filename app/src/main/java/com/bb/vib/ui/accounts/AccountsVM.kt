package com.bb.vib.ui.accounts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bb.vib.api.WebServiceRequests
import com.bb.vib.api.model.request.DeviceInfoRequestModel
import com.bb.vib.api.model.request.LoginRequestModel
import com.bb.vib.api.model.response.DeviceInfoResponseModel
import com.bb.vib.api.model.response.LoginResponseModel
import com.bb.vib.base.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AccountsVM(private val webServiceRequests: WebServiceRequests) : ViewModel() {

    val deviceInfoResponse = MutableLiveData<Event<DeviceInfoResponseModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    val progressIndicator = MutableLiveData<Boolean>()

    fun postDeviceInfo(request: DeviceInfoRequestModel) {
        progressIndicator.value = true
        webServiceRequests.postDeviceInfo(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                deviceInfoResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

}