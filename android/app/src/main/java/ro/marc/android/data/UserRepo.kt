package ro.marc.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ro.marc.android.data.api.BusinessPayload
import ro.marc.android.data.api.CallManagement
import ro.marc.android.data.api.CallStatus
import ro.marc.android.data.api.dto.LoginCredentials
import ro.marc.android.data.api.dto.TokenDTO

class UserRepo(
    private val userService: UserService
) {

    fun me(): LiveData<CallStatus<Void>> {
        val callStatus = MutableLiveData<CallStatus<Void>>()
        callStatus.value = CallStatus.Loading()

        userService.me().enqueue(object : Callback<BusinessPayload<Void>> {
            override fun onResponse(
                call: Call<BusinessPayload<Void>>,
                response: Response<BusinessPayload<Void>>
            ) {
                CallManagement.manageOnResponse(response, callStatus)
            }

            override fun onFailure(
                call: Call<BusinessPayload<Void>>,
                t: Throwable
            ) {
                CallManagement.manageOnFailure(t, callStatus)
            }

        })

        return callStatus
    }

    fun login(username: String, password: String): LiveData<CallStatus<TokenDTO>> {
        val callStatus = MutableLiveData<CallStatus<TokenDTO>>()
        callStatus.value = CallStatus.Loading()

        userService.login(LoginCredentials(username, password)).enqueue(object : Callback<BusinessPayload<TokenDTO>> {
            override fun onResponse(
                call: Call<BusinessPayload<TokenDTO>>,
                response: Response<BusinessPayload<TokenDTO>>
            ) {
                CallManagement.manageOnResponse(response, callStatus)
            }

            override fun onFailure(
                call: Call<BusinessPayload<TokenDTO>>,
                t: Throwable
            ) {
                CallManagement.manageOnFailure(t, callStatus)
            }

        })

        return callStatus
    }

}