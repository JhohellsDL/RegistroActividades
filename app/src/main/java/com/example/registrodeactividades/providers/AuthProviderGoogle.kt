package com.example.registrodeactividades.providers

import android.provider.Settings.Global.getString
import com.example.registrodeactividades.R
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class AuthProviderGoogle {

    val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("AIzaSyAjd4RrSCsUv1ktI0yc5yZXJjILfOnD8RU")
        .requestEmail()
        .build()

}