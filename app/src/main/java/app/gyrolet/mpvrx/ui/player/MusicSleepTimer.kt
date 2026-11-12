/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package app.gyrolet.mpvrx.ui.player

import android.os.SystemClock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/** A session-scoped sleep timer for music playback (never video or audiobooks). */
internal object MusicSleepTimer {
  data class Timer(val deadlineElapsedRealtime: Long, val remainingSeconds: Int)

  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
  private val _timer = MutableStateFlow<Timer?>(null)
  val timer = _timer.asStateFlow()
  @Volatile private var started = false

  private fun ensureStarted() {
    if (started) return
    synchronized(this) {
      if (started) return
      started = true
      scope.launch {
        while (isActive) {
          val activeTimer = _timer.value
          if (activeTimer != null) {
            val item = PlaybackSession.state.value.currentItem
            val state = PlaybackSession.state.value
            if (item == null || item.audiobook != null || !item.isDefinitelyAudioOnly() ||
              state.phase in setOf(PlaybackPhase.IDLE, PlaybackPhase.STOPPING, PlaybackPhase.ERROR)
            ) {
              _timer.value = null
            } else {
              val remaining = ((activeTimer.deadlineElapsedRealtime - SystemClock.elapsedRealtime() + 999) / 1000)
                .coerceAtLeast(0).toInt()
              if (remaining == 0) {
                _timer.value = null
                PlaybackSession.setPropertyBoolean("pause", true)
              } else if (remaining != activeTimer.remainingSeconds) {
                _timer.value = activeTimer.copy(remainingSeconds = remaining)
              }
            }
          }
          delay(250)
        }
      }
    }
  }

  fun setTimer(minutes: Int) {
    val item = PlaybackSession.state.value.currentItem ?: return
    if (item.audiobook != null || !item.isDefinitelyAudioOnly() || minutes !in 1..240) return
    ensureStarted()
    val seconds = minutes * 60
    _timer.value = Timer(SystemClock.elapsedRealtime() + seconds * 1000L, seconds)
  }

  fun clearTimer() {
    _timer.value = null
  }
}
