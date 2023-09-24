package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.repositories.SalaryRepository
import javax.inject.Inject

class GetAmountSalaryUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository
) {
    suspend operator fun invoke(): Double = salaryRepository.getSalary()?.amount ?: 0.0
}