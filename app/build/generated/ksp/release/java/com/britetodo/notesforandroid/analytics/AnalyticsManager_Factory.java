package com.britetodo.notesforandroid.analytics;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AnalyticsManager_Factory implements Factory<AnalyticsManager> {
  @Override
  public AnalyticsManager get() {
    return newInstance();
  }

  public static AnalyticsManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AnalyticsManager newInstance() {
    return new AnalyticsManager();
  }

  private static final class InstanceHolder {
    private static final AnalyticsManager_Factory INSTANCE = new AnalyticsManager_Factory();
  }
}
