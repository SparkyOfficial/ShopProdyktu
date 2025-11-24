package org.shop.prodyktu.repository

import org.shop.prodyktu.model.CartItem
import org.shop.prodyktu.model.Order
import org.shop.prodyktu.model.OrderStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class OrderRepository {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders.asStateFlow()
    
    fun createOrder(userId: String, cartItems: List<CartItem>): Order {
        val totalAmount = cartItems.sumOf { it.totalPrice }
        val order = Order(
            id = generateOrderId(),
            userId = userId,
            items = cartItems,
            totalAmount = totalAmount,
            status = OrderStatus.PENDING,
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
        
        val currentOrders = _orders.value.toMutableList()
        currentOrders.add(order)
        _orders.value = currentOrders
        
        return order
    }
    
    fun getUserOrders(userId: String): List<Order> {
        return _orders.value.filter { it.userId == userId }
    }
    
    fun getOrderById(orderId: String): Order? {
        return _orders.value.find { it.id == orderId }
    }
    
    fun updateOrderStatus(orderId: String, status: OrderStatus) {
        val currentOrders = _orders.value.toMutableList()
        val index = currentOrders.indexOfFirst { it.id == orderId }
        
        if (index != -1) {
            val updatedOrder = currentOrders[index].copy(status = status)
            if (status == OrderStatus.DELIVERED) {
                updatedOrder.deliveredAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            }
            currentOrders[index] = updatedOrder
            _orders.value = currentOrders
        }
    }
    
    private fun generateOrderId(): String {
        return "ORD-${System.currentTimeMillis()}"
    }
}