package com.islamicapp.data.common.mapper

abstract class BaseMapper<I, O> : Mapper<I, O> {

    fun apply(item: I): O {
        return map(item)
    }

    fun apply(list: List<I>?): List<O> {
        return list?.map { map(it) }.orEmpty()
    }

}
