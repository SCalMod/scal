package scal.guns;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBullet extends ModelBase 
{
	ModelRenderer Body;

	public ModelBullet() 
	{
		Body = new ModelRenderer(this, 0, 0);
		Body.addBox(0f, 0f, 0f, 1, 1, 2);
	}

	public void render(EntityBullet entity, float f, float f1, float f2, float f3, float f4, float f5) 
	{
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		Body.render(f5);
	}
}