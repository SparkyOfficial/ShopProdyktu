package org.shop.prodyktu.repository

import org.shop.prodyktu.model.CartItem
import org.shop.prodyktu.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepository {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()
    
    private val _totalAmount = MutableStateFlow(0.0)
    val totalAmount = _totalAmount.asStateFlow()
    
    fun addToCart(product: Product, quantity: Int = 1) {
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = currentItems.find { it.product.id == product.id }
        
        if (existingItem != null) {
            // Update quantity if item already exists
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + quantity)
            val index = currentItems.indexOfFirst { it.product.id == product.id }
            currentItems[index] = updatedItem
        } else {
            // Add new item to cart
            currentItems.add(CartItem(product, quantity))
        }
        
        _cartItems.value = currentItems
        calculateTotal()
    }
    
    fun removeFromCart(productId: String) {
        val currentItems = _cartItems.value.toMutableList()
        val index = currentItems.indexOfFirst { it.product.id == productId }
        
        if (index != -1) {
            currentItems.removeAt(index)
            _cartItems.value = currentItems
            calculateTotal()
        }
    }
    
    fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
            return
        }
        
        val currentItems = _cartItems.value.toMutableList()
        val index = currentItems.indexOfFirst { it.product.id == productId }
        
        if (index != -1) {
            val updatedItem = currentItems[index].copy(quantity = quantity)
            currentItems[index] = updatedItem
            _cartItems.value = currentItems
            calculateTotal()
        }
    }
    
    fun clearCart() {
        _cartItems.value = emptyList()
        _totalAmount.value = 0.0
    }
    
    private fun calculateTotal() {
        _totalAmount.value = _cartItems.value.sumOf { it.totalPrice }
    }
    
    fun getCartItemCount(): Int = _cartItems.value.sumOf { it.quantity }
}