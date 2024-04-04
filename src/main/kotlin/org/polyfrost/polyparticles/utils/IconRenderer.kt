package org.polyfrost.polyparticles.utils

import cc.polyfrost.oneconfig.events.event.*
import cc.polyfrost.oneconfig.gui.OneConfigGui
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.libs.universal.UResolution
import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.*
import net.minecraft.init.*
import net.minecraft.item.*
import net.minecraft.util.*
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import org.lwjgl.opengl.GL11
import kotlin.math.*
import net.minecraft.client.renderer.GlStateManager as GL

object IconRenderer {

    data class ParticleInfo(val id: Int, val x: Float, val y: Float)

    var particleInfo = ArrayList<ParticleInfo>()

    var update = 0

    var random = 0f

    var index = 0

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
        GL.translate(x, y, 0f)
        GL.enableRescaleNormal()
        GL.enableBlend()
        GL.tryBlendFuncSeparate(770, 771, 1, 0)
        GL.color(1.0f, 1.0f, 1.0f, 1.0f)
        RenderHelper.enableGUIStandardItemLighting()
        val itemRenderer = mc.renderItem
        val item = ItemStack(when (id) {
            3 -> Items.fireworks
            30 -> Items.redstone
            1, 2 -> Item.getItemFromBlock(Blocks.tnt)
            31 -> Items.snowball
            33 -> Items.slime_ball
            15 -> Items.potionitem
            37 -> Items.diamond_pickaxe
            35 -> Item.getItemFromBlock(Blocks.barrier)
            else -> null
        }, 1)
        if (item.item != null) {
            GL.scale(56 / 16f, 56 / 16f, 1f)
            itemRenderer.renderItemAndEffectIntoGUI(item, 0, 0)
        } else if (id == 24) {
            mc.textureManager.bindTexture(ResourceLocation("textures/blocks/portal.png"))
            GL11.glColor4f(1f, 1f, 1f, 1f)
            Gui.drawScaledCustomSizeModalRect(0, 0, 0f, index * 16f, 16, 16, 56, 56, 16f, 512f)
        } else {
            mc.textureManager.bindTexture(ResourceLocation("textures/particle/particles.png"))
            val info = IconIndex.getFromID(id)
            if (info != null) {
                var u = info.x
                var v = info.y
                when (id) {
                    9, 10 -> {
                        val c = (random * 0.30000001192092896 + 0.6000000238418579).toFloat()
                        val flag = id == 10
                        GL11.glColor4f(c * if (flag) 0.3f else 1f, (c * 0.96).toFloat() * if (flag) 0.8f else 1f, (c * 0.9).toFloat(), 1f)
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

                    else -> GL11.glColor4f(1f, 1f, 1f, 1f)
                }

                Gui.drawScaledCustomSizeModalRect(0, 0, u, v, 8, 8, 56, 56, 128f, 128f)
            }
        }
        RenderHelper.disableStandardItemLighting()
        GL.disableBlend()
        GL.disableRescaleNormal()
        GL.enableAlpha()
        GL.popMatrix()
    }


    @Subscribe
    fun change(e: TickEvent) {
        if (e.stage != Stage.START) return
        index ++
        if (index > 31) index = 0
        if (update < 0) {
            var value = Math.random().toFloat()
            while (value == random) {
                value = Math.random().toFloat()
            }
            random = value
            update = 30
        }
        update--
    }

}