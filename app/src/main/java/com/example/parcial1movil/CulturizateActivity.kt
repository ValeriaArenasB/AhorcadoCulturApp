package com.example.parcial1movil

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.parcial1movil.databinding.ActivityCulturizateBinding
import org.json.JSONObject

class CulturizateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCulturizateBinding
    private val PERMISSION_STORAGE = 101
    private var yaIntentoSolicitar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCulturizateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        verificarPermiso()
    }

    private fun verificarPermiso() {
        val permisoConcedido = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }

        if (permisoConcedido) {
            cargarListaDesdeJSON()
        } else if (!yaIntentoSolicitar) {
            solicitarPermiso()
            yaIntentoSolicitar = true
        } else {
            Toast.makeText(this, "Permiso denegado, funcionalidades reducidas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun solicitarPermiso() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), PERMISSION_STORAGE)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cargarListaDesdeJSON()
            } else {
                Toast.makeText(this, "Permiso denegado: funcionalidades reducidas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarListaDesdeJSON() {
        val jsonString = resources.openRawResource(R.raw.lista).bufferedReader().use { it.readText() }
        val jsonArray = JSONObject(jsonString).getJSONArray("palabra")
        val listaPalabras = mutableListOf<Palabra>()

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            listaPalabras.add(
                Palabra(
                    obj.getString("palabra"),
                    obj.getString("definicion"),
                    obj.getInt("nivel_dificultad"),
                    obj.getString("letras_dificiles")
                )
            )
        }

        val adapter = PalabraAdapter(this, listaPalabras)
        binding.listView.adapter = adapter
        binding.listView.setOnItemClickListener { _, _, position, _ ->
            val palabra = listaPalabras[position]
            val intent = Intent(this, PalabraDetalleActivity::class.java).apply {
                putExtra("palabra", palabra.palabra)
                putExtra("definicion", palabra.definicion)
                putExtra("nivel", palabra.nivel_dificultad)
                putExtra("letras", palabra.letras_dificiles)
            }
            startActivity(intent)
        }
    }
}
