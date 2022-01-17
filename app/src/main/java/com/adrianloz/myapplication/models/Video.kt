package com.adrianloz.myapplication.models

class Video (
    var id: Int = 0,
    var results: List<Results>? = null
) : BaseResponse() {
    inner class Dates {
        var maximum: String? = null
        var minimum: String? = null
        override fun toString(): String {
            return "Dates{" +
                    "maximum='" + maximum + '\'' +
                    ", minimum='" + minimum + '\'' +
                    '}'
        }
    }
}