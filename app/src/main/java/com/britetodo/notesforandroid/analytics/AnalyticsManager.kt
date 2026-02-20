package com.britetodo.notesforandroid.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Firebase Analytics wrapper. Mirrors iOS AnalyticsManager.swift / FirebaseAnalyticsSink.swift.
 * All event names match iOS events for unified reporting.
 */
@Singleton
class AnalyticsManager @Inject constructor() {
    private val analytics: FirebaseAnalytics = Firebase.analytics

    // ─── App lifecycle ─────────────────────────────────────────────────────

    fun logAppOpen() = logEvent("app_open")

    fun logOnboardingCompleted(pagesViewed: Int) = logEvent("onboarding_completed", Bundle().apply {
        putInt("pages_viewed", pagesViewed)
    })

    // ─── Notebook events ──────────────────────────────────────────────────

    fun logNotebookCreated(templateId: String, colorHex: String) = logEvent("notebook_created", Bundle().apply {
        putString("template_id", templateId)
        putString("color_hex", colorHex)
    })

    fun logNotebookOpened(notebookId: String) = logEvent("notebook_opened", Bundle().apply {
        putString("notebook_id", notebookId)
    })

    fun logNotebookDeleted() = logEvent("notebook_deleted")

    // ─── Editor events ────────────────────────────────────────────────────

    fun logDrawingStarted(tool: String) = logEvent("drawing_started", Bundle().apply {
        putString("tool", tool)
    })

    fun logPageCreated(templateId: String) = logEvent("page_created", Bundle().apply {
        putString("template_id", templateId)
    })

    fun logTemplateChanged(fromTemplate: String, toTemplate: String) = logEvent("template_changed", Bundle().apply {
        putString("from_template", fromTemplate)
        putString("to_template", toTemplate)
    })

    fun logStickerAdded(stickerCategory: String) = logEvent("sticker_added", Bundle().apply {
        putString("category", stickerCategory)
    })

    fun logTextNoteAdded() = logEvent("text_note_added")

    // ─── Export events ────────────────────────────────────────────────────

    fun logPdfExported(pageCount: Int) = logEvent("pdf_exported", Bundle().apply {
        putInt("page_count", pageCount)
    })

    // ─── Monetization events ──────────────────────────────────────────────

    fun logPaywallShown(source: String) = logEvent("paywall_shown", Bundle().apply {
        putString("source", source)
    })

    fun logPaywallDismissed() = logEvent("paywall_dismissed")

    fun logSubscriptionStarted(planId: String) = logEvent("subscription_started", Bundle().apply {
        putString("plan_id", planId)
    })

    // ─── Private helpers ──────────────────────────────────────────────────

    private fun logEvent(name: String, params: Bundle? = null) {
        analytics.logEvent(name, params)
    }

    fun setUserProperty(key: String, value: String) {
        analytics.setUserProperty(key, value)
    }
}
