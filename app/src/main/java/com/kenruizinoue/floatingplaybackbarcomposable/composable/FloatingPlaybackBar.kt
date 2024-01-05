package com.kenruizinoue.floatingplaybackbarcomposable.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.kenruizinoue.floatingplaybackbarcomposable.R
import com.kenruizinoue.floatingplaybackbarcomposable.data.PlaybackState
import com.kenruizinoue.floatingplaybackbarcomposable.ui.constants.FloatingPlaybackBarButtonIconSize
import com.kenruizinoue.floatingplaybackbarcomposable.ui.constants.FloatingPlaybackBarButtonSize
import com.kenruizinoue.floatingplaybackbarcomposable.ui.constants.FloatingPlaybackBarCoverSize
import com.kenruizinoue.floatingplaybackbarcomposable.ui.constants.FloatingPlaybackBarHeight
import com.kenruizinoue.floatingplaybackbarcomposable.ui.constants.FloatingPlaybackBarPrimaryTextStyle
import com.kenruizinoue.floatingplaybackbarcomposable.ui.constants.FloatingPlaybackBarSecondaryTextStyle
import com.kenruizinoue.floatingplaybackbarcomposable.ui.constants.LargeDp
import com.kenruizinoue.floatingplaybackbarcomposable.ui.constants.MediumDp
import com.kenruizinoue.floatingplaybackbarcomposable.ui.constants.PlaybackBarColor
import com.kenruizinoue.floatingplaybackbarcomposable.ui.constants.PrimaryWhite
import com.kenruizinoue.floatingplaybackbarcomposable.ui.constants.SmallDp
import com.kenruizinoue.floatingplaybackbarcomposable.data.SelectedTrackState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun FloatingPlaybackBar(
    // 1. Dynamic State Management with Flow
    selectedTrackStateFlow: Flow<SelectedTrackState> = flowOf(SelectedTrackState()),
    // 2. Interactive Controls through Lambda Functions
    onPreviousClicked: () -> Unit = {},
    onPlayPauseClicked: () -> Unit = {},
    onNextClicked: () -> Unit = {}
) {
    // 3. Real-Time UI Updates with State Observing
    val selectedTrackState = selectedTrackStateFlow.collectAsState(initial = SelectedTrackState()).value
    // 4. Efficient Image Rendering with Coil
    val imagePainter = rememberImagePainter(
        data =
        // 5. Image Handling with Fallbacks
        if (selectedTrackState.track.coverDrawableId == -1) null
        else selectedTrackState.track.coverDrawableId,
        builder = {
            fallback(R.drawable.fallback_album_cover)
        }
    )
    Card(
        modifier = Modifier
            .padding(MediumDp)
            .height(FloatingPlaybackBarHeight)
            .background(Color.Transparent)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = LargeDp),
        shape = RoundedCornerShape(MediumDp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(PlaybackBarColor)
                .padding(SmallDp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(FloatingPlaybackBarCoverSize)
                    .clip(RoundedCornerShape(MediumDp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = LargeDp)
                    .weight(1f)
            ) {
                Text(
                    style = FloatingPlaybackBarPrimaryTextStyle,
                    text = selectedTrackState.track.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    style = FloatingPlaybackBarSecondaryTextStyle,
                    text = selectedTrackState.track.artist,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            // 6. Wrapping Icon inside IconButton
            IconButton(
                onClick = { onPreviousClicked() },
                modifier = Modifier.size(FloatingPlaybackBarButtonSize)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_previous),
                    contentDescription = null,
                    tint = PrimaryWhite,
                    modifier = Modifier.size(FloatingPlaybackBarButtonIconSize)
                )
            }
            // 7. Responsive Control Icons
            val iconId =
                if (selectedTrackState.playbackState == PlaybackState.PLAYING) {
                    R.drawable.ic_pause
                } else {
                    R.drawable.ic_play
                }
            IconButton(
                onClick = { onPlayPauseClicked() },
                modifier = Modifier.size(FloatingPlaybackBarButtonSize)
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    tint = PrimaryWhite,
                    modifier = Modifier.size(FloatingPlaybackBarButtonIconSize)
                )
            }
            IconButton(
                onClick = { onNextClicked() },
                modifier = Modifier.size(FloatingPlaybackBarButtonSize)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = null,
                    tint = PrimaryWhite,
                    modifier = Modifier.size(FloatingPlaybackBarButtonIconSize)
                )
            }
        }
    }
}

@Preview
@Composable
fun FloatingPlaybackBarPreview() {
    FloatingPlaybackBar()
}