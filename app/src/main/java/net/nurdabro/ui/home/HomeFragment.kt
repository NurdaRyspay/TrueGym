package net.nurdabro.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import net.nurdabro.R
import net.nurdabro.data.UserPreferences
import net.nurdabro.data.network.Resource
import net.nurdabro.databinding.FragmentHomeBinding
import net.nurdabro.ui.handleApiError
import net.nurdabro.ui.logout
import net.nurdabro.ui.visible
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    @Inject
    lateinit var userPreferences: UserPreferences
    private lateinit var codeScanner: CodeScanner

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var userId: String
    private lateinit var access: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.progressBarContainer.visible(false)

        val scannerView = binding.scannerView
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
                sendQR(it.text)
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }


        setID()
        setUi()
        viewModel.detail.observe(viewLifecycleOwner, Observer {
            binding.progressBarContainer.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    SuccessDialog().show(parentFragmentManager,"")
                    lifecycleScope.launch {
                    }
                }
                is Resource.Failure ->  handleApiError(it)
            }
        })



        binding.buttonLogout.setOnClickListener {
            logout()
        }
        binding.buttonHistory.setOnClickListener {

        }
        binding.buttonChangePassword.setOnClickListener {
            val bundle = bundleOf("access" to access, "id" to userId.toInt())
            view.findNavController().navigate(R.id.action_homeFragment_to_settingsFragment, bundle)
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    fun sendQR(qrString: String) {
        viewModel.sendQr(userId.toInt(), qrString, access)
    }

    fun setUi() = lifecycleScope.launch {
    }

    fun setID() = lifecycleScope.launch {
        userId = userPreferences.userId.first()!!
        access = userPreferences.accessToken.first()!!
    }

    private fun updateUI(userid: String) {

    }
}