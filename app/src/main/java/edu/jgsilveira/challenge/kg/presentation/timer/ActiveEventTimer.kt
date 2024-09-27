package edu.jgsilveira.challenge.kg.presentation.timer

import android.os.CountDownTimer
import java.util.Locale

internal class ActiveEventTimer {
    private var listener: ActiveEventTimerListener? = null
    private var timer: CountDownTimer? = null

    fun setActiveEventTimeListener(listener: ActiveEventTimerListener?) {
        this.listener = listener
    }

    fun start(remainingTimeInMillis: Long, countDownInterval: Long) {
        cancel()
        initTimer(remainingTimeInMillis, countDownInterval)
        timer?.start()
    }

    fun cancel() {
        timer?.cancel()
    }

    private fun formatTimeUntilFinished(millisUntilFinished: Long): String {
        val hours = (millisUntilFinished / (1000 * 60 * 60)) % 24
        val minutes = (millisUntilFinished / (1000 * 60)) % 60
        val seconds = (millisUntilFinished / 1000) % 60

        return String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds
        )
    }

    private fun initTimer(remainingTimeInMillis: Long, countDownInterval: Long) {
        timer = object : CountDownTimer(remainingTimeInMillis, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val untilFinished = formatTimeUntilFinished(millisUntilFinished)
                listener?.onTick(untilFinished)
            }

            override fun onFinish() {
                listener?.onFinish()
            }
        }
    }
}