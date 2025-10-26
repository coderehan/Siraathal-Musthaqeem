package com.islamicapp.data.duas.prophetsduas.local

import com.islamicapp.domain.duas.prophetsduas.model.ProphetsDuas

interface ProphetsDuasDataSource {
    suspend fun getProphetsDuas(): List<ProphetsDuas>
}