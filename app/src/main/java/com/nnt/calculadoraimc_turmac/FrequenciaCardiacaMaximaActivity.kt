package com.nnt.calculadoraimc_turmac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.nnt.calculadoraimc_turmac.databinding.ActivityFrequenciaCardiacaMaximaBinding
import com.nnt.calculadoraimc_turmac.databinding.DialogFrequenciaCardiacaMaximaBinding
import com.nnt.calculadoraimc_turmac.model.Calculo


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
            val fcMaxima : Double
            if (binding.chipMasculino.isChecked){
                fcMaxima =220 - idade.toDouble()
            } else {
                fcMaxima = 226 - idade.toDouble()
            }
            Thread{
                val app = application as App
                val dao = app.db.calculoDao()
                val atualizarId = intent.extras?.getInt("atualizarId")
                if (atualizarId != null){
                    dao.atualizar(Calculo(id = atualizarId, "fcm", resultado = fcMaxima))
                } else {
                    dao.inserir(Calculo(tipo = "fcm", resultado = fcMaxima))
                }
                runOnUiThread {
                    Toast.makeText(this,"Registro salvo ou atualizado com sucesso!", Toast.LENGTH_LONG).show()
                }
            }.start()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_mostrar_registros){
            val intent = Intent(this,ListaDeCalculosActivity::class.java)
            intent.putExtra("tipo","fcm")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}