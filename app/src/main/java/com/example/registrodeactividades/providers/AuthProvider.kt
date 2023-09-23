package com.example.registrodeactividades.providers

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthProvider {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun login(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun getId(): String {
        return  auth.currentUser?.uid ?: ""
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun loginWithCredentials(credential: AuthCredential): Task<AuthResult> {
        return auth.signInWithCredential(credential)
    }

    fun existSession(): Boolean {
        var exist = false
        if (auth.currentUser != null) {
            exist =  true
        }
        return exist
    }
}