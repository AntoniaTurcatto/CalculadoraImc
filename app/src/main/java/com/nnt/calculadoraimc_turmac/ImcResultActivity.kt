package com.nnt.calculadoraimc_turmac

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.nnt.calculadoraimc_turmac.databinding.ActivityImcResultBinding
import com.nnt.calculadoraimc_turmac.databinding.DialogImcInfoBinding
import java.text.DecimalFormat

class ImcResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImcResultBinding
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImcResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imc = intent.getDoubleExtra("imc", 0.0)
        binding.textViewImc.text = DecimalFormat("#.##").format(imc).replace(".", ",")

        binding.botaoVoltar.setOnClickListener {
            startActivity(Intent(this, ImcActivity::class.java))
            finish()
        }

        binding.botaoInfo.setOnClickListener { imcInfo() }

        when {
            imc < 16 -> {
                binding.imageView.setImageResource(R.drawable.magreza_grave)
                binding.textViewResultado.text = "Você está gravemente abaixo do peso."
                binding.textViewImc.setTextColor(Color.parseColor("#0C4D8B"))
                binding.textViewResultado.setTextColor(Color.parseColor("#0C4D8B"))
            }
            imc >= 16 && imc < 17 -> {
                binding.imageView.setImageResource(R.drawable.magreza_moderada)
                binding.textViewResultado.text = "Você está moderadamente abaixo do peso."
                binding.textViewImc.setTextColor(Color.parseColor("#0377BE"))
                binding.textViewResultado.setTextColor(Color.parseColor("#0377BE"))
            }
            imc >= 17 && imc < 18.5 -> {
                binding.imageView.setImageResource(R.drawable.abaixo_do_peso)
                binding.textViewResultado.text = "Você está levemente abaixo do peso."
                binding.textViewImc.setTextColor(Color.parseColor("#64A2D7"))
                binding.textViewResultado.setTextColor(Color.parseColor("#64A2D7"))
            }
            imc >= 18.5 && imc < 25 -> {
                binding.imageView.setImageResource(R.drawable.saudavel)
                binding.textViewResultado.text = "Parabéns! Você está com o peso ideal."
                binding.textViewImc.setTextColor(Color.parseColor("#66BB6A"))
                binding.textViewResultado.setTextColor(Color.parseColor("#66BB6A"))
            }
            imc >= 25 && imc < 30 -> {
                binding.imageView.setImageResource(R.drawable.sobrepeso)
                binding.textViewResultado.text = "Você está com sobrepeso."
                binding.textViewImc.setTextColor(Color.parseColor("#FFFFC400"))
                binding.textViewResultado.setTextColor(Color.parseColor("#FFFFC400"))
            }
            imc >= 30 && imc < 35 -> {
                binding.imageView.setImageResource(R.drawable.obesidade_grau_i)
                binding.textViewResultado.text = "Você está com Obesidade Grau I."
                binding.textViewImc.setTextColor(Color.parseColor("#F2683C"))
                binding.textViewResultado.setTextColor(Color.parseColor("#F2683C"))
            }
            imc >= 35 && imc < 40 -> {
                binding.imageView.setImageResource(R.drawable.obesidade_grau_ii)
                binding.textViewResultado.text = "Você está com Obesidade Grau II (severa)."
                binding.textViewImc.setTextColor(Color.parseColor("#BE3A26"))
                binding.textViewResultado.setTextColor(Color.parseColor("#BE3A26"))
            }
            imc >= 40 -> {
                binding.imageView.setImageResource(R.drawable.obesidade_grau_iii)
                binding.textViewResultado.text = "Você está com Obesidade Grau III (mórbida)."
                binding.textViewImc.setTextColor(Color.parseColor("#861619"))
                binding.textViewResultado.setTextColor(Color.parseColor("#861619"))
            }
        }
    }

    //Dialog padrão
    private fun dialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("O que é o IMC")
        builder.setMessage(R.string.sobre_o_imc)
        builder.setPositiveButton("OK") {_, _ ->
            alertDialog.dismiss()
        }
        builder.setNeutralButton("Menu Inicial") {_, _ ->
            Intent(applicationContext, ImcActivity::class.java)
            finish()
        }
        alertDialog = builder.create()
        alertDialog.show()
    }

    private fun imcInfo() {
        val builder = AlertDialog.Builder(this, R.style.Theme_DialogCustomizada)
        val dialogBinding : DialogImcInfoBinding = DialogImcInfoBinding.inflate(LayoutInflater.from(this))
        builder.setView(dialogBinding.root)

        dialogBinding.buttonMenu.setOnClickListener{
            val intent = Intent(applicationContext, ImcActivity::class.java)
            startActivity(intent)
        }

        dialogBinding.buttonVoltar.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog = builder.create()
        alertDialog.show()
    }
}