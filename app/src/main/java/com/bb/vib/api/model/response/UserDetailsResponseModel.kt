package com.bb.vib.api.model.response

import com.google.gson.annotations.SerializedName

data class UserDetailsResponseModel(
    @SerializedName("result")
    val result: Result?,
    @SerializedName("statusCode")
    val statusCode: String?
) {
    data class Result(
        @SerializedName("blockAt")
        val blockAt: String?,
        @SerializedName("branch")
        val branch: String?,
        @SerializedName("changePassAt")
        val changePassAt: String?,
        @SerializedName("changePinAt")
        val changePinAt: String?,
        @SerializedName("clientNumber")
        val clientNumber: String?,
        @SerializedName("createAt")
        val createAt: String?,
        @SerializedName("createBy")
        val createBy: String?,
        @SerializedName("createChannel")
        val createChannel: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("falseLogins")
        val falseLogins: Any? = null,
        @SerializedName("fullName")
        val fullName: String?,
        @SerializedName("language")
        val language: String?,
        @SerializedName("loginAt")
        val loginAt: String?,
        @SerializedName("mobile")
        val mobile: String?,
        @SerializedName("otpType")
        val otpType: String?,
        @SerializedName("password")
        val password: String?,
        @SerializedName("passwordResetExpires")
        val passwordResetExpires: Int?,
        @SerializedName("passwordResetToken")
        val passwordResetToken: String?,
        @SerializedName("pin")
        val pin: Int?,
        @SerializedName("salt")
        val salt: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("updateAt")
        val updateAt: String?,
        @SerializedName("updatedBy")
        val updatedBy: String?,
        @SerializedName("userId")
        val userId: String?,
        @SerializedName("userSegmentation")
        val userSegmentation: Int?,
        @SerializedName("username")
        val username: String?
    )
}