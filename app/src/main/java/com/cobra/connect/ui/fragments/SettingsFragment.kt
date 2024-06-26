package com.cobra.connect.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.cobra.connect.databinding.FragmentSettingsBinding
import com.cobra.connect.utils.Constants


class SettingsFragment : Fragment() {


    private lateinit var mBinding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSettingsBinding.inflate(inflater)

        inIt()

        return mBinding.root
    }

    private fun inIt() {

        mBinding.materialSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){

            }
        }

        mBinding.contactLay.setOnClickListener {
            try {
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToContactFragment())
            }catch (e: Exception){
                Log.i(Constants.TAG, "onMapReady:: ${e.message}")
            }
        }

        mBinding.privacyLay.setOnClickListener {
            try {
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToPrivacyFragment())
            }catch (e: Exception){
                Log.i(Constants.TAG, "onMapReady:: ${e.message}")
            }
        }

        mBinding.aboutLay.setOnClickListener {
            try {
                findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToAboutFragment())
            }catch (e: Exception){
                Log.i(Constants.TAG, "onMapReady:: ${e.message}")
            }
        }

    }
}