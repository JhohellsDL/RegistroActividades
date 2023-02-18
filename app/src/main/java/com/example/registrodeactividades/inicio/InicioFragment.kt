package com.example.registrodeactividades.inicio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.registrodeactividades.R
import com.example.registrodeactividades.databinding.FragmentInicioBinding

class InicioFragment : Fragment() {

    private lateinit var binding: FragmentInicioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInicioBinding.inflate(inflater)

        //--------------------------------- AMBOS FUNCIONAN PARA LA NAVEGACION --------------------------------------------------------------
        /*binding.button.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_vistaPrincipalFragment_to_actividadesPositivas)
        }*/
        binding.buttonGotoActividades.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_inicioFragment_to_detalleUsuarioFragment)
        )
        //------------------------------------------------------------------------------------------------------------------------------------

        return binding.root
    }
}