package edu.jgsilveira.challenge.kg.presentation.view.activeevent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.jgsilveira.challenge.kg.databinding.ActiveEventItemBinding
import edu.jgsilveira.challenge.kg.presentation.model.ActiveSportEventViewData

internal typealias OnActiveSportEventFavoriteCheckChange = (
    activeSportEventId: String,
    isChecked: Boolean
) -> Unit

internal class ActiveSportEventAdapter
    : ListAdapter<ActiveSportEventViewData, ActiveSportEventViewHolder>(
        ActiveSportEventDiffCallback()
    )
{
    var onFavoriteCheck: OnActiveSportEventFavoriteCheckChange? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActiveSportEventViewHolder {
        val binding = ActiveEventItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ActiveSportEventViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ActiveSportEventViewHolder,
        position: Int
    ) {
        val event = currentList[position]
        holder.bind(event, onFavoriteCheck)
    }

    override fun onViewRecycled(
        holder: ActiveSportEventViewHolder
    ) {
        super.onViewRecycled(holder)
        holder.unbind()
    }
}

internal class ActiveSportEventViewHolder(
    private val binding: ActiveEventItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: ActiveSportEventViewData,
        onFavoriteCheck: OnActiveSportEventFavoriteCheckChange?
    ) = with(binding.activeEventItem) {
        setHomeCompetitor(data.homeCompetitor)
        setAwayCompetitor(data.awayCompetitor)
        isFavoriteChecked = data.isFavorite
        setOnFavoriteToggleCheckChanged {
            onFavoriteCheck?.invoke(data.id, it)
        }
        startCountdown(data.startsAt)
    }

    fun unbind() {
        with(binding.activeEventItem) {
            setOnFavoriteToggleCheckChanged(null)
            cancelCountdown()
        }
    }
}

internal class ActiveSportEventDiffCallback : DiffUtil.ItemCallback<ActiveSportEventViewData>() {

    override fun areItemsTheSame(
        oldItem: ActiveSportEventViewData,
        newItem: ActiveSportEventViewData
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ActiveSportEventViewData,
        newItem: ActiveSportEventViewData
    ): Boolean = oldItem == newItem
}