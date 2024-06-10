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
import com.cobra.carapp.databinding.FragmentReportDetailBinding
import com.cobra.carapp.datamodels.ReportDetailData
import com.cobra.carapp.ui.adapters.ReportDetailAdapter
import com.cobra.carapp.utils.Constants

class ReportDetailFragment : Fragment(), ReportCallback {

    private lateinit var reportDetailAdapter: ReportDetailAdapter

    private lateinit var mBinding: FragmentReportDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentReportDetailBinding.inflate(inflater)

        inIt()

        return mBinding.root
    }

    private fun inIt() {
        mBinding.rvReportDetails.layoutManager = LinearLayoutManager(requireContext())
        setUpAdapter()

        mBinding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpAdapter() {
        val reportDetailsList = mutableListOf(
            ReportDetailData("", false),
            ReportDetailData("", false),
            ReportDetailData("", true),
            ReportDetailData("", true),
            ReportDetailData("", false),
            ReportDetailData("", false)
        )

        reportDetailAdapter = ReportDetailAdapter(reportDetailsList, requireContext(), this)
        mBinding.rvReportDetails.adapter = reportDetailAdapter
    }

    override fun onReportDetailsClick() {
        try {
            findNavController().navigate(ReportDetailFragmentDirections.actionReportDetailFragmentToReportMapFragment())
        }catch (e: Exception){
            Log.i(Constants.TAG, "onMapReady:: ${e.message}")
        }
    }

}