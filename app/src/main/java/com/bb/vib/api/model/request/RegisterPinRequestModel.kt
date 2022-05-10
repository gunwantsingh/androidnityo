package com.bb.vib.api.model.request

import com.google.gson.annotations.SerializedName

data class RegisterPinRequestModel(
    @SerializedName("objUserDevice")
    val objUserDevice: ObjUserDevice?,
    @SerializedName("objUserLoginHistoryDevice")
    val objUserLoginHistoryDevice: ObjUserLoginHistoryDevice?,
    @SerializedName("pin")
    val pin: Int?,
    @SerializedName("userId")
    val userId: String?
) {
    data class ObjUserDevice(
        @SerializedName("createAt")
        val createAt: String?,
        @SerializedName("deviceId")
        val deviceId: String?,
        @SerializedName("updateAt")
        val updateAt: String?,
        @SerializedName("userDeviceId")
        val userDeviceId: String?,
        @SerializedName("userId")
        val userId: String?
    )

    data class ObjUserLoginHistoryDevice(
        @SerializedName("appVersion")
        val appVersion: String?,
        @SerializedName("createAt")
        val createAt: String?,
        @SerializedName("deviceId")
        val deviceId: String?,
        @SerializedName("ipAddress")
        val ipAddress: String?,
        @SerializedName("latitute")
        val latitute: Int?,
        @SerializedName("longtitute")
        val longtitute: Int?,
        @SerializedName("userId")
        val userId: String?,
        @SerializedName("userLoginHistoryId")
        val userLoginHistoryId: String?
    )
}