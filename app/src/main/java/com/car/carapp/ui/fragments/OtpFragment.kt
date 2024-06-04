package com.car.carapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.car.carapp.databinding.FragmentOtpBinding
import com.car.carapp.utils.Constants


class OtpFragment : Fragment() {


    private lateinit var mBinding: FragmentOtpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentOtpBinding.inflate(inflater)
        inIt()
        return mBinding.root
    }

    private fun inIt() {
        mBinding.btnConfirm.setOnClickListener {
            try {
                findNavController().navigate(OtpFragmentDirections.actionOtpFragmentToMainFragment())
            }catch (e: Exception){
                Log.i(Constants.TAG, "exception:: ${e.message}")
            }
        }
    }
}