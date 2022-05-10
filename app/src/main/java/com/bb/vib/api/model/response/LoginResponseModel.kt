package com.bb.vib.api.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    @SerializedName("result")
    val result: String?,
    @SerializedName("statusCode")
    val statusCode: String?
)