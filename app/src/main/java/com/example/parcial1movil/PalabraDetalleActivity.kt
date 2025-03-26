package com.example.parcial1movil

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial1movil.databinding.ActivityPalabraDetalleBinding

class PalabraDetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPalabraDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPalabraDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val palabra = intent.getStringExtra("palabra") ?: ""
        val definicion = intent.getStringExtra("definicion") ?: ""
        val nivel = intent.getIntExtra("nivel", 0)
        val letras = intent.getStringExtra("letras") ?: ""

        binding.textViewPalabra.text = "Palabra: $palabra"
        binding.textViewDefinicion.text = "Definición: $definicion"
        binding.textViewNivel.text = "Nivel de dificultad: $nivel"
        binding.textViewLetras.text = "Letras difíciles: $letras"
    }
}
