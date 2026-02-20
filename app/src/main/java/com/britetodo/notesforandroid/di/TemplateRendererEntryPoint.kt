package com.britetodo.notesforandroid.di

import com.britetodo.notesforandroid.templates.TemplateRenderer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface TemplateRendererEntryPoint {
    fun templateRenderer(): TemplateRenderer
}
