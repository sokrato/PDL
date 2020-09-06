package com.xux.elib.helper

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

interface JSONHelper {
    fun dump(obj: Any?): String

    fun parseMap(json: String): Map<String, String>
}

object MapTypeRef : TypeReference<Map<String, String>>() {
    //
}

class JSONHelperImpl(
        private val objectMapper: ObjectMapper
) : JSONHelper {
    override fun dump(obj: Any?): String {
        return objectMapper.writeValueAsString(obj)
    }

    override fun parseMap(json: String): Map<String, String> {
        return objectMapper.readValue(json, MapTypeRef)
    }
}
