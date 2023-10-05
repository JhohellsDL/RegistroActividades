package com.example.registrodeactividades.actividades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.registrodeactividades.databinding.ListItemActividadesNegativasBinding
import com.example.registrodeactividades.model.AccionNegativa

class ActividadesNegativasAdapter(
    private val onClickListener: (AccionNegativa) -> Unit
): ListAdapter<AccionNegativa, ActividadesNegativasAdapter.ActividadesNegativasViewHolder>(AccionNegativaDiffCallback()) {

    class ActividadesNegativasViewHolder private constructor(val binding: ListItemActividadesNegativasBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: AccionNegativa,
            onClickListener: (AccionNegativa) -> Unit
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
        DiffUtil.ItemCallback<AccionNegativa>() {
        override fun areItemsTheSame(oldItem: AccionNegativa, newItem: AccionNegativa): Boolean {
            return newItem.contador == oldItem.contador
        }

        override fun areContentsTheSame(oldItem: AccionNegativa, newItem: AccionNegativa): Boolean {
            return oldItem == newItem
        }
    }

}