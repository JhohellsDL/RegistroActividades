package com.example.registrodeactividades.detallevidas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.registrodeactividades.databinding.FragmentVidasScrollingBinding

class VidasScrollingFragment : Fragment() {

    private lateinit var binding: FragmentVidasScrollingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVidasScrollingBinding.inflate(inflater)

        return binding.root
    }
}