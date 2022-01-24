package com.technowave.ilabanktest.models

data class GetDataResponse(
    val msg: String,
    val products: List<Product>,
    val status: Boolean
) {
    data class Product(
        val description: String,
        val imageUrl: String,
        val price: Int,
        val title: String
    )
}