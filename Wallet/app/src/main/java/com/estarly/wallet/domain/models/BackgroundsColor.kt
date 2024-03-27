package com.estarly.wallet.domain.models

import com.estarly.wallet.R

data class BackgroundsColor(
    val name  : String,
    val color : Int
)

fun getColorBackground(name : String) : Int = colors.find { it.name == name }?.color ?: colors[0].color
fun getNameColorBackground(color: Int) : String = colors.find { it.color == color }?.name ?: colors[0].name

val colors = listOf(
    BackgroundsColor("one", R.drawable.one,),
    BackgroundsColor("two", R.drawable.two,),
    BackgroundsColor("three", R.drawable.three,),
    BackgroundsColor("four", R.drawable.four,),
    BackgroundsColor("five", R.drawable.five,),
    BackgroundsColor("six", R.drawable.six,),
    BackgroundsColor("seven", R.drawable.seven,),
    BackgroundsColor("eight", R.drawable.eight,),
    BackgroundsColor("nine", R.drawable.nine,),
    BackgroundsColor("ten", R.drawable.ten,),
    BackgroundsColor("eleven", R.drawable.eleven,),
)
