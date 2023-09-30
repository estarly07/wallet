package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.repositories.UserRepository
import javax.inject.Inject

class HavePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
     operator fun invoke() : Boolean = userRepository.havePassword()
}