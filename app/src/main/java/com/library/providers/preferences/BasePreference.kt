package com.library.providers.preferences

import android.content.Context
import com.chibatching.kotpref.KotprefModel

abstract class BasePreference(context: Context) : KotprefModel(context), IBasePreference {}

interface IBasePreference {}