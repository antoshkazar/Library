package com.library.providers.auth

import android.content.Context
import com.library.providers.preferences.BasePreference
import javax.inject.Inject

internal class AuthPreferenceImpl @Inject constructor(context: Context) : BasePreference(context),
    AuthPreference {

    override fun saveUserLogin(login: String) {
        this.login = login
    }

    override var name: String by stringPref("")
    override var login: String by stringPref("")
    override var rootGroupId: String by stringPref("")

    override fun clear() {
        login = ""
        rootGroupId = ""
        super.clear()
    }
}

interface AuthPreference {
    fun saveUserLogin(login: String)
    var name: String
    var login: String
    var rootGroupId: String
}