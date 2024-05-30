package com.car.carapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.car.carapp.callback.ReportCallback
import com.car.carapp.databinding.FragmentReportDetailBinding
import com.car.carapp.datamodels.ReportDetailData
import com.car.carapp.ui.adapters.ReportDetailAdapter
import com.car.carapp.utils.Constants

class ReportDetailFragment : Fragment(), ReportCallback {

    private lateinit var reportDetailAdapter: ReportDetailAdapter
    private lateinit var  reportDetailsList: MutableList<ReportDetailData>

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
        reportDetailsList = arrayListOf()
        mBinding.rvReportDetails.layoutManager = LinearLayoutManager(requireContext())
        setUpAdapter()

        mBinding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpAdapter() {
        reportDetailsList.clear()

        reportDetailsList.add(ReportDetailData("", false))
        reportDetailsList.add(ReportDetailData("", false))
        reportDetailsList.add(ReportDetailData("", true))
        reportDetailsList.add(ReportDetailData("", true))
        reportDetailsList.add(ReportDetailData("", false))
        reportDetailsList.add(ReportDetailData("", false))

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