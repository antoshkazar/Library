package com.library.data.models.responses

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
    @SerializedName("identifier")
    val identifier: String = "",
    @SerializedName("login")
    val login: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("root_group_id")
    val rootGroupId: String = "",
)