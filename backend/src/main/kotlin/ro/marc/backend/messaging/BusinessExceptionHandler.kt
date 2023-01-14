package ro.marc.backend.messaging

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class BusinessExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(BusinessException::class)
    fun <T> handleException(ex: BusinessException): ResponseEntity<BusinessPayload<T>?> {
        return ResponseEntity(BusinessPayload(ex.businessMessage), HttpStatus.OK)
    }

}
