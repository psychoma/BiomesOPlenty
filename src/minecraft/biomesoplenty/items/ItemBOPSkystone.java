package biomesoplenty.items;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBOPSkystone extends ItemBlock
{
    private static final String[] types = new String[] {"holystone", "holycobble", "holybrick"};
    
    public ItemBOPSkystone(int par1)
    {
        super(par1);
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    
    @Override
    public int getMetadata(int meta)
    {
        return meta & 15;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return types[itemstack.getItemDamage() & 15];
    }
}
