package com.example.parcial1movil

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class PalabraAdapter(context: Context, private val palabras: List<Palabra>) : ArrayAdapter<Palabra>(context, 0, palabras) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_palabra, parent, false)

        val palabra = palabras[position]
        val imagen = view.findViewById<ImageView>(R.id.imageView)
        val texto1 = view.findViewById<TextView>(R.id.texto1)
        val texto2 = view.findViewById<TextView>(R.id.texto2)

        imagen.setImageResource(R.drawable.ahorcado_0)
        texto1.text = palabra.palabra
        texto2.text = "Dificultad: ${palabra.nivel_dificultad}"

        return view
    }
}
