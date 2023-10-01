package com.example.registrodeactividades.registroporusuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.registrodeactividades.R
import com.example.registrodeactividades.database.HijosDataBase
import com.example.registrodeactividades.databinding.FragmentRegistroPorUsuarioBinding
import com.example.registrodeactividades.providers.AuthProvider
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class RegistroPorUsuarioFragment : Fragment() {

    private val authProvider = AuthProvider()
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

        val registroPorUsuarioViewModel =
            ViewModelProvider(this, viewModelFactory)[RegistroPorUsuarioViewModel::class.java]
        binding.registroPorUsuarioViewModel = registroPorUsuarioViewModel
        binding.lifecycleOwner = this

        registroPorUsuarioViewModel.user.observe(viewLifecycleOwner) {
            binding.fotoItem.setImageResource(it.photoResourceId)
            binding.textIdUser.text = "Id: ${it.hijoId}"
            binding.textDineroTienes.text = formatDecimalNumber(it.dinero)
            binding.textFecha.text = it.fechaACtual
        }

        registroPorUsuarioViewModel.dineroTotal.observe(viewLifecycleOwner) {
            binding.textDineroTienes.text = formatDecimalNumber(it)
        }
        registroPorUsuarioViewModel.dineroGanado.observe(viewLifecycleOwner) {
            binding.textDineroGanaste.text = formatDecimalNumber(it)
        }
        registroPorUsuarioViewModel.dineroPerdido.observe(viewLifecycleOwner) {
            binding.textDineroPerdiste.text = formatDecimalNumber(it)
        }

        //-------------------------------------------------------------------------------------------------------------------

        //--------------------------------- Para el ENVIAR DATOS-----------------------------------------------------------
        binding.buttonRegistar.setOnClickListener {
            it.findNavController().navigate(
                RegistroPorUsuarioFragmentDirections.actionRegistroPorUsuarioFragmentToActividadesFragment(
                    args.userId.toString()
                )
            )
        }
        //-------------------------------------------------------------------------------------------------------------------

        binding.cardButtonMas.setOnClickListener {
            registroPorUsuarioViewModel.masVidas()
            binding.labelTextInformation.visibility = View.VISIBLE
        }

        binding.cardButtonMenos.setOnClickListener {
            registroPorUsuarioViewModel.menosVidas()
            binding.labelTextInformation.visibility = View.VISIBLE
        }

        binding.buttonEdit.setOnClickListener {
            Toast.makeText(requireContext(), "Falta definir", Toast.LENGTH_SHORT).show()
        }
        binding.buttonActualizar.setOnClickListener {
            val vidas = binding.textPuntosHoy.text
            registroPorUsuarioViewModel.actualizarVidas(vidas.toString().toInt())
            Snackbar.make(binding.root, "Actualizado y Guardado correctamente", Toast.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.orange_new))
                .show()
        }

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