package jp.kukv.point.endpoint

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

@Serializable
class ErrorMessageResponse private constructor(private val error: String, private val message: String) {
    constructor(code: HttpStatusCode, message: String) : this("${code.value} ${code.description}", message)
}
