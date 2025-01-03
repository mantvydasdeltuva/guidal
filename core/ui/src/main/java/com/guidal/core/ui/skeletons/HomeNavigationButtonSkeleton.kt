package com.guidal.core.ui.skeletons

import com.guidal.core.ui.modifiers.shimmerModifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.theme.GuidalTheme

@Composable
fun HomeNavigationButtonSkeleton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surfaceBright
            )
            .padding(all = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .shimmerModifier(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp)
                ),
        )

        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(width = 70.dp, height = 16.dp)
                .shimmerModifier(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                ),
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewHomeNavigationButtonSkeleton() {
    GuidalTheme {
        HomeNavigationButtonSkeleton()
    }
}