package com.example.parcial1movil

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial1movil.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private val imagenes = listOf(
        R.drawable.ahorcado_0,
        R.drawable.ahorcado_1,
        R.drawable.ahorcado_2,
        R.drawable.ahorcado_3,
        R.drawable.ahorcado_4,
        R.drawable.ahorcado_5
    )

    //para que rote entre las palabras en la lista
    private val palabras = listOf("HOLA", "COMO", "ESTAS")
    private var indicePalabra = 0

    private var palabraSecreta = palabras[indicePalabra]
    private var letrasAdivinadas = mutableSetOf<Char>()
    private var letrasUsadas = mutableSetOf<Char>()
    private var intentosFallidos = 0
    private var puntaje = 0
    private var juegoTerminado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nombre = intent.extras?.getString("NOMBRE") ?: "Jugador"
        binding.competidorTextView.text = "COMPETIDOR: $nombre"
        actualizarUI()

        binding.validarButton.setOnClickListener {
            if (juegoTerminado) return@setOnClickListener
            val input = binding.letraEditText.text.toString().uppercase()
            if (input.length != 1) {
                Toast.makeText(this, "Ingresa solo una letra", Toast.LENGTH_SHORT).show()
                return@setOnClickListener }
            val letra = input[0]
            if (letrasUsadas.contains(letra)) {
                Toast.makeText(this, "Letra ya utilizada", Toast.LENGTH_SHORT).show()
                return@setOnClickListener }
            letrasUsadas.add(letra)
            if (letra in palabraSecreta) {
                letrasAdivinadas.add(letra)
            } else { intentosFallidos++ }
            binding.letraEditText.text.clear()
            actualizarUI()
        }
    }

    private fun actualizarUI() {
        val palabraMostrada = palabraSecreta.map {
            if (it in letrasAdivinadas) it else '_'
        }.joinToString(",")

        binding.palabraOcultaTextView.text = palabraMostrada
        binding.puntajeTextView.text = "PUNTAJE: $puntaje"
        binding.intentosTextView.text = "INTENTOS: ${letrasUsadas.joinToString(",")}"
        binding.imagenAhorcado.setImageResource(imagenes[intentosFallidos.coerceAtMost(imagenes.lastIndex)])

        when {
            palabraMostrada.replace(",", "") == palabraSecreta -> {
                puntaje++
                Toast.makeText(this, "Correcto! Siguiente palabra", Toast.LENGTH_SHORT).show()
                siguientePalabra()
            }
            intentosFallidos >= imagenes.lastIndex -> {
                juegoTerminado = true
                Toast.makeText(this, "Perdiste! La palabra era $palabraSecreta", Toast.LENGTH_LONG).show()
                binding.validarButton.isEnabled = false
                binding.letraEditText.isEnabled = false
            }
        }
    }

    private fun siguientePalabra() {
        indicePalabra = (indicePalabra + 1) % palabras.size
        palabraSecreta = palabras[indicePalabra]
        letrasAdivinadas.clear()
        letrasUsadas.clear()
        intentosFallidos = 0
        binding.letraEditText.isEnabled = true
        binding.validarButton.isEnabled = true
        actualizarUI()
    }
}
