package com.estarly.wallet.presentation
import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.estarly.wallet.databinding.AlertLayoutBinding
import com.estarly.wallet.presentation.adapters.AlertAdapter
import com.estarly.wallet.presentation.adapters.AlertItem

class FloatingAlertService : Service() {

    private var windowManager: WindowManager? = null
    private lateinit var alertBinding: AlertLayoutBinding
    private var timer: CountDownTimer? = null
    companion object {
        var list : List<AlertItem> = listOf()
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        alertBinding = AlertLayoutBinding.inflate(LayoutInflater.from(this), null,false)

        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            android.graphics.PixelFormat.TRANSLUCENT
        )
        layoutParams.gravity = Gravity.TOP
        windowManager?.addView(alertBinding.root, layoutParams)

        with(alertBinding){
            timer = object : CountDownTimer(30000, 1000) {
                @SuppressLint("SetTextI18n")
                override fun onTick(millisUntilFinished: Long) {
                    btnClose.text = "Cerrar (${millisUntilFinished / 1000} s)"
                }
                // Cierra el servicio cuando el temporizador finalice
                override fun onFinish() { stopSelf() }
            }.start()

            btnClose.setOnClickListener { stopSelf() }
            recycler.layoutManager = LinearLayoutManager(this@FloatingAlertService,LinearLayoutManager.VERTICAL, false)
            recycler.adapter = AlertAdapter(list)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        list = listOf()
        timer?.cancel()
        windowManager?.removeView(alertBinding.root)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
