# ML Document Scanner

This project shows the implementation for the Machine Learning Document Scanner. The sample project
contains a button, open the camera and scan the document, and using the `Intent` to open and preview the pdf
file.

Target SDK version: 35 <br />
Minimum SDK version: 28 <br />
Kotlin version: 2.1.0 <br />
Gradle version: 8.7.2 <br />

## Step 1 - Add the library

### Groovy

```groovy
ext {
    playServicesMlkitDocumentScanner = "16.0.0-beta1"
}

dependencies {
    implementation 'com.google.android.gms:play-services-mlkit-document-scanner:$playServicesMlkitDocumentScanner'
}
```

### Kotlin DSL

```kotlin
ext {
    playServicesMlkitDocumentScanner = "16.0.0-beta1"
}

dependencies {
    implementation("com.google.android.gms:play-services-mlkit-document-scanner:$playServicesMlkitDocumentScanner")
}
```

### libs.versions.toml

```toml
[version]
playServicesMlkitDocumentScanner = "16.0.0-beta1"

[libraries]
play-services-mlkit-document-scanner = { group = "com.google.android.gms", name = "play-services-mlkit-document-scanner", version.ref = "playServicesMlkitDocumentScanner" }
```

```kotlin
dependencies {
    // ML Kit
    implementation(libs.play.services.mlkit.document.scanner)
}
```

## Step 2 - Setup the Builder

```kotlin
val options = GmsDocumentScannerOptions.Builder().apply {
            setGalleryImportAllowed(false)
            setPageLimit(2)
            setResultFormats(RESULT_FORMAT_JPEG, RESULT_FORMAT_PDF)
            setScannerMode(SCANNER_MODE_FULL)
        }.build()
```

## Step 3 - Main Implementation
```kotlin

```

## Check my article

## References

https://android-developers.googleblog.com/2024/02/ml-kit-document-scanner-api.html <br />
https://developers.google.com/ml-kit/vision/doc-scanner <br />
https://developers.google.com/ml-kit/vision/doc-scanner/android#kotlin <br />