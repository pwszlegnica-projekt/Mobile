package com.pwszproducts.myapplication.ui.list

import androidx.lifecycle.ViewModel
import com.pwszproducts.myapplication.data.model.ListItem
import java.util.ArrayList

class ListViewModel(): ViewModel() {
    private var adapter: ListAdapter = ListAdapter(generateEmptyList())

    fun addToAdapter(listItem: ListItem) {
        adapter.addToList(listItem)
    }

    fun getAdapter(): ListAdapter {
        return adapter
    }

    fun generateEmptyList(): MutableList<ListItem> {
        return ArrayList<ListItem>()
    }
}