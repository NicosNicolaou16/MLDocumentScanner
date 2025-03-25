# ML Document Scanner

This project shows the implementation for the Machine Learning Document Scanner. The sample project
contains a button, open the camera and scan the document, and using the `Intent` to open and preview
the pdf
file.

> [!IMPORTANT]  
> Check my article :point_right: [Machine Learning Document (PDF) Scanner in Android - Medium](https://medium.com/@nicosnicolaou/machine-learning-document-pdf-scanner-in-android-8ed185331fa4) :point_left: <br />

# Example
<p align="left">
  <a title="simulator_image"><img src="examples/example_gif.gif" height="500" width="200"></a>
</p>

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
                 * Option 1 to show the pdf as image uri
                 * */
                data?.pages?.let { pages ->
                    for (page in pages) {
                        val imageUri = page.imageUri
                    }
                }
                /**
                 * Option 2 to show the pdf as pdf uri
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

## Versioning

Target SDK version: 35 <br />
Minimum SDK version: 28 <br />
Kotlin version: 2.1.20 <br />
Gradle version: 8.9.1 <br />

## References

https://android-developers.googleblog.com/2024/02/ml-kit-document-scanner-api.html <br />
https://developers.google.com/ml-kit/vision/doc-scanner <br />
https://developers.google.com/ml-kit/vision/doc-scanner/android#kotlin <br />