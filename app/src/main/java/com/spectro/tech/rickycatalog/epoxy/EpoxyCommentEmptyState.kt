package com.spectro.tech.rickycatalog.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.spectro.tech.rickycatalog.R

@EpoxyModelClass
abstract class EpoxyCommentEmptyState :
    EpoxyModelWithHolder<EpoxyCommentEmptyState.Holder>() {

    override fun getDefaultLayout(): Int {
        return R.layout.model_empty_comments
    }

    inner class Holder : EpoxyHolder() {

        lateinit var itemView: View

        override fun bindView(itemView: View) {
            this.itemView = itemView
        }
    }
}