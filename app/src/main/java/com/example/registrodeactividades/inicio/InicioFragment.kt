package com.example.registrodeactividades.inicio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.registrodeactividades.R
import com.example.registrodeactividades.databinding.FragmentInicioBinding
import com.example.registrodeactividades.providers.AuthProvider

class InicioFragment : Fragment() {

    private lateinit var binding: FragmentInicioBinding
    private var authProvider = AuthProvider()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInicioBinding.inflate(inflater)

        val nameUser = authProvider.getCurrentUser()?.displayName
        Log.d("asdasd", "name user : $nameUser")
        binding.nameUserActive.text = nameUser


        binding.buttonLogout.setOnClickListener {
            authProvider.auth.signOut()
            Toast.makeText(requireContext(), "Session cerrada", Toast.LENGTH_SHORT).show()
            goToLogin()
        }

        //--------------------------------- AMBOS FUNCIONAN PARA LA NAVEGACION --------------------------------------------------------------
        /*binding.button.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_vistaPrincipalFragment_to_actividadesPositivas)
        }*/
        binding.buttonGotoActividades.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_inicioFragment_to_detalleUsuarioFragment)
        )

        binding.buttonGotoAgua.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_inicioFragment_to_detalleAguaFragment)
        )

        binding.buttonGotoVidas.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_inicioFragment_to_vidasScrollingFragment)
        )
        //------------------------------------------------------------------------------------------------------------------------------------

        return binding.root

    }
    fun goToLogin() {
        findNavController().navigate(
            InicioFragmentDirections.actionInicioFragmentToLoginFragment()
        )
    }
}