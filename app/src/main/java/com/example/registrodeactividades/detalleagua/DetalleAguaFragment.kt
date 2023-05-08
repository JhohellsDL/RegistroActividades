package com.example.registrodeactividades.detalleagua

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.registrodeactividades.R
import com.example.registrodeactividades.databinding.FragmentDetalleAguaBinding

class DetalleAguaFragment : Fragment() {

    private lateinit var binding: FragmentDetalleAguaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetalleAguaBinding.inflate(inflater)

        

        return binding.root
    }
}