package com.nnt.myapplication.model

import androidx.room.TypeConverter
import java.util.Date

object CoversorDateLong {
    @TypeConverter
    fun paraDate(dateLong: Long?): Date? {
        return if(dateLong != null) Date(dateLong) else null
    }

    @TypeConverter
    fun deDate(date: Date?): Long? {
        return date?.time
    }
}