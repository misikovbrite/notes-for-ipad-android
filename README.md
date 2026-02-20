# Notes for Android

Android tablet port of **Notes for iPad** (`misikovbrite/notes_for_ipad`).

Professional digital notebook app for Android tablets (10"+) built with Kotlin + Jetpack Compose.

## Features

- **60+ Templates** — Daily planners, habit trackers, meal planners, journals, and more
- **Native Drawing** — Custom Canvas + MotionEvent with stylus pressure, cubic bezier smoothing
- **Stickers** — 1000+ stickers with drag, resize, and rotate
- **Text Notes** — Floating text boxes with formatting
- **PDF Export** — Export pages and notebooks as PDF
- **7 Languages** — EN, DE, ES, FR, PT-BR, JA, KO, RU
- **6 Color Themes** — Classic, Ocean, Forest, Sunset, Lavender, Rose, Dark
- **Tablet-only** — Optimized for 10"+ Android tablets

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
| Payments | Google Play Billing 7 |
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

## Setup

1. Add your `google-services.json` to `app/` (Firebase)
2. Add subscription products in Google Play Console:
   - `notes_android_yearly_trial` — $39.99/yr + 7-day trial
   - `notes_android_monthly` — $4.99/mo
   - `notes_android_yearly` — $39.99/yr
3. Build: `./gradlew assembleDebug`

## Monetization (R1 disabled)

Paywall code is complete but disabled for R1 via Firebase Remote Config.
Set `showPaywallOnLaunch=true` in Remote Config after app review passes.

## Package

`com.britetodo.notesforandroid`

## iOS Original

`misikovbrite/notes_for_ipad` — `com.britetodo.notesforpencil`
