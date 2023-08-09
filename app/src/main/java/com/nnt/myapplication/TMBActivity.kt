package com.nnt.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nnt.myapplication.databinding.ActivityTmbactivityBinding

class TMBActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTmbactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTmbactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.botaoVoltar.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}