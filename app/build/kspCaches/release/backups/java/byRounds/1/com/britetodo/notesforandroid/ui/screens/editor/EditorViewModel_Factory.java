package com.britetodo.notesforandroid.ui.screens.editor;

import com.britetodo.notesforandroid.data.repository.NotebookRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class EditorViewModel_Factory implements Factory<EditorViewModel> {
  private final Provider<NotebookRepository> repositoryProvider;

  public EditorViewModel_Factory(Provider<NotebookRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public EditorViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static EditorViewModel_Factory create(Provider<NotebookRepository> repositoryProvider) {
    return new EditorViewModel_Factory(repositoryProvider);
  }

  public static EditorViewModel newInstance(NotebookRepository repository) {
    return new EditorViewModel(repository);
  }
}
