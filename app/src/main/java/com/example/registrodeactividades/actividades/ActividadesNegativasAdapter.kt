package com.example.registrodeactividades.actividades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.registrodeactividades.databinding.ListItemActividadesNegativasBinding
import com.example.registrodeactividades.databinding.ListItemActividadesPositivasBinding
import com.example.registrodeactividades.generated.callback.OnClickListener
import com.example.registrodeactividades.model.AccionNegativa
import com.example.registrodeactividades.model.AccionPositiva

class ActividadesNegativasAdapter(
    private val onClickListener: (AccionNegativa) -> Unit,
    private val data: List<AccionNegativa>
): RecyclerView.Adapter<ActividadesNegativasAdapter.ActividadesNegativasViewHolder>() {

    class ActividadesNegativasViewHolder private constructor(val binding: ListItemActividadesNegativasBinding) :
    RecyclerView.ViewHolder(binding.root){

        fun bind(
            item: AccionNegativa,
            onClickListener: (AccionNegativa) -> Unit
        ){
            binding.actividadNegativaText.setText(item.stringResourceId)
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

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ActividadesNegativasViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onClickListener)
    }

}