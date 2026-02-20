package com.britetodo.notesforandroid.di;

import com.britetodo.notesforandroid.data.db.AppDatabase;
import com.britetodo.notesforandroid.data.db.NotebookDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideNotebookDaoFactory implements Factory<NotebookDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideNotebookDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public NotebookDao get() {
    return provideNotebookDao(dbProvider.get());
  }

  public static AppModule_ProvideNotebookDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideNotebookDaoFactory(dbProvider);
  }

  public static NotebookDao provideNotebookDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNotebookDao(db));
  }
}
