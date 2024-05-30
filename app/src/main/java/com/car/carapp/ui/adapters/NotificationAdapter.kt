package com.car.carapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.car.carapp.databinding.NotificationItemBinding

class NotificationAdapter(
    private val notList: MutableList<String>,
    var context: Context,

    ) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NotificationItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = notList[position]
    }

    override fun getItemCount(): Int {
        return notList.size
    }

    class ViewHolder(val mBinding: NotificationItemBinding) : RecyclerView.ViewHolder(mBinding.root)
}