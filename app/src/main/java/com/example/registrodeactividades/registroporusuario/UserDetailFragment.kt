package com.example.registrodeactividades.registroporusuario

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.registrodeactividades.R
import com.example.registrodeactividades.databinding.FragmentUserDetailBinding
import com.example.registrodeactividades.providers.AuthProvider
import com.example.registrodeactividades.providers.UserProvider
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Locale

class UserDetailFragment : Fragment() {

    private val userProvider = UserProvider()
    private val authProvider = AuthProvider()
    private lateinit var binding: FragmentUserDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater)

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
            binding.textUserName.text = it.name
            binding.textCurrentMoney.text = getString(R.string.format_text_money, it.currentMoney)
            binding.textLostMoney.text = getString(R.string.format_text_money, it.lostMoney)
            binding.textRecentMoney.text = getString(R.string.format_text_money, it.recentMoney)
            binding.textRecentDate.text =
                SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US).format(it.recentDate)
            binding.textStartDate.text =
                SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US).format(it.date!!)
            binding.textPointsEarned.text = getString(R.string.format_text_points, it.pointsEarned)
            binding.textPointsGames.text = getString(R.string.format_text_points, it.pointsGames)
            binding.textPointsExtras.text = getString(R.string.format_text_points, it.extras)
            binding.textLives.text = it.lives.toString()
        }

        viewModel.isAdmin.observe(viewLifecycleOwner) {
            if (it) {
                binding.cardButtonAddLives.isClickable = true
                binding.cardButtonRemoveLives.isClickable = true
                binding.cardButtonRegister.isClickable = true
            } else {
                binding.cardButtonAddLives.isClickable = false
                binding.cardButtonRemoveLives.isClickable = false
                binding.cardButtonRegister.isClickable = false
            }
        }

        binding.cardButtonAddLives.setOnClickListener {
            Snackbar.make(binding.root, "Tienes permisos!", Toast.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.orange_new))
                .show()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            Snackbar.make(binding.root, "Tienes permisos!", Toast.LENGTH_SHORT)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.orange_new))
                .show()
        }


        return binding.root
    }


}
