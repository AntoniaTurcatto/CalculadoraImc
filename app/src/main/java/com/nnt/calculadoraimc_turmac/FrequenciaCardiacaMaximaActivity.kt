package com.nnt.calculadoraimc_turmac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import com.nnt.calculadoraimc_turmac.databinding.ActivityFrequenciaCardiacaMaximaBinding
import com.nnt.calculadoraimc_turmac.databinding.DialogFrequenciaCardiacaMaximaBinding


class FrequenciaCardiacaMaximaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFrequenciaCardiacaMaximaBinding
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrequenciaCardiacaMaximaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var idade = 0

        binding.imageButtonVoltar.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.imageButtonInfo.setOnClickListener { fcmInfo() }

        binding.seekBarIdade.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.textViewIdade.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null){
                    idade=seekBar.progress
                }
            }

        })

        binding.buttonCalcular.setOnClickListener {
            var fcMaxima : Int
            if (binding.chipMasculino.isChecked){
                fcMaxima =220 - idade
            } else {
                fcMaxima = 226 - idade
            }
            val intent = Intent(this, FrequenciaCardiacaMaximaResultActivity::class.java)
            intent.putExtra("fcMaxima",fcMaxima)
            startActivity(intent)
        }
    }

    private fun fcmInfo(){
        val builder = AlertDialog.Builder(this,R.style.Theme_DialogCustomizada)
        val dialogBinding : DialogFrequenciaCardiacaMaximaBinding = DialogFrequenciaCardiacaMaximaBinding.inflate(
            LayoutInflater.from(this))
        builder.setView(dialogBinding.root)

        dialogBinding.buttonMenuDialog.setOnClickListener {
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        dialogBinding.buttonVoltarDialog.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog=builder.create()
        alertDialog.show()
    }

}