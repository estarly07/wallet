package com.estarly.wallet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(
    tableName = "transactions_table"
)
data class TransactionsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int ,
    @ColumnInfo(name = "description") val description : String,
    @ColumnInfo(name = "amount") val amount : Double,
    @ColumnInfo(name = "date") val date : Long,
    @ColumnInfo(name = "typeTransaction") val typeTransaction : String,
)
