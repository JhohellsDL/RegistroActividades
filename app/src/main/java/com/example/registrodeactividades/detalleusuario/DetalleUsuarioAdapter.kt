package com.example.registrodeactividades.detalleusuario

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.registrodeactividades.R
import com.example.registrodeactividades.database.Hijo
import com.example.registrodeactividades.databinding.ListItemUserBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetalleUsuarioAdapter(
    private val onClickListener: (Hijo) -> Unit
) : RecyclerView.Adapter<DetalleUsuarioAdapter.UserViewHolder>() {
    var data = listOf<Hijo>()
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

    class UserViewHolder private constructor(val binding: ListItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Hijo,
            onClickListener: (Hijo) -> Unit
        ) {
            binding.textIdItem.text = "ID: ${item.hijoId}"
            binding.userText.text = item.nombre
            binding.imageItem.setImageResource(item.photoResourceId)
            binding.textFecha.text = item.fecha
            binding.textFechaActual.text = item.fechaACtual
            binding.textVidas.text = item.vidas.toString()
            binding.textVidasAntes.text = item.vidasAntes.toString()
            binding.textDineroAntes.text = "S/. ${formatDecimalNumber(item.dineroAntes)}"
            binding.textDineroDespues.text = "S/. ${formatDecimalNumber(item.dineroUltimo)}"
            binding.textDinero.text = "S/. ${formatDecimalNumber(item.dinero)}"
            itemView.setOnClickListener { onClickListener(item) }

            if (item.vidas < 15){
                binding.textVidas.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red_1_new))
                binding.labelVidas.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red_1_new))
            }
        }

        private fun formatDecimalNumber(number: Float): String {
            val df = DecimalFormat("#.###")
            return df.format(number)
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemUserBinding.inflate(layoutInflater, parent, false)
                return UserViewHolder(binding)
            }
        }
    }
}

class ItemListener(val clickListener: (hijoId: Long) -> Unit) {
    fun onClick(hijo: Hijo) = clickListener(hijo.hijoId)
}