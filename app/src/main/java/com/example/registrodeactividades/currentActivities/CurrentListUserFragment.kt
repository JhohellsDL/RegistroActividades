package com.example.registrodeactividades.currentActivities

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import java.io.IOException
import java.io.OutputStream

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
            binding.textRecentMoney.text = userData.recentMoney

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

        binding.buttonShared.setOnClickListener {
            captureAndSaveImage()
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

    private fun captureAndSaveImage() {

        var message =
            "¡Hoy tuve una increíble sesión de lectura en mi app! Mejoré mi comprensión de lectura. ¡Siento cómo mi habilidad está creciendo día a día! #Lectura #Mejora #Aprendizaje\n\n¡Echa un vistazo a esta increíble aplicación\n"
        message += "https://play.google.com/store/apps/details?id=com.jdlstudios.lecturakids"

        val screenshot = captureFragmentScreen(requireParentFragment())
        val imagePath = screenshot?.let {
            saveImage(it)
        }
        if (imagePath != null) {
            shareImage(imagePath, message)
        } else {
            Toast.makeText(requireContext(), "Oops!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun captureFragmentScreen(fragment: Fragment): Bitmap? {

        val view = fragment.view

        val bitmap =
            view?.let { Bitmap.createBitmap(it.width, view.height - 500, Bitmap.Config.ARGB_8888) }

        val canvas = bitmap?.let { Canvas(it) }
        if (view != null) {
            view.draw(canvas)
        } else {
            Log.i("asd", "-- no hay captura dentro!")
        }

        return bitmap
    }

    private fun saveImage(image: Bitmap): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "screenshot.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/MyAppScreenshots"
            )
        }

        val resolver = requireContext().contentResolver
        var outputStream: OutputStream? = null
        val imageUri: Uri?
        try {
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            imageUri = resolver.insert(contentUri, contentValues)
            imageUri?.let {
                outputStream = resolver.openOutputStream(it)
                image.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                outputStream?.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return imageUri
    }

    private fun shareImage(imagePath: Uri, message: String) {
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_STREAM, imagePath)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(Intent.createChooser(intent, "Compartir imagen"))
    }

}