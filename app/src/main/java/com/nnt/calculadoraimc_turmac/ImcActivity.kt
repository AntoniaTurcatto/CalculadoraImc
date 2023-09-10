package com.nnt.calculadoraimc_turmac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.nnt.calculadoraimc_turmac.databinding.ActivityImcBinding
import com.nnt.calculadoraimc_turmac.model.Calculo

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
            Thread{
                val app = application as App
                val dao = app.db.calculoDao()
                val atualizarId= intent.extras?.getInt("atualizarId")
                if (atualizarId != null){
                    dao.atualizar(Calculo(id = atualizarId, tipo = "imc", resultado = imc))
                } else {
                    dao.inserir(Calculo(tipo = "imc", resultado = imc))
                }
                runOnUiThread {
                    Toast.makeText(this,"Registro salvo ou atualizado com sucesso!",Toast.LENGTH_LONG).show()
                }
            }.start()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_mostrar_registros){
            val intent = Intent(this,ListaDeCalculosActivity::class.java)
            intent.putExtra("tipo","imc")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}