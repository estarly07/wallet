package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.repositories.UserRepository
import javax.inject.Inject

class UpdateVisibilitySalaryUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
     operator fun invoke(visibility: Boolean) { userRepository.updateVisibilitySalary(visibility)}
}