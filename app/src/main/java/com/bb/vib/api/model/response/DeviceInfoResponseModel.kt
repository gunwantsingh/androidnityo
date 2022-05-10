package com.bb.vib.api.model.response

import com.google.gson.annotations.SerializedName

data class DeviceInfoResponseModel(
    @SerializedName("result")
    val result: Boolean?,
    @SerializedName("statusCode")
    val statusCode: String?
)