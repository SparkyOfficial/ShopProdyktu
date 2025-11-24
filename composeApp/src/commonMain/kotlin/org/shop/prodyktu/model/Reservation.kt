package org.shop.prodyktu.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Reservation(
    val id: String = "",
    val userId: String = "",
    val productId: String = "",
    val productName: String = "",
    val reservedQuantity: Int = 0,
    val reservationDate: LocalDateTime? = null,
    val pickupDate: LocalDateTime? = null,
    val status: ReservationStatus = ReservationStatus.PENDING
)

@Serializable
enum class ReservationStatus {
    PENDING,
    CONFIRMED,
    READY_FOR_PICKUP,
    PICKED_UP,
    CANCELLED
}