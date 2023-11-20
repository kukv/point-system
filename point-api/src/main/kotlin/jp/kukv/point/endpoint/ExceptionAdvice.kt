package jp.kukv.point.endpoint

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond
import jp.kukv.point.domain.policy.ResourceNotfoundException
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("ExceptionAdvice")

fun StatusPagesConfig.handler() {
    exception<Throwable> { call, cause ->
        when (cause) {
            is JsonConvertException,
            is BadRequestException,
            is IllegalArgumentException,
            is IllegalStateException,
            -> {
                val response = ErrorMessageResponse(HttpStatusCode.BadRequest, cause.message!!)
                logger.warn(cause.message)
                call.respond(HttpStatusCode.BadRequest, response)
            }

            is ResourceNotfoundException -> {
                val response = ErrorMessageResponse(HttpStatusCode.NotFound, cause.message!!)
                logger.warn(cause.message)
                call.respond(HttpStatusCode.NotFound, response)
            }

            else -> {
                val response = ErrorMessageResponse(HttpStatusCode.InternalServerError, cause.message!!)
                logger.error(cause.message, cause)
                call.respond(HttpStatusCode.InternalServerError, response)
            }
        }
    }
}
