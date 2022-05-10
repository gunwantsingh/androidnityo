package com.bb.vib.ui.home.account.currentAccount.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bb.vib.R

class TransactionHistoryAdapter(
    val context: Context
) :
    RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {

    private var mLastClickTime: Long = 0
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_transaction_history,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.setData(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
//                callback.onItemClicked(list[adapterPosition])
            }
        }

//        fun setData(data: MasterDataResponseModel.Result) {
//            itemView.textBankName.text = data.name
//        }
    }

    interface Callback {
//        fun onItemClicked(bank: MasterDataResponseModel.Result)
    }

}