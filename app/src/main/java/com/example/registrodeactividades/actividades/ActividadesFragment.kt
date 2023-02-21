package com.example.registrodeactividades.actividades

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contadorcasino.database.HijosDataBase
import com.example.contadorcasino.database.HijosDataBaseDao
import com.example.registrodeactividades.databinding.FragmentActividadesBinding

class ActividadesFragment : Fragment() {

    private lateinit var binding: FragmentActividadesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActividadesBinding.inflate(inflater)

        //--------------------------------- Para el RECIBIR DATOS-----------------------------------------------------------
        /*val args = RegistroPorUsuarioFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context, "numero recibido ffff!!: ${args.userId} !!!", Toast.LENGTH_SHORT).show()*/
        val args = ActividadesFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(context, "numero recibido ffff!!: ${args.userId} !!!", Toast.LENGTH_SHORT).show()
        //-------------------------------------------------------------------------------------------------------------------

        //--------------------------------- Para el VIEWMODEL --------------------------------------------------------------
        val application = requireNotNull(this.activity).application
        val dataSource = HijosDataBase.getInstance(application).hijosDataBaseDao
        val viewModelFactory = ActividadesViewModelFactory(args.userId, dataSource)

        val actividadesViewModel = ViewModelProvider(this, viewModelFactory)[ActividadesViewModel::class.java]
        binding.actividadesViewModel = actividadesViewModel
        binding.lifecycleOwner = this
        //-------------------------------------------------------------------------------------------------------------------

        return binding.root
    }
}