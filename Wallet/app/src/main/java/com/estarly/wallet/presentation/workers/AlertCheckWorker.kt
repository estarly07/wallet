package com.estarly.wallet.presentation.workers

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.estarly.wallet.R
import com.estarly.wallet.domain.usescases.GetAllCDTSUseCase
import com.estarly.wallet.presentation.FloatingAlertService
import com.estarly.wallet.presentation.adapters.AlertItem
import com.estarly.wallet.utils.formatSalary
import com.estarly.wallet.utils.parseDateWithoutDay
import com.estarly.wallet.utils.plusDays
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.filter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

fun Context.initAlertWorker(){
    val workRequest = PeriodicWorkRequestBuilder<AlertCheckWorker>(30, TimeUnit.MINUTES)
        .build()

    WorkManager.getInstance(this).enqueueUniquePeriodicWork(
        AlertCheckWorker.name,
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}

@HiltWorker
class AlertCheckWorker  @AssistedInject constructor(
    @Assisted context     : Context,
    @Assisted workerParams: WorkerParameters,
    private var getAllCDTSUseCase: GetAllCDTSUseCase
) : CoroutineWorker(context, workerParams) {

    companion object{ const val name = "AlertCheckWorker"}
    override suspend fun doWork(): Result {
        val intent = Intent(applicationContext, FloatingAlertService::class.java)
        val list = getAllCDTSUseCase.getAll().filter {
            it.days != null &&
            it.dateLastPaid.plusDays(it.days) == System.currentTimeMillis().parseDateWithoutDay()
        }.map {
            AlertItem(
                title    = "Hoy vence el CDT (${it.dateLastPaidFormatted}). \uD83C\uDF89",
                drawable = it.image,
                message  = "Tu ganancia estimada es de $ ${it.profit}."
            )
        }
        if(list.isEmpty())return Result.success()
        FloatingAlertService.list = list
        applicationContext.startService(intent)
        playAlertSound()
        return Result.success()
    }
    private fun playAlertSound() {
        try {
            val mediaPlayer = MediaPlayer.create(applicationContext, R.raw.sound_cash)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { it.release() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}