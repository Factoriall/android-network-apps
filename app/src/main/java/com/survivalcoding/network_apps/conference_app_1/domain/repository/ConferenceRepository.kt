package com.survivalcoding.network_apps.conference_app_1.domain.repository

import com.survivalcoding.network_apps.conference_app_1.domain.model.Conference

interface ConferenceRepository {
    suspend fun getConferences(): List<Conference>
}