package com.bb.vib.api.baseModel

import com.google.gson.annotations.SerializedName

data class ErrorBean(
    @SerializedName("statusCode")
    val statusCode: String?,
    @SerializedName("result")
    val result: Any? = null
)