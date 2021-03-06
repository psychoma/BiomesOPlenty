package biomesoplenty.items;

import biomesoplenty.BiomesOPlenty;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemBOPFlower extends ItemBlock
{
    private static final String[] plants = new String[] {"clover", "swampflower", "deadbloom", "glowflower", "hydrangea", "daisy", "tulip", "wildflower", "violet", "anemone", "lilyflower", "cactus", "aloe", "sunflowerbottom", "sunflowertop", "dandelion"};
    @SideOnly(Side.CLIENT)
    private Icon[] textures;    
	private static final int SUNFLOWERTOP = 14;

    public ItemBOPFlower(int par1)
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
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        textures = new Icon[1];

        textures[0] = iconRegister.registerIcon("BiomesOPlenty:item_sunflower");
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return (new StringBuilder()).append(plants[itemStack.getItemDamage()]).toString();
    }
	
    @Override
    public Icon getIconFromDamage(int meta)
    {
        if (meta == 13)
            return textures[0];
        else
            return Block.blocksList[this.itemID].getIcon(0, meta);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
    	if (par1ItemStack.getItemDamage() == 15)
    		return EnumAction.block;
    	else
    		return null;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	if (par1ItemStack.getItemDamage() == 15) {
    		double x = par3EntityPlayer.posX;
    		double y = par3EntityPlayer.posY;
    		double z = par3EntityPlayer.posZ;
    		
			for (int p = 0; p < 20; ++p)
			{
				BiomesOPlenty.proxy.spawnParticle("dandelion", x, y, z);
			};
    		
    		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
    	}
        return par1ItemStack;
    }

    
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        int id = world.getBlockId(x, y, z);

        if (id == Block.snow.blockID && (world.getBlockMetadata(x, y, z) & 7) < 1)
            side = 1;
        else if (!Block.blocksList[id].isBlockReplaceable(world, x, y, z))
        {
            if (side == 0)
                --y;

            if (side == 1)
                ++y;

            if (side == 2)
                --z;

            if (side == 3)
                ++z;

            if (side == 4)
                --x;

            if (side == 5)
                ++x;
        }
        
        if (!player.canPlayerEdit(x, y, z, side, itemStack))
        {
            return false;
        }
        else if (itemStack.stackSize == 0)
        {
            return false;
        }
        else
        {
            if (world.canPlaceEntityOnSide(this.getBlockID(), x, y, z, false, side, (Entity)null, itemStack))
            {
                Block block = Block.blocksList[this.getBlockID()];
                int j1 = block.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);

                if (world.setBlock(x, y, z, this.getBlockID(), itemStack.getItemDamage(), 3))
                {
					if (itemStack.getItemDamage() == 13 && world.getBlockMaterial(x, y + 1, z).isReplaceable())
                        world.setBlock(x, y + 1, z, this.getBlockID(), SUNFLOWERTOP, 2);
				
                    if (world.getBlockId(x, y, z) == this.getBlockID())
                    {
                        Block.blocksList[this.getBlockID()].onBlockPlacedBy(world, x, y, z, player, itemStack);
                        Block.blocksList[this.getBlockID()].onPostBlockPlaced(world, x, y, z, j1);
                    }

                    world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                    --itemStack.stackSize;
                }
            }

            return true;
        }
    }
}
