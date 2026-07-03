package com.pab.aplikasibersihin.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pab.aplikasibersihin.ui.theme.PrimaryTeal
import com.pab.aplikasibersihin.ui.theme.AccentMint
import com.pab.aplikasibersihin.utils.XpCalculator

@Composable
fun XpProgressBar(xp: Int, modifier: Modifier = Modifier) {
    val progression = XpCalculator.calculateProgression(xp)
    
    // Animate the progress bar when loaded
    val animatedProgress by animateFloatAsState(
        targetValue = progression.progressPercentage,
        animationSpec = tween(durationMillis = 1000),
        label = "XpProgress"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${xp} XP",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryTeal
            )
            
            progression.nextLevel?.let { next ->
                Text(
                    text = "Lanjut ke ${next.name} (${progression.remainingXp} XP lagi)",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            } ?: Text(
                text = "Level Maksimal",
                fontSize = 11.sp,
                color = PrimaryTeal,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Progress bar container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.LightGray.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedProgress)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(PrimaryTeal, AccentMint)
                        )
                    )
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = progression.currentLevel.name,
                fontSize = 10.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
            progression.nextLevel?.let { next ->
                Text(
                    text = next.name,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
