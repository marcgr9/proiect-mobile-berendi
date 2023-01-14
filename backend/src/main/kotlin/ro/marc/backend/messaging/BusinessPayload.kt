package ro.marc.backend.messaging

data class BusinessPayload<T>(
    val payload: T?,
    val message: BusinessMessage,
) {

    constructor(payload: T?): this(payload, BusinessMessage.OK)

    constructor(message: BusinessMessage): this(null, message)

}
