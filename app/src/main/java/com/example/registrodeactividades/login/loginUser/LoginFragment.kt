package com.example.registrodeactividades.login.loginUser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.registrodeactividades.databinding.FragmentLoginBinding
import com.example.registrodeactividades.providers.AuthProvider
import com.example.registrodeactividades.providers.AuthProviderGoogle
import com.example.registrodeactividades.providers.UserProvider
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {

    private val GOOGLE_SING_IN = 9001

    private lateinit var binding: FragmentLoginBinding
    private var authProvider = AuthProvider()
    private lateinit var mGoogleAuthProvider: AuthProviderGoogle

    private val userProvider = UserProvider()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)

        binding.buttonRegisterUser.setOnClickListener {
            registerUser()
        }

        binding.buttonLoginUser.setOnClickListener {
            loginUser()
        }

        binding.buttonRegisterGoogleUser.setOnClickListener {
            loginGoogleUser()

        }

        return binding.root
    }

    private fun loginGoogleUser() {
        mGoogleAuthProvider = AuthProviderGoogle(requireContext(), requireActivity())
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleAuthProvider.getClient())
        Log.d("asdasd", "Client: ${mGoogleAuthProvider.getClient()}")
        startActivityForResult(signInIntent, GOOGLE_SING_IN)
    }

    private fun loginUser() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        authProvider.login(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "correcto", Toast.LENGTH_SHORT).show()
                goToInicio()
            } else {
                Toast.makeText(requireContext(), "mal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        authProvider.register(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "correcto", Toast.LENGTH_SHORT).show()
                goToInicio()
            } else {
                Toast.makeText(requireContext(), "mal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SING_IN) {
            val result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
            if (result != null) {
                if (result.isSuccess) {
                    val account = result.signInAccount
                    firebaseAuthWithGoogle(account!!)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Inicio de sesión con Google fallido",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        authProvider.loginWithCredentials(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = authProvider.getCurrentUser()
                    Toast.makeText(
                        requireContext(),
                        "¡Bienvenido, ${user?.displayName}!",
                        Toast.LENGTH_SHORT
                    ).show()
                    goToInicio()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "La autenticación con Firebase falló.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun goToInicio() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToInicioFragment()
        )
    }

    override fun onStart() {
        super.onStart()
        if (authProvider.existSession()) {
            goToInicio()
        }
    }

}