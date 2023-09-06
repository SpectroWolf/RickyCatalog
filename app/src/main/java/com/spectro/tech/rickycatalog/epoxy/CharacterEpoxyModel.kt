package com.spectro.tech.rickycatalog.epoxy

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.spectro.tech.rickycatalog.R
import com.spectro.tech.rickycatalog.model.domain.CharacterDTO
import java.util.Locale

@EpoxyModelClass
abstract class CharacterEpoxyModel :
    EpoxyModelWithHolder<CharacterEpoxyModel.CharacterEpoxyModelHolder>() {

    @EpoxyAttribute
    lateinit var character: CharacterDTO

    @EpoxyAttribute
    lateinit var buttonCallBack: (CharacterDTO) -> Unit

    override fun bind(holder: CharacterEpoxyModelHolder) {
        super.bind(holder)

        holder.name.text = character.name
        holder.status.text =
            "${character.status.replaceFirstChar { it.titlecase(Locale.ROOT) }} - ${character.species}"
        holder.lastKnownLocation.text = character.location.name

        Glide.with(holder.characterImage)
            .load(character.image)
            .error(R.drawable.ic_generic_avatar)
            .into(holder.characterImage)

        setStatusImage(holder)
        setButtonClickListener(holder)

    }

    override fun getDefaultLayout(): Int {
        return R.layout.model_character_list_item
    }

    private fun setStatusImage(holder: CharacterEpoxyModelHolder) {

        val imageId = when (character.status) {
            "Alive" -> R.drawable.ic_alive_icon
            "Dead" -> R.drawable.ic_dead_icon
            else -> R.drawable.ic_unkwon_status_icon
        }

        Glide.with(holder.statusImage)
            .load(imageId)
            .into(holder.statusImage)
    }

    private fun setButtonClickListener(holder: CharacterEpoxyModelHolder) {
        holder.button.setOnClickListener {
            buttonCallBack(character)
        }
    }

    inner class CharacterEpoxyModelHolder : EpoxyHolder() {

        lateinit var itemView: View
        lateinit var name: TextView
        lateinit var statusImage: ImageView
        lateinit var status: TextView
        lateinit var characterImage: ImageView
        lateinit var lastKnownLocation: TextView
        lateinit var button: Button

        override fun bindView(itemView: View) {
            this.itemView = itemView
            this.name = itemView.findViewById(R.id.tv_character_name)
            this.status = itemView.findViewById(R.id.tv_character_status_and_species)
            this.statusImage = itemView.findViewById(R.id.iv_status_icon)
            this.characterImage = itemView.findViewById(R.id.iv_character_image)
            this.lastKnownLocation = itemView.findViewById(R.id.tv_location)
            this.button = itemView.findViewById(R.id.btn_show_more)
        }
    }
}