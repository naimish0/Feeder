package core.util

fun String.toTimeAgo(): String {
    return try {
        val instant = kotlin.time.Instant.parse(this)
        val diff = kotlin.time.Clock.System.now() - instant
        when {
            diff.inWholeSeconds < 60 -> "just now"
            diff.inWholeMinutes < 60 -> "${diff.inWholeMinutes}m"
            diff.inWholeHours < 24 -> "${diff.inWholeHours}h"
            diff.inWholeDays < 7 -> "${diff.inWholeDays}d"
            diff.inWholeDays < 30 -> "${diff.inWholeDays / 7}w"
            diff.inWholeDays < 365 -> "${diff.inWholeDays / 30}mo"
            else -> "${diff.inWholeDays / 365}y"
        }
    } catch (e: Exception) {
        ""  // Lemmy date parse fail — show nothing
    }
}

fun Long.formatCount(): String = when {
    this >= 1_000_000 -> "${this / 1_000_000}M"
    this >= 1_000 -> "${this / 1_000}k"
    else -> this.toString()
}