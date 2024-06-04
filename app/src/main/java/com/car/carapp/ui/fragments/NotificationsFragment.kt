package com.car.carapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.car.carapp.databinding.FragmentNotificationsBinding
import com.car.carapp.ui.adapters.NotificationAdapter


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
        val emptyItems = List(5) { "" }
        notificationAdapter = NotificationAdapter(emptyItems.toMutableList(), requireContext())
        mBinding.rvNotifications.adapter = notificationAdapter
    }
}