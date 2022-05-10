package com.bb.vib.api

import com.bb.vib.api.model.request.DeviceInfoRequestModel
import com.bb.vib.api.model.request.LoginRequestModel
import com.bb.vib.api.model.request.RegisterPinRequestModel
import com.bb.vib.api.model.request.UpdatePinRequestModel
import com.bb.vib.api.model.response.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    @POST(Constants.Partials.DeviceInfo)
    fun postDeviceInfo(@Body params: DeviceInfoRequestModel): Observable<DeviceInfoResponseModel>

    @PUT(Constants.Partials.DeviceLanguage)
    fun changeDeviceLanguage(@Body params: HashMap<String, Any>): Observable<DeviceInfoResponseModel>

    @POST(Constants.Partials.Login)
    fun login(@Body params: LoginRequestModel): Observable<LoginResponseModel>

    @POST(Constants.Partials.Logout)
    fun logout(@Body params: HashMap<String, Any>): Observable<DeviceInfoResponseModel>

    @GET(Constants.Partials.User)
    fun getUserDetails(@Path(Constants.Keys.UserId) userId: String): Observable<UserDetailsResponseModel>

    @PUT(Constants.Partials.VerifyUsername)
    fun verifyUsername(@Body params: HashMap<String, Any>): Observable<RegisterPinResponseModel>

    @PUT(Constants.Partials.VerifyPassword)
    fun verifyPassword(@Body params: HashMap<String, Any>): Observable<RegisterPinResponseModel>

    @PUT(Constants.Partials.RegisterPin)
    fun registerPin(@Body params: RegisterPinRequestModel): Observable<RegisterPinResponseModel>

    @PUT(Constants.Partials.UserPin)
    fun updatePin(@Body params: UpdatePinRequestModel): Observable<RegisterPinResponseModel>

    @PUT(Constants.Partials.EnableDisableBiometric)
    fun enableDisableBiometric(@Body params: HashMap<String, Any>): Observable<LoginResponseModel>

    @PUT(Constants.Partials.LoginWithPin)
    fun loginWithPin(@Body params: HashMap<String, Any>): Observable<LoginPinResponseModel>

    @PUT(Constants.Partials.LoginWithBiometric)
    fun loginWithBiometric(@Body params: HashMap<String, Any>): Observable<LoginPinResponseModel>

    @PUT(Constants.Partials.VerifyPin)
    fun verifyPin(@Body params: HashMap<String, Any>): Observable<RegisterPinResponseModel>

    @POST(Constants.Partials.SendActivationCode)
    fun sendActivationCode(@Body params: HashMap<String, Any>): Observable<RegisterPinResponseModel>

    @POST(Constants.Partials.VerifyActivationCode)
    fun verifyActivationCode(@Body params: HashMap<String, Any>): Observable<RegisterPinResponseModel>

    @POST(Constants.Partials.ActivateSmartOtp)
    fun activateSmartOtp(@Body params: HashMap<String, Any>): Observable<RegisterPinResponseModel>

}