/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package app.gyrolet.mpvrx.ui.preferences

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import app.gyrolet.mpvrx.R
import app.gyrolet.mpvrx.presentation.Screen
import app.gyrolet.mpvrx.ui.utils.LocalBackStack
import app.gyrolet.mpvrx.ui.utils.popSafely
import app.gyrolet.mpvrx.utils.clipboard.SafeClipboard
import kotlinx.serialization.Serializable

@Serializable
object DeveloperInfoScreen : Screen {
  @Composable
  override fun Content() {
    val context = LocalContext.current
    val backstack = LocalBackStack.current

    Scaffold(
      topBar = {
        SettingsTopAppBar(
          title = stringResource(R.string.pref_hall_of_fame_title),
          onBack = { backstack.popSafely() },
          titleModifier = Modifier.settingsSearchTarget(R.string.pref_hall_of_fame_title),
        )
      },
    ) { padding ->
      Column(
        modifier = Modifier.padding(padding).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
      ) {
        PreferenceSectionHeader(
          position = PreferenceSectionPosition.First,
          title = stringResource(R.string.ui_developer_info),
        )
        PreferenceCard {
          Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
          ) {
            Text(
              text = stringResource(R.string.ui_developer_name),
              style = MaterialTheme.typography.headlineSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Button(
              onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, "https://github.com/mmishad6-design".toUri()))
              },
              modifier = Modifier.fillMaxWidth().height(48.dp),
            ) {
              Text(stringResource(R.string.ui_developer_github))
            }
          }
        }

        PreferenceSectionHeader(
          position = PreferenceSectionPosition.Middle,
          title = stringResource(R.string.ui_developer_telegram_community),
        )
        PreferenceCard {
          Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            Button(
              onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, "https://t.me/mpvmx".toUri()))
              },
              modifier = Modifier.fillMaxWidth().height(48.dp),
            ) {
              Text(stringResource(R.string.ui_telegram_channel))
            }
            Button(
              onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, "https://t.me/mpvmxofficial".toUri()))
              },
              modifier = Modifier.fillMaxWidth().height(48.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
              ),
            ) {
              Text(stringResource(R.string.ui_telegram_group))
            }
          }
        }

        PreferenceSectionHeader(
          position = PreferenceSectionPosition.Middle,
          title = stringResource(R.string.ui_treat_me_a_coffee),
        )
        PreferenceCard {
          Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            Text(
              text = stringResource(R.string.ui_crypto_support_summary),
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
              text = stringResource(R.string.ui_bep20_network),
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary,
            )
            Text(
              text = "0x33D54Fe1bb30657De7214B0581cA6D8fC9e65b8D",
              style = MaterialTheme.typography.bodySmall,
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = stringResource(R.string.ui_crypto_network_warning),
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.error,
            )
            Button(
              onClick = {
                SafeClipboard.copyPlainText(
                  context,
                  "mpvmx_bep20_address",
                  "0x33D54Fe1bb30657De7214B0581cA6D8fC9e65b8D",
                )
              },
              modifier = Modifier.fillMaxWidth().height(48.dp),
            ) {
              Text(stringResource(R.string.ui_copy_wallet_address))
            }
          }
        }

        Spacer(Modifier.height(8.dp))
        Text(
          text = stringResource(R.string.ui_developer_thanks_mpvrx),
          modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(12.dp))
      }
    }
  }
}
