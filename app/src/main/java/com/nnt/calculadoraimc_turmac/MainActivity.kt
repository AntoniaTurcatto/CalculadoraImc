package com.nnt.calculadoraimc_turmac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nnt.calculadoraimc_turmac.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonIMC.setOnClickListener {
            val intent = Intent(this, ImcActivity::class.java)
            startActivity(intent)
        }

        binding.buttonTMB.setOnClickListener {
            startActivity(Intent(this, TMBActivity::class.java))
        }

        binding.buttonFrequenciaCardiacaMaxima.setOnClickListener {
            startActivity(Intent(this,FrequenciaCardiacaMaximaActivity::class.java))
        }
    }
}