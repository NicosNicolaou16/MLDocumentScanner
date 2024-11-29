package com.nicos.mldocumentscanner

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_PDF
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import com.nicos.mldocumentscanner.ui.theme.MLDocumentScannerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MLDocumentScannerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Scanner(innerPadding = innerPadding)
                }
            }
        }
    }

    private fun openPdfWithIntent(pdfUri: Uri) {
        val uri = FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.provider",
            pdfUri.toFile()
        )
        Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            try {
                startActivity(this)
            } catch (e: Exception) {
                Log.d("exception", e.message ?: "error")
            }
        }
    }

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
                     * Option 1 to show the pdf as pdf uri
                     * */
                    data?.pdf?.let { pdf ->
                        val pdfUri = pdf.uri
                        val pageCount = pdf.pageCount
                        openPdfWithIntent(pdfUri)
                    }
                }
            }

        val options = GmsDocumentScannerOptions.Builder().apply {
            setGalleryImportAllowed(false)
            setPageLimit(2)
            setResultFormats(RESULT_FORMAT_JPEG, RESULT_FORMAT_PDF)
            setScannerMode(SCANNER_MODE_FULL)
        }.build()
        val scanner = GmsDocumentScanning.getClient(options)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedButton(
                content = {
                    Text(
                        text = stringResource(id = R.string.scan),
                        style = TextStyle(fontSize = 21.sp)
                    )
                },
                modifier = Modifier.size(height = 70.dp, width = 250.dp),
                onClick = {
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
    }
}