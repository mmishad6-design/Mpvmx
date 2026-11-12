/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package app.gyrolet.mpvrx.ui.player.controls.components.sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.gyrolet.mpvrx.R
import app.gyrolet.mpvrx.presentation.components.PlayerSheet
import app.gyrolet.mpvrx.ui.player.MusicSleepTimer
import androidx.compose.ui.res.stringResource

@Composable
internal fun MusicSleepTimerSheet(onDismiss: () -> Unit) {
  val timer by MusicSleepTimer.timer.collectAsStateWithLifecycle()
  var customMinutes by remember { mutableStateOf("90") }
  val presets = listOf(15, 30, 45, 60)

  PlayerSheet(
    onDismissRequest = onDismiss,
    title = stringResource(R.string.music_sleep_timer_title),
  ) {
    Column(
      modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      if (timer != null) {
        val seconds = timer!!.remainingSeconds
        Text(
          text = stringResource(
            R.string.music_sleep_timer_remaining,
            seconds / 60,
            seconds % 60,
          ),
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.primary,
        )
        Surface(
          modifier = Modifier.fillMaxWidth().clickable {
            MusicSleepTimer.clearTimer()
            onDismiss()
          },
          shape = RoundedCornerShape(14.dp),
          color = MaterialTheme.colorScheme.errorContainer,
        ) {
          Text(
            text = stringResource(R.string.music_sleep_timer_cancel),
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onErrorContainer,
            style = MaterialTheme.typography.bodyLarge,
          )
        }
      }
      Text(
        text = stringResource(R.string.music_sleep_timer_choose_duration),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      presets.forEach { minutes ->
        Surface(
          modifier = Modifier.fillMaxWidth().clickable {
            MusicSleepTimer.setTimer(minutes)
            onDismiss()
          },
          shape = RoundedCornerShape(14.dp),
          color = if (timer?.remainingSeconds?.let { it / 60 == minutes } == true)
            MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surfaceContainer,
        ) {
          Text(
            text = stringResource(R.string.music_sleep_timer_minutes, minutes),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
          )
        }
      }
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
      ) {
        OutlinedTextField(
          value = customMinutes,
          onValueChange = { value -> customMinutes = value.filter(Char::isDigit).take(3) },
          modifier = Modifier.weight(1f),
          label = { Text(stringResource(R.string.music_sleep_timer_custom_minutes)) },
          singleLine = true,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Button(
          onClick = {
            customMinutes.toIntOrNull()?.takeIf { it in 1..240 }?.let(MusicSleepTimer::setTimer)
            onDismiss()
          },
          enabled = customMinutes.toIntOrNull()?.let { it in 1..240 } == true,
          modifier = Modifier.alignByBaseline(),
        ) {
          Text(stringResource(R.string.music_sleep_timer_start))
        }
      }
      Text(
        text = stringResource(R.string.music_sleep_timer_note),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
  }
}
