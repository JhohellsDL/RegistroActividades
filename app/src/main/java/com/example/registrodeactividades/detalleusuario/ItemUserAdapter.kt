package com.example.registrodeactividades.detalleusuario

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registrodeactividades.R
import com.example.registrodeactividades.databinding.ItemUserNewBinding
import com.example.registrodeactividades.model.UserData
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

class ItemUserAdapter(
    private val onClickListener: (UserData) -> Unit
) : RecyclerView.Adapter<ItemUserAdapter.UserViewHolder>() {
    var data = listOf<UserData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onClickListener)
    }

    class UserViewHolder private constructor(val binding: ItemUserNewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: UserData,
            onClickListener: (UserData) -> Unit
        ) {
            uploadPhotoUser(item.name)
            binding.textUserName.text = item.name
            binding.textStartDate.text = item.date
            binding.textRecentDate.text =
                SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US).format(item.recentDate)
            binding.textCurrentMoney.text =
                "S/. ${formatDecimalNumber(item.currentMoney.toFloat())}"

            uploadDailyLives(item.dailyLives)

            itemView.setOnClickListener { onClickListener(item) }
        }

        private fun uploadDailyLives(dailyLives: Int) {
            if (dailyLives == 0) {
                binding.imageLiveOne.setImageResource(R.drawable.baseline_favorite_border)
                binding.imageLiveTwo.setImageResource(R.drawable.baseline_favorite_border)
                binding.imageLiveThree.setImageResource(R.drawable.baseline_favorite_border)
            } else if (dailyLives == 1) {
                binding.imageLiveOne.setImageResource(R.drawable.baseline_favorite)
                binding.imageLiveTwo.setImageResource(R.drawable.baseline_favorite_border)
                binding.imageLiveThree.setImageResource(R.drawable.baseline_favorite_border)
            } else if (dailyLives == 2) {
                binding.imageLiveOne.setImageResource(R.drawable.baseline_favorite)
                binding.imageLiveTwo.setImageResource(R.drawable.baseline_favorite)
                binding.imageLiveThree.setImageResource(R.drawable.baseline_favorite_border)
            } else {
                binding.imageLiveOne.setImageResource(R.drawable.baseline_favorite)
                binding.imageLiveTwo.setImageResource(R.drawable.baseline_favorite)
                binding.imageLiveThree.setImageResource(R.drawable.baseline_favorite)
            }
        }

        private fun uploadPhotoUser(name: String) {
            when (name) {
                "Andrew Alfredo Dianderas Apaza" -> {
                    binding.imageItem.setImageResource(R.drawable.andrew)
                }

                "Matthew Fabian Dianderas Apaza" -> {
                    binding.imageItem.setImageResource(R.drawable.matthew)
                }

                else -> {
                    binding.imageItem.setImageResource(R.drawable.user)
                }
            }
        }

        private fun formatDecimalNumber(number: Float): String {
            val df = DecimalFormat("#.###")
            return df.format(number)
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserNewBinding.inflate(layoutInflater, parent, false)
                return UserViewHolder(binding)
            }
        }
    }
}

class ItemListenerNew(val clickListener: () -> Unit) {
    fun onClick(hijo: UserData) = clickListener()
}