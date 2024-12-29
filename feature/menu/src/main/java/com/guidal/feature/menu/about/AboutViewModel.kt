package com.guidal.feature.menu.about

import android.app.Application
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO Extract string resources
@HiltViewModel
internal class AboutViewModel @Inject constructor(
    private val context: Application
) : ViewModel() {
    private val _uiState = MutableStateFlow<AboutUiState>(
        value = AboutUiState.Loading()
    )

    val uiState = _uiState.asStateFlow()

    init {
        val packageInfo: PackageInfo? = try {
            context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }

        _uiState.update {
            it.transformTo<AboutUiState.Idle>().copy(
                version = packageInfo?.let { info ->
                    "${info.versionName} (${info.versionCode})"
                } ?: "unknown"
            )
        }
    }

    fun resetState() {
        _uiState.update {
            it.transformTo<AboutUiState.Idle>().copy(
                isNavigating = false
            )
        }
    }

    fun onNavigation() {
        _uiState.update {
            it.transformTo<AboutUiState.Idle>().copy(
                isNavigating = true
            )
        }
    }

    fun toExternalLink(url: String) {
        viewModelScope.launch {
            _uiState.update {
                it.transformTo<AboutUiState.Idle>().copy(
                    isNavigating = true
                )
            }

            startActivity(url)

            _uiState.update {
                it.transformTo<AboutUiState.Idle>().copy(
                    isNavigating = false
                )
            }
        }
    }

    private fun startActivity(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val chooser = Intent.createChooser(intent, "Choose your browser").apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(chooser)
    }
}