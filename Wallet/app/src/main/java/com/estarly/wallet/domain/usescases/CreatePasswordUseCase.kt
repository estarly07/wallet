package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.repositories.UserRepository
import javax.inject.Inject

class CreatePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
     operator fun invoke(password: String, ) = userRepository.createPassword(password)
}