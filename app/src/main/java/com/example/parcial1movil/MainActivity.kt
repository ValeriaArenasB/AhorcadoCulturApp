package com.example.parcial1movil

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.parcial1movil.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnJugar.setOnClickListener {
            if (camposCompletos()) {
                val bundle = Bundle().apply {
                    putString("NOMBRE", binding.editTextNombre.text.toString().trim())
                    putString("APELLIDO", binding.editTextApellido.text.toString().trim())
                }
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

        binding.btnCulturizate.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(this, CulturizateActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)

        }
    }

    private fun camposCompletos(): Boolean {
        val nombre = binding.editTextNombre.text.toString().trim()
        val apellido = binding.editTextApellido.text.toString().trim()

        return if (nombre.isEmpty() || apellido.isEmpty()) {
            Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
}