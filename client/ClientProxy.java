package scal.client;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import scal.common.CommonProxy;
import scal.common.SCal;
import scal.common.VariableHandler;
import scal.guns.EntityBullet;
import scal.guns.ItemGun;

public class ClientProxy extends CommonProxy
{
	@Override
	public void RegisterRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet());
		//RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderSnowball(SCal.m9));
	}

	@Override
	public void registerSounds()
	{

	}

	@Override
	public void renderMarker()
	{
		Minecraft client = FMLClientHandler.instance().getClient();

		if(VariableHandler.HitMarkerTimer > 0)
		{
			ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, client.entityRenderer, VariableHandler.ZoomLevel, "cameraZoom", "feild_78503_V");
			ScaledResolution scale = new ScaledResolution(client.gameSettings, client.displayWidth, client.displayHeight);
			int i = scale.getScaledWidth();
			int j = scale.getScaledHeight();
			client.renderEngine.bindTexture(new ResourceLocation("scal", "textures/overlays/HitMarker.png"));
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(i / 2 - 2 * j, j, -90d, 0.0d, 1.0d);
			tessellator.addVertexWithUV(i / 2 + 2 * j, j, -90d, 1.0d, 1.0d);
			tessellator.addVertexWithUV(i / 2 + 2 * j, 0.0d, -90d, 1.0d, 0.0d);
			tessellator.addVertexWithUV(i / 2 - 2 * j, 0.0d, -90d, 0.0d, 0.0d);
			tessellator.draw();
		}
	}

	@Override
	public void renderSight()
	{
		Minecraft client = FMLClientHandler.instance().getClient();

		if(client.thePlayer != null)
		{
			InventoryPlayer inventory = client.thePlayer.inventory;

			if(inventory.getCurrentItem() != null && inventory.getCurrentItem().getItem() instanceof ItemGun && client.currentScreen == null)
			{
				ItemGun gun = (ItemGun)inventory.getCurrentItem().getItem();
				
				if(Mouse.isButtonDown(0) && VariableHandler.ReloadInterval <= 0)
				{
					float newZoom = gun.Type.SightZoom;
					if(VariableHandler.ZoomLevel <= newZoom)
					{
						if(VariableHandler.ZoomLevel + 0.8f <= newZoom)
						{
							VariableHandler.ZoomLevel += 0.8f;
						}
						else
						{
							VariableHandler.ZoomLevel = newZoom;
						}
					}
					if(VariableHandler.ZoomLevel > newZoom)
					{
						if(VariableHandler.ZoomLevel + 0.8f >= newZoom)
						{
							VariableHandler.ZoomLevel -= 0.8f;
						}
						else
						{
							VariableHandler.ZoomLevel = newZoom;
						}
					}

					ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, client.entityRenderer, VariableHandler.ZoomLevel, "cameraZoom", "feild_78503_V");
					ScaledResolution scale = new ScaledResolution(client.gameSettings, client.displayWidth, client.displayHeight);
					int i = scale.getScaledWidth();
					int j = scale.getScaledHeight();
					client.renderEngine.bindTexture(new ResourceLocation("scal", "textures/overlays/" + gun.Type.ScopePath + ".png"));
					Tessellator tessellator = Tessellator.instance;
					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(i / 2 - 2 * j, j, -90d, 0.0d, 1.0d);
					tessellator.addVertexWithUV(i / 2 + 2 * j, j, -90d, 1.0d, 1.0d);
					tessellator.addVertexWithUV(i / 2 + 2 * j, 0.0d, -90d, 1.0d, 0.0d);
					tessellator.addVertexWithUV(i / 2 - 2 * j, 0.0d, -90d, 0.0d, 0.0d);
					tessellator.draw();

					VariableHandler.IsScoped = true;
				}
				else
				{
					if (VariableHandler.ZoomLevel >= 1.0)
					{
						VariableHandler.ZoomLevel -= 0.8;
					}
					if (VariableHandler.ZoomLevel < 1.0)
					{
						VariableHandler.ZoomLevel = 1.0F;
					}

					ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, client.entityRenderer, VariableHandler.ZoomLevel, "cameraZoom", "field_78503_V");
					ScaledResolution scale = new ScaledResolution(client.gameSettings, client.displayWidth, client.displayHeight);
					int i = scale.getScaledWidth();
					int j = scale.getScaledHeight();
					client.renderEngine.bindTexture(new ResourceLocation("scal", "textures/overlays/Reticle.png"));
					Tessellator tessellator = Tessellator.instance;
					
					//Top
					
					tessellator.startDrawingQuads();
					//Top Left
					tessellator.addVertexWithUV(i / 2 - 4, j / 2 - ((gun.Type.AccuracyHip) * (client.displayHeight / 75)) - (client.displayHeight / 40), -90d, 0.0d, 1.0d);
					//Top Right
					tessellator.addVertexWithUV(i / 2 + 4, j / 2 - ((gun.Type.AccuracyHip) * (client.displayHeight / 75)) - (client.displayHeight / 40), -90d, 0.1d, 1.0d);
					/*/Bottom Right
					tessellator.addVertexWithUV(i / 2 + 4, j / 2 - ((gun.Type.AccuracyHip) * (j / 26)), -90d, 0.1d, 0.0d);
					//Bottom Left
					tessellator.addVertexWithUV(i / 2 - 4, j / 2 - ((gun.Type.AccuracyHip) * (j / 26)), -90d, 0.0d, 0.0d);*/
					tessellator.addVertexWithUV(i / 2 + 2 * j, 0.0d, -90d, 1.0d, 0.0d);
					tessellator.addVertexWithUV(i / 2 - 2 * j, 0.0d, -90d, 0.0d, 0.0d);
					tessellator.draw();

					VariableHandler.IsScoped = false;
				}
			}
		}
	}

	@Override
	public void updateRecoil()
	{
		Minecraft client = FMLClientHandler.instance().getClient();

		if(client.thePlayer != null)
		{
			client.thePlayer.rotationPitch -= VariableHandler.RecoilLevel;
			client.thePlayer.rotationPitch += VariableHandler.AntiRecoil * 0.2f;
			VariableHandler.AntiRecoil *= 0.8f;
		}
	}

	@Override
	public void renderGUI()
	{

	}
}
