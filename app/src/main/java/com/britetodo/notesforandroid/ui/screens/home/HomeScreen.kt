package com.britetodo.notesforandroid.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.britetodo.notesforandroid.data.models.Notebook

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenNotebook: (String) -> Unit,
    onSettings: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val notebooks by viewModel.notebooks.collectAsStateWithLifecycle()
    var showCreateDialog by remember { mutableStateOf(false) }
    var selectedNotebook by remember { mutableStateOf<Notebook?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Notebooks",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )
                },
                actions = {
                    IconButton(onClick = onSettings) {
                        Icon(Icons.Default.Settings, "Settings")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(Icons.Default.Add, "Create Notebook", tint = Color.White)
            }
        },
    ) { padding ->
        if (notebooks.isEmpty()) {
            EmptyNotebooksPlaceholder(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                onCreate = { showCreateDialog = true },
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(notebooks, key = { it.id }) { notebook ->
                    NotebookCard(
                        notebook = notebook,
                        onClick = { onOpenNotebook(notebook.id) },
                        onLongClick = { selectedNotebook = notebook },
                    )
                }
            }
        }
    }

    if (showCreateDialog) {
        CreateNotebookDialog(
            onCreate = { name, color, icon ->
                viewModel.createNotebook(name, color, color.replace("#", "#AA"), icon)
                showCreateDialog = false
            },
            onDismiss = { showCreateDialog = false },
        )
    }

    selectedNotebook?.let { notebook ->
        NotebookOptionsSheet(
            notebook = notebook,
            onFavorite = {
                viewModel.toggleFavorite(notebook.id, !notebook.isFavorite)
                selectedNotebook = null
            },
            onArchive = {
                viewModel.archiveNotebook(notebook.id)
                selectedNotebook = null
            },
            onDelete = {
                viewModel.deleteNotebook(notebook.id)
                selectedNotebook = null
            },
            onDismiss = { selectedNotebook = null },
        )
    }
}

@Composable
private fun NotebookCard(
    notebook: Notebook,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val coverColor = try { Color(android.graphics.Color.parseColor(notebook.colorHex)) }
    catch (e: Exception) { Color(0xFF4A90E2) }
    val spineColor = try { Color(android.graphics.Color.parseColor(notebook.spineColorHex)) }
    catch (e: Exception) { Color(0xFF357ABD) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.75f)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        // Book spine
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(20.dp)
                .background(spineColor)
        )

        // Book cover
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp)
                .background(coverColor),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Book,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(40.dp),
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = notebook.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 2,
                )
                if (notebook.pageCount > 0) {
                    Text(
                        text = "${notebook.pageCount} pages",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                    )
                }
            }
        }

        if (notebook.isFavorite) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Favorite",
                tint = Color(0xFFFFD700),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(20.dp),
            )
        }
    }
}

@Composable
private fun EmptyNotebooksPlaceholder(
    modifier: Modifier = Modifier,
    onCreate: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("📓", fontSize = 80.sp)
        Spacer(Modifier.height(24.dp))
        Text(
            "No Notebooks Yet",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Create your first notebook to get started",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
        )
        Spacer(Modifier.height(32.dp))
        Button(onClick = onCreate) {
            Icon(Icons.Default.Add, null)
            Spacer(Modifier.width(8.dp))
            Text("Create Notebook")
        }
    }
}

@Composable
private fun CreateNotebookDialog(
    onCreate: (name: String, color: String, icon: String) -> Unit,
    onDismiss: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    val colors = listOf(
        "#4A90E2", "#50C878", "#FF6B6B", "#FF9F43",
        "#A29BFE", "#00B894", "#FD79A8", "#636E72",
    )
    var selectedColor by remember { mutableStateOf(colors[0]) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Notebook") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Notebook name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text("Color:", style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    colors.forEach { color ->
                        val c = try { Color(android.graphics.Color.parseColor(color)) }
                        catch (e: Exception) { Color.Gray }
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(c)
                                .clickable { selectedColor = color },
                            contentAlignment = Alignment.Center,
                        ) {
                            if (color == selectedColor) {
                                Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { if (name.isNotBlank()) onCreate(name, selectedColor, "book") },
                enabled = name.isNotBlank(),
            ) { Text("Create") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotebookOptionsSheet(
    notebook: Notebook,
    onFavorite: () -> Unit,
    onArchive: () -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
) {
    var showDeleteConfirm by remember { mutableStateOf(false) }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(16.dp)) {
            Text(notebook.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))

            ListItem(
                headlineContent = { Text(if (notebook.isFavorite) "Remove from Favorites" else "Add to Favorites") },
                leadingContent = { Icon(Icons.Default.Star, null) },
                modifier = Modifier.clickable { onFavorite() },
            )
            ListItem(
                headlineContent = { Text("Archive") },
                leadingContent = { Icon(Icons.Default.Archive, null) },
                modifier = Modifier.clickable { onArchive() },
            )
            ListItem(
                headlineContent = { Text("Delete", color = Color.Red) },
                leadingContent = { Icon(Icons.Default.Delete, null, tint = Color.Red) },
                modifier = Modifier.clickable { showDeleteConfirm = true },
            )
            Spacer(Modifier.height(32.dp))
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Delete Notebook?") },
            text = { Text("This will permanently delete \"${notebook.name}\" and all its pages.") },
            confirmButton = {
                TextButton(onClick = onDelete) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) { Text("Cancel") }
            },
        )
    }
}
