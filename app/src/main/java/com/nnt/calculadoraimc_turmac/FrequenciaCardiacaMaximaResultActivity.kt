package com.nnt.calculadoraimc_turmac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nnt.calculadoraimc_turmac.databinding.ActivityFrequenciaCardiacaMaximaResultBinding

class FrequenciaCardiacaMaximaResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFrequenciaCardiacaMaximaResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrequenciaCardiacaMaximaResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewResultadoTMB.text=intent.getIntExtra("fcMaxima",0).toString()

        binding.imageButtonVoltar.setOnClickListener {
            startActivity(Intent(this,FrequenciaCardiacaMaximaActivity::class.java))
            finish()
        }
    }
}