package com.library.providers.auth

import android.content.Context
import com.library.providers.preferences.BasePreference
import javax.inject.Inject

internal class AuthPreferenceImpl @Inject constructor(context: Context) : BasePreference(context),
    AuthPreference {

    override fun saveUserLogin(login: String) {
        this.login = login
    }

    override var login: String by stringPref("")
    override var userId: String by stringPref("")

    override fun clear() {
        login = ""
        userId = ""
        super.clear()
    }
}

interface AuthPreference {
    fun saveUserLogin(login: String)
    var login: String
    var userId: String
}