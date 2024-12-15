package com.guidal.core.ui.models

import org.junit.Test
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse

class UiModelTopAppBarIconUnitTest {

    @Test
    fun `test UiModelTopAppBarIcon initialization`() {
        val icon = ImageVector.Builder(
            defaultWidth = 0.dp, defaultHeight =  0.dp, viewportWidth = 0f, viewportHeight = 0f
        ).build()
        val onClick = {}
        val color = Color.Red
        val isEnabled = true
        val contentDescription = "Test Icon"
        val size= 32.dp

        val iconModel = UiModelTopAppBarIcon(
            icon = icon,
            onClick = onClick,
            color = color,
            isEnabled = isEnabled,
            contentDescription = contentDescription,
            size = size
        )

        assertEquals(icon, iconModel.icon)
        assertEquals(onClick, iconModel.onClick)
        assertEquals(color, iconModel.color)
        assertEquals(isEnabled, iconModel.isEnabled)
        assertEquals(contentDescription, iconModel.contentDescription)
        assertEquals(size, iconModel.size)
    }

    @Test
    fun `test default values for UiModelTopAppBarIcon`() {
        val icon = ImageVector.Builder(
            defaultWidth = 0.dp, defaultHeight =  0.dp, viewportWidth = 0f, viewportHeight = 0f
        ).build()
        val onClick = {}
        val color = Color.Blue

        val iconModel = UiModelTopAppBarIcon(
            icon = icon,
            onClick = onClick,
            color = color
        )

        assertEquals(true, iconModel.isEnabled)
        assertEquals("", iconModel.contentDescription)
        assertEquals(24.dp, iconModel.size)
    }

    @Test
    fun `test UiModelTopAppBarIcon with different isEnabled value`() {
        val icon = ImageVector.Builder(
            defaultWidth = 0.dp, defaultHeight =  0.dp, viewportWidth = 0f, viewportHeight = 0f
        ).build()
        val onClick = {}
        val color = Color.Green
        val isEnabled = false

        val iconModel = UiModelTopAppBarIcon(
            icon = icon,
            onClick = onClick,
            color = color,
            isEnabled = isEnabled
        )

        assertFalse(iconModel.isEnabled)
    }
}
