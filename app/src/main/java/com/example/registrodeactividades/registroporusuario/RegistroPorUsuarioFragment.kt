package com.example.registrodeactividades.registroporusuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.contadorcasino.database.HijosDataBase
import com.example.registrodeactividades.databinding.FragmentRegistroPorUsuarioBinding

class RegistroPorUsuarioFragment : Fragment() {

    private lateinit var binding: FragmentRegistroPorUsuarioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistroPorUsuarioBinding.inflate(inflater)

        //--------------------------------- Para el RECIBIR DATOS-----------------------------------------------------------
        val args = RegistroPorUsuarioFragmentArgs.fromBundle(requireArguments())
        //-------------------------------------------------------------------------------------------------------------------

        //--------------------------------- Para el VIEWMODEL --------------------------------------------------------------
        val application = requireNotNull(this.activity).application
        val datasource = HijosDataBase.getInstance(application).hijosDataBaseDao
        val viewModelFactory = RegistroPorUsuarioViewModelFactory(datasource, args.userId)

        val registroPorUsuarioViewModel = ViewModelProvider(this, viewModelFactory)[RegistroPorUsuarioViewModel::class.java]
        binding.registroPorUsuarioViewModel = registroPorUsuarioViewModel
        binding.lifecycleOwner = this

        registroPorUsuarioViewModel.user.observe(viewLifecycleOwner){
            binding.fotoItem.setImageResource(it.photoResourceId)
            binding.textIdUser.text = "Id: ${it.hijoId}"
            binding.textDineroTienes.text = "S/. ${it.dinero}"
            binding.textPuntosHoy.text = it.puntosHoy.toString()
            binding.textPuntosPremio.text = it.puntosPremio.toString()
            binding.textPuntosCastigo.text = it.puntosCastigo.toString()
        }

        registroPorUsuarioViewModel.dineroGanado.observe(viewLifecycleOwner){
            binding.textDineroGanaste.text = "S/. $it"
        }
        registroPorUsuarioViewModel.dineroPerdido.observe(viewLifecycleOwner){
            binding.textDineroPerdiste.text = "S/. $it"
        }

        //-------------------------------------------------------------------------------------------------------------------

        //--------------------------------- Para el ENVIAR DATOS-----------------------------------------------------------
        binding.buttonRegistar.setOnClickListener {
            it.findNavController().navigate(RegistroPorUsuarioFragmentDirections.actionRegistroPorUsuarioFragmentToActividadesFragment(args.userId))
        }
        //-------------------------------------------------------------------------------------------------------------------

        binding.buttonEdit.setOnClickListener {
            Toast.makeText(requireContext(), "Falta definir", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }


}