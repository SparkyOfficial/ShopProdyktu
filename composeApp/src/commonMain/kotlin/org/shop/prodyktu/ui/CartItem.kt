package org.shop.prodyktu.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.shop.prodyktu.model.CartItem

@Composable
fun CartItem(
    item: CartItem,
    onUpdateQuantity: (Int) -> Unit,
    onRemove: () -> Unit
) {
    var quantity by remember { mutableStateOf(item.quantity) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.product.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$${String.format("%.2f", item.product.price)} each",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextButton(onClick = {
                if (quantity > 1) {
                    quantity--
                    onUpdateQuantity(quantity)
                }
            }) {
                Text("-")
            }
            
            Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
            
            TextButton(onClick = {
                quantity++
                onUpdateQuantity(quantity)
            }) {
                Text("+")
            }
            
            Text(
                text = "= $${String.format("%.2f", item.totalPrice)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
            
            TextButton(onClick = onRemove) {
                Text("Remove")
            }
        }
    }
}