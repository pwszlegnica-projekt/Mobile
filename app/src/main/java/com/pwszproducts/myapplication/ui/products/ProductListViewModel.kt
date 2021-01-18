package com.pwszproducts.myapplication.ui.products

import androidx.lifecycle.ViewModel
import com.pwszproducts.myapplication.data.model.ListItem
import com.pwszproducts.myapplication.data.model.ProductItem
import java.util.ArrayList

class ProductListViewModel(): ViewModel() {
    private var adapter: ProductListAdapter = ProductListAdapter(generateEmptyList())

    fun addToAdapter(listItem: ProductItem) {
        adapter.addToList(listItem)
    }

    fun getAdapter(): ProductListAdapter {
        return adapter
    }

    fun generateEmptyList(): MutableList<ProductItem> {
        return ArrayList<ProductItem>()
    }
}