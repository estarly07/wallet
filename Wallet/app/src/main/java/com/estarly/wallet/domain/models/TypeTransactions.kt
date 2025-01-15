package com.estarly.wallet.domain.models

enum class TypeTransactions(private val value:String, val type : String) {
    DEPOSIT("deposit", "Se depositó"),
    PAY_DEBT("pay debt", "Se pagó"),
    TAKE_MONEY_OUT("take money out", "Se retiró"),
    SAVING_MONEY("saving money", "Se depositó en ahorros");

    override fun toString(): String = value
    companion object{
        fun enumValueOf(typeTransaction: String ): TypeTransactions
            = when(typeTransaction){
                TAKE_MONEY_OUT.toString() -> TAKE_MONEY_OUT
                PAY_DEBT.toString() -> PAY_DEBT
                SAVING_MONEY.toString() -> SAVING_MONEY
                else -> {DEPOSIT}
            }
    }
}
fun TypeTransactions.name() : String
    = when(this){
        TypeTransactions.DEPOSIT        -> "Depositar 💰"
        TypeTransactions.PAY_DEBT       -> "Pagar deuda 🏦"
        TypeTransactions.TAKE_MONEY_OUT -> "Retirar 💵💸"
        TypeTransactions.SAVING_MONEY   -> "Ahorrar 🐖🪙"
    }
fun TypeTransactions.advice() : String
        = when(this){
    TypeTransactions.DEPOSIT        -> "¡Depositar dinero es una buena forma de cuidar tu futuro! \uD83D\uDCB0"
    TypeTransactions.PAY_DEBT       -> "Paga tus deudas poco a poco para estar más tranquilo. \uD83D\uDCB8 "
    TypeTransactions.TAKE_MONEY_OUT -> "Solo saca lo que realmente necesites.\uD83D\uDE0C "
    TypeTransactions.SAVING_MONEY   -> "Ahorrar es como cuidar tu dinero para el futuro. \uD83C\uDFE6"
}