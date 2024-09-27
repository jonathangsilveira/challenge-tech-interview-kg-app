package edu.jgsilveira.challenge.kg.presentation.timer

internal interface ActiveEventTimerListener {
    fun onTick(formattedUntilFinished: String)
    fun onFinish()
}