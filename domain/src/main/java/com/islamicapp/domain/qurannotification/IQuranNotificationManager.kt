package com.islamicapp.domain.qurannotification

/**
 * Interface for showing notifications
 * Decouples data layer from presentation layer
 */
interface IQuranNotificationManager {
    fun showNotification(reminder: QuranReminder)
}