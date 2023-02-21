package com.example.registrodeactividades.detalleusuario

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contadorcasino.database.Hijo
import com.example.registrodeactividades.databinding.ListItemUserBinding

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
            /*val cad = "ID: ${item.hijoId}\n" +
                    "NOMBRE: ${item.nombre}\n" +
                    "FOTO: ${item.photoResourceId}\n" +
                    "FECHA: ${item.fecha}\n" +
                    "PTS PREMIO: ${item.puntosPremio}\n" +
                    "PTS CASTIGO: ${item.puntosCastigo}\n" +
                    "PTS JUEGO: ${item.puntosJuego}\n" +
                    "PTS AYER: ${item.puntosAyer}\n" +
                    "PTS HOY: ${item.puntosHoy}\n" +
                    "DINERO: ${item.dinero}\n"*/

            binding.textIdItem.text = "ID: ${item.hijoId}"
            binding.userText.text = item.nombre
            binding.imageItem.setImageResource(item.photoResourceId)
            binding.textFecha.text = item.fecha
            binding.textPuntosPremio.text = item.puntosPremio.toString()
            binding.textPuntosCastigo.text = item.puntosCastigo.toString()
            binding.textPuntosJuego.text = item.puntosJuego.toString()
            binding.textPuntosAyer.text = "Ayer ${item.puntosAyer} puntos"
            binding.textPuntosHoy.text = "Hoy ${item.puntosHoy} puntos"
            binding.textDinero.text = "S/. ${item.dinero}"

            itemView.setOnClickListener { onClickListener(item) }
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