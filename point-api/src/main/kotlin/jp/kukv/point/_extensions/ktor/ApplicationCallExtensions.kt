package jp.kukv.point._extensions.ktor

import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import jp.kukv.point.endpoint.Request

suspend inline fun <reified T : Request<T>> ApplicationCall.receiveAndValidate(): T {
    val request = receive<T>()
    val validator = request.validator
    val violation = validator.validate(request)
    if (!violation.isValid) {
        throw IllegalArgumentException(violation.toString())
    }

    return request
}
