package com.example.registrodeactividades.detalleusuario

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contadorcasino.database.Hijo
import com.example.registrodeactividades.R

class DetalleUsuarioAdapter: RecyclerView.Adapter<DetalleUsuarioAdapter.UserViewHolder>() {
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
        holder.bind(item)
    }

    class UserViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view){
        //        val id: TextView = view.findViewById(R.id.id_item)
//        val photo: ImageView = view.findViewById(R.id.image_item)
        val textNombre: TextView = view.findViewById(R.id.user_text)
        /*val textFecha: TextView = view.findViewById(R.id.text_fecha)
        val textPuntosPremio: TextView = view.findViewById(R.id.puntosPremio)
        val textPuntosCastigo: TextView = view.findViewById(R.id.puntosCastigo)
        val textPuntosJuego: TextView = view.findViewById(R.id.puntosJuego)
        val textPuntosAyer: TextView = view.findViewById(R.id.puntosAyer)
        val textPuntosHoy: TextView = view.findViewById(R.id.puntosHoy)
        val textDinero: TextView = view.findViewById(R.id.dinero)*/

        fun bind(
            item: Hijo
        ) {
            if (item.nombre == "Andrew") {
                textNombre.setTextColor(Color.RED)
            }
            textNombre.text = item.nombre
        }
        fun render(
            //context: Context,
            element: Hijo,
            //onClickListener: (Hijo) -> Unit
        ) {
//            id.text = element.hijoId.toString()
//            photo.setImageResource(element.photoResourceId)
            textNombre.text = element.nombre
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
                val view = layoutInflater.inflate(R.layout.list_item_user, parent, false)
                return UserViewHolder(view)
            }
        }
    }


}