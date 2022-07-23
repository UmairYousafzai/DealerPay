package com.moveitech.dealerpay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.moveitech.dealerpay.dataModel.response.transaction.TransactionResponse
import com.moveitech.dealerpay.databinding.HomeCardsBinding
import java.util.*
import kotlin.collections.ArrayList

class TransactionAdapter(var list:ArrayList<TransactionResponse> = ArrayList()):BaseAdapter<HomeCardsBinding>(list),Filterable {

    var tempList:ArrayList<TransactionResponse> = ArrayList()
    override fun bind(binding: HomeCardsBinding, item: Any, position: Int) {
        val model= dataList[position] as TransactionResponse
        binding.model= model
        binding.executePendingBindings()

    }

    override fun setList(list: List<Any>) {
        tempList.clear()
        this.list.clear()
        dataList=tempList
        this.list.addAll(list as ArrayList<TransactionResponse>)
        tempList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    )= HomeCardsBinding.inflate(layoutInflater,container,false)

    override fun getFilter(): Filter {

        return customFilter
    }

    val customFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            var filterList: ArrayList<TransactionResponse> = ArrayList()
            if (list.isNotEmpty()) {
                if (constraint.isEmpty()) {
                    filterList = list
                } else {
                    val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                    for (model in list) {
                        if (model.customer.firstName.lowercase(Locale.getDefault()).trim()
                                .contains(filterPattern)
                        ) {
                            filterList.add(model)
                        }
                    }
                }
            }
            val filterResults = FilterResults()
            if (filterList.size > 0) {
                filterResults.values = filterList
            }
            return filterResults
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            if (results.values != null) {
                tempList.clear()
                tempList.addAll(results.values as Collection<TransactionResponse>)

                notifyDataSetChanged()
            }
        }
    }
}