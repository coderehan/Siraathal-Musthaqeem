package com.islamicapp.data.duas.rabbanaduas.source.local

import com.islamicapp.domain.duas.rabbanaduas.model.RabbanaDuas

interface RabbanaDuasDataSource {
    suspend fun getRabbanaDuasFromAssets(): List<RabbanaDuas>?
}