# Notes for Android — CLAUDE.md

> Android port of "Notes for iPad" (misikovbrite/notes_for_ipad). Kotlin + Jetpack Compose tablet app.

## Quick Reference

| Key | Value |
|-----|-------|
| **Package** | `com.britetodo.notesforandroid` |
| **App Name** | Notes Pad - Handwriting & PDF |
| **Version** | 1.0.0 (versionCode 1) |
| **Min SDK** | 29 (Android 10) |
| **Target SDK** | 35 (Android 15) |
| **Tablet only** | Yes (xlarge screens) |
| **GitHub** | `misikovbrite/notes-for-ipad-android` |

## Keystore

| Field | Value |
|-------|-------|
| File | `app/notes-android.jks` |
| Alias | `notes-android` |
| Store password | `BriteTech2024!` |
| Key password | `BriteTech2024!` |

## Build Commands

```bash
export JAVA_HOME=/opt/homebrew/Cellar/openjdk@17/17.0.18/libexec/openjdk.jdk/Contents/Home

# Debug APK (CI)
./gradlew assembleDebug

# Signed Release AAB (Play Store)
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

## local.properties (not in git)

```
sdk.dir=/Users/m/Library/Android/sdk
```

## Google Play

- **Console:** https://play.google.com/console/u/0/developers/5487HDH2B9/
- **Package:** `com.britetodo.notesforandroid`
- **Service Account:** `play-console-api-access@brite-ads-automation.iam.gserviceaccount.com`
- **API Key:** `~/Downloads/brite-ads-automation-33f3693602ca.json`
- **Script:** `~/AndroidStudioProjects/play_console.py` (from misikovbrite/app-converter)

## Upload to Play Console

```bash
# Check status
python3 ~/AndroidStudioProjects/play_console.py status notes

# Upload new AAB
python3 ~/AndroidStudioProjects/play_console.py upload notes \
  app/build/outputs/bundle/release/app-release.aab --track production

# Update listing
python3 ~/AndroidStudioProjects/play_console.py update-listing notes en-US \
  --title "..." --short "..." --full "..."

# Upload screenshots
python3 ~/AndroidStudioProjects/play_console.py upload-screenshots notes \
  en-US tenInchScreenshots ./screenshots/
```

## Firebase

- **Project:** `notes-for-ipad-3f442`
- **Config:** `app/google-services.json`

## CPPFlow Screenshots

- **Template:** `ftp://138.68.62.199/cpps/notes-for-ipad-android/notes-android-main-ipad.html`
- **Assets:** `https://cppflow.com/assets/notes-android-clean1-5.png`
- **FTP:** `138.68.62.199` / `deploy` / `111345`

## Architecture

```
MainActivity (single activity)
└── AppNavigation (NavHost)
    ├── OnboardingScreen
    ├── HomeScreen + HomeViewModel          ← notebook gallery
    ├── NotebookDetailScreen + ViewModel    ← page grid
    ├── EditorScreen + EditorViewModel      ← main editor
    │   ├── TemplateLayer                   ← android.graphics.Canvas
    │   ├── DrawingCanvas                   ← MotionEvent + CubicBezier
    │   ├── TextNoteOverlay                 ← floating text boxes
    │   ├── StickerLayer                    ← drag/resize via Coil
    │   └── ToolBar
    └── SettingsScreen
```

## Stack

| Component | Technology |
|-----------|-----------|
| UI | Jetpack Compose + Material3 |
| Navigation | Navigation Compose |
| DI | Hilt |
| Database | Room |
| Drawing | Custom Canvas + MotionEvent |
| Templates | android.graphics.Canvas (60+) |
| Images | Coil |
| Serialization | kotlinx.serialization |
| PDF Export | Android PdfDocument API |
| Analytics | Firebase Analytics |
| Config | Firebase Remote Config |

## R1 Release Notes

- No subscriptions, no paywall — everything free
- Billing code removed entirely
- All templates unlocked
- Tablet-only (minSdk 29, xlarge screens)

## Localization

EN, DE, ES, FR, JA, KO, PT-BR, RU — `res/values-*/strings.xml`

## CI/CD

GitHub Actions → `.github/workflows/build.yml`
- JDK 17 Temurin
- `./gradlew assembleDebug`
- Uploads `app-debug.apk` as artifact
