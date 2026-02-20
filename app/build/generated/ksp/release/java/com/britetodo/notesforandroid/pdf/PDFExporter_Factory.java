package com.britetodo.notesforandroid.pdf;

import android.content.Context;
import com.britetodo.notesforandroid.data.repository.NotebookRepository;
import com.britetodo.notesforandroid.templates.TemplateRenderer;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class PDFExporter_Factory implements Factory<PDFExporter> {
  private final Provider<Context> contextProvider;

  private final Provider<TemplateRenderer> templateRendererProvider;

  private final Provider<NotebookRepository> repositoryProvider;

  public PDFExporter_Factory(Provider<Context> contextProvider,
      Provider<TemplateRenderer> templateRendererProvider,
      Provider<NotebookRepository> repositoryProvider) {
    this.contextProvider = contextProvider;
    this.templateRendererProvider = templateRendererProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public PDFExporter get() {
    return newInstance(contextProvider.get(), templateRendererProvider.get(), repositoryProvider.get());
  }

  public static PDFExporter_Factory create(Provider<Context> contextProvider,
      Provider<TemplateRenderer> templateRendererProvider,
      Provider<NotebookRepository> repositoryProvider) {
    return new PDFExporter_Factory(contextProvider, templateRendererProvider, repositoryProvider);
  }

  public static PDFExporter newInstance(Context context, TemplateRenderer templateRenderer,
      NotebookRepository repository) {
    return new PDFExporter(context, templateRenderer, repository);
  }
}
