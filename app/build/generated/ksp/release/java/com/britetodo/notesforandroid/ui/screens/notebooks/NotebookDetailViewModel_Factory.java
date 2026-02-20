package com.britetodo.notesforandroid.ui.screens.notebooks;

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
public final class NotebookDetailViewModel_Factory implements Factory<NotebookDetailViewModel> {
  private final Provider<NotebookRepository> repositoryProvider;

  public NotebookDetailViewModel_Factory(Provider<NotebookRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public NotebookDetailViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static NotebookDetailViewModel_Factory create(
      Provider<NotebookRepository> repositoryProvider) {
    return new NotebookDetailViewModel_Factory(repositoryProvider);
  }

  public static NotebookDetailViewModel newInstance(NotebookRepository repository) {
    return new NotebookDetailViewModel(repository);
  }
}
