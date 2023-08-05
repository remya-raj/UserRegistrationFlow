package com.example.dailyrounds.models

data class CountryData(
    val status: String,
    val statusCode: Int,
    val version: String,
    val access: String,
    val data: Map<String, CountryInfo>
)

data class CountryInfo(val country: String, val region: String)