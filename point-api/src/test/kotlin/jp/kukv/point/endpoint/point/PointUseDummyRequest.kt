package jp.kukv.point.endpoint.point

import kotlinx.serialization.Serializable

@Serializable
data class PointUseDummyRequest(
    private val id: Int?,
    private val point: Int?,
)
