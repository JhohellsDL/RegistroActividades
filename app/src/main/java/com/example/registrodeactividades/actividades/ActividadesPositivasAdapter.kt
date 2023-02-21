package com.example.registrodeactividades.actividades

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import com.example.registrodeactividades.database.DataSource
import com.example.registrodeactividades.databinding.ListItemActividadesPositivasBinding
import com.example.registrodeactividades.generated.callback.OnClickListener
import com.example.registrodeactividades.model.AccionPositiva

class ActividadesPositivasAdapter(
    private val onClickListener: (AccionPositiva) -> Unit,
    private val data: List<AccionPositiva>
): RecyclerView.Adapter<ActividadesPositivasAdapter.ActividadesPositivasViewHolder>() {

    /*var data = DataSource().loadPositiveActions()
        set(value) {
            field = value
            notifyDataSetChanged()
        }*/
    class ActividadesPositivasViewHolder private constructor(val binding: ListItemActividadesPositivasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: AccionPositiva,
            onClickListener: (AccionPositiva) -> Unit
        ) {
            binding.actividadPositivaText.setText(item.stringResourceId)
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

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ActividadesPositivasViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onClickListener)
    }
}