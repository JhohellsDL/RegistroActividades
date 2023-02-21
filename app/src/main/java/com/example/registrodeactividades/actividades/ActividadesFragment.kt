package com.example.registrodeactividades.actividades

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.contadorcasino.database.HijosDataBase
import com.example.contadorcasino.database.HijosDataBaseDao
import com.example.registrodeactividades.database.DataSource
import com.example.registrodeactividades.databinding.FragmentActividadesBinding
import com.example.registrodeactividades.detalleusuario.DetalleUsuarioAdapter
import com.example.registrodeactividades.detalleusuario.DetalleUsuarioFragmentDirections

class ActividadesFragment : Fragment() {

    private lateinit var binding: FragmentActividadesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myPositiveDataset = DataSource().loadPositiveActions()

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


        //--------------------------------- Para el RECYCLERVIEW --------------------------------------------------------------

        val adapter = ActividadesPositivasAdapter(onClickListener = {
            Toast.makeText(context, "hola valo9r!! ${it.valor}", Toast.LENGTH_SHORT).show()
            actividadesViewModel.onAccionPositivaClicked(it.valor)
        }, data =  myPositiveDataset)

        binding.listaPositivas.adapter = adapter

        actividadesViewModel.ptsGanados.observe(viewLifecycleOwner, Observer {
            Log.i("hijo"," listaddd ${it}")
        })
        //-----------------------------------------------------------------------------------------------------------------------------------


        return binding.root
    }
}