package com.example.registrodeactividades.login.loginUser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.registrodeactividades.databinding.FragmentLoginBinding
import com.example.registrodeactividades.providers.AuthProvider
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {

    private val GOOGLE_SING_IN = 100

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleApiClient: GoogleApiClient
    private val RC_SIGN_IN = 9001

    private lateinit var binding: FragmentLoginBinding
    private var authProvider = AuthProvider()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)

        if (authProvider.existSession()){
            Toast.makeText(requireContext(), "si hay session!2", Toast.LENGTH_SHORT).show()
        }

        binding.buttonRegisterUser.setOnClickListener{
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            authProvider.register(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "correcto", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "mal", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonLoginUser.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            authProvider.login(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "correcto", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "mal", Toast.LENGTH_SHORT).show()
                }
            }
        }

        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("AIzaSyAjd4RrSCsUv1ktI0yc5yZXJjILfOnD8RU")
            .requestEmail()
            .build()

        mGoogleApiClient = GoogleApiClient.Builder(requireContext())
            .enableAutoManage(requireActivity()) { /* Manejar errores */ }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        binding.buttonRegisterGoogleUser.setOnClickListener {

            it.findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToLoginActivity2()
            )
            //val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            //startActivityForResult(signInIntent, RC_SIGN_IN)
           /*
             //Configuracion
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("AIzaSyAjd4RrSCsUv1ktI0yc5yZXJjILfOnD8RU")
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(requireContext(), googleConf)

            startActivityForResult(googleClient.signInIntent, GOOGLE_SING_IN)
            */

        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
            if (result != null) {
                if (result.isSuccess) {
                    val account = result.signInAccount
                    firebaseAuthWithGoogle(account!!)
                } else {
                    Toast.makeText(requireContext(), "Inicio de sesión con Google fallido", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    Toast.makeText(requireContext(), "¡Bienvenido, ${user?.displayName}!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "La autenticación con Firebase falló.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        try {
            if (requestCode == GOOGLE_SING_IN){
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)

                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(requireContext(), "correcto", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "mal", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } catch (e: ApiException){
            Toast.makeText(requireContext(), "mal", Toast.LENGTH_SHORT).show()
        }
    }

     */
}