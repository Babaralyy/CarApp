package com.cobra.carapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.cobra.carapp.databinding.FragmentRegisterBinding
import com.cobra.carapp.utils.Constants.TAG


class RegisterFragment : Fragment() {


    private lateinit var mBinding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRegisterBinding.inflate(inflater)

        inIt()

        return mBinding.root
    }

    private fun inIt() {
        mBinding.btnRegister.setOnClickListener {
            try {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToOtpFragment())
            }catch (e: Exception){
                Log.i(TAG, "exception:: ${e.message}")
            }
        }
    }
}