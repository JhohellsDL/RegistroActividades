package com.example.registrodeactividades.currentActivities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.registrodeactividades.databinding.ListItemActividadesPositivasBinding
import com.example.registrodeactividades.model.AccionPositiva

class CurrentListPositiveAdapter (
    private val onClickListener: (AccionPositiva) -> Unit
): androidx.recyclerview.widget.ListAdapter<AccionPositiva, CurrentListPositiveAdapter.ActividadesPositivasViewHolder>(AccionPoditivaDiffCallback()) {

    class ActividadesPositivasViewHolder private constructor(val binding: ListItemActividadesPositivasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: AccionPositiva,
            onClickListener: (AccionPositiva) -> Unit
        ) {
            binding.textPositive.text = item.fecha
            binding.textValorPositive.text = "${item.valor} pts"
            binding.imagePositive.setImageResource(item.imageResource)
            binding.textContadorItem.text = item.contador.toString()
            itemView.setOnClickListener { onClickListener(item) }
        }

        companion object {
            fun from(parent: ViewGroup): ActividadesPositivasViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemActividadesPositivasBinding.inflate(layoutInflater, parent, false)
                return ActividadesPositivasViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActividadesPositivasViewHolder {
        return ActividadesPositivasViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ActividadesPositivasViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    class AccionPoditivaDiffCallback :
        DiffUtil.ItemCallback<AccionPositiva>() {
        override fun areItemsTheSame(oldItem: AccionPositiva, newItem: AccionPositiva): Boolean {
            return newItem.contador == oldItem.contador
        }

        override fun areContentsTheSame(oldItem: AccionPositiva, newItem: AccionPositiva): Boolean {
            return oldItem == newItem
        }
    }
}