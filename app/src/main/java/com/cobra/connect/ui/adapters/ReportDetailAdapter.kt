package com.cobra.connect.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobra.connect.callback.ReportCallback
import com.cobra.connect.databinding.ReportDetailItemBinding
import com.cobra.connect.datamodels.ReportDetailData

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