package net.simplifiedcoding.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource
import net.simplifiedcoding.data.network.UserApi
import net.simplifiedcoding.data.repository.UserRepository
import net.simplifiedcoding.data.responses.LoginResponse
import net.simplifiedcoding.ui.base.BaseViewModel
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


}