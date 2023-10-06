package com.example.registrodeactividades.actividades

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.registrodeactividades.R
import com.example.registrodeactividades.database.DataSource
import com.example.registrodeactividades.database.HijosDataBase
import com.example.registrodeactividades.databinding.FragmentActividadesBinding
import com.example.registrodeactividades.detalleusuario.DetalleUsuarioViewModel
import com.example.registrodeactividades.detalleusuario.DetalleUsuarioViewModelFactory
import com.example.registrodeactividades.model.AccionNegativa
import com.example.registrodeactividades.model.AccionPositiva
import com.example.registrodeactividades.providers.UserProvider
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


class ActividadesFragment : Fragment() {

    private val userProvider = UserProvider()
    private lateinit var binding: FragmentActividadesBinding
    private val myPositiveDataset = DataSource().loadPositiveActions()
    private val myNegativeDataset = DataSource().loadNegativeActions()

    private var recentlyMoney: Float = 00.00f
    private var startMoney: Float = 00.00f
    private var endMoney: Float = 00.00f

    private var moneyEarned: Float = 00.00f
    private var moneyLost: Float = 00.00f
    private var pointsEarned: Int = 0
    private var pointsLost: Int = 0

    private var listActionPositive: MutableList<AccionPositiva> = mutableListOf()
    private var listActionNegative: MutableList<AccionNegativa> = mutableListOf()

    private var currentFecha: String = "no date"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActividadesBinding.inflate(inflater)

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
        val adapterAccionesPositivas = ActividadesPositivasAdapter(
            onClickListener = {
                it.contador = it.contador + 1
                myPositiveDataset.forEach { newAction ->
                    if (newAction.stringResourceId == it.stringResourceId) {
                        newAction.contador = it.contador
                        actividadesViewModel.setItemPositiveList(myPositiveDataset)
                    }
                }

                if (args.userId == "2PDGhfqN0MyCYPzKNDiF") {
                    Log.d("asdasd", "Elemento agregado a la lista de Andrew: ${it.stringResourceId}")
                    detalleUsuarioViewModel.insertarAccionAndrew(
                        it.copy(
                            stringIdUser = args.userId,
                            fecha = currentFecha
                        )
                    )
                } else {
                    Log.d("asdasd", "Elemento agregado a la lista de Matthew: ${it.stringResourceId}")
                    detalleUsuarioViewModel.insertarAccionMatthew(
                        it.toAccionPositivaMatthew().copy(
                            stringIdUser = args.userId,
                            fecha = currentFecha
                        )
                    )
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
                if (args.userId == "2PDGhfqN0MyCYPzKNDiF") {
                    Log.d("asdasd", "Elemento agregado a la lista de Andrew: ${it.stringResourceId}")
                    detalleUsuarioViewModel.insertarAccionNegativaAndrew(
                        it.copy(
                            stringIdUser = args.userId,
                            fecha = currentFecha
                        )
                    )
                } else {
                    Log.d("asdasd", "Elemento agregado a la lista de Matthew: ${it.stringResourceId}")
                    detalleUsuarioViewModel.insertarAccionNegativaMatthew(
                        it.toAccionNegativaMatthew().copy(
                            stringIdUser = args.userId,
                            fecha = currentFecha
                        )
                    )
                }
                actividadesViewModel.onAccionNegativaClicked(it.valor)
            }
        )

        actividadesViewModel.recyclerPositivoVisible.observe(viewLifecycleOwner) {
            binding.listaPositivas.isVisible = it
        }
        actividadesViewModel.recyclerNegativoVisible.observe(viewLifecycleOwner) {
            binding.listaNegativas.isVisible = it
        }

        val manager = GridLayoutManager(activity, 4)
        binding.listaPositivas.layoutManager = manager
        val manager2 = GridLayoutManager(activity, 4)
        binding.listaNegativas.layoutManager = manager2
        //-----------------------------------------------------------------------------------------------------------------------------------

        actividadesViewModel.currentUser.observe(viewLifecycleOwner) {

            uploadPhotoUser(it.name)
            currentFecha = it.recentDate.toString()
            binding.textStartMoney.text = it.currentMoney
            startMoney = it.currentMoney.toFloat()

            binding.textRecentlyMoney.text = recentlyMoney.toString()
            binding.textMoneyNow.text = (it.currentMoney.toFloat() + recentlyMoney).toString()

            binding.textPointsEarned.text = pointsEarned.toString()
            binding.textPointsLost.text = pointsLost.toString()
            binding.textMoneyEarned.text = moneyEarned.toString()
            binding.textMoneyLost.text = moneyLost.toString()
        }

        actividadesViewModel.ptsGanados.observe(viewLifecycleOwner) {
            binding.textPointsEarned.text = it.toString()
        }
        actividadesViewModel.ptsPerdidos.observe(viewLifecycleOwner) {
            binding.textPointsLost.text = it.toString()
        }

        actividadesViewModel.dineroGanado.observe(viewLifecycleOwner) {
            binding.textMoneyEarned.text = formatDecimalNumber(it)
        }
        actividadesViewModel.dineroPerdido.observe(viewLifecycleOwner) {
            binding.textMoneyLost.text = formatDecimalNumber(it)
        }

        actividadesViewModel.dineroTotal.observe(viewLifecycleOwner) {
            binding.textRecentlyMoney.text = formatDecimalNumber(it)
            recentlyMoney = it + startMoney
            Log.d("asdasd", "recentrly money: $recentlyMoney")
            binding.textMoneyNow.text = formatDecimalNumber(recentlyMoney)
            Log.d("asdasd", "recentrly money format: ${formatDecimalNumber(recentlyMoney)}")
        }


        actividadesViewModel.myPositiveDataset.observe(viewLifecycleOwner) {
            it?.let {
                adapterAccionesPositivas.submitList(it)
                binding.listaPositivas.adapter = adapterAccionesPositivas
            }
        }
        actividadesViewModel.myNegativeDataset.observe(viewLifecycleOwner) {
            it?.let {
                adapterAccionesNegativas.submitList(it)
                binding.listaNegativas.adapter = adapterAccionesNegativas
            }
        }

        binding.buttonGuardar.setOnClickListener {
            val current = binding.textMoneyNow.text.toString()
            val lost = binding.textStartMoney.text.toString()
            val recently = binding.textRecentlyMoney.text.toString()
            val pointsEarned = binding.textPointsEarned.text.toString().toInt()
            val pointsLost = binding.textPointsLost.text.toString().toInt()

            Log.d("asdasd", "current: $current")
            Log.d("asdasd", "lost: $lost")
            Log.d("asdasd", "recently: $recently")
            Log.d("asdasd", "points earned: $pointsEarned")
            Log.d("asdasd", "points lost: $pointsLost")

            actividadesViewModel.updateMoneyInUser(current, lost, recently)
            actividadesViewModel.updateMoneyAndPoints(
                current,
                lost,
                recently,
                pointsEarned,
                pointsLost
            )
            actividadesViewModel.setListActionPositive(listActionPositive)
            actividadesViewModel.setListActionNegative(listActionNegative)
            //actividadesViewModel.saveDatos()

            Log.d(
                "asdasd",
                "click en asdfasd fasd fael item ${detalleUsuarioViewModel.listaPositiva.size}"
            )
            Snackbar.make(binding.root, "Guardado correctamente", Toast.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.orange_new))
                .show()
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

    private fun formatDecimalNumber(number: Float): String {
        val df = DecimalFormat("#.###", DecimalFormatSymbols(Locale.US))
        return df.format(number)
    }
}