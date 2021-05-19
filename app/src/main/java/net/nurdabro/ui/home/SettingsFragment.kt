package net.nurdabro.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.nurdabro.R
import net.nurdabro.data.UserPreferences
import net.nurdabro.data.network.Resource
import net.nurdabro.databinding.FragmentSettingsBinding
import net.nurdabro.ui.handleApiError
import net.nurdabro.ui.visible
import javax.inject.Inject

private const val TAG = "SettingsFragment"

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var userPreferences: UserPreferences
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var access: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        access = requireArguments().getString("access")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        binding.progressBarContainer.visible(false)
        binding.buttonChangePassword.setOnClickListener {
            changePassword()
        }
        viewModel.changed.observe(viewLifecycleOwner, Observer {
            binding.progressBarContainer.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        Toast.makeText(requireContext(), "${it.value.changed}", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it) { changePassword() }
                    requireActivity().onBackPressed()
                }
            }
        })
    }

    fun changePassword() {
        val password = binding.etNewPassword.text.toString().trim()
        val password2 = binding.etRepetNewPassword.text.toString().trim()
        val old_password = binding.etOldPassword.text.toString().trim()
        Log.i(TAG, "changePassword: ${password}   ${password2}   ${old_password}")
        viewModel.changePassword(password, password2, old_password, access)
    }


}