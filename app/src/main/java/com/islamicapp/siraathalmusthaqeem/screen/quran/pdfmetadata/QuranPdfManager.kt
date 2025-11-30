package com.islamicapp.siraathalmusthaqeem.screen.quran.pdfmetadata
import android.content.Context
import java.io.File
import java.io.FileOutputStream

class QuranPdfManager(private val context: Context) {

    private val localPdfFile: File
        get() = File(context.filesDir, "quran_mushaf.pdf")

    /**
     * Check if PDF is already copied to local storage
     */
    fun isPdfReady(): Boolean {
        return localPdfFile.exists() && localPdfFile.length() > 0
    }

    /**
     * Get local PDF file path
     */
    fun getLocalPdfPath(): String? {
        return if (isPdfReady()) localPdfFile.absolutePath else null
    }

    /**
     * Copy PDF from assets to internal storage (one-time operation)
     * This runs silently in background on first launch
     */
    fun copyPdfFromAssets(): Result<String> {
        return try {
            // If already copied, return immediately
            if (isPdfReady()) {
                return Result.success(localPdfFile.absolutePath)
            }

            // Copy from assets to internal storage
            context.assets.open("quran/quran_mushaf.pdf").use { inputStream ->
                FileOutputStream(localPdfFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            // Verify the copy was successful
            if (isPdfReady()) {
                Result.success(localPdfFile.absolutePath)
            } else {
                Result.failure(Exception("Failed to copy PDF from assets"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}