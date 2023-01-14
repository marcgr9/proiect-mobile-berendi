package ro.marc.backend.messaging

import java.lang.RuntimeException

class BusinessException(
    val businessMessage: BusinessMessage
): RuntimeException()

