package ro.marc.android.data.api


data class BusinessPayload<T>(

    val payload: T?,

    val message: BusinessMessage,

)