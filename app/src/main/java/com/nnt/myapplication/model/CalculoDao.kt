package com.nnt.myapplication.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CalculoDao {
    @Insert
    fun inserir(calculo: Calculo)

    @Query("SELECT * FROM Calculo WHERE tipo = :tipo")
    fun buscaRegistroPorTipo(tipo: String): List<Calculo>
}