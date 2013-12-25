package scal.client;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import scal.guns.EntityBullet;
import scal.guns.ModelBullet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderBullet extends Render
{
	public RenderBullet()
	{
		this.shadowSize = 0.5f;
	}
	
	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) 
	{
		this.render((EntityBullet)entity, d0, d1, d2, f, f1);
	}
	
	public void render(EntityBullet bullet, double d, double d1, double d2, float f, float f1)
	{
		if(bullet.shootingEntity == Minecraft.getMinecraft().thePlayer && bullet.ticksExisted < 5)
		{
			return;
		}
		
		this.bindEntityTexture(bullet);
		ResourceLocation locationTest = new ResourceLocation("scal:textures/models/ModelBullet.png");
		System.out.println(locationTest == null ? "Location is null" : "Location exists");
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef(f, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(90f - bullet.prevRotationPitch - (bullet.rotationPitch - bullet.prevRotationPitch) * f1, 1.0f, 0.0f, 0.0f);
		ModelBase model = new ModelBullet();
		model.render(bullet, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
		GL11.glPopMatrix();
	}

	protected ResourceLocation getBulletTextures(EntityBullet entityBullet)
    {
        return new ResourceLocation("scal", "textures/models/ModelBullet.png");
    }
	
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.getBulletTextures((EntityBullet)par1Entity);
    }
}
