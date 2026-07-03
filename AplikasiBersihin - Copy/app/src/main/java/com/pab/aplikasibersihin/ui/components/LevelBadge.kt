package com.pab.aplikasibersihin.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
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
import com.pab.aplikasibersihin.data.model.MemberLevel
import com.pab.aplikasibersihin.ui.theme.*

@Composable
fun LevelBadge(levelName: String, modifier: Modifier = Modifier) {
    val level = try { MemberLevel.valueOf(levelName.uppercase()) } catch (e: Exception) { MemberLevel.BRONZE }

    val gradient = when (level) {
        MemberLevel.BRONZE -> LevelGradientBronze
        MemberLevel.SILVER -> LevelGradientSilver
        MemberLevel.GOLD -> LevelGradientGold
        MemberLevel.PLATINUM -> LevelGradientPlatinum
    }

    val stars = when (level) {
        MemberLevel.BRONZE -> 1
        MemberLevel.SILVER -> 2
        MemberLevel.GOLD -> 3
        MemberLevel.PLATINUM -> 4
    }

    val borderColor = when (level) {
        MemberLevel.BRONZE -> Color.Transparent
        MemberLevel.SILVER -> Color.Transparent
        MemberLevel.GOLD -> ColorGold.copy(alpha = 0.4f)
        MemberLevel.PLATINUM -> ColorPlatinum.copy(alpha = 0.5f)
    }

    // Animated glow for Platinum & Gold
    val infiniteTransition = rememberInfiniteTransition(label = "LevelGlow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowAlpha"
    )

    val isPremium = level == MemberLevel.PLATINUM || level == MemberLevel.GOLD
    val actualGradient = if (isPremium) {
        gradient.map { it.copy(alpha = glowAlpha) }
    } else {
        gradient
    }

    Box(
        modifier = modifier
            .then(
                if (borderColor != Color.Transparent) {
                    Modifier.border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else Modifier
            )
            .background(
                brush = Brush.horizontalGradient(actualGradient),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier.padding(end = 4.dp)) {
                repeat(stars) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Bintang",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Text(
                text = level.name,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}
