package com.britetodo.notesforandroid.billing

import com.britetodo.notesforandroid.data.repository.NotebookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Feature gate for premium features.
 * R1: All features unlocked (showPaywallOnLaunch=false via Remote Config).
 * Post-review: Enable paywall via Remote Config.
 */
@Singleton
class EntitlementManager @Inject constructor(
    private val billingManager: BillingManager,
    private val repository: NotebookRepository,
) {
    companion object {
        const val FREE_NOTEBOOK_LIMIT = 2
    }

    val isPremium: Flow<Boolean> = billingManager.subscriptionStatus
        .combine(kotlinx.coroutines.flow.flowOf(false)) { status, _ ->
            // R1: Always return true (all features unlocked)
            // Post-review: use status.isActive
            true
        }

    suspend fun canCreateNotebook(): Boolean {
        // R1: No limit
        return true
        // Post-review:
        // val count = repository.countActiveNotebooks()
        // return isPremium.first() || count < FREE_NOTEBOOK_LIMIT
    }

    fun canAccessTemplate(templateId: String): Boolean {
        // R1: All templates unlocked
        return true
    }

    fun canUseStickers(): Boolean = true
    fun canExportPdf(): Boolean = true
    fun canChangeTheme(): Boolean = true
}
