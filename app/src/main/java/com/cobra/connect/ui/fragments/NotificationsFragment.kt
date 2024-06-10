package com.cobra.connect.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobra.connect.callback.NotificationCallback
import com.cobra.connect.databinding.FragmentNotificationsBinding
import com.cobra.connect.ui.adapters.NotificationAdapter
import com.cobra.connect.utils.Constants.TAG


class NotificationsFragment : Fragment(), NotificationCallback {

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

    override fun onNotificationClick() {
        try {
            findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToNotificationDetailFragment())
        }catch (e: Exception){
            Log.i(TAG, "onNotificationClick:: ${e.message}")
        }
    }

    private fun setUpAdapter() {
        val emptyItems = List(5) { "" }
        notificationAdapter = NotificationAdapter(emptyItems.toMutableList(), requireContext(), this)
        mBinding.rvNotifications.adapter = notificationAdapter
    }


}