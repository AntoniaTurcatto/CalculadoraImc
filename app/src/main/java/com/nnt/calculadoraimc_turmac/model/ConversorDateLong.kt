package com.nnt.calculadoraimc_turmac.model

import androidx.room.TypeConverter
import java.util.Date

object ConversorDateLong {

    @TypeConverter
    fun paraDate(dateLong: Long?):Date?{
        return if (dateLong != null){
            Date(dateLong)
        } else null
    }

    @TypeConverter
    fun deDateParaLong(date: Date?):Long?{
        return date?.time
    }

}