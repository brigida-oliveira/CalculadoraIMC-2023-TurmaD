package com.nnt.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nnt.myapplication.databinding.ActivityListaCalculoBinding
import com.nnt.myapplication.model.Calculo
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.Locale

class ListaCalculoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaCalculoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaCalculoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resultado = mutableListOf<Calculo>()
        val adapter = ListaCalculoAdapter(resultado)

        val recyclerView = binding.rvLista
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val tipo = intent?.extras?.getString("tipo") ?: throw IllegalStateException("Tipo não encontrado!")

        Thread{
            val app = application as App
            val dao = app.db.calculoDao()
            val resposta = dao.buscaRegistroPorTipo(tipo)

            runOnUiThread{
                resultado.addAll(resposta)
                adapter.notifyDataSetChanged()
            }

        }.start()

    }

    private inner class ListaCalculoAdapter(
        private val listaCalculo: List<Calculo>
    ): RecyclerView.Adapter<ListaCalculoAdapter.ListaCalculoViewHolder>(){
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ListaCalculoAdapter.ListaCalculoViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            return  ListaCalculoViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: ListaCalculoAdapter.ListaCalculoViewHolder,
            position: Int
        ) {
            val itemAtual = listaCalculo[position]
            holder.bind(itemAtual)
        }

        override fun getItemCount(): Int {
            return listaCalculo.size
        }

        private inner class ListaCalculoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            fun bind(item: Calculo) {
                val textView = itemView as TextView
                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))

                val resultado = item.resultado
                val data = simpleDateFormat.format(item.data)

                textView.text = "TMB: $resultado - $data"
            }
        }

    }
}