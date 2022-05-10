package com.bb.vib.utils.dialogs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bb.vib.R
import kotlinx.android.synthetic.main.item_view_selection_list.view.*

class ListSelectionAdapter(
    val context: Context,
    private var list: List<String>,
    private var callback: Callback
) :
    RecyclerView.Adapter<ListSelectionAdapter.ViewHolder>() {

    private var mLastClickTime: Long = 0
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_selection_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
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