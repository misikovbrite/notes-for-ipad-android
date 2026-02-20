package com.britetodo.notesforandroid.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val packageInfo = remember {
        try { context.packageManager.getPackageInfo(context.packageName, 0) }
        catch (e: Exception) { null }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                SettingsSection("Appearance") {
                    SettingsItem(
                        icon = Icons.Default.DarkMode,
                        title = "Theme",
                        subtitle = "System default",
                        onClick = { /* TODO: theme picker */ },
                    )
                }
            }

            item {
                SettingsSection("Drawing") {
                    SettingsItem(
                        icon = Icons.Default.Edit,
                        title = "Default pen color",
                        subtitle = "Black",
                        onClick = { },
                    )
                    SettingsItem(
                        icon = Icons.Default.LineWeight,
                        title = "Default stroke width",
                        subtitle = "4px",
                        onClick = { },
                    )
                }
            }

            item {
                SettingsSection("Export") {
                    SettingsItem(
                        icon = Icons.Default.PictureAsPdf,
                        title = "PDF Quality",
                        subtitle = "High",
                        onClick = { },
                    )
                }
            }

            item {
                SettingsSection("About") {
                    SettingsItem(
                        icon = Icons.Default.Info,
                        title = "Version",
                        subtitle = packageInfo?.versionName ?: "1.0.0",
                        onClick = { },
                    )
                    SettingsItem(
                        icon = Icons.Default.Star,
                        title = "Rate on Google Play",
                        subtitle = "Share your feedback",
                        onClick = { /* Open Play Store */ },
                    )
                    SettingsItem(
                        icon = Icons.Default.Email,
                        title = "Contact Support",
                        subtitle = "support@britetodo.com",
                        onClick = { /* Open email */ },
                    )
                    SettingsItem(
                        icon = Icons.Default.Policy,
                        title = "Privacy Policy",
                        subtitle = null,
                        onClick = { /* Open URL */ },
                    )
                    SettingsItem(
                        icon = Icons.Default.Gavel,
                        title = "Terms of Service",
                        subtitle = null,
                        onClick = { /* Open URL */ },
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column {
        Text(
            title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(content = content)
        }
    }
}

@Composable
private fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String?,
    onClick: () -> Unit,
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = subtitle?.let { { Text(it, fontSize = 13.sp) } },
        leadingContent = { Icon(icon, null, tint = MaterialTheme.colorScheme.primary) },
        trailingContent = { Icon(Icons.Default.ChevronRight, null) },
        modifier = Modifier.clickable(onClick = onClick),
    )
    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
}
