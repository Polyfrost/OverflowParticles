package org.polyfrost.overflowparticles.utils

import net.minecraft.block.state.IBlockState
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.init.Blocks
import net.minecraft.util.BlockPos
import net.minecraft.world.*
import net.minecraft.world.gen.ChunkProviderDebug

object DummyWorld : World(null, null, WorldProviderSurface(), null, true) {
    val SHEEP = EntitySheep(DummyWorld)

    init {
        chunkProvider = createChunkProvider()
    }

    override fun createChunkProvider() = ChunkProviderDebug(this)
    override fun getRenderDistanceChunks() = 0
    override fun getBlockState(pos: BlockPos?): IBlockState = Blocks.air.defaultState

}