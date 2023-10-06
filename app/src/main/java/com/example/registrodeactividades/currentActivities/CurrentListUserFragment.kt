package com.example.registrodeactividades.currentActivities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registrodeactividades.R
import com.example.registrodeactividades.actividades.ActividadesFragmentArgs
import com.example.registrodeactividades.actividades.ActividadesViewModel
import com.example.registrodeactividades.actividades.ActividadesViewModelFactory
import com.example.registrodeactividades.database.HijosDataBase
import com.example.registrodeactividades.databinding.FragmentCurrentListUserBinding
import com.example.registrodeactividades.detalleusuario.DetalleUsuarioViewModel
import com.example.registrodeactividades.detalleusuario.DetalleUsuarioViewModelFactory
import com.example.registrodeactividades.providers.UserProvider

class CurrentListUserFragment : Fragment() {
    private val userProvider = UserProvider()
    private lateinit var binding: FragmentCurrentListUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrentListUserBinding.inflate(inflater)

        //--------------------------------- Para el RECIBIR DATOS-----------------------------------------------------------
        val args = ActividadesFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = ActividadesViewModelFactory(userProvider, args.userId)
        val actividadesViewModel =
            ViewModelProvider(this, viewModelFactory)[ActividadesViewModel::class.java]
        binding.actividadesViewModel = actividadesViewModel
        binding.lifecycleOwner = this
        //-------------------------------------------------------------------------------------------------------------------

        //--------------------------------- Para el VIEWMODEL --------------------------------------------------------------
        val application = requireNotNull(this.activity).application
        val dataAccionPositiva = HijosDataBase.getInstance(application).accionPositivaDao
        val dataAccionNegativa = HijosDataBase.getInstance(application).accionNegativaDao
        val dataAccionNegativaMatthew =
            HijosDataBase.getInstance(application).accionNegativaMatthewDao
        val dataAccionPositivaMatthew =
            HijosDataBase.getInstance(application).accionPositivaMatthewDao
        val viewModelFactory1 =
            DetalleUsuarioViewModelFactory(
                dataAccionPositiva,
                dataAccionNegativa,
                dataAccionPositivaMatthew,
                dataAccionNegativaMatthew,
                application
            )

        val detalleUsuarioViewModel =
            ViewModelProvider(this, viewModelFactory1)[DetalleUsuarioViewModel::class.java]
        binding.lifecycleOwner = this
        //-----------------------------------------------------------------------------------------------------------------------------------

//--------------------------------- Para el RECYCLERVIEW --------------------------------------------------------------
        val adapterAccionesPositivas = CurrentListPositiveAdapter(
            onClickListener = {
            }
        )
        val adapterAccionesNegativas = CurrentListNegativeAdapter(
            onClickListener = {
            }
        )
        val adapterAccionesPositivasMatthew = CurrentListPositiveMatthewAdapter(
            onClickListener = {}
        )
        val adapterAccionesNegativasMatthew = CurrentListNegativeMatthewAdapter(
            onClickListener = {}
        )

        binding.listaPositivas.adapter = adapterAccionesPositivas
        binding.listaNegativas.adapter = adapterAccionesNegativas

        val manager = LinearLayoutManager(activity)
        binding.listaPositivas.layoutManager = manager
        val manager2 = LinearLayoutManager(activity)
        binding.listaNegativas.layoutManager = manager2


        //-----------------------------------------------------------------------------------------------------------------------------------
        actividadesViewModel.currentUser.observe(viewLifecycleOwner) { userData ->
            uploadPhotoUser(userData.name)
            binding.labelCurrentDate.text = userData.recentDate.toString()
            binding.labelUserName.text = userData.name
            binding.textStartMoney.text = userData.currentMoney

            when (userData.name) {
                "Andrew Alfredo Dianderas Apaza" -> {
                    detalleUsuarioViewModel.listPositivasAndrew.observe(viewLifecycleOwner) {
                        Log.d("asdasd", "lista para el adapter: $it")
                        adapterAccionesPositivas.submitList(it)
                    }
                    detalleUsuarioViewModel.listNegativasAndrew.observe(viewLifecycleOwner) {
                        adapterAccionesNegativas.submitList(it)
                    }
                }

                "Matthew Fabian Dianderas Apaza" -> {
                    detalleUsuarioViewModel.listPositivasMatthew.observe(viewLifecycleOwner) {
                        Log.d("asdasd", "lista para el adapter: $it")
                        adapterAccionesPositivasMatthew.submitList(it)
                    }
                    detalleUsuarioViewModel.listNegativasMatthew.observe(viewLifecycleOwner) {
                        adapterAccionesNegativasMatthew.submitList(it)
                    }
                }

                else -> {
                    binding.fotoItem.setImageResource(R.drawable.user)
                }
            }
        }

        return binding.root
    }

    private fun uploadPhotoUser(name: String) {
        when (name) {
            "Andrew Alfredo Dianderas Apaza" -> {
                binding.fotoItem.setImageResource(R.drawable.andrew)
            }

            "Matthew Fabian Dianderas Apaza" -> {
                binding.fotoItem.setImageResource(R.drawable.matthew)
            }

            else -> {
                binding.fotoItem.setImageResource(R.drawable.user)
            }
        }
    }

}