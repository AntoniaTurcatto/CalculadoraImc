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
import com.nnt.calculadoraimc_turmac.databinding.ActivityTmbactivityBinding
import com.nnt.calculadoraimc_turmac.databinding.DialogTmbInfoBinding
import com.nnt.calculadoraimc_turmac.databinding.DialogTmbResultBinding
import com.nnt.calculadoraimc_turmac.model.Calculo
import java.text.DecimalFormat

class TMBActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTmbactivityBinding
    private lateinit var alertDialog: AlertDialog

    private var idade = 0
    private var alturaEmCentimetros = 0
    private var peso = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTmbactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButtonVoltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.imageButtonInfo.setOnClickListener { infoTMB() }

        binding.seekbarIdade.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //faça enquanto a barra estiver mudando
                    binding.textViewIdade.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //faça algo quando alguém tocar a barra
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //faça algo quando parar a ação na seekbar
                if (seekBar != null) {
                    idade = seekBar.progress
                }
            }
        })

        binding.seekbarAltura.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.textViewAltura.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null)
                    alturaEmCentimetros = seekBar.progress
            }
        })

        binding.seekbarPeso.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.textViewPeso.text = DecimalFormat("#0.00").format(progress/100.00).replace('.', ',')
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null)
                    peso = seekBar.progress/100.00
            }
        })

        binding.buttonCalcular.setOnClickListener { calcularTMB(idade, alturaEmCentimetros, peso) }
    }

    private fun calcularTMB(idade: Int, altura: Int, peso: Double) {
        var tmb = 0.0
        if(binding.chipMasculino.isChecked) {
            tmb = 66 + (13.7 * peso + ( 5 * altura - (6.8 * idade)))
        } else if (binding.chipFeminino.isChecked) {
            tmb = 655 + ((9.6 * peso) + (1.8 * altura) - (4.7 * idade))
        }
        resultadoTMB(tmb)
    }

    private fun resultadoTMB(tmb: Double) {
        val builder = AlertDialog.Builder(this, R.style.Theme_DialogCustomizada)
        val dialogBinding: DialogTmbResultBinding = DialogTmbResultBinding.inflate(LayoutInflater.from(this))
        builder.setView(dialogBinding.root)
        dialogBinding.textViewResultadoTMB.text = tmb.toString()
        dialogBinding.botaoVoltar.setOnClickListener {
            Thread{
                val app = application as App
                val dao=app.db.calculoDao()
                val atualizarId=intent.extras?.getInt("atualizarId")

                if (atualizarId != null){
                    dao.atualizar(Calculo(id = atualizarId, tipo = "tmb", resultado = tmb))
                } else{
                    dao.inserir(Calculo(tipo = "tmb", resultado = tmb))
                }
                runOnUiThread{
                    Toast.makeText(this@TMBActivity,"Registro salvo ou atualizado com sucesso!",Toast.LENGTH_LONG).show()
                }
            }.start()
            alertDialog.dismiss()
        }
        alertDialog = builder.create()
        alertDialog.show()
    }

    private fun infoTMB() {
        val builder = AlertDialog.Builder(this, R.style.Theme_DialogCustomizada)
        val dialogBinding: DialogTmbInfoBinding = DialogTmbInfoBinding.inflate(LayoutInflater.from(this))
        builder.setView(dialogBinding.root)
        dialogBinding.buttonSairDaDialog.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_mostrar_registros){
            val intent = Intent(this@TMBActivity,ListaDeCalculosActivity::class.java)
            intent.putExtra("tipo","tmb")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}