package com.example.registrodeactividades.utils

import android.view.View
import androidx.core.content.ContextCompat
import com.example.registrodeactividades.R
import com.google.android.material.snackbar.Snackbar

class Utils {
    object SnackbarUtils {
        fun showSnackBar(rootView: View, message: String) {
            Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(rootView.context, R.color.orange_new))
                .show()
        }
    }
}