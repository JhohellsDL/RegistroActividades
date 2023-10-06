package com.example.registrodeactividades.currentActivities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.registrodeactividades.databinding.ListItemActividadesPositivasRecientesBinding
import com.example.registrodeactividades.model.AccionPositivaMatthew

class CurrentListPositiveMatthewAdapter(
    private val onClickListener: (AccionPositivaMatthew) -> Unit
) : androidx.recyclerview.widget.ListAdapter<AccionPositivaMatthew, CurrentListPositiveMatthewAdapter.ActividadesPositivasViewHolder>(
    AccionPoditivaDiffCallback()
) {

    class ActividadesPositivasViewHolder private constructor(val binding: ListItemActividadesPositivasRecientesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: AccionPositivaMatthew,
            onClickListener: (AccionPositivaMatthew) -> Unit
        ) {
            binding.textRecentDate.text = item.fecha
            binding.textPositive.text = item.fecha
            binding.textValorPositive.text = "${item.valor} pts"
            binding.imagePositive.setImageResource(item.imageResource)
            binding.textContadorItem.text = item.contador.toString()
            itemView.setOnClickListener { onClickListener(item) }
        }

        companion object {
            fun from(parent: ViewGroup): ActividadesPositivasViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemActividadesPositivasRecientesBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
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
        DiffUtil.ItemCallback<AccionPositivaMatthew>() {
        override fun areItemsTheSame(
            oldItem: AccionPositivaMatthew,
            newItem: AccionPositivaMatthew
        ): Boolean {
            return newItem.contador == oldItem.contador
        }

        override fun areContentsTheSame(
            oldItem: AccionPositivaMatthew,
            newItem: AccionPositivaMatthew
        ): Boolean {
            return oldItem == newItem
        }
    }
}