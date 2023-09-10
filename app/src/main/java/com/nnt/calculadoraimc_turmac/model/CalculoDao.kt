package com.nnt.calculadoraimc_turmac.model

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
    fun buscarRegistroPorTipo(tipo: String): List<Calculo>

    @Query("SELECT * FROM Calculo ORDER BY tipo ASC")
    fun getAll():List<Calculo>

    @Delete
    fun delete(calculo: Calculo):Int

    @Update
    fun atualizar(calculo: Calculo)

    //Query = busca
    //insert = insere
    //delete = deleta
    //update = atualiza
}