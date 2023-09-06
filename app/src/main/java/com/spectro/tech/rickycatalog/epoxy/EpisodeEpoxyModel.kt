package com.spectro.tech.rickycatalog.epoxy

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.spectro.tech.rickycatalog.R
import com.spectro.tech.rickycatalog.model.domain.EpisodeDTO

@EpoxyModelClass
abstract class EpisodeEpoxyModel :
    EpoxyModelWithHolder<EpisodeEpoxyModel.EpisodeEpoxyModelHolder>() {

    @EpoxyAttribute
    lateinit var episode: EpisodeDTO

    override fun bind(holder: EpisodeEpoxyModelHolder) {
        super.bind(holder)

        holder.episodeName.text = episode.name
        holder.episodeNumber.text = episode.episode
        holder.episodeDate.text = episode.air_date

    }

    override fun getDefaultLayout(): Int {
        return R.layout.model_episode_list_item
    }

    inner class EpisodeEpoxyModelHolder : EpoxyHolder() {

        lateinit var itemView: View
        lateinit var episodeName: TextView
        lateinit var episodeNumber: TextView
        lateinit var episodeDate: TextView


        override fun bindView(itemView: View) {
            this.itemView = itemView
            this.episodeName = itemView.findViewById(R.id.tv_episode_title)
            this.episodeNumber = itemView.findViewById(R.id.tv_episode_number)
            this.episodeDate = itemView.findViewById(R.id.tv_episode_date)
        }
    }
}