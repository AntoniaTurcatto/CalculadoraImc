package com.nnt.calculadoraimc_turmac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nnt.calculadoraimc_turmac.databinding.ActivityImcBinding

class ImcActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImcBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalcular.setOnClickListener {
            if(!validar()) {
                Toast.makeText(this, "Por favor, digite seu peso e sua altura.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val imc = calcular()
            val intent = Intent(this, ImcResultActivity::class.java)
            intent.putExtra("imc", imc)
            startActivity(intent)
        }
    }

    private fun calcular(): Double {
        val peso = binding.editPeso.text.toString().replace(",", ".").toDouble()
        val altura = binding.editAltura.text.toString().replace(",", ".").toDouble()
        return peso / (altura * altura)
    }

    private fun validar(): Boolean {
        return binding.editPeso.text.toString().isNotEmpty() &&
                binding.editAltura.text.toString().isNotEmpty() &&
                !binding.editPeso.text.toString().startsWith("0") &&
                binding.editAltura.text.toString() != "0"
    }
}