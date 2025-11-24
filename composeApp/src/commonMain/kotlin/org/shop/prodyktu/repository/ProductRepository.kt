package org.shop.prodyktu.repository

import org.shop.prodyktu.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductRepository {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: Flow<List<Product>> = _products.asStateFlow()
    
    // Sample data for demonstration
    init {
        _products.value = listOf(
            Product(
                id = "1",
                name = "Fresh Milk",
                description = "Fresh cow milk from local farm",
                price = 2.50,
                category = "Dairy",
                isAvailable = true,
                stockQuantity = 20
            ),
            Product(
                id = "2",
                name = "Bread",
                description = "Freshly baked bread",
                price = 1.20,
                category = "Bakery",
                isAvailable = true,
                stockQuantity = 15
            ),
            Product(
                id = "3",
                name = "Eggs",
                description = "Free-range chicken eggs",
                price = 3.00,
                category = "Dairy",
                isAvailable = true,
                stockQuantity = 30
            ),
            Product(
                id = "4",
                name = "Apples",
                description = "Fresh local apples",
                price = 2.00,
                category = "Fruits",
                isAvailable = true,
                stockQuantity = 25
            ),
            Product(
                id = "5",
                name = "Potatoes",
                description = "Organic potatoes",
                price = 1.50,
                category = "Vegetables",
                isAvailable = true,
                stockQuantity = 40
            )
        )
    }
    
    fun getAllProducts(): List<Product> = _products.value
    
    fun getProductById(id: String): Product? = _products.value.find { it.id == id }
    
    fun getProductsByCategory(category: String): List<Product> = 
        _products.value.filter { it.category == category }
    
    fun searchProducts(query: String): List<Product> = 
        _products.value.filter { 
            it.name.contains(query, ignoreCase = true) || 
            it.description.contains(query, ignoreCase = true) 
        }
}