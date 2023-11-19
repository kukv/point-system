package jp.kukv.point.endpoint.point

import kotlinx.serialization.Serializable

@Serializable
data class PointRegisterDummyRequest(
    private val id: Int?,
    private val amount: Int?,
)
