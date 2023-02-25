package com.example.registrodeactividades.actividades

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.contadorcasino.database.HijosDataBase
import com.example.registrodeactividades.database.DataSource
import com.example.registrodeactividades.databinding.FragmentActividadesBinding


class ActividadesFragment : Fragment() {

    private lateinit var binding: FragmentActividadesBinding
    private val myPositiveDataset = DataSource().loadPositiveActions()
    private val myNegativeDataset = DataSource().loadNegativeActions()

    private var aux1: Float = 0f
    private var aux2: Float = 0f

    private var dineroAux: Float = 0f
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


        actividadesViewModel.user.observe(viewLifecycleOwner){
            binding.fotoItem.setImageResource(it.photoResourceId)
            binding.textDineroActual.text = it.dinero.toString()
            aux1 = it.dinero
            Log.i(
                "hijo",
                "dinero actual: $aux1"
            )
        }

        actividadesViewModel.ptsGanados.observe(viewLifecycleOwner){
            binding.textPuntosGanados.text = it.toString()
        }
        actividadesViewModel.ptsPerdidos.observe(viewLifecycleOwner) {
            binding.textPuntosPerdidos.text = it.toString()
        }
        actividadesViewModel.dineroTotal.observe(viewLifecycleOwner){
            binding.textDineroTotal.text = it.toString()
            aux2 = it +aux1
            Log.i(
                "hijo",
                "dinero actual2: $it + $aux2"
            )
            binding.textDineroSuma.text = aux2.toString()
            dineroAux = it
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

        binding.buttonGuardar.setOnClickListener {
            actividadesViewModel.registroDatos()
            Toast.makeText(requireContext(), "Guardado: $dineroAux soles", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}