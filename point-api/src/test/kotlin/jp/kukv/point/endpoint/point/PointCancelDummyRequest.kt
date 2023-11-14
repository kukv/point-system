package jp.kukv.point.endpoint.point

import jp.kukv.point.configuration.serializer.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class PointCancelDummyRequest(
    @SerialName("id") private val id: Int?,
    @Serializable(with = UUIDSerializer::class) @SerialName("transaction_number") private val transactionNumber: UUID?,
)
