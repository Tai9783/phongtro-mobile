package com.example.apptimphongtro.model.entity

import com.example.apptimphongtro.model.entity.Ward

data class FilterState(
    val city: String="Tp Hồ Chí Minh",
    val wards: List<Ward> = emptyList(),
    val prices: Set<String> = emptySet(),
    val aminities: Set<String> = emptySet()
)