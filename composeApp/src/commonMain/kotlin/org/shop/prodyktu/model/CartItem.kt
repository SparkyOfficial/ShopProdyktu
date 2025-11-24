package org.shop.prodyktu.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val product: Product,
    var quantity: Int
) {
    val totalPrice: Double
        get() = product.price * quantity
}