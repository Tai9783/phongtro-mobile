package com.example.apptimphongtro.feature.search.viewmodel

import com.example.apptimphongtro.core.model.Ward

data class FilterState(
    val city: String="Tp Hồ Chí Minh",
    val wards: List<Ward> = emptyList(),
    val prices: Set<String> = emptySet(),
    val aminities: Set<String> = emptySet()
)