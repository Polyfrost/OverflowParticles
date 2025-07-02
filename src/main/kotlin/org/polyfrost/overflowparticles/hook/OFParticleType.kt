package org.polyfrost.overflowparticles.hook

//#if MC >= 1.16.5
//$$import net.minecraft.client.particle.Particle
//$$import net.minecraft.core.particles.ParticleType
//$$
//$$interface OFParticleType {
//$$
//$$    companion object {
//$$
//$$        @JvmStatic
//$$        fun getParticleType(entity: Particle): ParticleType<*>? {
//$$            return (entity as? OFParticleType)?.`overflowparticles$getParticleType`()
//$$        }
//$$
//$$        @JvmStatic
//$$        fun setParticleType(entity: Particle, particleType: ParticleType<*>) {
//$$            (entity as? OFParticleType)?.`overflowparticles$setParticleType`(particleType)
//$$        }
//$$
//$$    }
//$$
//$$    fun `overflowparticles$getParticleType`(): ParticleType<*>
//$$
//$$    fun `overflowparticles$setParticleType`(particleType: ParticleType<*>)
//$$
//$$}
//#endif
