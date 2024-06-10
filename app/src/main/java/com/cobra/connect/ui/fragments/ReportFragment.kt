package com.cobra.carapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobra.carapp.callback.ReportCallback
import com.cobra.carapp.databinding.FragmentReportBinding
import com.cobra.carapp.datamodels.ReportData
import com.cobra.carapp.ui.adapters.ReportAdapter
import com.cobra.carapp.utils.Constants


class ReportFragment : Fragment(), ReportCallback {

    private lateinit var reportAdapter: ReportAdapter


    private lateinit var mBinding: FragmentReportBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentReportBinding.inflate(inflater)

        inIt()

        return mBinding.root
    }

    private fun inIt() {
        mBinding.rvReport.layoutManager = LinearLayoutManager(requireContext())
        setUpAdapter()
    }

    private fun setUpAdapter() {
        val reportList = mutableListOf(
            ReportData("", false),
            ReportData("", false),
            ReportData("", true),
            ReportData("", true),
            ReportData("", false),
            ReportData("", false)
        )

        reportAdapter = ReportAdapter(reportList, requireContext(), this)
        mBinding.rvReport.adapter = reportAdapter
    }

    override fun onReportClick() {
        try {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToReportDetailFragment())
        } catch (e: Exception) {
            Log.i(Constants.TAG, "exception:: ${e.message}")
        }
    }


}