package ro.marc.android.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ro.marc.android.data.api.BusinessPayload
import ro.marc.android.data.api.CallManagement
import ro.marc.android.data.api.CallStatus
import ro.marc.android.data.api.dto.EntityDTO
import ro.marc.android.data.service.EntitiesService

class EntitiesRepo(
    private val entitiesService: EntitiesService,
) {
    
    fun getAll(): LiveData<CallStatus<List<EntityDTO>>> {
        val callStatus = MutableLiveData<CallStatus<List<EntityDTO>>>()
        callStatus.value = CallStatus.Loading()

        entitiesService.fetch().enqueue(object : Callback<BusinessPayload<List<EntityDTO>>> {
            override fun onResponse(
                call: Call<BusinessPayload<List<EntityDTO>>>,
                response: Response<BusinessPayload<List<EntityDTO>>>
            ) {
                CallManagement.manageOnResponse(response, callStatus)
            }

            override fun onFailure(
                call: Call<BusinessPayload<List<EntityDTO>>>,
                t: Throwable
            ) {
                CallManagement.manageOnFailure(t, callStatus)
            }

        })

        return callStatus
    }
    
    fun add(dto: EntityDTO): LiveData<CallStatus<EntityDTO>> {
        val callStatus = MutableLiveData<CallStatus<EntityDTO>>()
        callStatus.value = CallStatus.Loading()

        entitiesService.post(dto).enqueue(object : Callback<BusinessPayload<EntityDTO>> {
            override fun onResponse(
                call: Call<BusinessPayload<EntityDTO>>,
                response: Response<BusinessPayload<EntityDTO>>
            ) {
                CallManagement.manageOnResponse(response, callStatus)
            }

            override fun onFailure(
                call: Call<BusinessPayload<EntityDTO>>,
                t: Throwable
            ) {
                CallManagement.manageOnFailure(t, callStatus)
            }

        })

        return callStatus
    }

    fun update(dto: EntityDTO, id: Long): LiveData<CallStatus<EntityDTO>> {
        val callStatus = MutableLiveData<CallStatus<EntityDTO>>()
        callStatus.value = CallStatus.Loading()

        entitiesService.patch(dto, id).enqueue(object : Callback<BusinessPayload<EntityDTO>> {
            override fun onResponse(
                call: Call<BusinessPayload<EntityDTO>>,
                response: Response<BusinessPayload<EntityDTO>>
            ) {
                CallManagement.manageOnResponse(response, callStatus)
            }

            override fun onFailure(
                call: Call<BusinessPayload<EntityDTO>>,
                t: Throwable
            ) {
                CallManagement.manageOnFailure(t, callStatus)
            }

        })

        return callStatus
    }
    
}