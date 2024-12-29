package org.polyfrost.overflowparticles.config

class ParticleConfig(val name: String, val id: Int) {

    val entry: ParticleEntry
        get() {
            ModConfig.particles[name] ?: ModConfig.particles.put(name, ParticleEntry())
            return ModConfig.particles[name]!!
        }

}