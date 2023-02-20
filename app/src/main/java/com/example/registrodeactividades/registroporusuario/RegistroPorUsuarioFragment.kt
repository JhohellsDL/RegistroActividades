package com.example.registrodeactividades.registroporusuario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.contadorcasino.database.HijosDataBase
import com.example.registrodeactividades.R
import com.example.registrodeactividades.databinding.FragmentRegistroPorUsuarioBinding
import com.example.registrodeactividades.detalleusuario.DetalleUsuarioViewModel
import com.example.registrodeactividades.detalleusuario.DetalleUsuarioViewModelFactory

class RegistroPorUsuarioFragment : Fragment() {

    private lateinit var binding: FragmentRegistroPorUsuarioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistroPorUsuarioBinding.inflate(inflater)

        //--------------------------------- Para el RECIBIR DATOS-----------------------------------------------------------
        val args = RegistroPorUsuarioFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context, "numero recibido!!: ${args.userId} !!!", Toast.LENGTH_SHORT).show()
        //-------------------------------------------------------------------------------------------------------------------

        //--------------------------------- Para el VIEWMODEL --------------------------------------------------------------
        val application = requireNotNull(this.activity).application
        val datasource = HijosDataBase.getInstance(application).hijosDataBaseDao
        val viewModelFactory = RegistroPorUsuarioViewModelFactory(datasource, args.userId)

        val registroPorUsuarioViewModel = ViewModelProvider(this, viewModelFactory)[RegistroPorUsuarioViewModel::class.java]
        binding.registroPorUsuarioViewModel = registroPorUsuarioViewModel
        binding.lifecycleOwner = this
        //-------------------------------------------------------------------------------------------------------------------

        return binding.root
    }

}