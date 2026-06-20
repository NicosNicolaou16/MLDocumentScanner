# ML Document Scanner

[![Linktree](https://img.shields.io/badge/linktree-1de9b6?style=for-the-badge&logo=linktree&logoColor=white)](https://linktr.ee/nicos_nicolaou)
[![Site](https://img.shields.io/badge/Site-blue?style=for-the-badge&label=Web)](https://nicosnicolaou16.github.io/)
[![X](https://img.shields.io/badge/X-%23000000.svg?style=for-the-badge&logo=X&logoColor=white)](https://twitter.com/nicolaou_nicos)
[![LinkedIn](https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/nicos-nicolaou-a16720aa)
[![Medium](https://img.shields.io/badge/Medium-12100E?style=for-the-badge&logo=medium&logoColor=white)](https://medium.com/@nicosnicolaou)
[![Mastodon](https://img.shields.io/badge/-MASTODON-%232B90D9?style=for-the-badge&logo=mastodon&logoColor=white)](https://androiddev.social/@nicolaou_nicos)
[![Bluesky](https://img.shields.io/badge/Bluesky-0285FF?style=for-the-badge&logo=Bluesky&logoColor=white)](https://bsky.app/profile/nicolaounicos.bsky.social)
[![Dev.to blog](https://img.shields.io/badge/dev.to-0A0A0A?style=for-the-badge&logo=dev.to&logoColor=white)](https://dev.to/nicosnicolaou16)
[![YouTube](https://img.shields.io/badge/YouTube-%23FF0000.svg?style=for-the-badge&logo=YouTube&logoColor=white)](https://www.youtube.com/@nicosnicolaou16)
[![Google Developer Profile](https://img.shields.io/badge/Developer_Profile-blue?style=for-the-badge&label=Google)](https://g.dev/nicolaou_nicos)

This project demonstrates the implementation of the **Google ML Kit Document Scanner API** in Android using Jetpack Compose. It provides a seamless interface to scan physical documents, digitize them, and handle the resulting PDF or JPEG files.

> [!IMPORTANT]  
> Read the full technical guide here:  
> 👉 **[Machine Learning Document (PDF) Scanner in Android - Medium](https://medium.com/@nicosnicolaou/machine-learning-document-pdf-scanner-in-android-8ed185331fa4)** 👈

## ✨ Features

*   **High-Quality Scanning:** Leverages Google's ML Kit for advanced edge detection and perspective correction.
*   **Multi-Format Support:** Export scans as high-quality **JPEG** images or as a multi-page **PDF**.
*   **Customizable UI:** Configure page limits, gallery imports, and scanner modes (Base, Filter, Full).
*   **Compose Integration:** Uses `rememberLauncherForActivityResult` for a clean, reactive implementation in Jetpack Compose.
*   **Intent Preview:** Automatically handles PDF URI results to open and preview documents in external viewers.

## 🛠️ Tech Stack

- **UI:** [Jetpack Compose](https://developer.android.com/develop/ui/compose)
- **ML Engine:** [Google ML Kit Document Scanner](https://developers.google.com/ml-kit/vision/doc-scanner)
- **Language:** [Kotlin](https://kotlinlang.org/)
- **Architecture:** MVVM / Clean Architecture

## 📸 Demo

<p align="left">
  <a title="Document Scanner Demo">
    <img src="examples/example_gif.gif" height="500" width="230">
  </a>
</p>

## 🚀 Quick Start

## Step 1 - Add the library

### Groovy

```groovy
ext {
    playServicesMlkitDocumentScanner = "16.0.0"
}

dependencies {
    implementation 'com.google.android.gms:play-services-mlkit-document-scanner:$playServicesMlkitDocumentScanner'
}
```

### Kotlin DSL

```kotlin
ext {
    playServicesMlkitDocumentScanner = "16.0.0"
}

dependencies {
    implementation("com.google.android.gms:play-services-mlkit-document-scanner:$playServicesMlkitDocumentScanner")
}
```

### libs.versions.toml

```toml
[version]
playServicesMlkitDocumentScanner = "16.0.0"

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

### Builder Configuration

```kotlin
val options = GmsDocumentScannerOptions.Builder().apply {
    setGalleryImportAllowed(false)
    setPageLimit(2)
    setResultFormats(RESULT_FORMAT_JPEG, RESULT_FORMAT_PDF)
    setScannerMode(SCANNER_MODE_FULL)
}.build()
```

## Step 3 - Main Implementation

### Get the PDF URI

```kotlin
@Composable
fun Scanner(
    innerPadding: PaddingValues
) {
    /**
     * registerForActivityResult for Activity/Fragment instead of the rememberLauncherForActivityResult (For Compose)
     * */
    val scannerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data =
                    GmsDocumentScanningResult.fromActivityResultIntent(result.data)
                /**
                 * Option 1 to show the PDF as image uri
                 * */
                data?.pages?.let { pages ->
                    for (page in pages) {
                        val imageUri = page.imageUri
                    }
                }
                /**
                 * Option 2 to show the PDF as PDF uri
                 * */
                data?.pdf?.let { pdf ->
                    val pdfUri = pdf.uri
                    val pageCount = pdf.pageCount
                    // handle pdf uri and open it via Intent
                    openPdfWithIntent(pdfUri)
                }
            }
        }

    // Other Code Here - UI
}
```

### Set the Builder Configuration

```kotlin
val scanner = GmsDocumentScanning.getClient(options)
```

### UI

```kotlin
@Composable
fun Scanner(
    innerPadding: PaddingValues
) {
    //Other Code Here

    ElevatedButton(
        content = {
            Text(
                text = stringResource(id = R.string.scan),
                style = TextStyle(fontSize = 21.sp)
            )
        },
        modifier = Modifier.size(height = 70.dp, width = 250.dp),
        onClick = {
            /**
             * start the scanner
             * */
            scanner.getStartScanIntent(this@MainActivity)
                .addOnSuccessListener { intentSender ->
                    scannerLauncher.launch(
                        IntentSenderRequest.Builder(intentSender).build()
                    )
                }
                .addOnFailureListener {
                    Log.d("exception", "error")
                }
        }
    )
}
```

## 🔧 Versioning

- **Target SDK:** **37**
- **Minimum SDK:** **29**
- **Kotlin Version:** **2.4.0**
- **Gradle Version:** **9.2.1**

## 📚 References

- [Official ML Kit Document Scanner Guide](https://developers.google.com/ml-kit/vision/doc-scanner/android)
- [Android Developers Blog: Introducing Document Scanner API](https://android-developers.googleblog.com/2024/02/ml-kit-document-scanner-api.html)
- [Document Scanner for Android (Kotlin/Java)](https://developers.google.com/ml-kit/vision/doc-scanner/android#kotlin)

## ⭐ Stargazers

If you enjoy this project, please give it a star!
Check out all the stargazers
here: [Stargazers on GitHub](https://github.com/NicosNicolaou16/MLDocumentScanner/stargazers)

## 🙏 Support & Contributions

This project is actively maintained. Feedback, bug reports, and feature requests are welcome! Please feel free to **open an issue** or submit a **pull request**.