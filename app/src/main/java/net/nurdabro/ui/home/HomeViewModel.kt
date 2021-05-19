package net.nurdabro.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.nurdabro.data.network.Resource
import net.nurdabro.data.network.UserApi
import net.nurdabro.data.repository.UserRepository
import net.nurdabro.data.responses.LoginResponse
import net.nurdabro.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel(repository) {

    private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val user: LiveData<Resource<LoginResponse>>
        get() = _user

    private val _detail: MutableLiveData<Resource<UserApi.SendQrResponse>> = MutableLiveData()
    val detail: LiveData<Resource<UserApi.SendQrResponse>>
        get() = _detail

    private val _changed: MutableLiveData<Resource<UserApi.ChangePasswordResponse>> = MutableLiveData()
    val changed: LiveData<Resource<UserApi.ChangePasswordResponse>>
        get() = _changed

    fun getUser() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser()
    }

    fun sendQr(
        user_id: Int,
        qr_string: String,
        access: String
    ) = viewModelScope.launch {
        _detail.value = Resource.Loading
        _detail.value = repository.senQr(user_id, qr_string,access)
    }

    fun changePassword(
        password: String,
        password2: String,
        old_password: String,
        access: String
    ) = viewModelScope.launch {
        _changed.value = Resource.Loading
        _changed.value = repository.changePassword(password, password2, old_password, access)
    }


}