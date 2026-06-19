package feature_post_detail.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun PostDetailSkeleton(
    brush: Brush, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
    ) {

        // Community · Author row
        Row {
            Box(
                modifier = Modifier.width(90.dp).height(10.dp).clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier.width(70.dp).height(10.dp).clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Title — 2 lines
        Box(
            modifier = Modifier.fillMaxWidth().height(20.dp).clip(RoundedCornerShape(4.dp))
                .background(brush)
        )
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier.fillMaxWidth(0.75f).height(20.dp).clip(RoundedCornerShape(4.dp))
                .background(brush)
        )

        Spacer(Modifier.height(20.dp))

        // Body — 4 lines
        repeat(4) { index ->
            Box(
                modifier = Modifier.fillMaxWidth(if (index == 3) 0.5f else 1f).height(14.dp)
                    .clip(RoundedCornerShape(4.dp)).background(brush)
            )
            Spacer(Modifier.height(6.dp))
        }

        Spacer(Modifier.height(20.dp))

        // Score · Comment · Time row
        Row {
            Box(
                modifier = Modifier.width(48.dp).height(10.dp).clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier.width(48.dp).height(10.dp).clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier.width(32.dp).height(10.dp).clip(RoundedCornerShape(4.dp))
                    .background(brush)
            )
        }
    }
}