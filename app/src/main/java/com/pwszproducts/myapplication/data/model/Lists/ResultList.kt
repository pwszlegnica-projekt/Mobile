package com.pwszproducts.myapplication.data.model.Lists

data class ResultList (
        var success: Boolean,
        var lists: List<ItemList>? = null
    )