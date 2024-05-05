package org.polyfrost.polyparticles.utils

import cc.polyfrost.oneconfig.events.event.*
import cc.polyfrost.oneconfig.gui.OneConfigGui
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.libs.universal.UResolution
import cc.polyfrost.oneconfig.utils.InputHandler
import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.*
import net.minecraft.entity.*
import net.minecraft.init.*
import net.minecraft.item.*
import net.minecraft.util.*
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.lwjgl.opengl.GL11
import java.util.*
import kotlin.math.*
import net.minecraft.client.renderer.GlStateManager as GL

object IconRenderer {

    data class ParticleInfo(val id: Int, val x: Float, val y: Float)

    var particleInfo = ArrayList<ParticleInfo>()

    var update = 0

    var random = 0f

    var randomNext = 0f

    var animation = 0

    var index = 0

    var rendering = false

    @SubscribeEvent
    fun draw(e: GuiScreenEvent.DrawScreenEvent.Post) {
        if (particleInfo.isEmpty()) return
        val oneConfigGui = mc.currentScreen as? OneConfigGui ?: return
        val unscaleMC = 1 / UResolution.scaleFactor
        val oneUIScale = OneConfigGui.getScaleFactor() * oneConfigGui.animationScaleFactor
        val rawX = ((UResolution.windowWidth - 1200 * oneUIScale) / 2f).toInt()
        val rawY = ((UResolution.windowHeight - 800 * oneUIScale) / 2f).toInt()
        GL.pushMatrix()
        GL11.glEnable(GL11.GL_SCISSOR_TEST)
        GL11.glScissor((rawX + 244 * oneUIScale).toInt(), rawY, (956 * oneUIScale).toInt(), (728 * oneUIScale).toInt())
        GL.scale(unscaleMC * oneUIScale, unscaleMC * oneUIScale, 1.0)
        for (i in particleInfo) {
            drawItem(i.id, i.x, i.y)
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST)
        GL.popMatrix()
        particleInfo.clear()
    }

    fun drawItem(id: Int, x: Float, y: Float) {
        GL.pushMatrix()
        GL.enableRescaleNormal()
        GL.enableBlend()
        GL.tryBlendFuncSeparate(770, 771, 1, 0)
        GL.color(1.0f, 1.0f, 1.0f, 1.0f)
        RenderHelper.enableGUIStandardItemLighting()
        val itemRenderer = mc.renderItem
        if (id == 0) {
            drawEntityPointingMouse(DummyWorld.Sheep, x.toInt() - 8, y.toInt() + 6)
        } else {
            GL.translate(x, y, 0f)
            val item = ItemStack(when (id) {
                1, 2 -> Item.getItemFromBlock(Blocks.tnt)
                3 -> Items.fireworks
                6 -> Items.fishing_rod
                8 -> Item.getItemFromBlock(Blocks.bedrock)
                13, 14, 15 -> Items.potionitem
                16 -> Item.getItemFromBlock(Blocks.beacon)
                18 -> Items.water_bucket
                19 -> Items.lava_bucket
                22 -> Item.getItemFromBlock(Blocks.mycelium)
                26 -> Item.getItemFromBlock(Blocks.torch)
                30 -> Items.redstone
                31 -> Items.snowball
                33 -> Items.slime_ball
                35 -> Item.getItemFromBlock(Blocks.barrier)
                36 -> Items.bread
                37 -> Items.diamond_pickaxe
                else -> null
            }, 1)
            if (item.item != null) {
                GL.scale(56 / 16f, 56 / 16f, 1f)
                itemRenderer.renderItemAndEffectIntoGUI(item, 0, 0)
            } else if (id == 24) {
                mc.textureManager.bindTexture(ResourceLocation("textures/blocks/portal.png"))
                GL11.glColor4f(1f, 1f, 1f, 1f)
                Gui.drawScaledCustomSizeModalRect(0, 0, 0f, animation * 16f, 16, 16, 56, 56, 16f, 512f)
            } else if (id == 28) {
                mc.textureManager.bindTexture(ResourceLocation("textures/particle/footprint.png"))
                GL11.glColor4f(1f, 1f, 1f, 0.5f)
                Gui.drawScaledCustomSizeModalRect(0, 0, 0f, 0f, 8, 8, 56, 56, 8f, 8f)
            } else {
                mc.textureManager.bindTexture(ResourceLocation("textures/particle/particles.png"))
                val info = IconIndex.getFromID(id)
                if (info != null) {
                    var u = info.x
                    var v = info.y
                    GL11.glColor4f(1f, 1f, 1f, 1f)
                    when (id) {
                        7 -> GL11.glColor4f(0.4f, 0.4f, 0.7f, 1f)

                        9, 10 -> {
                            val c = (random * 0.30000001192092896 + 0.6000000238418579).toFloat()
                            val flag = id == 10
                            GL11.glColor4f(c * if (flag) 0.3f else 1f, (c * 0.96).toFloat() * if (flag) 0.8f else 1f, (c * 0.9).toFloat(), 1f)
                        }

                        11, 12, 17, 29 -> {
                            u = (index / 2) * 8f
                            if (id == 11 || id == 12) {
                                val c = (random * 0.30000001192092896).toFloat()
                                GL11.glColor4f(c, c, c, 1f)
                            }
                            if (id == 17) {
                                val c = randomNext * 0.5f + 0.35f
                                GL11.glColor4f(c, 0f, c, 1f)
                            }
                        }

                        23 -> {
                            val r = MathHelper.sin((random.absoluteValue + 0.0f) * (Math.PI.toFloat()) * 2.0f) * 0.65f + 0.35f
                            val g = MathHelper.sin((random.absoluteValue + 0.33333334f) * (Math.PI.toFloat()) * 2.0f) * 0.65f + 0.35f
                            val b = MathHelper.sin((random.absoluteValue + 0.6666667f) * (Math.PI.toFloat()) * 2.0f) * 0.65f + 0.35f
                            GL11.glColor4f(r, g, b, 1f)
                        }

                        25 -> {
                            val index = 1 + (random.absoluteValue * 26).toInt()
                            u = (index % 16) * 8f
                            v += (index / 16) * 8f
                            GL.translate(10.5f, 0f, 0f)
                        }

                        5, 39 -> GL.translate(7f, -14f, 0f)

                        else -> GL11.glColor4f(1f, 1f, 1f, 1f)
                    }

                    Gui.drawScaledCustomSizeModalRect(0, 0, u, v, 8, 8, 56, 56, 128f, 128f)
                }
            }
        }
        RenderHelper.disableStandardItemLighting()
        GL.disableBlend()
        GL.disableRescaleNormal()
        GL.enableAlpha()
        GL.popMatrix()
    }

    private var reverse = false

    private fun drawEntityPointingMouse(
        entity: Entity,
        x: Int,
        y: Int
    ) {
        GL.enableDepth()
        GL.color(1f, 1f, 1f, 1f)
        GL.enableColorMaterial()
        GL.pushMatrix()
        GL.translate(x.toFloat(), y.toFloat(), 50f)
        GL.scale(-56f, 56f, 56f)
        GL.translate(0f, entity.height / 2, 0f)
        GL.rotate(180f, 0f, 0f, 1f)

        try {
            val dx = 0
            val dy = 0
            val tempData = (entity as? EntityLivingBase)?.let { TempData(it) }

            GL.rotate(135f, 0f, 1f, 0f)
            RenderHelper.enableStandardItemLighting()
            GL.rotate(-135f, 0f, 1f, 0f)

            (entity as? EntityLivingBase)?.apply {
                rotationYaw = atan(dx / 40f) * 40f
                rotationYawHead = rotationYaw
                renderYawOffset = rotationYaw
                rotationPitch = -atan(dy / 40f) * 20f
                riddenByEntity = this // cancel nametag
                deathTime = 7
            }

            GL.rotate(-atan(dy / 40f) * 20f, 1f, 0f, 0f)
            GL.rotate(-atan(dx / 40f) * 40f, 0f, 1f, 0f)

            with(mc.renderManager) {
                playerViewX = 0f
                playerViewY = 180f
                isRenderShadow = false
                rendering = true
                doRenderEntity(entity, 0.0, 0.0, 0.0, 0f, 1f, true)
                rendering = false
                isRenderShadow = true
            }

            tempData?.reset(entity)
        } catch (ex: Exception) {
            mc.fontRendererObj.drawCenteredString("Unable to draw entity", 0f, 0f, 0xFFFFFF)
            ex.printStackTrace()
        }

        GL.popMatrix()

        RenderHelper.disableStandardItemLighting()
        GL.disableRescaleNormal()
        GL.setActiveTexture(OpenGlHelper.lightmapTexUnit)
        GL.disableTexture2D()
        GL.setActiveTexture(OpenGlHelper.defaultTexUnit)
    }

    private fun FontRenderer.drawCenteredString(text: String, x: Float, y: Float, color: Int) =
        drawStringWithShadow(text, x - getStringWidth(text) / 2f, y, color)


    private data class TempData(
        val yaw: Float,
        val yawHead: Float,
        val yawOffset: Float,
        val pitch: Float,
        val riddenBy: Entity?,
    ) {
        constructor(entity: EntityLivingBase) : this(
            entity.rotationYaw,
            entity.rotationYawHead,
            entity.renderYawOffset,
            entity.rotationPitch,
            entity.riddenByEntity,
        )

        fun reset(entity: EntityLivingBase) {
            entity.rotationYaw = yaw
            entity.rotationYawHead = yawHead
            entity.renderYawOffset = yawOffset
            entity.rotationPitch = pitch
            entity.riddenByEntity = riddenBy
        }
    }

    @Subscribe
    fun change(e: TickEvent) {
        if (e.stage != Stage.START) return
        animation++
        if (animation > 31) animation = 0
        if (reverse) index-- else index++
        if (index <= 0) reverse = false
        if (index > 14) reverse = true
        if (update < 0) {
            var value = Math.random().toFloat()
            while (value == random) {
                value = Math.random().toFloat()
            }
            random = value
            randomNext = Random().nextFloat()
            update = 30
        }
        update--
    }

}