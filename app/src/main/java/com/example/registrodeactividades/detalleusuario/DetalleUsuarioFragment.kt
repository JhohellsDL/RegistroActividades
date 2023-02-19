package com.example.registrodeactividades.detalleusuario

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.contadorcasino.database.HijosDataBase
import com.example.registrodeactividades.R
import com.example.registrodeactividades.databinding.FragmentDetalleUsuarioBinding

class DetalleUsuarioFragment : Fragment() {

    private lateinit var binding: FragmentDetalleUsuarioBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetalleUsuarioBinding.inflate(inflater)

        //--------------------------------- Para el VIEWMODEL --------------------------------------------------------------
        val application = requireNotNull(this.activity).application
        val datasource = HijosDataBase.getInstance(application).hijosDataBaseDao
        val viewModelFactory = DetalleUsuarioViewModelFactory(datasource, application)

        val detalleUsuarioViewModel = ViewModelProvider(this, viewModelFactory)[DetalleUsuarioViewModel::class.java]
        binding.detalleUsuarioViewModel = detalleUsuarioViewModel
        binding.lifecycleOwner = this

        //-----------------------------------------------------------------------------------------------------------------------------------



        //--------------------------------- Para el RECYCLERVIEW --------------------------------------------------------------
        val adapter = DetalleUsuarioAdapter(onClickListener = {
            Toast.makeText(context, "hola ${it.nombre}", Toast.LENGTH_SHORT).show()
        })

        binding.detalleRecyclerview.adapter = adapter

        detalleUsuarioViewModel.hijos.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            Log.i("hijo"," lista ${it}")
        })
        //-----------------------------------------------------------------------------------------------------------------------------------


        return binding.root
    }

}