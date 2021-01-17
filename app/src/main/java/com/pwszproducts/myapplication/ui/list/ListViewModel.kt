package com.pwszproducts.myapplication.ui.list

import androidx.lifecycle.ViewModel
import com.pwszproducts.myapplication.data.model.ListItem

class ListViewModel: ViewModel() {

    private var adapter: ListAdapter = ListAdapter(generateDummyList())

    fun getAdapter(): ListAdapter {
        return adapter
    }

    fun generateDummyList(): MutableList<ListItem> {
        val list = ArrayList<ListItem>()

        for(i in 1..20) {
            val item = ListItem("Hello $i")
            list += item
        }

        return list
    }
}