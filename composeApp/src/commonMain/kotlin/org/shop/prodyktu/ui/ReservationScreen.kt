package org.shop.prodyktu.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.shop.prodyktu.model.Reservation

@Composable
fun ReservationScreen(reservations: List<Reservation>) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (reservations.isEmpty()) {
            Text(
                text = "No reservations yet",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(reservations) { reservation ->
                    ReservationItem(reservation = reservation)
                }
            }
        }
    }
}

@Composable
fun ReservationItem(reservation: Reservation) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Reservation #${reservation.id.takeLast(6)}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Product: ${reservation.productName}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Quantity: ${reservation.reservedQuantity}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Reservation Date: ${reservation.reservationDate?.date}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Status: ${reservation.status.name}",
            style = MaterialTheme.typography.bodyMedium
        )
        if (reservation.pickupDate != null) {
            Text(
                text = "Pickup Date: ${reservation.pickupDate?.date}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}