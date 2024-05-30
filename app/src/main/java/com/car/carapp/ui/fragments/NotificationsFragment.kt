package com.car.carapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.car.carapp.R
import com.car.carapp.databinding.FragmentNotificationsBinding
import com.car.carapp.datamodels.ReportDetailData
import com.car.carapp.ui.adapters.NotificationAdapter
import com.car.carapp.ui.adapters.ReportDetailAdapter


class NotificationsFragment : Fragment() {

    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var notList: MutableList<String>

    private lateinit var mBinding: FragmentNotificationsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentNotificationsBinding.inflate(inflater)

        inIt()

        return mBinding.root
    }

    private fun inIt() {
        notList = arrayListOf()

        mBinding.rvNotifications.layoutManager = LinearLayoutManager(requireContext())

        setUpAdapter()
    }

    private fun setUpAdapter() {
        notList.clear()

        notList.add("")
        notList.add("")
        notList.add("")
        notList.add("")
        notList.add("")

        notificationAdapter = NotificationAdapter(notList, requireContext())
        mBinding.rvNotifications.adapter = notificationAdapter
    }
}