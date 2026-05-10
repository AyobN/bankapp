package com.ayobn.bankapp

import android.content.Context

class TokenStore(context: Context) {
    private val prefs = context.getSharedPreferences("bank_prefs", Context.MODE_PRIVATE)

    var token: String?
        get() = prefs.getString("token", null)
        set(value) {
            prefs.edit().putString("token", value).apply()
        }

    fun bearer(): String? = token?.let { "Bearer $it" }

    fun clear() {
        prefs.edit().remove("token").apply()
    }
}