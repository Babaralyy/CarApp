package com.car.carapp.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.car.carapp.R
import com.car.carapp.databinding.ConfirmDialogLayBinding
import com.car.carapp.databinding.FragmentMainBinding
import com.car.carapp.utils.Constants
import com.car.carapp.utils.Constants.TAG


class MainFragment : Fragment() {

    private val originalImages =
        intArrayOf(
            R.drawable.but_1_pressed,
            R.drawable.but_2_pressed,
            R.drawable.but_3_pressed,
            R.drawable.but_4_pressed
        )
    private val pressedImages = intArrayOf(
        R.drawable.but_1_tap,
        R.drawable.but_2_tap,
        R.drawable.but_3_tap,
        R.drawable.but_4_tap
    )


    private lateinit var mBinding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMainBinding.inflate(inflater)

        inIt()

        return mBinding.root
    }

    private fun inIt() {
        mBinding.ivCarLoc.setOnClickListener {
            try {
                Constants.mainClick = Constants.LOCATION
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment())
            } catch (e: Exception) {
                Log.i(Constants.TAG, "exception:: ${e.message}")
            }
        }

        mBinding.ivCarHistory.setOnClickListener {
            try {
                showConfirmDialog("History")

            } catch (e: Exception) {
                Log.i(Constants.TAG, "exception:: ${e.message}")
            }
        }

        mBinding.ivSettings.setOnClickListener {
            showConfirmDialog("Settings")
        }

        mBinding.ivTakeCar.setOnClickListener {
        }

        mBinding.ivCarSpeed.setOnClickListener {

        }
    }

    private fun showConfirmDialog(s: String) {

        val bottomBinding = ConfirmDialogLayBinding.inflate(layoutInflater)

        val dialog = Dialog(requireContext(), R.style.TransparentDialog)
        dialog.setContentView(bottomBinding.root)
        dialog.setCancelable(false)

        val window = dialog.window
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window?.attributes)
        layoutParams.width = (requireContext().resources.displayMetrics.widthPixels * 0.9).toInt()
        window?.attributes = layoutParams

        bottomBinding.tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        setTouchListener(bottomBinding.imageButton1, 0, dialog, s)
        setTouchListener(bottomBinding.imageButton2, 1, dialog, s)
        setTouchListener(bottomBinding.imageButton3, 2, dialog, s)
        setTouchListener(bottomBinding.imageButton4, 3, dialog, s)

        dialog.show()
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(imageView: ImageView, index: Int, dialog: Dialog, s: String) {
        imageView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    (v as ImageView).setImageResource(pressedImages[index])
                    if (index == 3){
                        try {
                            performAction(index, dialog, s)
                        }catch (e: Exception){
                            Log.i(TAG, "setTouchListener: ${e.message}")
                        }

                    }
                    true
                }

                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    (v as ImageView).setImageResource(originalImages[index])

                    true
                }

                else -> false
            }
        }
    }

    private fun performAction(index: Int, dialog: Dialog, s: String) {
        dialog.dismiss()
        if (s == "History") {
            Constants.mainClick = Constants.HISTORY
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment())
        } else {
            Constants.mainClick = Constants.SETTINGS
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment())
        }
    }
}