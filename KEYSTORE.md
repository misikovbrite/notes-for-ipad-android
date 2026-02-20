# Release Keystore

Keystore file: `app/notes-android.jks`

| Field          | Value                |
|----------------|----------------------|
| Alias          | `notes-android`      |
| Store password | `BriteTech2024!`     |
| Key password   | `BriteTech2024!`     |
| Validity       | 10 000 days          |
| Algorithm      | RSA 2048             |
| Organization   | Brite Technologies LLC |

## Build signed AAB

```bash
export JAVA_HOME=/opt/homebrew/Cellar/openjdk@17/17.0.18/libexec/openjdk.jdk/Contents/Home
./gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

## Build signed APK

```bash
export JAVA_HOME=/opt/homebrew/Cellar/openjdk@17/17.0.18/libexec/openjdk.jdk/Contents/Home
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

## Upload to Google Play (Internal Testing)

```bash
python3 ~/AndroidStudioProjects/play_console.py upload notes \
  app/build/outputs/bundle/release/app-release.aab --track internal
```
