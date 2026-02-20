package com.britetodo.notesforandroid.ui.screens.paywall;

import com.britetodo.notesforandroid.billing.BillingManager;
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
public final class PaywallViewModel_Factory implements Factory<PaywallViewModel> {
  private final Provider<BillingManager> billingManagerProvider;

  public PaywallViewModel_Factory(Provider<BillingManager> billingManagerProvider) {
    this.billingManagerProvider = billingManagerProvider;
  }

  @Override
  public PaywallViewModel get() {
    return newInstance(billingManagerProvider.get());
  }

  public static PaywallViewModel_Factory create(Provider<BillingManager> billingManagerProvider) {
    return new PaywallViewModel_Factory(billingManagerProvider);
  }

  public static PaywallViewModel newInstance(BillingManager billingManager) {
    return new PaywallViewModel(billingManager);
  }
}
