package com.example.registrodeactividades.providers

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback

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

    fun getClient(): GoogleApiClient {
        return mGoogleApiClient
    }

    fun silentSignIn(callback: (GoogleSignInAccount?) -> Unit) {
        if (mGoogleApiClient == null) {
            callback(null)
            return
        }

        val opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient)
        opr.setResultCallback(ResultCallback {
            if (it.isSuccess) {
                val account = it.signInAccount
                callback(account)
            } else {
                callback(null)
            }
        })
    }

    fun signOut() {
        if (mGoogleApiClient != null && mGoogleApiClient!!.isConnected) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback {}
        }
    }

    fun disconnect() {
        if (mGoogleApiClient != null && mGoogleApiClient!!.isConnected) {
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback {}
        }
    }
}