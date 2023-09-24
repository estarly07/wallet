package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.SalaryModel
import com.estarly.wallet.domain.repositories.SalaryRepository
import javax.inject.Inject

class SaveSalaryUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository
) {
    suspend operator fun invoke(salaryModel: SalaryModel){ salaryRepository.saveSalary(salaryModel) }
}