package com.polstat.digitalarchive.ui

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.polstat.digitalarchive.R

class StisToast {
    companion object {
        fun showSuccess(context: Context, message: String) {
            show(context, message, ToastType.SUCCESS)
        }

        fun showError(context: Context, message: String) {
            show(context, message, ToastType.ERROR)
        }

        fun showInfo(context: Context, message: String) {
            show(context, message, ToastType.INFO)
        }

        private fun show(context: Context, message: String, type: ToastType) {
            val inflater = LayoutInflater.from(context)
            val layout = inflater.inflate(R.layout.toast_layout, null)

            val container = layout.findViewById<LinearLayout>(R.id.toastContainer)
            val icon = layout.findViewById<ImageView>(R.id.toastIcon)
            val textView = layout.findViewById<TextView>(R.id.toastMessage)

            // Set background color and icon based on type
            when (type) {
                ToastType.SUCCESS -> {
                    container.setBackgroundColor(ContextCompat.getColor(context, R.color.stis_blue))
                    icon.setImageResource(R.drawable.ic_success)
                }
                ToastType.ERROR -> {
                    container.setBackgroundColor(ContextCompat.getColor(context, R.color.error_color))
                    icon.setImageResource(R.drawable.ic_error)
                }
                ToastType.INFO -> {
                    container.setBackgroundColor(ContextCompat.getColor(context, R.color.stis_blue_light))
                    icon.setImageResource(R.drawable.ic_info)
                }
            }

            textView.text = message

            val toast = Toast(context)
            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = layout
            toast.show()
        }

        private enum class ToastType {
            SUCCESS, ERROR, INFO
        }
    }
}