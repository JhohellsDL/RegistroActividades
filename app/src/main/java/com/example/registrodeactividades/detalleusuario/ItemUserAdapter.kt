package com.example.registrodeactividades.detalleusuario

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registrodeactividades.databinding.ItemUserNewBinding
import com.example.registrodeactividades.model.UserData
import java.text.DecimalFormat

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
            binding.textUserName.text = item.name
            //binding.imageItem.setImageResource(item.photoResourceId)
            binding.textCurrentMoney.text = item.currentMoney
            //binding.textRecentDate.text = item.recentDate
            //binding.textStartDate.text = item.date
            itemView.setOnClickListener { onClickListener(item) }

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