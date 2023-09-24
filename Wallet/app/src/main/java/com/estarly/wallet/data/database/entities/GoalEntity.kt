package com.estarly.wallet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "goal_table"
)
data class GoalEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "image") val image : String,
    @ColumnInfo(name = "description") val description : String,
    @ColumnInfo(name = "amountSaved") val amountSaved : Double,
    @ColumnInfo(name = "amountGoal") val amountGoal : Double,
    @ColumnInfo(name = "finished") val finished : Int
)
