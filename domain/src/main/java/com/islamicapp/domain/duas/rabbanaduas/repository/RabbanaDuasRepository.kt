package com.islamicapp.domain.duas.rabbanaduas.repository

import com.islamicapp.domain.duas.rabbanaduas.model.RabbanaDuas

interface RabbanaDuasRepository {
    suspend fun getRabbanaDuas(): List<RabbanaDuas>?
}
