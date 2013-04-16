package biomesoplenty.helpers;

import biomesoplenty.mod_BiomesOPlenty;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FurnaceFuel implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		return mod_BiomesOPlenty.addFuel(fuel.itemID, fuel.getItemDamage());
	}
}