package com.guidal.core.ui.skeletons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.modifiers.shimmerModifier
import com.guidal.core.ui.theme.GuidalTheme
import kotlin.random.Random

@Composable
fun TextBlockSkeleton(
    modifier: Modifier = Modifier
) {
    Column() {
        TextLineSkeleton(widthFraction = 1f)
        TextLineSkeleton(widthFraction = 0.9f)
        TextLineSkeleton(widthFraction = 0.6f)
        TextLineSkeleton(widthFraction = 0.8f)
        TextLineSkeleton(widthFraction = 0.4f)
    }
}

@Composable
fun TextLineSkeleton(
    modifier: Modifier = Modifier,
    widthFraction: Float = 1f
) {
    Box(
        modifier = modifier
            .fillMaxWidth(widthFraction)
            .height(20.dp)
            .padding(bottom = 10.dp)
            .shimmerModifier(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
    )
}

@Preview(showBackground = false)
@Composable
private fun PreviewTextBlockSkeleton() {
    GuidalTheme {
        TextBlockSkeleton()
    }
}