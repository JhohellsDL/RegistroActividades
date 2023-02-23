package com.example.registrodeactividades.actividades

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.contadorcasino.database.HijosDataBase
import com.example.registrodeactividades.database.DataSource
import com.example.registrodeactividades.databinding.FragmentActividadesBinding
import com.example.registrodeactividades.model.AccionPositiva

class ActividadesFragment : Fragment() {

    private lateinit var binding: FragmentActividadesBinding
    private val myPositiveDataset = DataSource().loadPositiveActions()
    private val myNegativeDataset = DataSource().loadNegativeActions()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActividadesBinding.inflate(inflater)

        //--------------------------------- Para el RECIBIR DATOS-----------------------------------------------------------
        val args = ActividadesFragmentArgs.fromBundle(requireArguments())
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
        val adapterAccionesPositivas = ActividadesPositivasAdapter(
            onClickListener = {
                it.contador = it.contador + 1
                myPositiveDataset.forEach { newAction ->
                    if (newAction.stringResourceId == it.stringResourceId) {
                        newAction.contador = it.contador
                        actividadesViewModel.setItemPositiveList(myPositiveDataset)
                    }
                }
                actividadesViewModel.onAccionPositivaClicked(it.valor)
            }
        )

        val adapterAccionesNegativas = ActividadesNegativasAdapter(
            onClickListener = {
                it.contador = it.contador + 1
                myNegativeDataset.forEach { newNegative ->
                    if (newNegative.stringResourceId == it.stringResourceId) {
                        newNegative.contador = it.contador
                        actividadesViewModel.setItemNegativeList(myNegativeDataset)
                    }
                }
                actividadesViewModel.onAccionNegativaClicked(it.valor)
            }
        )

        actividadesViewModel.recyclerPositivoVisible.observe(viewLifecycleOwner){
            binding.listaPositivas.isVisible = it
        }
        actividadesViewModel.recyclerNegativoVisible.observe(viewLifecycleOwner){
            binding.listaNegativas.isVisible = it
        }

        val manager = GridLayoutManager(activity, 3)
        binding.listaPositivas.layoutManager = manager
        val manager2 = GridLayoutManager(activity, 3)
        binding.listaNegativas.layoutManager = manager2
        //-----------------------------------------------------------------------------------------------------------------------------------

        actividadesViewModel.ptsGanados.observe(viewLifecycleOwner){
            binding.textPuntosGanados.text = it.toString()
        }
        actividadesViewModel.ptsPerdidos.observe(viewLifecycleOwner, Observer {
            binding.textPuntosPerdidos.text = it.toString()
        })
        actividadesViewModel.ptsTotal.observe(viewLifecycleOwner, Observer {
            binding.textPuntosHoy.text = it.toString()
        })
        actividadesViewModel.dineroTotal.observe(viewLifecycleOwner){
            binding.textDineroTotal.text = it.toString()
        }

        actividadesViewModel.myPositiveDataset.observe(viewLifecycleOwner){
            it?.let {
                adapterAccionesPositivas.submitList(it)
                binding.listaPositivas.adapter = adapterAccionesPositivas
            }
        }
        actividadesViewModel.myNegativeDataset.observe(viewLifecycleOwner){
            it?.let {
                adapterAccionesNegativas.submitList(it)
                binding.listaNegativas.adapter = adapterAccionesNegativas
            }
        }

        return binding.root
    }
}