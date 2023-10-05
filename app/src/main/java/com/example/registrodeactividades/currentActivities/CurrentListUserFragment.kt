package com.example.registrodeactividades.currentActivities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.registrodeactividades.databinding.FragmentCurrentListUserBinding

class CurrentListUserFragment : Fragment() {

    private lateinit var binding: FragmentCurrentListUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentListUserBinding.inflate(inflater)





        return binding.root
    }

}