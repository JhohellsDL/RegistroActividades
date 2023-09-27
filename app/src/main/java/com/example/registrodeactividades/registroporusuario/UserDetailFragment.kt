package com.example.registrodeactividades.registroporusuario

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.registrodeactividades.databinding.FragmentUserDetailBinding
import com.example.registrodeactividades.providers.UserProvider
import java.text.SimpleDateFormat

class UserDetailFragment : Fragment() {

    private val userProvider = UserProvider()
    private lateinit var binding: FragmentUserDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater)

        val args = UserDetailFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = UserDetailViewModelFactory(userProvider, args.userId)

        val viewModel = ViewModelProvider(this, viewModelFactory)[UserDetailViewModel::class.java]

        viewModel.currentUser.observe(viewLifecycleOwner) {
            Log.d("asdasd", "user user user: ${it.name}")
            binding.textUserName.text = it.name
            binding.textCurrentMoney.text = "S/. ${it.currentMoney}"
            binding.textLostMoney.text = "S/. ${it.lostMoney}"
            binding.textRecentMoney.text = "S/. ${it.recentMoney}"
            binding.textRecentDate.text = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(it.recentDate)
            binding.textStartDate.text = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(it.date)
            binding.textPointsEarned.text = "${it.pointsEarned} puntos"
            binding.textPointsGames.text = "${it.pointsGames} puntos"
            binding.textPointsExtras.text = "${it.extras} puntos"
            binding.textLives.text = it.lives.toString()
        }

        return binding.root
    }

}