package com.estarly.wallet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "pending_purchase_table"
)
data class PendingPurchaseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "amount") val amount : Double,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "finished") val finished : Int,
    @ColumnInfo(name = "image") val image : String,
)
