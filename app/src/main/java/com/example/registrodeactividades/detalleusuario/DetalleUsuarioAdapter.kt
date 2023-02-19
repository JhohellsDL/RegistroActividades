package com.example.registrodeactividades.detalleusuario

import android.graphics.Color
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
        //val res = holder.textNombre.context.resources
        holder.bind(item, onClickListener)
    }

    class UserViewHolder private constructor(val binding: ListItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        /*val textFecha: TextView = view.findViewById(R.id.text_fecha)
        val textPuntosPremio: TextView = view.findViewById(R.id.puntosPremio)
        val textPuntosCastigo: TextView = view.findViewById(R.id.puntosCastigo)
        val textPuntosJuego: TextView = view.findViewById(R.id.puntosJuego)
        val textPuntosAyer: TextView = view.findViewById(R.id.puntosAyer)
        val textPuntosHoy: TextView = view.findViewById(R.id.puntosHoy)
        val textDinero: TextView = view.findViewById(R.id.dinero)*/

        fun bind(
            item: Hijo,
            onClickListener: (Hijo) -> Unit
        ) {
            if (item.nombre == "Andrew") {
                binding.userText.setTextColor(Color.RED)
            }
            val cad = "ID: ${item.hijoId}\n" +
                    "NOMBRE: ${item.nombre}\n" +
                    "FOTO: ${item.photoResourceId}\n" +
                    "FECHA: ${item.fecha}\n" +
                    "PTS PREMIO: ${item.puntosPremio}\n" +
                    "PTS CASTIGO: ${item.puntosCastigo}\n" +
                    "PTS JUEGO: ${item.puntosJuego}\n" +
                    "PTS AYER: ${item.puntosAyer}\n" +
                    "PTS HOY: ${item.puntosHoy}\n" +
                    "DINERO: ${item.dinero}\n"

            binding.userText.text = cad
            //binding.clickListener = item
            itemView.setOnClickListener { onClickListener(item) }
        }

        fun render(
            //context: Context,
            element: Hijo,
            //onClickListener: (Hijo) -> Unit
        ) {
//            id.text = element.hijoId.toString()
//            photo.setImageResource(element.photoResourceId)
            binding.userText.text = element.nombre
//            textFecha.text = "Fecha:  ${element.fecha}"
//            textPuntosPremio.text = element.puntosPremio.toString()
//            textPuntosCastigo.text = element.puntosCastigo.toString()
//            textPuntosJuego.text = element.puntosJuego.toString()
//            textPuntosAyer.text = "Ayer ${element.puntosAyer} puntos"
//            textPuntosHoy.text = "Hoy ${element.puntosHoy} puntos"
//            textDinero.text = "S/. ${element.dinero}"
            //itemView.setOnClickListener { onClickListener(element) }
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