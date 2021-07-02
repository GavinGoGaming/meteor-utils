package com.franek.meteor_tweaks.mixins;

import com.franek.meteor_tweaks.modules.NoPortalHitbox;
import minegame159.meteorclient.systems.modules.Modules;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetherPortalBlock.class)
public abstract class NoPortalHitboxMixin {
	@Inject(method = "getOutlineShape",at = @At("HEAD"), cancellable = true)
	private void getshape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir){
		if (Modules.get().isActive(NoPortalHitbox.class)){
			cir.setReturnValue(VoxelShapes.empty());
		}
	}
}
