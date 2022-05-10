package com.bb.vib.api

import com.bb.vib.api.model.request.DeviceInfoRequestModel
import com.bb.vib.api.model.request.LoginRequestModel
import com.bb.vib.api.model.request.RegisterPinRequestModel
import com.bb.vib.api.model.request.UpdatePinRequestModel
import com.bb.vib.api.model.response.*
import io.reactivex.Observable

class WebServiceRequests {

    companion object {
        val instance = WebServiceRequests()
    }

    val apiService by lazy { ApiClient.getClient()!!.create(ApiService::class.java) }

    fun postDeviceInfo(
        deviceInfoRequest: DeviceInfoRequestModel
    ): Observable<DeviceInfoResponseModel> {
        return apiService!!.postDeviceInfo(deviceInfoRequest)
    }

    fun changeDeviceLanguage(
        language: String
    ): Observable<DeviceInfoResponseModel> {
        val params = HashMap<String, Any>()
        params[Constants.Keys.DeviceId] = Constants.DeviceIdAPI
        params[Constants.Keys.Language] = language
        return apiService!!.changeDeviceLanguage(params)
    }

    fun login(
        loginRequest: LoginRequestModel
    ): Observable<LoginResponseModel> {
        return apiService!!.login(loginRequest)
    }

    fun logout(
        userId: String
    ): Observable<DeviceInfoResponseModel> {
        val params = HashMap<String, Any>()
        params[Constants.Keys.UserId] = userId
        return apiService!!.logout(params)
    }

    fun getUserDetails(
        userId: String
    ): Observable<UserDetailsResponseModel> {
        return apiService!!.getUserDetails(userId)
    }

    fun verifyUsername(
        userId: String,
        username: String
    ): Observable<RegisterPinResponseModel> {
        val params = HashMap<String, Any>()
        params[Constants.Keys.UserId] = userId
        params[Constants.Keys.username] = username
        params[Constants.Keys.Pin] = 0
        return apiService!!.verifyUsername(params)
    }

    fun verifyPassword(
        userId: String,
        password: String
    ): Observable<RegisterPinResponseModel> {
        val params = HashMap<String, Any>()
        params[Constants.Keys.UserId] = userId
        params[Constants.Keys.Password] = password
        return apiService!!.verifyPassword(params)
    }

    fun registerPin(
        registerPinRequest: RegisterPinRequestModel
    ): Observable<RegisterPinResponseModel> {
        return apiService!!.registerPin(registerPinRequest)
    }

    fun updatePin(
        updatePinRequest: UpdatePinRequestModel
    ): Observable<RegisterPinResponseModel> {
        return apiService!!.updatePin(updatePinRequest)
    }

    fun enableDisableBiometric(
    biometricsEnabled: String
    ): Observable<LoginResponseModel> {
        val params = HashMap<String, Any>()
        params[Constants.Keys.DeviceId] = Constants.DeviceIdAPI
        params[Constants.Keys.BiometricsEnabled] = biometricsEnabled
        return apiService!!.enableDisableBiometric(params)
    }

    fun loginWithPin(
        userId: String,
        pin: Int
    ): Observable<LoginPinResponseModel> {
        val params = HashMap<String, Any>()
        params[Constants.Keys.UserId] = userId
        params[Constants.Keys.username] = ""
        params[Constants.Keys.Pin] = pin
        return apiService!!.loginWithPin(params)
    }

    fun loginWithBiometric(
        userId: String,
        biometricToken: String
    ): Observable<LoginPinResponseModel> {
        val params = HashMap<String, Any>()
        params[Constants.Keys.UserId] = userId
        params[Constants.Keys.DeviceId] = Constants.DeviceIdAPI
        params[Constants.Keys.BiometricToken] = biometricToken
        return apiService!!.loginWithBiometric(params)
    }

    fun verifyPin(
        userId: String,
        pin: Int
    ): Observable<RegisterPinResponseModel> {
        val params = HashMap<String, Any>()
        params[Constants.Keys.UserId] = userId
        params[Constants.Keys.username] = ""
        params[Constants.Keys.Pin] = pin
        return apiService!!.verifyPin(params)
    }

    fun sendActivationCode(
        userId: String
    ): Observable<RegisterPinResponseModel> {
        val params = HashMap<String, Any>()
        params[Constants.Keys.UserId] = userId
        return apiService!!.sendActivationCode(params)
    }

    fun verifyActivationCode(
        userId: String,
        activationCode: String
    ): Observable<RegisterPinResponseModel> {
        val params = HashMap<String, Any>()
        params[Constants.Keys.UserId] = userId
        params[Constants.Keys.ActivationCode] = activationCode
        return apiService!!.verifyActivationCode(params)
    }

    fun activateSmartOtp(
        userId: String
    ): Observable<RegisterPinResponseModel> {
        val params = HashMap<String, Any>()
        params[Constants.Keys.UserId] = userId
        return apiService!!.activateSmartOtp(params)
    }

}