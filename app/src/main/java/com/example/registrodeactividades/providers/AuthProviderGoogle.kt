package com.example.registrodeactividades.providers

import android.app.Activity
import android.content.Context
import android.provider.Settings.Global.getString
import androidx.fragment.app.FragmentActivity
import com.example.registrodeactividades.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient

class AuthProviderGoogle(context: Context, activity: FragmentActivity) {

    private val mGoogleApiClient: GoogleApiClient

    init {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("385141225861-r87q8jae7ftt1jnr62sdipdbcmqlcrmd.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleApiClient = GoogleApiClient.Builder(context)
            .enableAutoManage(activity) { /* Manejar errores */ }
            .addApi(Auth.GOOGLE_SIGN_IN_API, googleConf)
            .build()
    }

    fun getGoogleApiClient(): GoogleApiClient {
        return mGoogleApiClient
    }
}