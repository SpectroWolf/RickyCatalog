package com.spectro.tech.rickycatalog.epoxy

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.spectro.tech.rickycatalog.R
import com.spectro.tech.rickycatalog.model.domain.CharacterDTO
import com.spectro.tech.rickycatalog.model.domain.Comments
import com.spectro.tech.rickycatalog.model.domain.EpisodeDTO

@EpoxyModelClass
abstract class CommentEpoxyModel :
    EpoxyModelWithHolder<CommentEpoxyModel.CommentEpoxyModelHolder>() {

    @EpoxyAttribute
    lateinit var comment: Comments

    @EpoxyAttribute
    lateinit var editButtonCallback: (Comments) -> Unit

    @EpoxyAttribute
    lateinit var deleteButtonCallback: (Comments) -> Unit

    override fun bind(holder: CommentEpoxyModelHolder) {
        super.bind(holder)

        holder.characterName.text = comment.characterName
        holder.comment.text = comment.comment

        buttonListeners(holder)
    }

    override fun getDefaultLayout(): Int {
        return R.layout.model_comment_list_item
    }

    private fun buttonListeners(holder: CommentEpoxyModelHolder){

        holder.editButton.setOnClickListener {
            editButtonCallback(comment)
        }

        holder.deleteButton.setOnClickListener {
            deleteButtonCallback(comment)
        }
    }

    inner class CommentEpoxyModelHolder : EpoxyHolder() {

        lateinit var itemView: View
        lateinit var characterName: TextView
        lateinit var comment: TextView
        lateinit var editButton: ImageView
        lateinit var deleteButton: ImageView

        override fun bindView(itemView: View) {
            this.itemView = itemView
            this.characterName = itemView.findViewById(R.id.tv_character_name)
            this.comment = itemView.findViewById(R.id.tv_comment)
            this.editButton = itemView.findViewById(R.id.iv_edit)
            this.deleteButton = itemView.findViewById(R.id.iv_delete)
        }
    }
}