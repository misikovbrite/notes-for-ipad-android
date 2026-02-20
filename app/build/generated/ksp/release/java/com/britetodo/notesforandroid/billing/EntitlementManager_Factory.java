package com.britetodo.notesforandroid.billing;

import com.britetodo.notesforandroid.data.repository.NotebookRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class EntitlementManager_Factory implements Factory<EntitlementManager> {
  private final Provider<BillingManager> billingManagerProvider;

  private final Provider<NotebookRepository> repositoryProvider;

  public EntitlementManager_Factory(Provider<BillingManager> billingManagerProvider,
      Provider<NotebookRepository> repositoryProvider) {
    this.billingManagerProvider = billingManagerProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public EntitlementManager get() {
    return newInstance(billingManagerProvider.get(), repositoryProvider.get());
  }

  public static EntitlementManager_Factory create(Provider<BillingManager> billingManagerProvider,
      Provider<NotebookRepository> repositoryProvider) {
    return new EntitlementManager_Factory(billingManagerProvider, repositoryProvider);
  }

  public static EntitlementManager newInstance(BillingManager billingManager,
      NotebookRepository repository) {
    return new EntitlementManager(billingManager, repository);
  }
}
