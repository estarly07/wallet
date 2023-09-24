package com.estarly.wallet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "debt_table"
)
data class DebtEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "amount") val amount : Double,
    @ColumnInfo(name = "amount_paid") val amount_paid: Double,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "date_last_paid") val dateLastPaid : Long,
    @ColumnInfo(name = "finished") val finished : Int
)
