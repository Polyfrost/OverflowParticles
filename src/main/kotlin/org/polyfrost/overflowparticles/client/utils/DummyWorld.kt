package org.polyfrost.overflowparticles.client.utils

import net.minecraft.block.state.IBlockState
import net.minecraft.entity.passive.EntitySheep
import net.minecraft.init.Blocks
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import net.minecraft.world.WorldProviderSurface
import net.minecraft.world.chunk.IChunkProvider

//#if MC >= 1.12.2
//$$ import net.minecraft.world.chunk.ClientChunkProvider
//#else
import net.minecraft.world.gen.ChunkProviderDebug
//#endif

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object DummyWorld : World(
    null,
    null,
    WorldProviderSurface(),
    null,
    true
) {

    val SHEEP = EntitySheep(DummyWorld)

    init {
        //#if FORGE
        chunkProvider = createChunkProvider()
        //#else
        //$$ chunkProvider = chunkCache
        //#endif
    }

    override fun createChunkProvider(): IChunkProvider {
        //#if MC >= 1.12.2
        //$$ return ClientChunkProvider(this)
        //#else
        return ChunkProviderDebug(this)
        //#endif
    }

    //#if MC >= 1.12.2
    //$$ override fun isChunkLoaded(chunkX: Int, chunkZ: Int, canBeEmpty: Boolean): Boolean {
    //$$     return true
    //$$ }
    //#else
    override fun getRenderDistanceChunks(): Int {
        return 0
    }
    //#endif

    override fun getBlockState(pos: BlockPos): IBlockState {
        return Blocks.air.defaultState
    }

    //#if MC >= 1.16.5
    //$$ private object
    //#endif

}
