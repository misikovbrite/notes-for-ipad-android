package com.britetodo.notesforandroid.ui.screens.paywall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Paywall screen (Vinyl-style, port of iOS VinylStylePaywallViewController).
 * DISABLED for R1: showPaywallOnLaunch=false via Firebase Remote Config.
 * This code is ready and will be activated post-review.
 */
@Composable
fun PaywallScreen(
    onDismiss: () -> Unit,
    viewModel: PaywallViewModel = hiltViewModel(),
) {
    val products by viewModel.products.collectAsState(emptyList())
    var selectedPlan by remember { mutableStateOf(0) } // 0=yearly trial, 1=monthly, 2=yearly

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A1A2E), Color(0xFF16213E), Color(0xFF0F3460))
                )
            )
    ) {
        // Close button
        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
        ) {
            Icon(Icons.Default.Close, "Close", tint = Color.White)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 48.dp)
                .padding(top = 60.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Hero
            Text("📓", fontSize = 72.sp)
            Spacer(Modifier.height(16.dp))
            Text(
                "Notes Premium",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Text(
                "Unlock everything",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.7f),
            )
            Spacer(Modifier.height(32.dp))

            // Features list
            val features = listOf(
                "60+ professional templates",
                "1000+ stickers & decoration",
                "Unlimited notebooks",
                "PDF export",
                "Dark mode themes",
                "Priority support",
            )
            features.forEach { feature ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp),
                ) {
                    Icon(
                        Icons.Default.Check,
                        null,
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(feature, color = Color.White, fontSize = 15.sp)
                }
            }

            Spacer(Modifier.weight(1f))

            // Plan selection
            val plans = listOf(
                Triple("Yearly + Trial", "$39.99/yr", "7-day free trial"),
                Triple("Monthly", "$4.99/mo", ""),
                Triple("Yearly", "$39.99/yr", "Best value"),
            )

            plans.forEachIndexed { index, (name, price, badge) ->
                PlanOption(
                    name = name,
                    price = price,
                    badge = badge,
                    isSelected = selectedPlan == index,
                    onClick = { selectedPlan = index },
                )
                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(24.dp))

            // CTA button
            Button(
                onClick = { viewModel.purchase(selectedPlan) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2)),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    if (selectedPlan == 0) "Start Free Trial" else "Subscribe Now",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(Modifier.height(12.dp))
            TextButton(onClick = onDismiss) {
                Text("Restore Purchases", color = Color.White.copy(alpha = 0.6f))
            }
            Text(
                "Cancel anytime. Subscriptions renew automatically.",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.4f),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun PlanOption(
    name: String,
    price: String,
    badge: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) Color(0xFF4A90E2).copy(alpha = 0.3f)
        else Color.White.copy(alpha = 0.1f),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF4A90E2)) else null,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(name, color = Color.White, fontWeight = FontWeight.SemiBold)
                if (badge.isNotEmpty()) {
                    Text(badge, color = Color(0xFF4CAF50), fontSize = 12.sp)
                }
            }
            Text(price, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}
