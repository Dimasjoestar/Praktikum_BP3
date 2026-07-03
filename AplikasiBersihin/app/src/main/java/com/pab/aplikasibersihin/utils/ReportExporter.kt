package com.pab.aplikasibersihin.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.pab.aplikasibersihin.data.database.entity.OrderEntity
import com.pab.aplikasibersihin.data.database.entity.ServiceEntity
import com.pab.aplikasibersihin.data.database.entity.UserEntity
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ReportExporter {

    /**
     * Mengekspor data transaksi selesai ke dalam file CSV.
     * Disimpan di direktori Documents eksternal aplikasi.
     */
    fun exportToCSV(
        context: Context,
        orders: List<OrderEntity>,
        users: List<UserEntity>,
        services: List<ServiceEntity>
    ): File? {
        val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        if (dir != null && !dir.exists()) {
            dir.mkdirs()
        }

        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val file = File(dir, "Laporan_Pendapatan_$timestamp.csv")

        try {
            val fos = FileOutputStream(file)
            val writer = java.io.OutputStreamWriter(fos, "UTF-8")
            // Header CSV
            writer.write("ID Order,Tanggal Selesai,Nama Pelanggan,Layanan,Berat (kg),Subtotal,Diskon,Total,Tipe Pengantaran\n")

            orders.forEach { order ->
                val customerName = (users.find { it.id == order.userId }?.name as? String) ?: "Tidak Diketahui"
                val serviceName = (services.find { it.id == order.serviceId }?.name as? String) ?: "Layanan #${order.serviceId}"
                val dateStr = DateFormatter.formatDateOnly(order.updatedAt)
                
                // Escape commas in text fields
                val cleanCustomer = customerName.replace(",", " ")
                val cleanService = serviceName.replace(",", " ")
                
                val pickupTypeStr = (order.pickupType as? String) ?: "ANTAR_SENDIRI"
                val subtotalVal = order.subtotal
                val discountVal = order.discount
                val totalVal = order.total
                val weightVal = order.weight

                writer.write("${order.id},$dateStr,$cleanCustomer,$cleanService,$weightVal,$subtotalVal,$discountVal,$totalVal,$pickupTypeStr\n")
            }

            writer.flush()
            writer.close()
            fos.close()
            return saveToPublicDownloads(context, file, "text/csv")
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * Mengekspor laporan pendapatan visual terformat ke dalam file PDF.
     * Disimpan di direktori Documents eksternal aplikasi.
     */
    fun exportToPDF(
        context: Context,
        orders: List<OrderEntity>,
        users: List<UserEntity>,
        services: List<ServiceEntity>,
        totalRevenue: Double,
        totalCount: Int,
        pendingCount: Int,
        completedCount: Int
    ): File? {
        val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        if (dir != null && !dir.exists()) {
            dir.mkdirs()
        }

        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val file = File(dir, "Laporan_Pendapatan_$timestamp.pdf")

        val pdfDocument = PdfDocument()
        
        // Ukuran Halaman A4 standard: 595 x 842 pt
        val pageWidth = 595
        val pageHeight = 842
        var pageNumber = 1

        var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas = page.canvas

        // Paint setup
        val paint = Paint().apply {
            isAntiAlias = true
        }

        // Margin & Layout parameters
        val margin = 40f
        var currentY = 50f

        // Helper untuk drawing text
        fun drawText(text: String, x: Float, y: Float, size: Float, isBold: Boolean = false, colorHex: Int = 0xFF000000.toInt()) {
            paint.color = colorHex
            paint.textSize = size
            paint.typeface = if (isBold) Typeface.create(Typeface.DEFAULT, Typeface.BOLD) else Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            canvas.drawText(text, x, y, paint)
        }

        // Helper untuk menggambar garis pemisah
        fun drawLine(startX: Float, startY: Float, endX: Float, endY: Float, thickness: Float = 1f, colorHex: Int = 0xFFE0E0E0.toInt()) {
            paint.color = colorHex
            paint.strokeWidth = thickness
            canvas.drawLine(startX, startY, endX, endY, paint)
        }

        // --- DRAW HEADER ---
        // Brand Name (Teal color: #008080)
        drawText("Bersih.in", margin, currentY, 24f, isBold = true, colorHex = 0xFF008080.toInt())
        currentY += 20f
        
        // Document Title
        drawText("LAPORAN PENDAPATAN & TRANSAKSI", margin, currentY, 12f, isBold = true, colorHex = 0xFF333333.toInt())
        
        // Printed Date
        val printDate = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale.forLanguageTag("id-ID")).format(Date())
        drawText("Tanggal Cetak: $printDate", pageWidth - margin - 180f, currentY, 9f, isBold = false, colorHex = 0xFF757575.toInt())
        currentY += 10f

        // Header Line Divider
        drawLine(margin, currentY, pageWidth - margin, currentY, thickness = 1.5f, colorHex = 0xFF008080.toInt())
        currentY += 25f

        // --- DRAW SUMMARY STATS ---
        drawText("Ringkasan Pendapatan", margin, currentY, 13f, isBold = true, colorHex = 0xFF333333.toInt())
        currentY += 15f

        // Latar Belakang Kartu Statistik Ringkasan (Light Teal Box)
        paint.color = 0xFFE0F2F1.toInt() // Sangat teal muda
        canvas.drawRect(margin, currentY, pageWidth - margin, currentY + 70f, paint)

        // Isi di dalam kotak statistik
        val statY = currentY + 25f
        drawText("Total Pendapatan", margin + 15f, statY, 10f, isBold = false, colorHex = 0xFF555555.toInt())
        drawText(CurrencyFormatter.formatIdr(totalRevenue), margin + 15f, statY + 20f, 15f, isBold = true, colorHex = 0xFF008080.toInt())

        val col2X = margin + 180f
        drawText("Total Transaksi", col2X, statY, 10f, isBold = false, colorHex = 0xFF555555.toInt())
        drawText("$totalCount Order", col2X, statY + 20f, 14f, isBold = true, colorHex = 0xFF333333.toInt())

        val col3X = margin + 300f
        drawText("Selesai / Menunggu", col3X, statY, 10f, isBold = false, colorHex = 0xFF555555.toInt())
        drawText("$completedCount Selesai / $pendingCount Pending", col3X, statY + 20f, 12f, isBold = true, colorHex = 0xFF333333.toInt())

        currentY += 95f

        // --- DRAW TABLE ---
        drawText("Rincian Transaksi Selesai", margin, currentY, 13f, isBold = true, colorHex = 0xFF333333.toInt())
        currentY += 15f

        // Table Header
        val colWidths = floatArrayOf(50f, 85f, 120f, 130f, 130f) // Total: 515 (lebar area printable)
        val colPositions = FloatArray(colWidths.size)
        var tempX = margin
        for (i in colWidths.indices) {
            colPositions[i] = tempX
            tempX += colWidths[i]
        }

        // Draw header background (Teal #008080)
        paint.color = 0xFF008080.toInt()
        canvas.drawRect(margin, currentY, pageWidth - margin, currentY + 22f, paint)

        val headerY = currentY + 15f
        drawText("ID", colPositions[0] + 5f, headerY, 9f, isBold = true, colorHex = 0xFFFFFFFF.toInt())
        drawText("Tanggal", colPositions[1] + 5f, headerY, 9f, isBold = true, colorHex = 0xFFFFFFFF.toInt())
        drawText("Pelanggan", colPositions[2] + 5f, headerY, 9f, isBold = true, colorHex = 0xFFFFFFFF.toInt())
        drawText("Layanan", colPositions[3] + 5f, headerY, 9f, isBold = true, colorHex = 0xFFFFFFFF.toInt())
        // Rata kanan untuk uang total
        paint.textAlign = Paint.Align.RIGHT
        drawText("Total", colPositions[4] + colWidths[4] - 8f, headerY, 9f, isBold = true, colorHex = 0xFFFFFFFF.toInt())
        paint.textAlign = Paint.Align.LEFT

        currentY += 22f

        // Draw Rows
        var isZebra = false
        val rowHeight = 22f
        val maxPageContentHeight = pageHeight - 50f

        orders.forEach { order ->
            // Cek apakah y melebihi batas halaman, jika ya buat halaman baru
            if (currentY + rowHeight > maxPageContentHeight) {
                pdfDocument.finishPage(page)
                pageNumber++
                pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                currentY = 40f

                // Tulis header tabel ulang di halaman baru
                paint.color = 0xFF008080.toInt()
                canvas.drawRect(margin, currentY, pageWidth - margin, currentY + 22f, paint)
                val newHeaderY = currentY + 15f
                drawText("ID", colPositions[0] + 5f, newHeaderY, 9f, isBold = true, colorHex = 0xFFFFFFFF.toInt())
                drawText("Tanggal", colPositions[1] + 5f, newHeaderY, 9f, isBold = true, colorHex = 0xFFFFFFFF.toInt())
                drawText("Pelanggan", colPositions[2] + 5f, newHeaderY, 9f, isBold = true, colorHex = 0xFFFFFFFF.toInt())
                drawText("Layanan", colPositions[3] + 5f, newHeaderY, 9f, isBold = true, colorHex = 0xFFFFFFFF.toInt())
                paint.textAlign = Paint.Align.RIGHT
                drawText("Total", colPositions[4] + colWidths[4] - 8f, newHeaderY, 9f, isBold = true, colorHex = 0xFFFFFFFF.toInt())
                paint.textAlign = Paint.Align.LEFT
                currentY += 22f
            }

            // Zebra background
            if (isZebra) {
                paint.color = 0xFFF5F5F5.toInt()
                canvas.drawRect(margin, currentY, pageWidth - margin, currentY + rowHeight, paint)
            }
            isZebra = !isZebra

            // Border bottom row
            drawLine(margin, currentY + rowHeight, pageWidth - margin, currentY + rowHeight, thickness = 0.5f, colorHex = 0xFFE0E0E0.toInt())

            val rowTextY = currentY + 14f
            val customerName = users.find { it.id == order.userId }?.name ?: "Unknown"
            val serviceName = services.find { it.id == order.serviceId }?.name ?: "Layanan #${order.serviceId}"
            val dateStr = DateFormatter.formatDateOnly(order.updatedAt)

            drawText("#${order.id}", colPositions[0] + 5f, rowTextY, 9f, colorHex = 0xFF333333.toInt())
            drawText(dateStr, colPositions[1] + 5f, rowTextY, 9f, colorHex = 0xFF333333.toInt())
            
            // Batasi panjang nama jika kepanjangan
            val shortCustomer = if (customerName.length > 20) customerName.take(18) + ".." else customerName
            drawText(shortCustomer, colPositions[2] + 5f, rowTextY, 9f, colorHex = 0xFF333333.toInt())
            
            val shortService = if (serviceName.length > 22) serviceName.take(20) + ".." else serviceName
            drawText(shortService, colPositions[3] + 5f, rowTextY, 9f, colorHex = 0xFF333333.toInt())

            // Format Rupiah rata kanan
            val formattedPrice = CurrencyFormatter.formatIdr(order.total)
            paint.textAlign = Paint.Align.RIGHT
            drawText(formattedPrice, colPositions[4] + colWidths[4] - 8f, rowTextY, 9f, colorHex = 0xFF333333.toInt())
            paint.textAlign = Paint.Align.LEFT

            currentY += rowHeight
        }

        // Tulis Footer halaman terakhir
        val footerY = pageHeight - 30f
        drawLine(margin, footerY - 5f, pageWidth - margin, footerY - 5f, thickness = 0.5f, colorHex = 0xFFBDBDBD.toInt())
        drawText("Laporan Keuangan Bersih.in - Terbuat Otomatis", margin, footerY + 8f, 8f, isBold = false, colorHex = 0xFF757575.toInt())
        drawText("Halaman $pageNumber", pageWidth - margin - 50f, footerY + 8f, 8f, isBold = false, colorHex = 0xFF757575.toInt())

        pdfDocument.finishPage(page)

        return try {
            val fos = FileOutputStream(file)
            pdfDocument.writeTo(fos)
            pdfDocument.close()
            fos.flush()
            fos.close()
            saveToPublicDownloads(context, file, "application/pdf")
        } catch (e: Exception) {
            e.printStackTrace()
            pdfDocument.close()
            null
        }
    }

    /**
     * Menyimpan file sementara ke folder unduhan publik (Downloads) di perangkat.
     * Menggunakan MediaStore pada Android 10+ (API 29+) sehingga bebas izin runtime.
     */
    private fun saveToPublicDownloads(context: Context, tempFile: File, mimeType: String): File? {
        val resolver = context.contentResolver
        val fileName = tempFile.name
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                    put(MediaStore.MediaColumns.IS_PENDING, 1)
                }
                var uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                
                // Fallback for CSV if resolver.insert fails due to unrecognized mimeType
                if (uri == null && mimeType == "text/csv") {
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
                    uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                }
                if (uri == null) {
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/octet-stream")
                    uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                }
                
                if (uri != null) {
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        tempFile.inputStream().use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    
                    // Release pending status to make it visible in File Manager
                    val updateValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.IS_PENDING, 0)
                    }
                    resolver.update(uri, updateValues, null, null)
                    
                    // Return representasi File di folder Download publik
                    return File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Fall through to legacy direct file system copy if MediaStore throws
            }
        }
        
        // Legacy or Fallback method
        try {
            val publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!publicDir.exists()) {
                publicDir.mkdirs()
            }
            val destFile = File(publicDir, fileName)
            tempFile.copyTo(destFile, overwrite = true)
            return destFile
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
