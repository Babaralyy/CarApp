package com.cobra.carapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cobra.carapp.callback.ReportCallback
import com.cobra.carapp.databinding.ReportItemViewBinding
import com.cobra.carapp.databinding.ReportWeekendItemViewBinding
import com.cobra.carapp.datamodels.ReportData

class ReportAdapter(
    private val reportDataList: MutableList<ReportData>,
    var context: Context,
    private var reportCallback: ReportCallback,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_1 = 1
        private const val VIEW_TYPE_2 = 2
    }

    inner class Type1ViewHolder(private val binding: ReportItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReportData) {
            // Bind data to viewsReportItemViewBinding
        }
    }

    inner class Type2ViewHolder(private val binding: ReportWeekendItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReportData) {
            // Bind data to views
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_1 -> {
                val binding = ReportItemViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                Type1ViewHolder(binding)
            }

            VIEW_TYPE_2 -> {
                val binding = ReportWeekendItemViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                Type2ViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_1 -> {
                val viewHolder = holder as Type1ViewHolder
                viewHolder.bind(reportDataList[position])

                holder.itemView.setOnClickListener{
                    reportCallback.onReportClick()
                }

            }

            VIEW_TYPE_2 -> {
                val viewHolder = holder as Type2ViewHolder
                viewHolder.bind(reportDataList[position])
                holder.itemView.setOnClickListener{
                    reportCallback.onReportClick()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return reportDataList.size
    }

    override fun getItemViewType(position: Int): Int {

        return if (reportDataList[position].isWeekend) {
            VIEW_TYPE_2
        } else {
            VIEW_TYPE_1
        }
    }
}
