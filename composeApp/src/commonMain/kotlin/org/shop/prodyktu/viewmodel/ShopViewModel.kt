package org.shop.prodyktu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.shop.prodyktu.model.CartItem
import org.shop.prodyktu.model.Order
import org.shop.prodyktu.model.Product
import org.shop.prodyktu.model.Reservation
import org.shop.prodyktu.model.User
import org.shop.prodyktu.repository.CartRepository
import org.shop.prodyktu.repository.OrderRepository
import org.shop.prodyktu.repository.ProductRepository
import org.shop.prodyktu.repository.ReservationRepository

class ShopViewModel : ViewModel() {
    private val productRepository = ProductRepository()
    private val cartRepository = CartRepository()
    private val orderRepository = OrderRepository()
    private val reservationRepository = ReservationRepository()
    
    // User state
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()
    
    // Products state
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()
    
    // Cart state
    val cartItems = cartRepository.cartItems
    val cartTotal = cartRepository.totalAmount
    val cartItemCount = cartRepository.getCartItemCount()
    
    // Orders state
    private val _userOrders = MutableStateFlow<List<Order>>(emptyList())
    val userOrders = _userOrders.asStateFlow()
    
    // Reservations state
    private val _userReservations = MutableStateFlow<List<Reservation>>(emptyList())
    val userReservations = _userReservations.asStateFlow()
    
    init {
        loadProducts()
    }
    
    fun setCurrentUser(user: User) {
        _currentUser.value = user
        loadUserOrders()
        loadUserReservations()
    }
    
    private fun loadProducts() {
        viewModelScope.launch {
            _products.value = productRepository.getAllProducts()
        }
    }
    
    fun searchProducts(query: String) {
        viewModelScope.launch {
            _products.value = productRepository.searchProducts(query)
        }
    }
    
    fun filterProductsByCategory(category: String) {
        viewModelScope.launch {
            _products.value = productRepository.getProductsByCategory(category)
        }
    }
    
    fun addToCart(product: Product, quantity: Int = 1) {
        cartRepository.addToCart(product, quantity)
    }
    
    fun removeFromCart(productId: String) {
        cartRepository.removeFromCart(productId)
    }
    
    fun updateCartQuantity(productId: String, quantity: Int) {
        cartRepository.updateQuantity(productId, quantity)
    }
    
    fun clearCart() {
        cartRepository.clearCart()
    }
    
    fun placeOrder(): Order? {
        val user = _currentUser.value ?: return null
        val items = cartRepository.cartItems.value
        
        if (items.isEmpty()) return null
        
        val order = orderRepository.createOrder(user.id, items)
        clearCart()
        loadUserOrders()
        return order
    }
    
    private fun loadUserOrders() {
        viewModelScope.launch {
            val user = _currentUser.value ?: return@launch
            _userOrders.value = orderRepository.getUserOrders(user.id)
        }
    }
    
    fun createReservation(productId: String, quantity: Int): Reservation? {
        val user = _currentUser.value ?: return null
        val product = productRepository.getProductById(productId) ?: return null
        
        val reservation = reservationRepository.createReservation(
            userId = user.id,
            productId = productId,
            productName = product.name,
            quantity = quantity
        )
        
        loadUserReservations()
        return reservation
    }
    
    private fun loadUserReservations() {
        viewModelScope.launch {
            val user = _currentUser.value ?: return@launch
            _userReservations.value = reservationRepository.getUserReservations(user.id)
        }
    }
    
    fun getCategories(): List<String> {
        return _products.value.map { it.category }.distinct()
    }
}