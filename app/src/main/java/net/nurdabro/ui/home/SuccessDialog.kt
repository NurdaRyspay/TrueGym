package net.nurdabro.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import net.nurdabro.R

class SuccessDialog: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.rounded_corner)
        return inflater.inflate(R.layout.success_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
    }

}