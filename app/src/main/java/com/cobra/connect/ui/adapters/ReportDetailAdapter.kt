package com.cobra.carapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobra.carapp.callback.ReportCallback
import com.cobra.carapp.databinding.ReportDetailItemBinding
import com.cobra.carapp.datamodels.ReportDetailData

class ReportDetailAdapter(
    private val reportDetailsList: MutableList<ReportDetailData>,
    var context: Context,
    private var reportCallback: ReportCallback,

    ) : RecyclerView.Adapter<ReportDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ReportDetailItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val event = reportDetailsList[position]

        holder.itemView.setOnClickListener{
            reportCallback.onReportDetailsClick()
        }
    }

    override fun getItemCount(): Int {
        return reportDetailsList.size
    }

    class ViewHolder(val mBinding: ReportDetailItemBinding) : RecyclerView.ViewHolder(mBinding.root)
}