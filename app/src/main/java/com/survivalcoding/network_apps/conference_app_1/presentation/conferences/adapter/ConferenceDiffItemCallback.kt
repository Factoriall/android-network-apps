package com.survivalcoding.network_apps.conference_app_1.presentation.conferences.adapter

import androidx.recyclerview.widget.DiffUtil
import com.survivalcoding.network_apps.conference_app_1.domain.model.Conference

object ConferenceDiffItemCallback : DiffUtil.ItemCallback<Conference>() {
    override fun areItemsTheSame(oldItem: Conference, newItem: Conference): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: Conference, newItem: Conference): Boolean {
        return oldItem == newItem
    }
}