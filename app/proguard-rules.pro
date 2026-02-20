# Notes for Android - ProGuard rules

# Keep serialization classes
-keep class com.britetodo.notesforandroid.data.models.** { *; }
-keepattributes *Annotation*

# Kotlinx serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** { *** Companion; }
-keepclasseswithmembers class kotlinx.serialization.json.** { kotlinx.serialization.KSerializer serializer(...); }
-keep,includedescriptorclasses class com.britetodo.notesforandroid.**$$serializer { *; }
-keepclassmembers class com.britetodo.notesforandroid.** {
    *** Companion;
}
-keepclasseswithmembers class com.britetodo.notesforandroid.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Firebase
-keep class com.google.firebase.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Room
-keep class androidx.room.** { *; }

# Billing
-keep class com.android.billingclient.** { *; }

# Coil
-dontwarn okhttp3.**

# General Android
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}
