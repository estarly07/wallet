package com.estarly.wallet.domain.repositories

interface UserRepository {
     fun createPassword(password : String)
     fun validatePassword(password : String) : Boolean
     fun havePassword() : Boolean
     fun getUserSalary() : Double
     fun updateUserSalary(salary :Double)
}