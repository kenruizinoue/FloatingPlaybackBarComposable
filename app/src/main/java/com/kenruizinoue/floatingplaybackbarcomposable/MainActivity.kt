package com.kenruizinoue.floatingplaybackbarcomposable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.kenruizinoue.floatingplaybackbarcomposable.composable.FloatingPlaybackBar
import com.kenruizinoue.floatingplaybackbarcomposable.data.PlaybackState
import com.kenruizinoue.floatingplaybackbarcomposable.data.SelectedTrackState
import com.kenruizinoue.floatingplaybackbarcomposable.data.TrackItemData
import kotlinx.coroutines.flow.flowOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 1. Simulated Track State
            val trackState = remember {
                mutableStateOf(
                    SelectedTrackState(
                        track = TrackItemData(
                            title = "Some Title",
                            artist = "Some Artist",
                            coverDrawableId = R.drawable.fallback_album_cover
                        ),
                        playbackState = PlaybackState.PAUSED
                    )
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingPlaybackBar(
                    selectedTrackStateFlow = flowOf(trackState.value),
                    // 2. Play/Pause Interaction Logic
                    onPlayPauseClicked = {
                        trackState.value = trackState.value.copy(
                            playbackState = if (trackState.value.playbackState == PlaybackState.PLAYING)
                                PlaybackState.PAUSED else PlaybackState.PLAYING
                        )
                    }
                )
                // 3. Playback Status Display
                Text(
                    text =
                    if (trackState.value.playbackState == PlaybackState.PLAYING) "Playing"
                    else "Paused",
                    fontSize = 20.sp
                )
            }
        }
    }
}