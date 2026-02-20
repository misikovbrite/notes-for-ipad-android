package com.britetodo.notesforandroid.data.repository;

import android.content.Context;
import com.britetodo.notesforandroid.data.db.NotebookDao;
import com.britetodo.notesforandroid.data.db.PageDao;
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
public final class NotebookRepository_Factory implements Factory<NotebookRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<NotebookDao> notebookDaoProvider;

  private final Provider<PageDao> pageDaoProvider;

  public NotebookRepository_Factory(Provider<Context> contextProvider,
      Provider<NotebookDao> notebookDaoProvider, Provider<PageDao> pageDaoProvider) {
    this.contextProvider = contextProvider;
    this.notebookDaoProvider = notebookDaoProvider;
    this.pageDaoProvider = pageDaoProvider;
  }

  @Override
  public NotebookRepository get() {
    return newInstance(contextProvider.get(), notebookDaoProvider.get(), pageDaoProvider.get());
  }

  public static NotebookRepository_Factory create(Provider<Context> contextProvider,
      Provider<NotebookDao> notebookDaoProvider, Provider<PageDao> pageDaoProvider) {
    return new NotebookRepository_Factory(contextProvider, notebookDaoProvider, pageDaoProvider);
  }

  public static NotebookRepository newInstance(Context context, NotebookDao notebookDao,
      PageDao pageDao) {
    return new NotebookRepository(context, notebookDao, pageDao);
  }
}
