package com.example.registrodeactividades.detalleusuario

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.registrodeactividades.database.HijosDataBase
import com.example.registrodeactividades.databinding.CardAlertReiniciarBinding
import com.example.registrodeactividades.databinding.FragmentDetalleUsuarioBinding
import com.example.registrodeactividades.providers.UserProvider

class DetalleUsuarioFragment : Fragment() {

    private lateinit var binding: FragmentDetalleUsuarioBinding
    private lateinit var bindingDialog: CardAlertReiniciarBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetalleUsuarioBinding.inflate(inflater)

        //--------------------------------- Para el VIEWMODEL --------------------------------------------------------------
        val application = requireNotNull(this.activity).application
        val datasource = HijosDataBase.getInstance(application).hijosDataBaseDao
        val viewModelFactory = DetalleUsuarioViewModelFactory(datasource, application)

        val detalleUsuarioViewModel =
            ViewModelProvider(this, viewModelFactory)[DetalleUsuarioViewModel::class.java]
        binding.detalleUsuarioViewModel = detalleUsuarioViewModel
        binding.lifecycleOwner = this
        //-----------------------------------------------------------------------------------------------------------------------------------

        val viewModelFactoryNew = ItemUserViewModelFactory(UserProvider(), application)
        val itemUseViewModel =
            ViewModelProvider(this, viewModelFactoryNew)[ItemUserViewModel::class.java]
        binding.lifecycleOwner = this

        //-----------------------------------------------------------------------------------------------------------------------------------

        //--------------------------------- Para el RECYCLERVIEW --------------------------------------------------------------
        val adapter = ItemUserAdapter(
            onClickListener = {
                Log.d("asdasd", "click en el item ${it.name}")
            })

        //*********** PARA NAVEGACION *****************************
        detalleUsuarioViewModel.idUserForNavigation.observe(
            viewLifecycleOwner) {
                it?.let {
                    this.findNavController().navigate(
                        DetalleUsuarioFragmentDirections.actionDetalleUsuarioFragmentToRegistroPorUsuarioFragment(
                            it
                        )
                    )
                    detalleUsuarioViewModel.onUserClickedNavigated()
                }
            }
        //**********************************************************

        binding.detalleRecyclerview.adapter = adapter
        itemUseViewModel.listUsers.observe(viewLifecycleOwner) {
            adapter.data = it
        }
        //-----------------------------------------------------------------------------------------------------------------------------------

        binding.buttonReiniciar.setOnClickListener {
            bindingDialog = CardAlertReiniciarBinding.inflate(layoutInflater)
            val myDialog = Dialog(it.context)
            myDialog.setContentView(bindingDialog.root)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            bindingDialog.buttonReiniciar.setOnClickListener {
                detalleUsuarioViewModel.onReinicioRegistroHijos()
                myDialog.dismiss()
                Toast.makeText(requireContext(),"Reiniciado",Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}