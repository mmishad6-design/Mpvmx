import { defineConfig } from "blume";

export default defineConfig({
  title: "mpvmx — MISHAD EDITION",
  description:
    "mpvmx is a free, open-source Android media player customized by Mishad. Enjoy video and music playback, subtitles, streaming, playback controls, and a built-in music sleep timer.",
  logo: {
    href: "/",
    image: "/images/icon.svg",
    text: "mpvmx",
  },
  banner: {
    content: "mpvmx — MISHAD EDITION",
    link: {
      href: "https://github.com/mmishad6-design/Mpvmx/releases/latest",
      text: "Download Stable",
    },
  },
  content: {
    root: "content",
  },
  navigation: {
    actions: [
      { href: "/docs", label: "Docs" },
      { href: "https://github.com/mmishad6-design/Mpvmx/releases", label: "Releases" },
    ],
    cta: {
      href: "https://github.com/mmishad6-design/Mpvmx/releases/latest",
      label: "Download mpvmx",
    },
    repo: true,
  },
  search: {
    provider: "orama",
    popular: [
      { href: "/docs/getting-started/installation", icon: "download", label: "Install mpvmx" },
      { href: "/docs/playback/controls-and-gestures", icon: "gamepad-2", label: "Player controls" },
      { href: "/docs/customization/custom-commands", icon: "terminal", label: "Scripting API" },
    ],
  },
  ai: {
    api: true,
    llmsTxt: {
      enabled: true,
      details:
        "mpvmx (MISHAD EDITION) is a free, open-source Android media player customized by Mishad and based on the mpvRx project. These docs cover installation, video and music playback, subtitles, streaming, configuration, and scripting.",
    },
  },
  deployment: {
    output: "static",
  },
  github: {
    owner: "mmishad6-design",
    repo: "Mpvmx",
    branch: "main",
    dir: "website/content",
  },
  seo: {
    og: {
      logo: false,
      palette: {
        accent: "#8b6cff",
        background: "#0c0a14",
        border: "#30264a",
        foreground: "#f7f4ff",
        muted: "#b8afd0",
      },
      titles: {
        "/": "mpvmx — MISHAD EDITION | Android Media Player",
      },
    },
    software: {
      applicationCategory: "MultimediaApplication",
      license: "AGPL-3.0-or-later",
      name: "mpvmx — MISHAD EDITION",
      operatingSystem: "Android",
      price: 0,
      sameAs: [
        "https://github.com/mmishad6-design/Mpvmx",
        "https://github.com/mmishad6-design",
      ],
    },
  },
  theme: {
    accent: {
      dark: "#9b82ff",
      light: "#6547d7",
    },
    action: "#805cf5",
    background: {
      dark: "#0c0a14",
      light: "#f7f5ff",
    },
    backgroundImage: {
      dark: "radial-gradient(circle at 85% 0%, rgba(139, 108, 255, 0.14), transparent 34%)",
      light: "radial-gradient(circle at 85% 0%, rgba(101, 71, 215, 0.1), transparent 36%)",
    },
    fonts: {
      body: { name: "IBM Plex Sans", weights: [400, 500, 600, 700] },
      display: { name: "Space Grotesk", weights: [500, 600, 700] },
      mono: "ibm-plex-mono",
    },
    mode: "system",
    radius: "sm",
  },
});
