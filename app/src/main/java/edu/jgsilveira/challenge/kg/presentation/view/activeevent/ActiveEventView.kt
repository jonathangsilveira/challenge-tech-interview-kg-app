package edu.jgsilveira.challenge.kg.presentation.view.activeevent

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import edu.jgsilveira.challenge.kg.R
import edu.jgsilveira.challenge.kg.databinding.ActiveEventItemLayoutBinding
import edu.jgsilveira.challenge.kg.presentation.timer.ActiveEventTimer
import edu.jgsilveira.challenge.kg.presentation.timer.ActiveEventTimerListener

private const val INTERVAL = 1000L

internal class ActiveEventView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding by lazy {
        ActiveEventItemLayoutBinding.inflate(
            LayoutInflater.from(context),
            this
        )
    }

    var isFavoriteChecked: Boolean = false
        set(value) {
            field = value
            updateFavoriteToggle(value)
        }

    private var startAtTimeMillis: Long? = null

    private val timer = ActiveEventTimer()

    private val timerListener = object : ActiveEventTimerListener {
        override fun onTick(formattedUntilFinished: String) {
            binding.activeEventItemRemainingTime.text = formattedUntilFinished
        }

        override fun onFinish() {
            binding.activeEventItemRemainingTime.text = context.getString(
                R.string.live_event
            )
        }
    }

    init {
        layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        context.withStyledAttributes(
            attrs,
            R.styleable.ActiveEventItemView,
            defStyleAttr
        ) {
            val isFavorite = getBoolean(
                R.styleable.ActiveEventItemView_active_event_item_favorite,
                false
            )
            val homeCompetitor = getString(
                R.styleable.ActiveEventItemView_active_event_item_homeCompetitor
            )
            val awayCompetitor = getString(
                R.styleable.ActiveEventItemView_active_event_item_awayCompetitor
            )
            isFavoriteChecked = isFavorite
            setHomeCompetitor(homeCompetitor)
            setAwayCompetitor(awayCompetitor)
        }
    }

    fun setHomeCompetitor(homeCompetitor: String?) {
        binding.activeEventItemHomeCompetitor.text = homeCompetitor
    }

    fun setAwayCompetitor(awayCompetitor: String?) {
        binding.activeEventItemAwayCompetitor.text = awayCompetitor
    }

    fun setOnFavoriteToggleCheckChanged(
        listener: ((Boolean) -> Unit)?
    ) {
        binding.activeEventItemFavoriteToggle.setOnClickListener {
            isFavoriteChecked = !isFavoriteChecked
            listener?.invoke(isFavoriteChecked)
        }
    }

    fun startCountdown() {
        startAtTimeMillis?.let { startTime ->
            val remainingTime = startTime - System.currentTimeMillis()
            with(timer) {
                setActiveEventTimeListener(timerListener)
                start(remainingTime, INTERVAL)
            }
        }
    }

    fun cancelCountdown() {
        with(timer) {
            cancel()
            setActiveEventTimeListener(null)
        }
    }

    private fun updateFavoriteToggle(isChecked: Boolean) {
        binding.activeEventItemFavoriteToggle.setImageResource(
            if (isChecked) {
                R.drawable.round_star_24
            } else {
                R.drawable.round_star_border_24
            }
        )
    }
}