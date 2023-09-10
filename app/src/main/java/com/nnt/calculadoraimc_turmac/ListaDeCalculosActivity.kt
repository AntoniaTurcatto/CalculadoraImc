package com.nnt.calculadoraimc_turmac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nnt.calculadoraimc_turmac.databinding.ActivityListaDeCalculosBinding
import com.nnt.calculadoraimc_turmac.model.Calculo
import java.lang.IllegalStateException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

class ListaDeCalculosActivity : AppCompatActivity(), OnListClickListener {

    private lateinit var binding: ActivityListaDeCalculosBinding
    private lateinit var resultado: MutableList<Calculo>
    private lateinit var adapter: ListaDeCalculosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListaDeCalculosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultado= mutableListOf<Calculo>()
        adapter = ListaDeCalculosAdapter(resultado, this)

        binding.recyclerViewCalculos.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCalculos.adapter= adapter

        val tipo = intent?.extras?.getString("tipo")
        if (tipo != null){
            Thread{
                val app = application as App
                val dao = app.db.calculoDao()
                val resposta = dao.buscarRegistroPorTipo(tipo)
                runOnUiThread {
                    resultado.addAll(resposta)
                    adapter.notifyDataSetChanged()
                }
            }.start()
        } else {
            Thread{
                val app = application as App
                val dao = app.db.calculoDao()
                val resposta = dao.getAll()
                runOnUiThread {
                    resultado.addAll(resposta)
                    adapter.notifyDataSetChanged()
                }
            }.start()
        }
    }

    private inner class ListaDeCalculosAdapter(private val listaCalculo: List<Calculo>, private val listener: OnListClickListener):
        RecyclerView.Adapter<ListaDeCalculosAdapter.ListaCalculosViewHolder>(){
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int): ListaDeCalculosAdapter.ListaCalculosViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.celula_layout, parent, false)
            return ListaCalculosViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: ListaDeCalculosAdapter.ListaCalculosViewHolder,
            position: Int
        ) {
            val itemAtual = listaCalculo[position]
            holder.bind(itemAtual)
        }

        override fun getItemCount(): Int {
            return listaCalculo.size
        }

        private inner class ListaCalculosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val resultado: TextView = itemView.findViewById(R.id.resultado_celula)
            val data: TextView = itemView.findViewById(R.id.data_celula)
            val tipoTextView: TextView = itemView.findViewById(R.id.tipo_celula)

            fun bind(item: Calculo){
                val simpleDateFormat = SimpleDateFormat("dd/mm/yyyy HH:mm", Locale("pt","BR"))
                val decimalFormat = DecimalFormat("##.#")

                when (item.tipo){
                    "imc" -> {
                        tipoTextView.text="IMC: "
                    }
                    "tmb" -> {
                        tipoTextView.text="TMB: "
                    }
                    "fcm" ->{
                        tipoTextView.text="FCM: "
                    }
                }

                resultado.text = decimalFormat.format(item.resultado) //calculo de IMC ou TMB
                data.text = "Data: ${simpleDateFormat.format(item.data)}" //data formatada
                itemView.setOnClickListener{
                    listener.onClick(item.id,item.tipo)
                }
                itemView.setOnLongClickListener{
                    listener.onLongClick(adapterPosition,item)
                    true
                }
            }

        }

    }

    override fun onClick(id: Int, tipo: String) {
        when(tipo){
            "imc" -> {
                val intent = Intent(this,ImcActivity::class.java)
                intent.putExtra("atualizarId",id)
                startActivity(intent)
            }
            "tmb" -> {
                val intent = Intent(this,TMBActivity::class.java)
                intent.putExtra("atualizarId",id)
                startActivity(intent)
            }
            "fcm" -> {
                val intent=Intent(this,FrequenciaCardiacaMaximaActivity::class.java)
                intent.putExtra("atualizarId",id)
                startActivity(intent)
            }
        }
    }

    override fun onLongClick(position: Int, calculo: Calculo) {
        AlertDialog.Builder(this).setMessage("Você quer mesmo apagar esse registro?")
            .setNegativeButton("Não"){ dialog, wich ->
                dialog.dismiss()
            }
            .setPositiveButton("Sim"){dialog,wich->
                Thread{
                    val app = application as App
                    val dao = app.db.calculoDao()
                    val resposta = dao.delete(calculo)

                    if (resposta > 0){
                        runOnUiThread {
                            resultado.removeAt(position)
                            adapter.notifyItemRemoved(position)
                        }
                    }
                }.start()
            }.create()
            .show()
    }
}