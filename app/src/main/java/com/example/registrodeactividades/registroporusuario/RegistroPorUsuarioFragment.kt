package com.example.registrodeactividades.registroporusuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.registrodeactividades.databinding.FragmentRegistroPorUsuarioBinding
import com.example.registrodeactividades.providers.AuthProvider
import java.text.DecimalFormat

class RegistroPorUsuarioFragment : Fragment() {

    private val authProvider = AuthProvider()
    private lateinit var binding: FragmentRegistroPorUsuarioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistroPorUsuarioBinding.inflate(inflater)



        return binding.root
    }

    private fun formatDecimalNumber(number: Float): String {
        val df = DecimalFormat("#.###")
        return df.format(number)
    }

    override fun onStart() {
        super.onStart()
        if (authProvider.getCurrentUser()?.email == "jhohellserick@gmail.com" ||
            authProvider.getCurrentUser()?.email == "rudyjudithapazamendoza@gmail.com"
        ) {
            binding.buttonEdit.isVisible = true
            binding.buttonRegistar.isVisible = true
        } else {
            binding.buttonEdit.isVisible = false
            binding.buttonRegistar.isVisible = false
        }
    }

}