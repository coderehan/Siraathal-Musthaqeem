package com.islamicapp.data.qibla.mapper

import com.islamicapp.data.common.mapper.BaseMapper
import com.islamicapp.domain.qibla.model.Qibla
import okhttp3.ResponseBody

class QiblaMapper : BaseMapper<ResponseBody, Qibla>() {
    override fun map(input: ResponseBody): Qibla {
        return Qibla(
            imageBytes = input.bytes()
        )
    }
}
