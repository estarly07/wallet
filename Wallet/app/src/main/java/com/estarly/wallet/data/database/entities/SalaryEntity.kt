package com.estarly.wallet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(
    tableName = "salary_table"
)
data class SalaryEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")     val id     : Int,
    @ColumnInfo(name = "amount") val amount : Double
)
