package com.example.registrodeactividades.providers

import com.example.registrodeactividades.model.UserData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserProvider {
    val db = Firebase.firestore.collection("users")

    fun create(user: UserData) : Task<Void> {
        return db.document(user.id).set(user)
    }

    fun getUserData(userId: String): Task<DocumentSnapshot> {
        val documentReference = db.document(userId)
        return documentReference.get()
    }

    fun updateCurrentUser(userId: String, updateUser: UserData): Task<Void> {
        val userReference = db.document(userId)
        return userReference.set(updateUser)
    }
}