package com.spectro.tech.rickycatalog.epoxy

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import com.spectro.tech.rickycatalog.model.domain.Comments

class CommentListController(
    val editComment: (Comments) -> Unit,
    val deleteComment: (Comments) -> Unit,
) : TypedEpoxyController<List<Comments>>() {

    override fun buildModels(data: List<Comments>?) {

        if(data.isNullOrEmpty()){

            addEmptyState()
            return
        }

        data.forEach { comment ->
            CommentEpoxyModel_()
                .id(comment.id)
                .comment(comment)
                .editButtonCallback { editComment(comment) }
                .deleteButtonCallback { deleteComment(comment) }
                .addTo(this)
        }
    }

    private fun addEmptyState() {
        EpoxyCommentEmptyState_()
            .id("Empty State")
            .addTo(this)
    }
}