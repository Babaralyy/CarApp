package com.cobra.connect.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.cobra.connect.R
import com.cobra.connect.databinding.FragmentDetailsBinding
import com.cobra.connect.utils.Constants


class DetailsFragment : Fragment() {

    private lateinit var mBinding: FragmentDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDetailsBinding.inflate(inflater)

        inIt()

        return mBinding.root
    }

    private fun inIt() {
        setUpBottomNav()

        when (Constants.mainClick) {
            Constants.LOCATION -> {
                replaceFragment(MapFragment())
                updateTab(mBinding.tabMap, R.drawable.tab_map_selected)
            }

            Constants.HISTORY -> {
                replaceFragment(ReportFragment())
                updateTab(mBinding.tabReport, R.drawable.tab_report_selected)
            }

            Constants.TAKE -> {

            }

            Constants.SPEED -> {

            }

            Constants.SETTINGS -> {
                replaceFragment(SettingsFragment())
                updateTab(mBinding.tabSettings, R.drawable.menu_settings_selected)
            }
            Constants.NOTIFICATION -> {
                replaceFragment(NotificationsFragment())
                updateTab(mBinding.tabAlert, R.drawable.tab_alert_selected)
            }
        }

    }

    private fun resetTabs() {
        mBinding.tabMain.setImageResource(R.drawable.tab_main)
        mBinding.tabMap.setImageResource(R.drawable.tab_map)
        mBinding.tabReport.setImageResource(R.drawable.tab_report)
        mBinding.tabAlert.setImageResource(R.drawable.tab_alert)
        mBinding.tabSettings.setImageResource(R.drawable.tab_settings)
    }

    private fun updateTab(tab: ImageView, selectedImage: Int) {
        resetTabs()
        tab.setImageResource(selectedImage)
    }

    private fun setUpBottomNav() {
        mBinding.tabMain.setOnClickListener {
            updateTab(mBinding.tabMain, R.drawable.tab_main_selected)
            findNavController().popBackStack()
        }
        mBinding.tabMap.setOnClickListener {
            replaceFragment(MapFragment())
            updateTab(mBinding.tabMap, R.drawable.tab_map_selected)

        }
        mBinding.tabReport.setOnClickListener {
            Constants.mainClick = Constants.HISTORY
            replaceFragment(ReportFragment())
            updateTab(mBinding.tabReport, R.drawable.tab_report_selected)
        }
        mBinding.tabAlert.setOnClickListener {
            Constants.mainClick = Constants.NOTIFICATION
            replaceFragment(NotificationsFragment())
            updateTab(mBinding.tabAlert, R.drawable.tab_alert_selected)
        }
        mBinding.tabSettings.setOnClickListener {
            Constants.mainClick = Constants.SETTINGS
            replaceFragment(SettingsFragment())
            updateTab(mBinding.tabSettings, R.drawable.menu_settings_selected)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment)
            .commit()
    }

}