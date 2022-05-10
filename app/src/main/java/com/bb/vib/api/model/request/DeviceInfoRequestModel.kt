package com.bb.vib.api.model.request

import com.google.gson.annotations.SerializedName

data class DeviceInfoRequestModel(
    @SerializedName("appVersion")
    val appVersion: String?,
    @SerializedName("biometricsEnabled")
    val biometricsEnabled: String?,
    @SerializedName("createAt")
    val createAt: String?,
    @SerializedName("deviceCode")
    val deviceCode: String?,
    @SerializedName("deviceId")
    val deviceId: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("latitute")
    val latitute: Int?,
    @SerializedName("longtitute")
    val longtitute: Int?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("notificationToken")
    val notificationToken: String?,
    @SerializedName("oS_Name")
    val oSName: String?,
    @SerializedName("platform")
    val platform: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("updateAt")
    val updateAt: String?
)