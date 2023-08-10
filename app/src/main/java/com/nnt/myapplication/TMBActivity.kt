package com.nnt.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import com.nnt.myapplication.databinding.ActivityTmbactivityBinding
import com.nnt.myapplication.databinding.DialogInfoTmbBinding
import com.nnt.myapplication.databinding.DialogTmbBinding
import java.text.DecimalFormat

class TMBActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTmbactivityBinding
    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTmbactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.botaoVoltar.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        var idade = 0
        var altura = 0
        var peso = 0.0

        binding.seekBarIdade.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //Faz alguma coisa enquanto a barra se move
                ("$progress anos").also { binding.textViewIdade.text = it }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //Faz alguma coisa quando a barra começa a mover
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //Faz alguma coisa quando a barra para de se mover
                if (seekBar != null) {
                    idade = seekBar.progress
                }
            }
        })

        binding.seekBarAltura.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                altura = progress
                (altura.toString().replace(".", ",") + "cm").also { binding.textViewAltura.text = it }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    altura = seekBar.progress
                }
            }
        })

        binding.seekBarPeso.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                peso = progress / 100.00
                "${peso}kg".also { binding.textViewPeso.text = it }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    peso = seekBar.progress / 100.00
                }
            }
        })

        binding.botaoCalcularTmb.setOnClickListener { calcularTMB(idade, altura, peso) }
        binding.botaoInfo.setOnClickListener { infoTMB() }
    }

    fun calcularTMB(idade: Int, altura: Int, peso: Double) {
        var tmb = 0.0

        when {
            binding.chipMasculino.isChecked -> {
                //Para homens: TMB = 66,47 + (13,75 x peso em kg) + (5,0 x altura em cm) – (6,76 x idade em anos)
                tmb = 66.47 + (13.75 * peso) + (5.0 * altura) - (6.76 * idade)
                dialogTMB(tmb)
            }

            binding.chipFeminino.isChecked -> {
                //Para mulheres: TMB = 655,1 + (9,56 x peso em kg) + (1,85 x altura em cm) – (4,68 x idade em anos)
                tmb = 655.1 + (9.56 * peso) + (1.85 * altura) - (4.68 * idade)
                 dialogTMB(tmb)
            }
        }
    }

    fun dialogTMB(tmb: Double) {
        val builder = AlertDialog.Builder(this, R.style.Theme_Dialog)
        val dialogBinding = DialogTmbBinding.inflate(LayoutInflater.from(this))
        builder.setView(dialogBinding.root)

        (DecimalFormat("#.##").format(tmb).replace(".",",") + " calorias").also { dialogBinding.textViewTMB.text = it }
        dialogBinding.buttonTMB.setOnClickListener { alertDialog.dismiss() }

        alertDialog = builder.create()
        alertDialog.show()
    }

    fun infoTMB() {
        val builder = AlertDialog.Builder(this, R.style.Theme_Dialog)
        val dialogBinding = DialogInfoTmbBinding.inflate(LayoutInflater.from(this))
        builder.setView(dialogBinding.root)

        dialogBinding.button.setOnClickListener { alertDialog.dismiss() }

        alertDialog = builder.create()
        alertDialog.show()
    }
}