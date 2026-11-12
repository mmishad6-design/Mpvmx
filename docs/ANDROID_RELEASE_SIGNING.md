# Android release signing for mpvmx

mpvmx must be signed with the same private key for every APK that is intended to update an existing installation. The keystore and passwords must never be committed to Git.

## 1. Create the keystore (on your own computer)

Install a JDK that provides `keytool`, then run:

```bash
keytool -genkeypair -v -keystore mpvmx-release.jks -alias mpvmx -keyalg RSA -keysize 4096 -validity 10000
```

Choose strong passwords and keep them in a password manager. Keep at least two secure offline backups of `mpvmx-release.jks`. If this file or its passwords are lost, future APKs cannot update installs signed with this key.

Do not send the keystore or passwords in chat, email, issues, or commits.

## 2. Add these four GitHub Actions repository secrets

Open **Settings → Secrets and variables → Actions → New repository secret** in the `mmishad6-design/Mpvmx` repository.

| Secret name | Value |
| --- | --- |
| `MPVMX_SIGNING_KEYSTORE_BASE64` | Base64 encoding of the complete `mpvmx-release.jks` file |
| `MPVMX_SIGNING_STORE_PASSWORD` | Keystore password |
| `MPVMX_SIGNING_KEY_ALIAS` | `mpvmx` (or the alias chosen above) |
| `MPVMX_SIGNING_KEY_PASSWORD` | Key password |

Create the Base64 value locally:

**Linux:**
```bash
base64 -w 0 mpvmx-release.jks
```

**macOS:**
```bash
base64 < mpvmx-release.jks | tr -d '\\n'
```

**Windows PowerShell:**
```powershell
[Convert]::ToBase64String([IO.File]::ReadAllBytes("mpvmx-release.jks")) | Set-Clipboard
```

Paste the resulting Base64 text into the `MPVMX_SIGNING_KEYSTORE_BASE64` secret. Add the other three values as separate secrets. Never put any of these values in workflow YAML.

## 3. Build behavior

- Pull-request validation builds deliberately do not receive signing secrets and use the temporary debug key.
- Main-branch builds use the persistent keystore when all four secrets are present.
- If signing secrets are not configured, CI falls back to debug signing so source/build validation can still run. Such APKs are previews only and are not suitable as a permanent update channel.
- The workflow writes the decoded keystore only to the runner's temporary directory; it is not uploaded as an artifact.

## Important compatibility notes

Android accepts an in-place update only when the package ID and signing certificate match the installed app.

The current application ID is `app.gyrolet.mpvrx`. The planned final ID `app.mishad.mpvmx` is a different app identity; changing it means users must install the final app separately. Existing preview APKs signed with temporary CI debug keys may also need to be uninstalled before installing the first APK signed with this permanent key.

Do not publish a production update until the permanent key is configured, the final package ID is settled, and an update has been tested on a device.
