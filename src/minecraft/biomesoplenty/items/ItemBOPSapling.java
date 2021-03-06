package biomesoplenty.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemBOPSapling extends ItemBlock
{
    private static final String[] saplings = new String[] {"apple", "yellowAutumn", "bamboo", "magic", "dark", "dead", "fir", "holy", "orangeAutumn", "origin", "pinkCherry", "maple", "whiteCherry"};
    private static final int MAX = 12;
    
    public ItemBOPSapling(int par1)
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
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int meta = itemStack.getItemDamageForDisplay() > MAX ? 0 : itemStack.getItemDamageForDisplay();
        return (new StringBuilder()).append(saplings[meta]).append("Sapling").toString();
    }
    
    
    
    @Override
    public Icon getIconFromDamage(int meta)
    {
        return Block.blocksList[this.itemID].getIcon(0, meta);
    }
}
