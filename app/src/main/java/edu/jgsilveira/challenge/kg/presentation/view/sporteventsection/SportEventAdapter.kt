package edu.jgsilveira.challenge.kg.presentation.view.sporteventsection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.jgsilveira.challenge.kg.databinding.SportEventSectionItemBinding
import edu.jgsilveira.challenge.kg.presentation.model.SportEventViewData
import edu.jgsilveira.challenge.kg.presentation.view.activeevent.ActiveSportEventViewHolder
import edu.jgsilveira.challenge.kg.presentation.view.activeevent.OnActiveSportEventFavoriteCheckChange

internal typealias OnSportEventFilterCheckChange = (
    sportEventId: String,
    isChecked: Boolean
) -> Unit

internal class SportEventAdapter : ListAdapter<SportEventViewData, SportEventViewHolder>(
    SportEventDiffCallback()
) {
    var onFilterCheck: OnSportEventFilterCheckChange? = null

    var onFavoriteCheck: OnActiveSportEventFavoriteCheckChange? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SportEventViewHolder {
        val binding = SportEventSectionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SportEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SportEventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, onFilterCheck, onFavoriteCheck)
    }

    override fun onViewRecycled(
        holder: SportEventViewHolder
    ) {
        super.onViewRecycled(holder)
        holder.unbind()
    }
}

internal class SportEventViewHolder(
    private val binding: SportEventSectionItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        event: SportEventViewData,
        onFilterCheck: OnSportEventFilterCheckChange?,
        onFavoriteCheck: OnActiveSportEventFavoriteCheckChange?
    ) = with(binding.sportEventSectionItem) {
        setTitle(event.title)
        setActiveEvents(event.activeEvents)
        setOnFilterCheckChanged {
            onFilterCheck?.invoke(event.id, it)
        }
        setOnFavoriteCheckChanged(onFavoriteCheck)
    }

    fun unbind() = with(binding.sportEventSectionItem) {
        setOnFilterCheckChanged(null)
        setOnFavoriteCheckChanged(null)
    }
}

internal class SportEventDiffCallback : DiffUtil.ItemCallback<SportEventViewData>() {

    override fun areItemsTheSame(
        oldItem: SportEventViewData,
        newItem: SportEventViewData
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: SportEventViewData,
        newItem: SportEventViewData
    ): Boolean = oldItem == newItem
}