package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.repositories.UserRepository
import javax.inject.Inject

class GetSalaryUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
     operator fun invoke() : Double  = userRepository.getUserSalary()
}