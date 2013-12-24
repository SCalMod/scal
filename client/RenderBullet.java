package scal.client;

import org.lwjgl.opengl.GL11;

import scal.guns.EntityBullet;
import scal.guns.ModelBullet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

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
		if(bullet.shootingEntity == Minecraft.getMinecraft().thePlayer && bullet.ticksExisted < 1)
		{
			return;
		}
		
		this.bindEntityTexture(bullet);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d, (float) d1, (float) d2);
		GL11.glRotatef(f, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(90f - bullet.prevRotationPitch - (bullet.rotationPitch - bullet.prevRotationPitch) * f1, 1.0f, 0.0f, 0.0f);
		ModelBase model = new ModelBullet();
		model.render(bullet, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return new ResourceLocation("scal", "textures/models/ModelBullet.png");
	}
}
