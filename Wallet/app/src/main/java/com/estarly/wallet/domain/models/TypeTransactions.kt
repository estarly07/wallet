package com.estarly.wallet.domain.models

enum class TypeTransactions(private val value:String, val type : String) {
    DEPOSIT("deposit", "Se depositó"),
    PAY_DEBT("pay debt", "Se pagó"),
    TAKE_MONEY_OUT("take money out", "Se retiró");

    override fun toString(): String = value
    companion object{
        fun enumValueOf(typeTransaction: String ): TypeTransactions
            = when(typeTransaction){
                TAKE_MONEY_OUT.toString() -> TAKE_MONEY_OUT
                PAY_DEBT.toString() -> PAY_DEBT
                else -> {DEPOSIT}
            }
    }
}