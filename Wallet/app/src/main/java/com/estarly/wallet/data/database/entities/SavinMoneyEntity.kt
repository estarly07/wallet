package com.estarly.wallet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(
    tableName = "saving_money_table"
)
data class SavinMoneyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "amount_saving") val amountSaving : Double,
//    @ColumnInfo(name = "last_amount_deposited") val lastAmountDeposited : Long
)
