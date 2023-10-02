package com.example.registrodeactividades.registroporusuario

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.registrodeactividades.R
import com.example.registrodeactividades.databinding.FragmentUserDetailBinding
import com.example.registrodeactividades.providers.AuthProvider
import com.example.registrodeactividades.providers.UserProvider
import com.example.registrodeactividades.utils.Utils
import java.text.SimpleDateFormat
import java.util.Locale

class UserDetailFragment : Fragment() {

    private val userProvider = UserProvider()
    private val authProvider = AuthProvider()
    private lateinit var binding: FragmentUserDetailBinding

    private var liveOne = false
    private var liveTwo = false
    private var liveThree = false
    private var dailyLives = 0
    private var consumeWater = 0
    private var initializeConsumeWater = 0
    private var initializeDailyLives = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater)

        var currentLives: Int = 0
        var initialLives: Int = 0

        val args = UserDetailFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = UserDetailViewModelFactory(userProvider, args.userId)
        val viewModel = ViewModelProvider(this, viewModelFactory)[UserDetailViewModel::class.java]

        if (authProvider.getCurrentUser()?.email == "jhohellserick@gmail.com" ||
            authProvider.getCurrentUser()?.email == "rudyjudithapazamendoza@gmail.com"
        ) {
            viewModel.setIsAdmin(true)
        } else {
            viewModel.setIsAdmin(false)
        }

        viewModel.currentUser.observe(viewLifecycleOwner) {
            Log.d("asdasd", "user user user: ${it.name}")
            uploadPhotoUser(it.name)
            binding.textUserName.text = it.name
            binding.textCurrentMoney.text = getString(R.string.format_text_money, it.currentMoney)
            binding.textLostMoney.text = getString(R.string.format_text_money, it.lostMoney)
            binding.textRecentMoney.text = getString(R.string.format_text_money, it.recentMoney)
            binding.textRecentDate.text =
                SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US).format(it.recentDate)
            binding.textStartDate.text = it.date
            binding.textPointsEarned.text = getString(R.string.format_text_points, it.pointsEarned)
            binding.textPointsGames.text = getString(R.string.format_text_points, it.pointsGames)
            binding.textPointsExtras.text = getString(R.string.format_text_points, it.extras)

            binding.textLives.text = it.lives.toString()
            initialLives = it.lives
            currentLives = binding.textLives.text.toString().toInt()

            dailyLives = it.dailyLives
            initializeDailyLives = it.dailyLives
            initializeDailyLives()

            binding.textConsumeWater.text = it.consumeWater.toString()
            consumeWater = it.consumeWater
            initializeConsumeWater = it.consumeWater
        }

        viewModel.isAdmin.observe(viewLifecycleOwner) {
            obtainPermissionAdminForButtons(it)
        }

        binding.cardButtonAddLives.setOnClickListener {
            currentLives++
            binding.textLives.text = currentLives.toString()
        }

        binding.cardButtonRemoveLives.setOnClickListener {
            currentLives--
            binding.textLives.text = currentLives.toString()
        }

        binding.cardButtonConsumeWater.setOnClickListener {
            consumeWater--
            binding.textConsumeWater.text = consumeWater.toString()
        }


        binding.imageLiveOne.setOnClickListener {
            if (liveOne) {
                binding.imageLiveOne.setImageResource(R.drawable.baseline_favorite)
                dailyLives++
                currentLives++
                binding.textLives.text = currentLives.toString()
                Log.d("asdasd", "daily initialLives button: $dailyLives")
            } else {
                binding.imageLiveOne.setImageResource(R.drawable.baseline_favorite_border)
                dailyLives--
                currentLives--
                binding.textLives.text = currentLives.toString()
                Log.d("asdasd", "daily initialLives button: $dailyLives")
            }
            liveOne = !liveOne
        }
        binding.imageLiveTwo.setOnClickListener {
            if (liveTwo) {
                binding.imageLiveTwo.setImageResource(R.drawable.baseline_favorite)
                dailyLives++
                currentLives++
                binding.textLives.text = currentLives.toString()
                Log.d("asdasd", "daily initialLives button: $dailyLives")
            } else {
                binding.imageLiveTwo.setImageResource(R.drawable.baseline_favorite_border)
                dailyLives--
                currentLives--
                binding.textLives.text = currentLives.toString()
                Log.d("asdasd", "daily initialLives button: $dailyLives")
            }
            liveTwo = !liveTwo
        }
        binding.imageLiveThree.setOnClickListener {
            if (liveThree) {
                binding.imageLiveThree.setImageResource(R.drawable.baseline_favorite)
                dailyLives++
                currentLives++
                binding.textLives.text = currentLives.toString()
                Log.d("asdasd", "daily initialLives button: $dailyLives")
            } else {
                binding.imageLiveThree.setImageResource(R.drawable.baseline_favorite_border)
                dailyLives--
                currentLives--
                binding.textLives.text = currentLives.toString()
                Log.d("asdasd", "daily initialLives button: $dailyLives")
            }
            liveThree = !liveThree
        }

        binding.cardButtonRegister.setOnClickListener {
            it.findNavController().navigate(
                UserDetailFragmentDirections.actionUserDetailFragmentToActividadesFragment(args.userId)
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (initialLives != currentLives && initializeDailyLives != dailyLives){
                viewModel.updateAllLivesInUser(currentLives, dailyLives)
                Utils.SnackbarUtils.showSnackBar(binding.root, "Vidas Actualizadas")
            } else if (initialLives != currentLives) {
                viewModel.updateLivesInUser(currentLives)
                Utils.SnackbarUtils.showSnackBar(binding.root, "Vidas Diarias Actualizadas")
            } else if (initializeDailyLives != dailyLives) {
                viewModel.updateDailyLivesInUser(dailyLives)
                Utils.SnackbarUtils.showSnackBar(binding.root, "Vidas Actualizadas")
            } else if (initializeConsumeWater != consumeWater) {
                viewModel.updateConsumeWaterUser(consumeWater)
                Utils.SnackbarUtils.showSnackBar(binding.root, "Consumo de agua actualizado")
            }
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun uploadPhotoUser(name: String) {
        when (name) {
            "Andrew Alfredo Dianderas Apaza" -> {
                binding.imagePhotoUser.setImageResource(R.drawable.andrew)
            }

            "Matthew Fabian Dianderas Apaza" -> {
                binding.imagePhotoUser.setImageResource(R.drawable.matthew)
            }

            else -> {
                binding.imagePhotoUser.setImageResource(R.drawable.user)
            }
        }
    }

    private fun obtainPermissionAdminForButtons(it: Boolean) {
        if (it) {
            binding.cardButtonAddLives.isClickable = true
            binding.cardButtonRemoveLives.isClickable = true
            binding.cardButtonRegister.isClickable = true
            binding.imageLiveOne.isClickable = true
            binding.imageLiveTwo.isClickable = true
            binding.imageLiveThree.isClickable = true
        } else {
            binding.cardButtonAddLives.isClickable = false
            binding.cardButtonRemoveLives.isClickable = false
            binding.cardButtonRegister.isClickable = false
            binding.imageLiveOne.isClickable = false
            binding.imageLiveTwo.isClickable = false
            binding.imageLiveThree.isClickable = false
        }
    }

    private fun initializeDailyLives() {
        if (dailyLives == 2) {
            liveOne = true
            binding.imageLiveOne.setImageResource(R.drawable.baseline_favorite_border)
        }
        if (dailyLives == 1) {
            liveOne = true
            binding.imageLiveOne.setImageResource(R.drawable.baseline_favorite_border)
            liveTwo = true
            binding.imageLiveTwo.setImageResource(R.drawable.baseline_favorite_border)
        }
        if (dailyLives == 0) {
            liveOne = true
            binding.imageLiveOne.setImageResource(R.drawable.baseline_favorite_border)
            liveTwo = true
            binding.imageLiveTwo.setImageResource(R.drawable.baseline_favorite_border)
            liveThree = true
            binding.imageLiveThree.setImageResource(R.drawable.baseline_favorite_border)
        }
    }

}
