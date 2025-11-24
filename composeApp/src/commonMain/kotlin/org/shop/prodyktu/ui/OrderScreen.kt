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
import org.shop.prodyktu.model.Order
import org.shop.prodyktu.model.OrderStatus

@Composable
fun OrderScreen(orders: List<Order>) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (orders.isEmpty()) {
            Text(
                text = "No orders yet",
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
                items(orders) { order ->
                    OrderItem(order = order)
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Order #${order.id.takeLast(6)}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Date: ${order.createdAt?.date}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Status: ${order.status.name}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Total: $${String.format("%.2f", order.totalAmount)}",
            style = MaterialTheme.typography.bodyMedium
        )
        
        // Display order items
        order.items.forEach { item ->
            Text(
                text = "${item.quantity} x ${item.product.name}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}