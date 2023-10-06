package com.example.registrodeactividades.detalleusuario

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.registrodeactividades.database.HijosDataBase
import com.example.registrodeactividades.databinding.CardAlertReiniciarBinding
import com.example.registrodeactividades.databinding.FragmentDetalleUsuarioBinding
import com.example.registrodeactividades.providers.AuthProvider
import com.example.registrodeactividades.providers.UserProvider
import com.example.registrodeactividades.utils.Utils

class DetalleUsuarioFragment : Fragment() {

    private val authProvider = AuthProvider()
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
        val dataAccionPositiva = HijosDataBase.getInstance(application).accionPositivaDao
        val dataAccionNegativa = HijosDataBase.getInstance(application).accionNegativaDao
        val dataAccionNegativaMatthew =
            HijosDataBase.getInstance(application).accionNegativaMatthewDao
        val dataAccionPositivaMatthew =
            HijosDataBase.getInstance(application).accionPositivaMatthewDao
        val viewModelFactory = DetalleUsuarioViewModelFactory(
            dataAccionPositiva,
            dataAccionNegativa,
            dataAccionPositivaMatthew,
            dataAccionNegativaMatthew,
            application
        )

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
        if (authProvider.getCurrentUser()?.email == "jhohellserick@gmail.com" ||
            authProvider.getCurrentUser()?.email == "rudyjudithapazamendoza@gmail.com"
        ) {
            detalleUsuarioViewModel.setIsAdmin(true)
        } else {
            detalleUsuarioViewModel.setIsAdmin(false)
        }
        //--------------------------------- Para el RECYCLERVIEW --------------------------------------------------------------
        val adapter = ItemUserAdapter(
            onClickListener = {
                Log.d("asdasd", "click en el item ${it.name}")
                it.let {
                    this.findNavController().navigate(
                        DetalleUsuarioFragmentDirections.actionDetalleUsuarioFragmentToUserDetailFragment(
                            it.id
                        )
                    )
                }
            })

        //*********** PARA NAVEGACION *****************************
        detalleUsuarioViewModel.idUserForNavigation.observe(
            viewLifecycleOwner
        ) {
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
            it.let {
                adapter.data = it
            }
        }
        //-----------------------------------------------------------------------------------------------------------------------------------

        binding.cardButtonRestartUsers.setOnClickListener {
            bindingDialog = CardAlertReiniciarBinding.inflate(layoutInflater)
            val myDialog = Dialog(it.context)
            myDialog.setContentView(bindingDialog.root)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            myDialog.show()

            bindingDialog.buttonReiniciar.setOnClickListener {
                itemUseViewModel.resetUserAndrew()
                itemUseViewModel.resetUserMatthew()
                myDialog.dismiss()
                Utils.SnackbarUtils.showSnackBar(binding.root, "Reiniciado")
            }
        }

        binding.swipeRefreshList.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshList.isRefreshing = false
                itemUseViewModel.getUsers()
                itemUseViewModel.listUsers.observe(viewLifecycleOwner) {
                    it.let {
                        adapter.data = it
                    }
                }
            }, 1000)
        }

        detalleUsuarioViewModel.isAdmin.observe(viewLifecycleOwner) {
            obtainPermissionAdminForButtons(it)
        }


        return binding.root
    }

    private fun obtainPermissionAdminForButtons(it: Boolean) {
        binding.cardButtonRestartUsers.isClickable = it
    }
}