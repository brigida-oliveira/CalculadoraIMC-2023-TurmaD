package com.nnt.myapplication

import com.nnt.myapplication.model.Calculo

interface OnListClickListener {
    fun onClick(id: Int, tipo: String)
    fun onLongClick(position: Int, calculo: Calculo)
}