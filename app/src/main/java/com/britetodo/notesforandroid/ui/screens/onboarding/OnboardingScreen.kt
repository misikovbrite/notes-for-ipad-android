package com.britetodo.notesforandroid.ui.screens.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class OnboardingPage(
    val title: String,
    val subtitle: String,
    val description: String,
    val accentColor: Color,
    val emoji: String,
)

private val onboardingPages = listOf(
    OnboardingPage(
        title = "Beautiful Notes",
        subtitle = "Create beautiful notes on your Android tablet",
        description = "A professional digital notebook designed specifically for tablets. Write, draw, and organize your thoughts.",
        accentColor = Color(0xFF4A90E2),
        emoji = "📓",
    ),
    OnboardingPage(
        title = "60+ Templates",
        subtitle = "Daily planners, journals, trackers & more",
        description = "Choose from 60+ professionally designed templates — daily planners, habit trackers, meal planners, and many more.",
        accentColor = Color(0xFF50C878),
        emoji = "📋",
    ),
    OnboardingPage(
        title = "Natural Handwriting",
        subtitle = "Write and sketch naturally with stylus or finger",
        description = "Advanced drawing engine with pressure sensitivity, smooth curves, and multiple brush types for a natural feel.",
        accentColor = Color(0xFF9B59B6),
        emoji = "✍️",
    ),
    OnboardingPage(
        title = "Stickers & Decoration",
        subtitle = "1000+ stickers to personalize your pages",
        description = "Express yourself with a vast collection of stickers. Drag, resize, and rotate to create beautiful page layouts.",
        accentColor = Color(0xFFFF9F43),
        emoji = "🌟",
    ),
    OnboardingPage(
        title = "Ready to Start",
        subtitle = "Your notes await",
        description = "Everything you need to capture ideas, plan your days, and stay organized — all in one beautiful app.",
        accentColor = Color(0xFFE74C3C),
        emoji = "🚀",
    ),
)

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    var currentPage by remember { mutableIntStateOf(0) }
    val page = onboardingPages[currentPage]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        page.accentColor.copy(alpha = 0.15f),
                        Color.White,
                    )
                )
            )
    ) {
        AnimatedContent(
            targetState = currentPage,
            transitionSpec = {
                slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
            },
            label = "onboarding",
        ) { pageIndex ->
            val p = onboardingPages[pageIndex]
            OnboardingPageContent(page = p)
        }

        // Bottom controls
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 48.dp, vertical = 40.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Skip button
            if (currentPage < onboardingPages.size - 1) {
                TextButton(onClick = onFinish) {
                    Text("Skip", color = Color.Gray, fontSize = 16.sp)
                }
            } else {
                Spacer(Modifier.width(80.dp))
            }

            // Page indicators
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                onboardingPages.indices.forEach { i ->
                    Box(
                        modifier = Modifier
                            .size(if (i == currentPage) 24.dp else 8.dp, 8.dp)
                            .clip(CircleShape)
                            .background(if (i == currentPage) page.accentColor else Color.LightGray)
                    )
                }
            }

            // Next / Get Started button
            Button(
                onClick = {
                    if (currentPage < onboardingPages.size - 1) currentPage++
                    else onFinish()
                },
                colors = ButtonDefaults.buttonColors(containerColor = page.accentColor),
                shape = RoundedCornerShape(24.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            ) {
                Text(
                    text = if (currentPage < onboardingPages.size - 1) "Next" else "Get Started",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 80.dp)
            .padding(top = 100.dp, bottom = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Illustration / emoji
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(page.accentColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Text(page.emoji, fontSize = 80.sp)
        }

        Spacer(Modifier.height(48.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = page.accentColor,
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = page.subtitle,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = Color(0xFF333333),
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            lineHeight = 26.sp,
        )
    }
}
