package biomesoplenty;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.world.World;
import biomesoplenty.api.Items;
import biomesoplenty.blocks.renderers.FoliageRenderer;
import biomesoplenty.blocks.renderers.PlantsRenderer;
import biomesoplenty.items.projectiles.EntityDart;
import biomesoplenty.items.projectiles.EntityMudball;
import biomesoplenty.items.projectiles.EntityPoisonDart;
import biomesoplenty.items.projectiles.RenderDart;
import biomesoplenty.particles.EntityDandelionFX;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	  public static Minecraft mc = Minecraft.getMinecraft();

	@Override
	public void registerRenderers() 
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityMudball.class, new RenderSnowball(Items.mudball.get(), 0)); 
		RenderingRegistry.registerEntityRenderingHandler(EntityDart.class, new RenderDart(0)); 
		RenderingRegistry.registerEntityRenderingHandler(EntityPoisonDart.class, new RenderDart(1)); 
		
		RenderingRegistry.registerBlockHandler(new FoliageRenderer());
		RenderingRegistry.registerBlockHandler(new PlantsRenderer());
	}
	
	@Override
	public void spawnParticle(String string, double x, double y, double z)
	{
        EntityFX entityfx = null;
        
         if (string == "mud")
             entityfx = new EntityBreakingFX(mc.theWorld, x, y, z, Items.mudball.get(), mc.renderEngine);
         else if (string == "dart")
             entityfx = new EntityBreakingFX(mc.theWorld, x, y, z, Items.dart.get(), mc.renderEngine);
         else if (string == "dandelion")
             entityfx = new EntityDandelionFX(mc.theWorld, x, y, z, 1.0F);
         
 		mc.effectRenderer.addEffect(entityfx);
	} 

	@Override
	public int addArmor(String armor)
	{
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}
}