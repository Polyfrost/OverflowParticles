plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "26.3" /* [SC] DO NOT EDIT */

stonecutter {
    tasks {
        order("publishModrinth")
    }

    parameters {
        replacements {
            regex(eval(current.version, "= 1.8.9")) {
                replace("(?<![\\w.])ParticleEngine\\b", "ParticleManager", "(?<![\\w.])ParticleManager\\b", "ParticleEngine")
                replace("(?<![\\w.])TrackingEmitter\\b", "EmitterParticle", "(?<![\\w.])EmitterParticle\\b", "TrackingEmitter")
                replace("(?<![\\w.])ClientLevel\\b", "ClientWorld", "(?<![\\w.])ClientWorld\\b", "ClientLevel")
            }
            string(eval(current.version, "= 1.8.9")) {
                replace(
                    "net.minecraft.client.particle.Particle",
                    "net.minecraft.client.entity.particle.Particle"
                )
                replace(
                    "net.minecraft.client.particle.ParticleEngine",
                    "net.minecraft.client.ParticleManager"
                )
                replace(
                    "net.minecraft.client.particle.TrackingEmitter",
                    "net.minecraft.client.entity.particle.EmitterParticle"
                )
                replace(
                    "net.minecraft.client.multiplayer.ClientLevel",
                    "net.minecraft.client.world.ClientWorld"
                )
                replace(
                    "net.minecraft.world.entity.Entity",
                    "net.minecraft.entity.Entity"
                )
                replace(
                    "net.minecraft.world.entity.LivingEntity",
                    "net.minecraft.entity.living.LivingEntity"
                )
                replace(
                    "net.minecraft.server.Bootstrap",
                    "net.minecraft.Bootstrap"
                )
                replace(
                    "import net.minecraft.core.particles.ParticleType\n",
                    "import org.polyfrost.overflowparticles.utils.ParticleType\n"
                )
                replace(
                    "import net.minecraft.core.particles.ParticleTypes\n",
                    "import net.minecraft.entity.particle.ParticleType as ParticleTypes\n"
                )
                replace(
                    "import net.minecraft.resources.ResourceLocation\n",
                    "import net.minecraft.resource.Identifier as ResourceLocation\n"
                )
                replace(
                    "import net.minecraft.world.entity.player.Player\n",
                    "import net.minecraft.entity.living.player.PlayerEntity as Player\n"
                )
                replace(
                    "import net.minecraft.network.protocol.Packet\n",
                    "import net.minecraft.network.packet.Packet\n"
                )
                replace(
                    "import net.minecraft.network.protocol.game.ClientboundEntityEventPacket\n",
                    "import net.minecraft.network.packet.s2c.play.EntityEventS2CPacket as ClientboundEntityEventPacket\n"
                )
            }
        }
    }
}
