package com.franek.meteor_tweaks.utils;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.Item;

import static meteordevelopment.meteorclient.utils.Utils.mc;

public class MyInvUtils {
	
	public static boolean switchtoitem(FindItemResult item, boolean quickmove, boolean force, Module thismodule, Item iteminmessage) {
		if (mc.player == null) return false;
		
		if (item.found()) {
			if (item.isHotbar()) {
				InvUtils.swap(item.getSlot());
				return true;
			}else {
				FindItemResult empty = InvUtils.findEmpty();
				if (empty.found() && empty.isHotbar()) {
					if (quickmove && !(mc.currentScreen instanceof GenericContainerScreen)) InvUtils.quickMove().from(item.getSlot()).toHotbar(empty.getSlot());
					else InvUtils.move().from(item.getSlot()).toHotbar(empty.getSlot());
					InvUtils.swap(empty.getSlot());
					return true;
				}else if (force){
					InvUtils.move().from(item.getSlot()).toHotbar(mc.player.getInventory().selectedSlot);
					return true;
				}else {
					if (thismodule != null)	thismodule.info("no space in hotbar");
					return false;
				}
			}
		}else {
			if (thismodule != null) thismodule.info("no " + (iteminmessage != null ? iteminmessage : "required item") + " found");
			return false;
		}
	}
	
	
	public static boolean switchtoitem(Item item, boolean quickmove, boolean force, Module thismodule) {
		if (mc.player != null && mc.player.getMainHandStack().getItem() == item) return true;
		return switchtoitem(InvUtils.find(item), quickmove, force, thismodule, item);
	}
}
