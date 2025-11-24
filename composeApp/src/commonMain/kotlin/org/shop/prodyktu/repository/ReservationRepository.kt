package org.shop.prodyktu.repository

import org.shop.prodyktu.model.Reservation
import org.shop.prodyktu.model.ReservationStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ReservationRepository {
    private val _reservations = MutableStateFlow<List<Reservation>>(emptyList())
    val reservations = _reservations.asStateFlow()
    
    fun createReservation(
        userId: String,
        productId: String,
        productName: String,
        quantity: Int
    ): Reservation {
        val reservation = Reservation(
            id = generateReservationId(),
            userId = userId,
            productId = productId,
            productName = productName,
            reservedQuantity = quantity,
            reservationDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            status = ReservationStatus.PENDING
        )
        
        val currentReservations = _reservations.value.toMutableList()
        currentReservations.add(reservation)
        _reservations.value = currentReservations
        
        return reservation
    }
    
    fun getUserReservations(userId: String): List<Reservation> {
        return _reservations.value.filter { it.userId == userId }
    }
    
    fun getReservationById(reservationId: String): Reservation? {
        return _reservations.value.find { it.id == reservationId }
    }
    
    fun updateReservationStatus(reservationId: String, status: ReservationStatus) {
        val currentReservations = _reservations.value.toMutableList()
        val index = currentReservations.indexOfFirst { it.id == reservationId }
        
        if (index != -1) {
            val updatedReservation = currentReservations[index].copy(status = status)
            if (status == ReservationStatus.READY_FOR_PICKUP) {
                updatedReservation.pickupDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            }
            currentReservations[index] = updatedReservation
            _reservations.value = currentReservations
        }
    }
    
    private fun generateReservationId(): String {
        return "RES-${System.currentTimeMillis()}"
    }
}