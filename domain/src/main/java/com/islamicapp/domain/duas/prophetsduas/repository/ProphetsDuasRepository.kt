package com.islamicapp.domain.duas.prophetsduas.repository

import com.islamicapp.domain.duas.prophetsduas.model.ProphetsDuas

interface ProphetsDuasRepository {
    suspend fun getProphetsDuas(): List<ProphetsDuas>
}