package com.britetodo.notesforandroid.di;

import com.britetodo.notesforandroid.data.db.AppDatabase;
import com.britetodo.notesforandroid.data.db.PageDao;
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
public final class AppModule_ProvidePageDaoFactory implements Factory<PageDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvidePageDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public PageDao get() {
    return providePageDao(dbProvider.get());
  }

  public static AppModule_ProvidePageDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvidePageDaoFactory(dbProvider);
  }

  public static PageDao providePageDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providePageDao(db));
  }
}
