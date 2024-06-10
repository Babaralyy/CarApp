package com.cobra.connect.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cobra.connect.R
import com.cobra.connect.databinding.ConfirmDialogLayBinding
import com.cobra.connect.databinding.FragmentMainBinding
import com.cobra.connect.utils.Constants
import com.cobra.connect.utils.Constants.TAG
import java.util.Locale


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
                Log.i(TAG, "exception:: ${e.message}")
            }
        }

        mBinding.ivCarHistory.setOnClickListener {
            try {
                showConfirmDialog("History")

            } catch (e: Exception) {
                Log.i(TAG, "exception:: ${e.message}")
            }
        }

        mBinding.ivTakeCar.setOnClickListener {

            val url =  "https://www.google.com/maps/place/Tel+Aviv"


            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }

            // Check if there's an application that can handle this intent
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                // Handle the case where no application can handle the intent
                // For example, show a toast message
                Toast.makeText(requireContext(), "No application can handle this request.", Toast.LENGTH_SHORT).show()
            }
        }

        mBinding.ivSettings.setOnClickListener {
            showConfirmDialog("Settings")
        }


        val layoutParams = mBinding.tvSpeedNumber.layoutParams as ViewGroup.MarginLayoutParams

        val detectedLanguage = getDeviceLanguage()

        if (detectedLanguage == "iw") {
            layoutParams.marginEnd = 8.dpToPx()
            mBinding.tvSpeedNumber.layoutParams = layoutParams
        } else {
            layoutParams.marginStart = 8.dpToPx()
            mBinding.tvSpeedNumber.layoutParams = layoutParams
        }

    }

    // Extension function to convert dp to px
    private fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
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

        setColorOnText(bottomBinding)

        dialog.window?.decorView?.layoutDirection = View.LAYOUT_DIRECTION_LTR

        dialog.show()
    }

    private fun setColorOnText(bottomBinding: ConfirmDialogLayBinding) {

        val text = getString(R.string.you_have_4_attempts)
        val spannableString = SpannableString(text)

        // Define the color you want to use
        val color = ContextCompat.getColor(
            requireContext(),
            R.color.text_yellow
        )

        val start = text.indexOf("4")
        val end = start + 1

        // Apply the color span
        spannableString.setSpan(
            ForegroundColorSpan(color),
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Apply the bold style span
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            end,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        bottomBinding.tvAttempts.text = spannableString

    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(imageView: ImageView, index: Int, dialog: Dialog, s: String) {
        imageView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    (v as ImageView).setImageResource(pressedImages[index])
                    if (index == 3) {
                        try {
                            performAction(dialog, s)
                        } catch (e: Exception) {
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

    private fun performAction(dialog: Dialog, s: String) {
        dialog.dismiss()
        if (s == "History") {
            Constants.mainClick = Constants.HISTORY
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment())
        } else {
            Constants.mainClick = Constants.SETTINGS
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailsFragment())
        }
    }

    private fun getDeviceLanguage(): String {
        return Locale.getDefault().language
    }
}