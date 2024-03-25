package com.library.providers.auth

import android.content.Context
import com.library.data.models.responses.LoginResponseModel
import com.library.providers.preferences.BasePreference
import javax.inject.Inject

internal class AuthPreferenceImpl @Inject constructor(context: Context) : BasePreference(context),
    AuthPreference {

    override fun saveUserData(model: LoginResponseModel) {
        login = model.login
        rootGroupId = model.rootGroupId
        name = model.name
        identifier = model.identifier
    }

    override var name: String by stringPref("")
    override var login: String by stringPref("")
    override var rootGroupId: String by stringPref("")
    override var identifier: String by stringPref("")

    override fun clear() {
        login = ""
        rootGroupId = ""
        super.clear()
    }
}

interface AuthPreference {
    fun saveUserData(model: LoginResponseModel)
    var name: String
    var login: String
    var rootGroupId: String
    var identifier: String
}