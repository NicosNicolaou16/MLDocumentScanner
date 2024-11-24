package com.nicos.mldocumentscanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

    @Composable
    fun Scanner(
        innerPadding: PaddingValues
    ) {
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
                    /*val scannerLauncher =
                        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                            if (result.resultCode == RESULT_OK) {
                                val data =
                                    GmsDocumentScanningResult.fromActivityResultIntent(result.data)
                                data?.pages?.let { pages ->
                                    for (page in pages) {
                                        val imageUri = page.imageUri
                                    }
                                }
                                data?.pdf?.let { pdf ->
                                    val pdfUri = pdf.uri
                                    val pageCount = pdf.pageCount
                                }
                            }
                        }*/
                }
            )
        }
    }
}