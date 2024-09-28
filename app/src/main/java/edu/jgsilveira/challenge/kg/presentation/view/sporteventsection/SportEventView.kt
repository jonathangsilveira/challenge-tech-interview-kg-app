package edu.jgsilveira.challenge.kg.presentation.view.sporteventsection

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import edu.jgsilveira.challenge.kg.R
import edu.jgsilveira.challenge.kg.databinding.SportEventSectionLayoutBinding
import edu.jgsilveira.challenge.kg.presentation.model.ActiveSportEventViewData
import edu.jgsilveira.challenge.kg.presentation.view.activeevent.ActiveSportEventAdapter
import edu.jgsilveira.challenge.kg.presentation.view.activeevent.OnActiveSportEventFavoriteCheckChange

internal class SportEventView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding by lazy {
        SportEventSectionLayoutBinding.inflate(
            LayoutInflater.from(context),
            this
        )
    }

    private val activeSportEventAdapter by lazy {
        ActiveSportEventAdapter()
    }

    private var onFilterCheckChanged: ((Boolean) -> Unit)? = null

    var isExpanded: Boolean = false
        set(value) {
            field = value
            updateExpandedState(value)
        }

    var isFilterChecked: Boolean = false
        set(value) {
            field = value
            updateFilterCheckState(value)
        }

    init {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        context.withStyledAttributes(
            attrs,
            R.styleable.SportEventSectionView,
            defStyleAttr
        ) {
            setTitle(
                getString(
                    R.styleable.SportEventSectionView_sport_event_section_title
                )
            )
            isFilterChecked = getBoolean(
                R.styleable.SportEventSectionView_sport_event_section_favorite,
                false
            )
            isExpanded = getBoolean(
                R.styleable.SportEventSectionView_sport_event_section_expanded,
                true
            )
            binding.sportEventSectionHeaderArrowSwitch.setOnClickListener {
                toggleExpanded()
            }
            binding.sportEventSectionGrid.adapter = activeSportEventAdapter
        }
    }

    fun setTitle(title: String?) {
        binding.sportEventSectionHeaderTitle.text = title
    }

    fun setActiveEvents(activeEvents: List<ActiveSportEventViewData>?) {
        activeSportEventAdapter.submitList(
            activeEvents ?: listOf()
        )
    }

    fun setOnFilterCheckChanged(listener: ((Boolean) -> Unit)?) {
        onFilterCheckChanged = listener
        binding.sportEventSectionHeaderFavoriteSwitch
            .setOnCheckedChangeListener { _, isChecked ->
                onFilterCheckChanged?.invoke(isChecked)
            }
    }

    fun setOnFavoriteCheckChanged(
        listener: OnActiveSportEventFavoriteCheckChange?
    ) {
        activeSportEventAdapter.onFavoriteCheck = listener
    }

    private fun updateExpandedState(isExpanded: Boolean) {
        binding.sportEventSectionHeaderArrowSwitch.setImageResource(
            if (isExpanded) {
                R.drawable.round_arrow_drop_up_24
            } else {
                R.drawable.round_arrow_drop_down_24
            }
        )
        binding.sportEventSectionGrid.isVisible = isExpanded
    }

    private fun toggleExpanded() {
        isExpanded = !isExpanded
        updateExpandedState(isExpanded)
    }

    private fun updateFilterCheckState(isChecked: Boolean) {
        with(binding.sportEventSectionHeaderFavoriteSwitch) {
            setOnCheckedChangeListener(null)
            this.isChecked = isChecked
            setOnCheckedChangeListener { _, isChecked ->
                onFilterCheckChanged?.invoke(isChecked)
            }
        }
    }
}