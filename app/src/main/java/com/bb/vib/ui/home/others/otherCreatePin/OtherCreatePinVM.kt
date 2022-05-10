package com.bb.vib.ui.home.others.otherCreatePin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bb.vib.api.WebServiceRequests
import com.bb.vib.api.model.request.UpdatePinRequestModel
import com.bb.vib.api.model.response.RegisterPinResponseModel
import com.bb.vib.base.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OtherCreatePinVM(private val webServiceRequests: WebServiceRequests) : ViewModel() {

    val updatePinResponse = MutableLiveData<Event<RegisterPinResponseModel>>()
    val errorResponse = MutableLiveData<Throwable>()
    val progressIndicator = MutableLiveData<Boolean>()

    fun updatePin(request: UpdatePinRequestModel) {
        progressIndicator.value = true
        webServiceRequests.updatePin(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressIndicator.value = false
                updatePinResponse.value = Event(it)
            }, {
                progressIndicator.value = false
                errorResponse.value = it
            })
    }

}