package com.nnt.calculadoraimc_turmac

import com.nnt.calculadoraimc_turmac.model.Calculo
import java.text.FieldPosition

interface OnListClickListener {

    fun onClick(id: Int, tipo: String)

    fun onLongClick(position: Int, calculo: Calculo)

}