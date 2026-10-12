# OverflowParticles

[![Modrinth downloads](https://img.shields.io/modrinth/dt/61g97TVF?style=flat&logo=modrinth&label=Modrinth&color=1bd96a)](https://modrinth.com/mod/overflowparticles)
[![CurseForge downloads](https://img.shields.io/curseforge/dt/1147767?style=flat&logo=curseforge&label=CurseForge&color=f16436)](https://www.curseforge.com/minecraft/mc-mods/overflowparticles)

OverflowParticles gives each particle type its own settings. You can hide a particle, recolor it, resize it, spawn more of it and make it fade out. The changes only affect what you see.

## Features

### Per-particle settings

The settings screen lists the vanilla particle types, such as Critical, Flame, Smoke and Portal. Each one has these options:

- A toggle that stops the particle from spawning.
- A custom color. "Multiply" tints the particle's own color and "Override" replaces it.
- A size slider from 0.5 to 5 times the vanilla size.
- A multiplier from 0 to 10 that sets how many particles spawn for each vanilla one. The mod rounds the value up to a whole number, and 0 spawns none.
- A fade, which lowers the particle's opacity toward the end of its life. "Fade Out Start" sets the point where the fade begins, from 0 to 1 of the particle's lifespan. The default is 0.5.

Explosion and firework particles have no fade option.

### Block particles

Block particles have a different set of options:

- Hide the particles you see while you mine a block and when a block breaks.
- Hide the particles that entities kick up when they land. You can limit this to visible entities.
- Fade the particles out, with the same "Fade Out Start" slider.

### Hit particles

These options are off by default.

- "Always Show Critical" shows critical hit particles on hits that are not critical.
- "Always Show Sharpness" shows sharpness particles when the weapon has no enchantments.
- "Check Invulnerability" only shows them for your own hits, after the server sends an event for the entity you hit.

### Other options

These options are also off by default.

- "Clean View" hides your own potion effect particles while you are in first person.
- "Particles No-Clip" lets particles pass through blocks. The mod skips their collision checks.
- "Static Particle Color" draws particles at full brightness and skips the light lookup the game does for each particle every frame.

## Supported versions

- Minecraft 1.8.9 on Ornithe.
- Minecraft 1.21.1, 1.21.4, 1.21.5, 1.21.8, 1.21.10, 1.21.11, 26.1, 26.2 and 26.3 on Fabric.

OverflowParticles needs [OneConfig](https://modrinth.com/mod/oneconfig) and Fabric Language Kotlin. The Fabric builds also need Fabric API.

## Support

- Report bugs and ask questions on the [Polyfrost Discord](https://polyfrost.org/discord).
- Check [Polyfrost Status](https://status.polyfrost.org/) if a Polyfrost service is down.

## Building

```sh
./gradlew ":26.3:build"
```

Replace `26.3` with any supported Minecraft version. The build needs JDK 21 and JDK 25 installed.
