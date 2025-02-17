package util

import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun logException(thread: Thread, exception: Throwable) {
    val timestamp = LocalDateTime.now().format(
        DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
    )
    val logFileName = "error_log_$timestamp.txt"
    val logFile = File(logFileName)
    try {
        logFile.createNewFile()
        PrintWriter(logFile.writer()).use { writer ->
            writer.println("Timestamp: $timestamp")
            writer.println("Thread: ${thread.name}")
            writer.println("Exception: ${exception.javaClass.name}: ${exception.message}")
            val sw = StringWriter()
            exception.printStackTrace(PrintWriter(sw))
            writer.println("Stacktrace:\n$sw")
        }
        println("Error log saved to: $logFileName")
    } catch (e: Exception) {
        System.err.println("Failed to write error log to file: ${e.message}")
        e.printStackTrace()
    }
}
