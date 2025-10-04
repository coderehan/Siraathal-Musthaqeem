package com.islamicapp.data.common.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}
