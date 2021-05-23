package net.nurdabro.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import net.nurdabro.data.UserPreferences
import net.nurdabro.data.network.Resource
import net.nurdabro.data.repository.ChangePasswordResponse
import net.nurdabro.data.repository.SendQrResponse
import net.nurdabro.data.repository.UserRepository
import net.nurdabro.data.responses.LoginResponse
import net.nurdabro.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository

) : BaseViewModel(repository) {

    @Inject
    lateinit var userPreferences: UserPreferences

    private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val user: LiveData<Resource<LoginResponse>>
        get() = _user

    private val _detail: MutableLiveData<Resource<SendQrResponse>> = MutableLiveData()
    val detail: LiveData<Resource<SendQrResponse>>
        get() = _detail

    private val _changed: MutableLiveData<Resource<ChangePasswordResponse>> = MutableLiveData()
    val changed: LiveData<Resource<ChangePasswordResponse>>
        get() = _changed

    fun getUser() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser()
    }

    fun sendQr(
        qr_string: String
    ) = viewModelScope.launch {
        val userId = userPreferences.userId.first()?.toInt()
        val access = userPreferences.accessToken.first()
        _detail.value = Resource.Loading
        _detail.value = userId?.let { access?.let { it1 -> repository.senQr(it, qr_string, it1) } }
    }

    fun changePassword(
        password: String,
        password2: String,
        old_password: String,
    ) = viewModelScope.launch {
        val userId = userPreferences.userId.first()?.toInt()
        val access = userPreferences.accessToken.first()
        _changed.value = Resource.Loading
        _changed.value =
            userId?.let {
                access?.let { it1 ->
                    repository.changePassword(
                        password, password2, old_password,
                        it1, it
                    )
                }
            }
    }


}