package biomesoplenty.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import biomesoplenty.mod_BiomesOPlenty;
import biomesoplenty.worldgen.WorldGenAutumn;
import biomesoplenty.worldgen.WorldGenAutumn2;
import biomesoplenty.worldgen.WorldGenBambooTree;
import biomesoplenty.worldgen.WorldGenBambooTree2;
import biomesoplenty.worldgen.WorldGenCherry1;
import biomesoplenty.worldgen.WorldGenCherry2;
import biomesoplenty.worldgen.WorldGenDeadTree2;
import biomesoplenty.worldgen.WorldGenMaple;
import biomesoplenty.worldgen.WorldGenMystic2;
import biomesoplenty.worldgen.WorldGenOminous1;
import biomesoplenty.worldgen.WorldGenOminous2;
import biomesoplenty.worldgen.WorldGenOriginTree;
import biomesoplenty.worldgen.WorldGenPromisedTree;
import biomesoplenty.worldgen.WorldGenTaiga9;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBOPSapling extends BlockSapling
{
    private static final String[] saplings = new String[] {"yellow", "bamboo", "magic", "dark", "brown", "fir", "holy", "orange", "origin", "pink", "red", "white"};
    @SideOnly(Side.CLIENT)
    private Icon[] textures;
    private static final int TYPES = 15;
    
    public BlockBOPSapling(int par1)
    {
        super(par1);
        setHardness(0.0F);
        setStepSound(Block.soundGrassFootstep);
        this.setCreativeTab(mod_BiomesOPlenty.tabBiomesOPlenty);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        textures = new Icon[saplings.length];
        
        for (int i = 0; i < saplings.length; ++i)
            textures[i] = iconRegister.registerIcon("BiomesOPlenty:" + saplings[i] + "sapling");

    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
    {
        if (meta < 0 || meta >= saplings.length)
            meta = 0;

        return textures[meta];
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < saplings.length; ++i)
            list.add(new ItemStack(blockID, 1, i));
    }
    
    @Override
    public void growTree(World world, int x, int y, int z, Random random)
    {
        int meta = world.getBlockMetadata(x, y, z) & TYPES;
        Object obj = null;
        int rnd = random.nextInt(8);

        if (obj == null)
        {
            switch (meta)
            {
                case 0: // Autumn Tree
                    obj = new WorldGenAutumn(false);
                    break;
                    
                case 1: // Bamboo Tree
                    rnd = random.nextInt(8);
                    
                    if (rnd == 0)
                        obj = new WorldGenBambooTree(false);
                    else
                        obj = new WorldGenBambooTree2(false);
                    break;
                    
                case 2: // Magic Tree
                    obj = new WorldGenMystic2(false);
                    break;
                    
                case 3: // Dark Tree
                    rnd = random.nextInt(8);
                    
                    if (rnd == 0)
                        obj = new WorldGenOminous2();
                    else
                        obj = new WorldGenOminous1(false);
                    break;
                    
                case 4: // Dead Tree
                    obj = new WorldGenDeadTree2(false);
                    break;
                    
                case 5: // Fir Tree
                    obj = new WorldGenTaiga9(false);
                    break;
                    
                case 6: // Holy Tree
                    obj = new WorldGenPromisedTree(false);
                    break;
                    
                case 7: // Autumn Tree
                    obj = new WorldGenAutumn2(false);
                    break;
                    
                case 8: // Origin Tree
                    obj = new WorldGenOriginTree(false);
                    break;
                    
                case 9: // Pink Cherry Tree
                    obj = new WorldGenCherry1(false);
                    break;
                    
                case 10: // Maple Tree
                    obj = new WorldGenMaple(false);
                    break;
                    
                case 11: // White Cherry Tree
                    obj = new WorldGenCherry2(false);
                    break;
            }
        }

        if (obj != null)
        {
            world.setBlockToAir(x, y, z);

            if (!((WorldGenerator)obj).generate(world, random, x, y, z))
                world.setBlock(x, y, z, this.blockID, meta, 2);
        }
    }
    
    @Override
    public int damageDropped(int meta)
    {
        return meta & TYPES;
    }
    
    @Override
    public int getDamageValue(World world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z) & TYPES;
    }
}