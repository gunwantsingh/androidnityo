package com.bb.vib.api.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("clientNumber")
    val clientNumber: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("pin")
    val pin: Int?,
    @SerializedName("falseLoginTimes")
    val falseLoginTimes: Int?,
    @SerializedName("otpType")
    val otpType: String?,
    @SerializedName("salt")
    val salt: String?,
    @SerializedName("passwordResetToken")
    val passwordResetToken: String?,
    @SerializedName("passwordResetExpires")
    val passwordResetExpires: Int?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("mobile")
    val mobile: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("branch")
    val branch: String?,
    @SerializedName("createBy")
    val createBy: String?,
    @SerializedName("createChannel")
    val createChannel: String?,
    @SerializedName("updatedBy")
    val updatedBy: String?,
    @SerializedName("userSegmentation")
    val userSegmentation: Int?,
    @SerializedName("blockAt")
    val blockAt: String?,
    @SerializedName("loginAt")
    val loginAt: String?,
    @SerializedName("changePinAt")
    val changePinAt: String?,
    @SerializedName("changePassAt")
    val changePassAt: String?,
    @SerializedName("createAt")
    val createAt: String?,
    @SerializedName("updateAt")
    val updateAt: String?,
    @SerializedName("isActive")
    val isActive: Boolean?
)