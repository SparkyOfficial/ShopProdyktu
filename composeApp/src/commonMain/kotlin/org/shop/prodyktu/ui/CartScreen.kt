package org.shop.prodyktu.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.shop.prodyktu.model.CartItem
import org.shop.prodyktu.viewmodel.ShopViewModel

@Composable
fun CartScreen(
    viewModel: ShopViewModel,
    onPlaceOrder: () -> Unit,
    cartItems: List<CartItem>,
    totalAmount: Double
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (cartItems.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your cart is empty",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Add some products to your cart",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(cartItems) { item ->
                    CartItem(
                        item = item,
                        onUpdateQuantity = { quantity ->
                            viewModel.updateCartQuantity(item.product.id, quantity)
                        },
                        onRemove = {
                            viewModel.removeFromCart(item.product.id)
                        }
                    )
                }
            }
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Total: $${String.format("%.2f", totalAmount)}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                
                Button(
                    onClick = onPlaceOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Place Order")
                }
                
                Button(
                    onClick = { viewModel.clearCart() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    enabled = cartItems.isNotEmpty()
                ) {
                    Text("Clear Cart")
                }
            }
        }
    }
}