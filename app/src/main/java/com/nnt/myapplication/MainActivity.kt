package com.nnt.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.nnt.myapplication.databinding.ActivityMainBinding
import com.nnt.myapplication.model.Calculo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoCalcular.setOnClickListener { calcular() }
    }

    private fun calcular() {
        val peso = binding.campoPeso.text.toString().replace(",", ".").toDoubleOrNull()
        val altura = binding.campoAltura.text.toString().replace(",", ".").toDoubleOrNull()
        val imc: Double

        if(peso == null || altura == null) {
            Toast.makeText(this, "Por favor preencha todos os campos.", Toast.LENGTH_SHORT).show()
        } else {
            imc = peso / (altura * altura) // IMC = peso (kg) / altura (m) x altura (m)

            Thread{
                val app = application as App
                val dao = app.db.calculoDao()

                val atualizaId = intent.extras?.getInt("atualizaId")

                if (atualizaId != null) {
                    dao.atualizar(Calculo(id = atualizaId, tipo = "imc", resultado = imc))
                } else {
                    dao.inserir(Calculo(tipo = "imc", resultado = imc))
                }

                runOnUiThread{
                    Toast.makeText(this, "Medição salva com sucesso!", Toast.LENGTH_LONG).show()
                }

            }.start()

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("imc", imc.toString())

            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_listar){
            val intent = Intent(this, ListaCalculoActivity::class.java)
            intent.putExtra("tipo", "imc")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}