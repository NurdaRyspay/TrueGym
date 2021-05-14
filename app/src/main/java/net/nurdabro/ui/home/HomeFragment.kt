package net.nurdabro.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import net.nurdabro.R
import net.nurdabro.data.UserPreferences
import net.nurdabro.data.network.Resource
import net.nurdabro.databinding.FragmentHomeBinding
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
        binding.progressbar.visible(false)

        val scannerView = binding.scannerView
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {
               sendQR(it.text)
                Toast.makeText(requireContext(), it.text, Toast.LENGTH_SHORT).show()
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }


        setID()
        setUi()
        viewModel.detail.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        Toast.makeText(requireContext(), "Дабро пожаловать", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                is Resource.Failure -> Toast.makeText(requireContext(), "Oh no Error", Toast.LENGTH_SHORT).show()
            }
        })



        binding.buttonLogout.setOnClickListener {
            logout()
        }
        binding.buttonSendQr.setOnClickListener {

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

    fun sendQR(qrString: String){
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