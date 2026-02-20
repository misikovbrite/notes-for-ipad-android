# Notes Pad — Handwriting & PDF

![Build](https://github.com/misikovbrite/notes-for-ipad-android/actions/workflows/build.yml/badge.svg)
![Version](https://img.shields.io/badge/version-1.0.0-blue)
![Platform](https://img.shields.io/badge/platform-Android%20Tablet-green)
![Min SDK](https://img.shields.io/badge/minSdk-29-orange)

Android tablet port of **Notes for iPad** (`misikovbrite/notes_for_ipad`).

Professional digital notebook app for Android tablets (10"+) built with Kotlin + Jetpack Compose.

**Google Play:** `com.britetodo.notesforandroid` · Status: **Submitted for review** ✅

---

## Features

- **60+ Templates** — Daily planners, habit trackers, meal planners, journals, and more
- **Native Drawing** — Custom Canvas + MotionEvent with stylus pressure, cubic bezier smoothing
- **1000+ Stickers** — Drag, resize, and rotate
- **Text Notes** — Floating text boxes
- **PDF Export** — Export pages and notebooks as PDF
- **7 Languages** — EN, DE, ES, FR, PT-BR, JA, KO, RU
- **6 Color Themes** — Classic, Ocean, Forest, Sunset, Lavender, Rose, Dark
- **Tablet-only** — Optimized for 10"+ Android tablets (minSdk 29)

## Tech Stack

| Component | Library |
|-----------|---------|
| UI | Jetpack Compose + Material3 |
| Drawing | Custom Canvas + MotionEvent |
| Database | Room |
| DI | Hilt |
| Navigation | Navigation Compose |
| Images | Coil |
| JSON | Kotlinx Serialization |
| Analytics | Firebase Analytics |
| Config | Firebase Remote Config |
| Min SDK | Android 10 (API 29) |

## Architecture

```
[Template Layer]   → Bitmap rendered template background
[Drawing Layer]    → Strokes via custom DrawingEngine
[Sticker Layer]    → Draggable/resizable stickers (Coil)
[Text Layer]       → Floating text boxes
    ↓
[ZoomableCanvas]   → Pinch-to-zoom + pan (TransformableState)
```

## Build

```bash
# Requires local.properties with sdk.dir
echo "sdk.dir=/Users/m/Library/Android/sdk" > local.properties

export JAVA_HOME=/opt/homebrew/Cellar/openjdk@17/17.0.18/libexec/openjdk.jdk/Contents/Home

# Debug APK
./gradlew assembleDebug

# Signed Release AAB (Play Store)
./gradlew bundleRelease
# → app/build/outputs/bundle/release/app-release.aab
```

## Keystore

See `KEYSTORE.md` for credentials. File: `app/notes-android.jks`

## iOS Original

`misikovbrite/notes_for_ipad` — `com.britetodo.notesforpencil`
