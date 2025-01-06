package com.estarly.wallet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "cdt_table"
)
data class CDTEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")             val id           : Int,
    @ColumnInfo(name = "amount")         val amount       : Double,
    @ColumnInfo(name = "date_last_paid") val dateLastPaid : Long,
    @ColumnInfo(name = "image")          val image        : String,
    @ColumnInfo(name = "tea")            val tea          : Double? = null,//Tasa efectiva anual
    @ColumnInfo(name = "tna")            val tna          : Double? = null,//Tasa nominal anual
    @ColumnInfo(name = "days")           val days         : Int?    = null,//plazo
    @ColumnInfo(name = "rteFuente")      val rteFuente    : Double? = null,//Retención en la fuente
)
