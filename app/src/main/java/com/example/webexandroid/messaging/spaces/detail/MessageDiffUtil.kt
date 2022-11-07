package com.example.webexandroid.messaging.spaces.detail

import androidx.recyclerview.widget.DiffUtil

class MessageDiffUtil(
    var oldmess: List<Message>,
    var newmess: MutableList<Message>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldmess.size
    }

    override fun getNewListSize(): Int {
        return newmess.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldmess.get(oldItemPosition).messageId == newmess.get(newItemPosition).messageId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldmess.get(oldItemPosition).message == newmess.get(newItemPosition).message
    }

}