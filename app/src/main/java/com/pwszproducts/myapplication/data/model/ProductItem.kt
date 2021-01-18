package com.pwszproducts.myapplication.data.model

data class ProductItem(
    val id: Int,
    var name: String,
    var entity: String,
    var quantity: Int,
    var checked: Int
)