package com.kenruizinoue.floatingplaybackbarcomposable.data

data class SelectedTrackState(
    val track: TrackItemData = TrackItemData(),
    val playbackState: PlaybackState = PlaybackState.PAUSED
)