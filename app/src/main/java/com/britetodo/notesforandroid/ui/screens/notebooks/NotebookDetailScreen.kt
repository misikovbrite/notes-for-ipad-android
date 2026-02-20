package com.britetodo.notesforandroid.ui.screens.notebooks

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.britetodo.notesforandroid.data.models.Page

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotebookDetailScreen(
    notebookId: String,
    onOpenPage: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: NotebookDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(notebookId) { viewModel.init(notebookId) }

    val notebook by viewModel.notebook.collectAsStateWithLifecycle()
    val pages by viewModel.pages.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        notebook?.name ?: "Notebook",
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.addPage() }) {
                        Icon(Icons.Default.Add, "Add Page")
                    }
                },
            )
        },
        floatingActionButton = {
            if (pages.isEmpty()) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.addPage() },
                    icon = { Icon(Icons.Default.Add, null) },
                    text = { Text("Add First Page") },
                )
            }
        },
    ) { padding ->
        if (pages.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📄", fontSize = 60.sp)
                    Spacer(Modifier.height(16.dp))
                    Text("No pages yet", style = MaterialTheme.typography.headlineSmall)
                    Text("Tap + to add your first page", color = Color.Gray)
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 180.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(pages, key = { it.id }) { page ->
                    PageCard(
                        page = page,
                        onClick = { onOpenPage(page.id) },
                        onDelete = { viewModel.deletePage(page.id) },
                    )
                }
            }
        }
    }
}

@Composable
private fun PageCard(
    page: Page,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.77f)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            ) {
                // Thumbnail placeholder or actual thumbnail
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .then(
                            Modifier.then(
                                Modifier // grey placeholder
                            )
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    if (page.hasContent) {
                        Icon(Icons.Default.Description, null, tint = Color.LightGray, modifier = Modifier.size(40.dp))
                    } else {
                        Text("📄", fontSize = 32.sp)
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = page.title ?: "Page ${page.order + 1}",
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                )
            }

            // Options button
            IconButton(
                onClick = { showMenu = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(32.dp),
            ) {
                Icon(Icons.Default.MoreVert, null, modifier = Modifier.size(16.dp))
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
            ) {
                DropdownMenuItem(
                    text = { Text("Delete", color = Color.Red) },
                    onClick = { showMenu = false; onDelete() },
                    leadingIcon = { Icon(Icons.Default.Delete, null, tint = Color.Red) },
                )
            }
        }
    }
}
