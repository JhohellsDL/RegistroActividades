package com.example.registrodeactividades.actividades

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
import com.example.registrodeactividades.database.DataSource
import com.example.registrodeactividades.databinding.FragmentActividadesBinding
import com.example.registrodeactividades.model.AccionPositiva

class ActividadesFragment : Fragment() {

    private lateinit var binding: FragmentActividadesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myPositiveDataset = DataSource().loadPositiveActions()
        val myNegativeDataser = DataSource().loadNegativeActions()

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

        var lista = listOf<AccionPositiva>()

        val adapterAccionesPositivas = ActividadesPositivasAdapter(
            onClickListener = {
                it.contador = it.contador + 1
                Toast.makeText(context, "Contador: ${it.contador}", Toast.LENGTH_SHORT).show()


                val rec = it.stringResourceId

                myPositiveDataset.forEach { accionDentro ->
                    if (accionDentro.stringResourceId == rec){
                        accionDentro.contador = it.contador
                        Log.i("hijo"," lista dentro onclick  ${accionDentro.contador} y ${it.contador}")
                        actividadesViewModel.setItemList(myPositiveDataset)
                    }
                }

                actividadesViewModel.onAccionPositivaClicked(it.valor)
            }
        )

        actividadesViewModel.myPositiveDataset.observe(viewLifecycleOwner){
            it?.let {
                Log.i("hijo"," lista dentro ${it}")
               adapterAccionesPositivas.submitList(it)
                binding.listaPositivas.adapter = adapterAccionesPositivas
            }
        }


        //actividadesViewModel.setItemList(lista)
        //Log.i("hijo"," lista fuera $lista")


        val adapterAccionesNegativas = ActividadesNegativasAdapter(
            onClickListener = { actividadesViewModel.onAccionNegativaClicked(it.valor) },
            data = myNegativeDataser
        )


        binding.listaNegativas.adapter = adapterAccionesNegativas

        actividadesViewModel.ptsGanados.observe(viewLifecycleOwner, Observer {
            Log.i("hijo"," listaddd ${it}")
        })
        //-----------------------------------------------------------------------------------------------------------------------------------


        return binding.root
    }
}