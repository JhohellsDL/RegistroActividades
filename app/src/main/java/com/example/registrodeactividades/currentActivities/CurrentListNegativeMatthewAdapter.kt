package com.example.registrodeactividades.currentActivities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.registrodeactividades.databinding.ListItemActividadesNegativasBinding
import com.example.registrodeactividades.model.AccionNegativaMatthew

class CurrentListNegativeMatthewAdapter (
    private val onClickListener: (AccionNegativaMatthew) -> Unit
): ListAdapter<AccionNegativaMatthew, CurrentListNegativeMatthewAdapter.ActividadesNegativasViewHolder>(AccionNegativaDiffCallback()) {

    class ActividadesNegativasViewHolder private constructor(val binding: ListItemActividadesNegativasBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: AccionNegativaMatthew,
            onClickListener: (AccionNegativaMatthew) -> Unit
        ){
            binding.textValorNegativo.text = "${item.valor} pts"
            binding.textNegativa.setText(item.stringResourceId)
            binding.imageNegativa.setImageResource(item.imageResource)
            binding.textContadorItem.text = item.contador.toString()
            itemView.setOnClickListener { onClickListener(item) }
        }

        companion object {

            fun from(parent: ViewGroup): ActividadesNegativasViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemActividadesNegativasBinding.inflate(layoutInflater, parent, false)
                return ActividadesNegativasViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActividadesNegativasViewHolder {
        return ActividadesNegativasViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ActividadesNegativasViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickListener)
    }

    class AccionNegativaDiffCallback :
        DiffUtil.ItemCallback<AccionNegativaMatthew>() {
        override fun areItemsTheSame(oldItem: AccionNegativaMatthew, newItem: AccionNegativaMatthew): Boolean {
            return newItem.contador == oldItem.contador
        }

        override fun areContentsTheSame(oldItem: AccionNegativaMatthew, newItem: AccionNegativaMatthew): Boolean {
            return oldItem == newItem
        }
    }

}