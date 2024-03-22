package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.repositories.UserRepository
import javax.inject.Inject

class UpdateSalaryUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
     operator fun invoke(salary : String) { userRepository.updateUserSalary(salary.toDouble())}
}