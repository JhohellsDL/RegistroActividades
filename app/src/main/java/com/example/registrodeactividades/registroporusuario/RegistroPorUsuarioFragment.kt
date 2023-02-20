package com.example.registrodeactividades.registroporusuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.registrodeactividades.R
import com.example.registrodeactividades.databinding.FragmentRegistroPorUsuarioBinding


class RegistroPorUsuarioFragment : Fragment() {

    private lateinit var binding: FragmentRegistroPorUsuarioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistroPorUsuarioBinding.inflate(inflater)


        // Inflate the layout for this fragment


        return binding.root
    }

}