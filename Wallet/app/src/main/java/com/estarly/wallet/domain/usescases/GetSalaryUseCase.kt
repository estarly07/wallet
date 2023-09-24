package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.SalaryModel
import com.estarly.wallet.domain.repositories.SalaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSalaryUseCase @Inject constructor(
    private val  salaryRepository: SalaryRepository
) {
    operator fun invoke(): Flow<SalaryModel?> = salaryRepository.salary
}