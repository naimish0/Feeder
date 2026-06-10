package com.example.feeder

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import feature_feed.presentation.viewmodel.FeedViewModel
import org.koin.mp.KoinPlatform.getKoin

fun MainViewController() = ComposeUIViewController {
    val feedViewMode = remember { getKoin().get<FeedViewModel>() }
    App(feedViewMode)
}