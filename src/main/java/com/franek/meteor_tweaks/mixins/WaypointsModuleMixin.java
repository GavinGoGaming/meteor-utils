package com.franek.meteor_tweaks.mixins;

import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.render.WaypointsModule;
import meteordevelopment.meteorclient.utils.Utils;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WaypointsModule.class,remap = false)
public class WaypointsModuleMixin {
	
	@Shadow @Final private SettingGroup sgDeathPosition;
	
	
	private Setting<Boolean> noinsingle = null;
	
	@Inject(method = "<init>", at = @At("TAIL"))
	private void addsetting(CallbackInfo ci) {
		noinsingle = sgDeathPosition.add(new BoolSetting.Builder()
				.name("no-death-in-singleplayer")
				.description("will not create death waypoint when in singleplayer")
				.defaultValue(true)
				.build()
		);
	}
	
	@Inject(method = "addDeath", at = @At("HEAD"), cancellable = true)
	private void noDeathInSingle(Vec3d deathPos, CallbackInfo ci) {
		if (noinsingle.get() && Utils.mc.isInSingleplayer()) ci.cancel();
	}
}
