package com.estarly.wallet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "distribution_table"
)
data class DistributionOfMoneyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "amountSaved") val amountSaved : Double,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "image") val image : String,
    @ColumnInfo(name = "amountExpected") val amountExpected : Double
)
